package uz.sb.messageservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.sb.messageservice.config.FeignConfig;
import uz.sb.messageservice.domain.dto.response.UserResponse;

@FeignClient(name = "AUTH-SERVICE", configuration = FeignConfig.class)
public interface AuthServiceClient {


    @GetMapping("/api/auth/find-by-id/{id}")
    UserResponse findById(@PathVariable("id") Long id);
}
