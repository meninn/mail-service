package com.example.MailService.services;

import com.example.MailService.config.ObjectStorageClientConfig;
import com.oracle.bmc.objectstorage.requests.GetObjectRequest;
import com.oracle.bmc.objectstorage.responses.GetObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class FileObjectStorageService {
    String bucketName = "analysis-reports";
    String nameSpace = "grggrklgovlo";

    @Autowired
    private ObjectStorageClientConfig objectStorageClientConfig;

    public String getReportFileContent(String filename) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .namespaceName(nameSpace)
                .bucketName(bucketName)
                .objectName(filename)
                .build();

        try {
            GetObjectResponse objectResponse = objectStorageClientConfig.getObjectStorage().getObject(objectRequest);
            InputStream inputStream = objectResponse.getInputStream();

            return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining());
        } catch (Exception error) {
            error.printStackTrace();
        }

        return "";
    }
}
