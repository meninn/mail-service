package com.example.MailService.services;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Locale;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.from}")
    private String from;

    public void sendReport(String content, String to) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);

            Calendar calendar = Calendar.getInstance();
            String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
            String year = String.valueOf(calendar.get(Calendar.YEAR));

            messageHelper.setFrom(from);
            messageHelper.setText(content, true);
            messageHelper.setSubject(String.format("Analysis Report - %s %s", monthName, year));
            messageHelper.setTo(to);

            javaMailSender.send(message);
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
