package org.group4.comp231.inventorymanagementservice.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Service
public class AWSS3Service {

    private static final Log log = LogFactory.getLog(KeycloakClientService.class);
    private final AmazonS3 s3client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public AWSS3Service(AmazonS3 s3client) {
        this.s3client = s3client;
    }

    public String uploadFile(String keyName, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getBytes().length);
            metadata.setContentType(file.getContentType());
            String objectName = Date.from(Instant.now()) + UUID.randomUUID().toString().replace("-", "");
            s3client.putObject(bucketName, objectName, file.getInputStream(), metadata);
            return String.valueOf(s3client.getUrl(bucketName, objectName));
        } catch (Exception e) {
            return "";
        }
    }
}
