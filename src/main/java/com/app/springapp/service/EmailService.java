package com.app.springapp.service;

public interface EmailService {
    public void sendNotificationEmail(String to, String subject, String content);
}