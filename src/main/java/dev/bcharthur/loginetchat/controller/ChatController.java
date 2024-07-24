package dev.bcharthur.loginetchat.controller;

import dev.bcharthur.loginetchat.model.Chat;
import dev.bcharthur.loginetchat.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {
    @GetMapping("/chat")
    public String chat(Model model, Principal principal) {

        return "chat/chat";
    }
}

@RestController
@RequestMapping("/api/chats")
class ChatAPIController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/list")
    public List<Chat> listAllChats() {
        return chatService.listAll();
    }

    @PostMapping("/send")
    public Chat sendMessage(@RequestBody Chat chat) {
        return chatService.save(chat);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/chats")
    public Chat broadcastMessage(Chat chat) {
        return chatService.save(chat);
    }

    @PostMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Chat updateMessage(@PathVariable Long id, @RequestBody String newMessage) {
        return chatService.updateMessage(id, newMessage);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        chatService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/hide/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Chat hideMessage(@PathVariable Long id) {
        return chatService.hideMessage(id);
    }
}
