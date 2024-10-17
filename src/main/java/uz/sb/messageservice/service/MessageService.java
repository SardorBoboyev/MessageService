package uz.sb.messageservice.service;

import uz.sb.messageservice.domain.dto.request.MessageRequest;
import uz.sb.messageservice.domain.dto.request.UpdateMessageRequest;
import uz.sb.messageservice.domain.entity.MessageEntity;

import java.util.List;

public interface MessageService {

    MessageEntity save(MessageRequest request);

    MessageEntity findById(Long messageId);

    List<MessageEntity> findAllByChatID(Long chatID);

    void deleteById(Long messageId);

    MessageEntity update(UpdateMessageRequest request);
}
