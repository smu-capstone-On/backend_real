
package graduation.petshop.domain.chat.dto.response;
import graduation.petshop.domain.chat.entity.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageResponseDto {
    private Long id;
    private String senderNickName;
    private String message;
    private String timestamp;

    public ChatMessageResponseDto(ChatMessage entity) {
        this.id = entity.getId();
        this.senderNickName = entity.getSender().getNickName(); // 보내는 사람의 닉네임을 가져옵니다.
        this.message = entity.getMessage(); // 메시지 내용
        this.timestamp = entity.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")); // 타임스탬프 형식으로 변환하여 저장합니다.
    }

    @Override //테스트용도//
    public String toString() {
        return "ChatMessageResponseDto{" +
                "id=" + id +
                ", senderNickName='" + senderNickName + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

}
