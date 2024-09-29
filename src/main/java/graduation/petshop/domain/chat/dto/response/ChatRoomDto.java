
package graduation.petshop.domain.chat.dto.response;

import graduation.petshop.domain.chat.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChatRoomDto {
    private Long id;
    private Long userId;
    private String userNickname;

    public ChatRoomDto(ChatRoom entity, Long currentUserId) {
        this.id = entity.getId();
        if (entity.getUser1().getId().equals(currentUserId)) {
            this.userId = entity.getUser2().getId();
            this.userNickname = entity.getUser2().getNickName();
        } else {
            this.userId = entity.getUser1().getId();
            this.userNickname = entity.getUser1().getNickName();

        }
    }
}
