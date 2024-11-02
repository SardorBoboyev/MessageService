package uz.sb.messageservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sb.messageservice.domain.entity.MessageEntity;

import java.util.List;

public interface MessageRepository extends JpaRepository<MessageEntity, Long> {

    List<MessageEntity> findAllByChatId(Long chatId);

    MessageEntity findTopByChatIdOrderByCreatedAtDesc(Long chatId);
}
