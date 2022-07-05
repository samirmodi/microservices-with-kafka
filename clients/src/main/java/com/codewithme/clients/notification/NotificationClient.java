package com.codewithme.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("notification")
public interface NotificationClient {
    @PostMapping("api/v1/notifications")
    public void sendNotification(NotificationRequest request);
}
