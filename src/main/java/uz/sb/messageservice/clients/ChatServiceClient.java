package uz.sb.messageservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.sb.domain.dto.response.ChatServiceResponse;
import uz.sb.messageservice.config.FeignConfig;



@FeignClient(name = "CHAT-SERVICE", configuration = FeignConfig.class)
public interface ChatServiceClient {


    @GetMapping("/api/chat/find-by-id/{id}")
    public ChatServiceResponse findById(@PathVariable long id);
}
