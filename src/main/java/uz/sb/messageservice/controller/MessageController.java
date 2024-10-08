package uz.sb.messageservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sb.domain.dto.request.MessageRequest;
import uz.sb.domain.dto.request.UpdateMessageRequest;
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
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
        messageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update")
    public MessageEntity update(@RequestBody UpdateMessageRequest updateMessageRequest) {
        return messageService.update(updateMessageRequest);
    }
}
