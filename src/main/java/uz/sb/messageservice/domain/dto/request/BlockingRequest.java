package uz.sb.messageservice.domain.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class BlockingRequest {
    private Long blockerUserId;
    private Long blockedUserId;
}
