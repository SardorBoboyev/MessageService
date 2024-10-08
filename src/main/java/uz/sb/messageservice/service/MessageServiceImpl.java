package uz.sb.messageservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.sb.domain.dto.request.MessageRequest;
import uz.sb.domain.dto.request.UpdateMessageRequest;
import uz.sb.domain.dto.response.ChatServiceResponse;
import uz.sb.messageservice.clients.AuthServiceClient;
import uz.sb.messageservice.clients.BlockingServiceClient;
import uz.sb.messageservice.clients.ChatServiceClient;
import uz.sb.messageservice.domain.entity.MessageEntity;
import uz.sb.messageservice.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatServiceClient chatServiceClient;
    private final AuthServiceClient authServiceClient;
    private final BlockingServiceClient blockingServiceClient;

    @Override
    public MessageEntity save(MessageRequest request) {
        Long chatId = request.getChatId();
        Long senderId = request.getSenderId();

        ChatServiceResponse chat = chatServiceClient.findById(chatId);

        if (Objects.isNull(chat) ||
                Objects.isNull(authServiceClient.findById(senderId))) {
            throw new RuntimeException("chat or user not found");
        }

        boolean isUser1 = chat.getUser1Id().equals(senderId);
        boolean isUser2 = chat.getUser2Id().equals(senderId);

        if ((isUser1 && blockingServiceClient.isBlocked(chat.getUser2Id(), senderId))
                || (isUser2 && blockingServiceClient.isBlocked(chat.getUser1Id(), senderId))) {
            throw new RuntimeException("You are blocked, so you cannot write a message");
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
