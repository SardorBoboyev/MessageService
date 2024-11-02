package uz.sb.messageservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.sb.messageservice.clients.AuthServiceClient;
import uz.sb.messageservice.clients.ChatServiceClient;
import uz.sb.messageservice.domain.dto.request.MessageRequest;
import uz.sb.messageservice.domain.dto.request.UpdateMessageRequest;
import uz.sb.messageservice.domain.dto.response.ChatServiceResponse;
import uz.sb.messageservice.domain.entity.MessageEntity;
import uz.sb.messageservice.exception.DataNotFoundException;
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

    @Override
    public MessageEntity save(MessageRequest request) {
        Long chatId = request.getChatId();
        Long senderId = request.getSenderId();

        ChatServiceResponse chat = chatServiceClient.findById(chatId);

        if (Objects.isNull(chat) ||
                Objects.isNull(authServiceClient.findById(senderId))) {
            throw new DataNotFoundException("chat or user not found");
        }

        boolean isUser1 = chat.getUser1Id().equals(senderId); //false
        boolean isUser2 = chat.getUser2Id().equals(senderId); //true

        if (!(((!isUser1) && isUser2) || (isUser1 && !isUser2))) {
            throw new DataNotFoundException("this user is not present in this chat");
        }

        if ((isUser1 && chat.isDeletedByUser1()) || (isUser2 && chat.isDeletedByUser2())) {
            throw new DataNotFoundException("You cannot write a message because the chat is deleted");
        }

        if ((isUser1 && authServiceClient.isBlocked(chat.getUser2Id(), senderId))
                || (isUser2 && authServiceClient.isBlocked(chat.getUser1Id(), senderId))) {
            throw new DataNotFoundException("You are blocked, so you cannot write a message");
        }

        return messageRepository.save(MessageEntity.builder()
                .text(request.getMessage())
                .chatId(chatId)
                .senderId(senderId)
                .build());
    }


    @Override
    public MessageEntity findById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new DataNotFoundException("message not found"));
    }

    @Override
    public List<MessageEntity> findAllByChatID(Long chatID) {
        return messageRepository.findAllByChatId(chatID);
    }

    @Override
    public void deleteById(Long messageId) {
        MessageEntity byId = findById(messageId);
        messageRepository.delete(byId);
    }

    @Override
    public MessageEntity update(UpdateMessageRequest request) {
        MessageEntity byId = findById(request.getMessageId());
        byId.setText(request.getText());
        byId.setUpdatedAt(LocalDateTime.now());
        return messageRepository.save(byId);
    }

    @Override
    public MessageEntity findLastMessage(Long chatID) {
        ChatServiceResponse byId = chatServiceClient.findById(chatID);
        if (Objects.isNull(byId)) {
            throw new DataNotFoundException("chat not found");
        }
        MessageEntity message = messageRepository.findTopByChatIdOrderByCreatedAtDesc(chatID);
        if (Objects.isNull(message)) {
            throw new DataNotFoundException("no message");
        }
        return message;
    }


}
