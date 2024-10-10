package uz.sb.messageservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import uz.sb.domain.dto.response.UserResponse;
import uz.sb.messageservice.config.FeignConfig;


@FeignClient(name = "AUTH-SERVICE", configuration = FeignConfig.class)
public interface AuthServiceClient {


    @GetMapping("/api/auth/find-by-id/{id}")
    UserResponse findById(@PathVariable("id") Long id);

    @GetMapping("/api/block/isBlocked/{blockerId}/{blockedId}")
    public boolean isBlocked(@PathVariable Long blockerId, @PathVariable Long blockedId);
}
