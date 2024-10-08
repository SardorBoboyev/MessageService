package uz.sb.messageservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import uz.sb.messageservice.config.FeignConfig;
import uz.sb.messageservice.domain.dto.request.BlockingRequest;

@FeignClient(name = "AUTH-SERVICE", configuration = FeignConfig.class)
public interface BlockingServiceClient {

    @GetMapping("/api/block/isBlocked/{blockerId}/{blockedId}")
    public boolean isBlocked(@PathVariable Long blockerId, @PathVariable Long blockedId);
}
