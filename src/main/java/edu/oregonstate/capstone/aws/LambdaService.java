package edu.oregonstate.capstone.aws;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ServiceException;
import edu.oregonstate.capstone.entities.Experience;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
public class LambdaService {

    public void invoke(Experience experience) {
        String functionName = "capstone-location";

        String address = getAddress(experience);

        InvokeRequest invokeRequest = new InvokeRequest()
                .withFunctionName(functionName)
                .withPayload("{\n" +
                        " \"id\": \"" + experience.getId() + "\",\n" +
                        " \"address\": \"" + address + "\"\n" +
                        "}");

        InvokeResult invokeResult = null;

        try {
            AWSLambda awsLambda = AWSLambdaClientBuilder.standard()
                    .withRegion(Regions.US_WEST_2).build();

            invokeResult = awsLambda.invoke(invokeRequest);

            String ans = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);
            System.out.println(ans);
        } catch (ServiceException e) {
            System.out.println(e.getMessage());
        }

        assert invokeResult != null;
        System.out.println(invokeResult.getStatusCode());
    }

    private String getAddress(Experience experience) {

        StringBuilder sb = new StringBuilder();
        sb.append(experience.getCity());

        if (!Objects.equals(experience.getState(), "")) {
            sb.append(", ");
            sb.append(experience.getState());
        }

        if (!Objects.equals(experience.getCountry(), "")) {
            sb.append(", ");
            sb.append(experience.getCountry());
        }

        return sb.toString();
    }
}

