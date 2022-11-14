package edu.oregonstate.capstone.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.oregonstate.capstone.entities.Experience;
import edu.oregonstate.capstone.models.LocationMessage;
import edu.oregonstate.capstone.services.ExperienceService;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SqsListener implements Runnable {

    public static final int MAX_MESSAGES = 10;
    public static final int DEFAULT_VISIBILITY_TIMEOUT = 15;
    public static final int WAIT_TIME = 20; // value < 0 = long polling, which reduces SQS cost
    public static final int PROCESSORS = 2;

    ExecutorService executor;
    ExperienceService experienceService;
    private final String queueUrl = "https://sqs.us-west-2.amazonaws.com/489967615225/capstone-queue";
    private final AmazonSQS amazonSqs;
    ArrayBlockingQueue<Message> messageHoldingQueue;

    public SqsListener(ExperienceService experienceService) {
        this.amazonSqs = getSQSClient();
        messageHoldingQueue = new ArrayBlockingQueue<>(PROCESSORS);
        //process more than 1 messages at a time.
        executor = Executors.newFixedThreadPool(PROCESSORS);
        this.experienceService = experienceService;
    }

    @Override
    public void run() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest()
                .withQueueUrl(queueUrl)
                .withMaxNumberOfMessages(MAX_MESSAGES)
                .withVisibilityTimeout(DEFAULT_VISIBILITY_TIMEOUT)
                .withWaitTimeSeconds(WAIT_TIME);

        while (true) {
            try {
                List<Message> messages = amazonSqs.receiveMessage(receiveMessageRequest).getMessages();
                if (messages == null || messages.size() == 0) {
                    // If there were no messages during this poll period, SQS
                    // will return this list as null. Continue polling.
                    continue;
                }

                for (Message message : messages) {
                    try {
                        //will wait here till the queue has free space to add new messages. Read documentation
                        messageHoldingQueue.put(message);
                    } catch (InterruptedException e) {

                    }
                    Runnable run = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Message sqsMessage = messageHoldingQueue.poll();
                                if (sqsMessage != null && sqsMessage.getBody() != null) {
                                    processMessage(sqsMessage.getBody());
                                    amazonSqs.deleteMessage(queueUrl, sqsMessage.getReceiptHandle());
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    executor.execute(run);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Make this singleton
    static AmazonSQS getSQSClient(){
        return AmazonSQSClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    private void processMessage(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        LocationMessage locationMsg = mapper.readValue(message, LocationMessage.class);
        System.out.println("Message received: " + locationMsg.toString());
        Experience experience = experienceService.findById(locationMsg.getExperienceId());
        experience.setLatitude(locationMsg.getLatitude());
        experience.setLongitude(locationMsg.getLongitude());
        experienceService.save(experience);
        System.out.println("Experience " + experience.getTitle() + " location coordinates saved");
    }
}
