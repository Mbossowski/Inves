package com.example.Inves.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 09/12/2024 - 23:14
 */
@RestController
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Method to send stock updates
    public void sendStockUpdate(String stockUpdate) {
        messagingTemplate.convertAndSend("/topic/stocks", stockUpdate); // Broadcast to all subscribers
    }
}
