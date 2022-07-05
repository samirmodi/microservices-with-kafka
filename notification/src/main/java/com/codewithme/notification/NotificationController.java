package com.codewithme.notification;

import com.codewithme.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@Slf4j
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @PostMapping()
    public void sendNotification(@RequestBody NotificationRequest notificationRequest) {
      log.info("Saving notification for customer {}", notificationRequest);
      notificationService.sendNotification(notificationRequest);
    }
}
