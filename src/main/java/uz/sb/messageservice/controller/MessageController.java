package uz.sb.messageservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sb.messageservice.domain.dto.request.MessageRequest;
import uz.sb.messageservice.domain.dto.request.UpdateMessageRequest;
import uz.sb.messageservice.domain.entity.MessageEntity;
import uz.sb.messageservice.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/create")
    public MessageEntity create(@RequestBody MessageRequest messageRequest) {
        return messageService.save(messageRequest);
    }

    @GetMapping("/find-all-by-chat-id/{chatId}")
    public List<MessageEntity> findAllByChatId(@PathVariable Long chatId) {
        return messageService.findAllByChatID(chatId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long id) {
        messageService.deleteById(id);
        return ResponseEntity.ok("Message deleted successfully");
    }

    @PatchMapping("/update")
    public MessageEntity update(@RequestBody UpdateMessageRequest updateMessageRequest) {
        return messageService.update(updateMessageRequest);
    }

    @GetMapping("/get-last-message/{chatId}")
    public ResponseEntity<MessageEntity> getLastMessage(@PathVariable Long chatId) {
        MessageEntity lastMessage = messageService.findLastMessage(chatId);
        return ResponseEntity.ok(lastMessage);
    }
}
