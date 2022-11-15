package edu.oregonstate.capstone.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import edu.oregonstate.capstone.models.Secret;
import edu.oregonstate.capstone.security.SecretManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class AmazonS3Service {

    private AmazonS3 s3client;

    @Value("https://capstone-osu.s3-us-west-2.amazonaws.com")
    private String endpointUrl;

    @Value("capstone-osu")
    private String bucketName;

    public String uploadFile(MultipartFile multipartFile, Long id) throws IOException {
        try {
            File file = convertMultiPartToFile(multipartFile);
            String objectKey = id.toString() + "/" + multipartFile.getOriginalFilename().replace(" ", "_");

            s3client.putObject(new PutObjectRequest(bucketName, objectKey, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            file.delete();

            return endpointUrl + "/" + objectKey;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @PostConstruct
    private void initializeAmazon() {
        Secret secret = getSecret();

        if (secret == null) {
            return;
        }

        AWSCredentials credentials = new BasicAWSCredentials(secret.getAccessKey(), secret.getSecretKey());
        this.s3client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_WEST_2)
                .build();
    }

    private Secret getSecret() {
        try {
            return SecretManager.getSecret();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
