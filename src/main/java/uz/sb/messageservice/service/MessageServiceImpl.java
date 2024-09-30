package uz.sb.messageservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.sb.messageservice.clients.AuthServiceClient;
import uz.sb.messageservice.clients.ChatServiceClient;
import uz.sb.messageservice.domain.dto.request.MessageRequest;
import uz.sb.messageservice.domain.dto.request.UpdateMessageRequest;
import uz.sb.messageservice.domain.entity.MessageEntity;
import uz.sb.messageservice.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatServiceClient chatServiceClient;
    private final AuthServiceClient authServiceClient;

    @Override
    public MessageEntity save(MessageRequest request) {
        Long chatId = request.getChatId();
        Long senderId = request.getSenderId();

        if (Objects.isNull(chatServiceClient.findById(chatId)) ||
                Objects.isNull(authServiceClient.findById(senderId))) {
            throw new RuntimeException("chat or user not found");
        }

        return messageRepository.save(MessageEntity.builder()
                .text(request.getMessage())
                .chatId(chatId)
                .senderId(senderId)
                .build());
    }

    @Override
    public MessageEntity findById(Long messageId) {
        return messageRepository.findById(messageId).orElseThrow(() -> new RuntimeException("message not found"));
    }

    @Override
    public List<MessageEntity> findAllByChatID(Long chatID) {
        return messageRepository.findAllByChatId(chatID);
    }

    @Override
    public void deleteById(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    @Override
    public MessageEntity update(UpdateMessageRequest request) {
        MessageEntity byId = findById(request.getMessageId());
        byId.setText(request.getText());
        byId.setUpdatedAt(LocalDateTime.now());
        return messageRepository.save(byId);
    }


}
