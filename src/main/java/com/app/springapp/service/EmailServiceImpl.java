package com.app.springapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendNotificationEmail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(buildEmailHtml(subject, content), true);  // HTML 형식
            mailSender.send(message);
            log.info("이메일 전송 완료 - to: {}", to);
        } catch (Exception e) {
            log.error("이메일 전송 실패 - to: {}, error: {}", to, e.getMessage());
        }
    }

    private String buildEmailHtml(String subject, String content) {
        return """
                <div style="font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto; padding: 20px;">
                    <div style="background-color: #7b5ea7; padding: 20px; border-radius: 8px 8px 0 0;">
                        <h1 style="color: white; margin: 0; font-size: 24px;">이음</h1>
                    </div>
                    <div style="background-color: #f9f9f9; padding: 30px; border-radius: 0 0 8px 8px;">
                        <h2 style="color: #333;">%s</h2>
                        <p style="color: #666; font-size: 16px; line-height: 1.6;">%s</p>
                        <a href="http://localhost:3000" 
                           style="display: inline-block; background-color: #7b5ea7; color: white; 
                                  padding: 12px 24px; border-radius: 24px; text-decoration: none; margin-top: 20px;">
                            이음 바로가기
                        </a>
                    </div>
                </div>
                """.formatted(subject, content);
    }
}