package com.meninn.MailService.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ScheduleReportService {
    @Autowired
    private FileObjectStorageService fileObjectStorageService;

    @Autowired
    private EmailService emailService;

    private List<String> emailList = Arrays.asList("gabrielm.boff@gmail.com");

    private final Integer SEVEN_DAYS_IN_MILLISECONDS = 604800000;

    @Scheduled(fixedRate = 30000)
    public void sendReport() {
        try {
            String report = fileObjectStorageService.getReportFileContent("report.html");

            for (String email : emailList) {
                emailService.sendReport(report, email);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
