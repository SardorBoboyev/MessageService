package uz.sb.messageservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChatServiceResponse extends BaseResponse {
    Long user1Id;
    Long user2Id;
}
