package uz.sb.messageservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.sb.messageservice.config.FeignConfig;
import uz.sb.messageservice.domain.dto.response.ChatServiceResponse;

@FeignClient(name = "CHAT-SERVICE", configuration = FeignConfig.class)
public interface ChatServiceClient {

    @GetMapping("/api/chat/find-by-id/{id}")
    ChatServiceResponse findById(@PathVariable("id") long id);

}
