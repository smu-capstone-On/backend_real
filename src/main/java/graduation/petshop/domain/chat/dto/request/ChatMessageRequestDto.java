package graduation.petshop.domain.chat.dto.request;

import graduation.petshop.domain.chat.entity.ChatMessage;
import graduation.petshop.domain.chat.entity.ChatRoom;
import graduation.petshop.domain.profile.entity.Profile;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageRequestDto {
    private String message;
    private Long senderId;
    private Long recipientId;

    @Builder
    public ChatMessageRequestDto(String message, Long senderId, Long recipientId) {
        this.message = message;
        this.senderId = senderId;
        this.recipientId = recipientId;
    }

    public ChatMessage toEntity(Profile sender, Profile recipient, ChatRoom chatRoom) {
        return ChatMessage.builder()
                .message(this.message)
                .sender(sender)
                .recipient(recipient)
                .chatRoom(chatRoom)
                .timestamp(LocalDateTime.now())
                .build();
    }
}