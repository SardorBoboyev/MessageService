package uz.sb.messageservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.sb.domain.dto.response.ChatServiceResponse;


@FeignClient(name = "CHAT-SERVICE", configuration = FeignClientsConfiguration.class)
public interface ChatServiceClient {


    @GetMapping("/find-by-id/{id}")
    public ChatServiceResponse findById(@PathVariable long id);
}
