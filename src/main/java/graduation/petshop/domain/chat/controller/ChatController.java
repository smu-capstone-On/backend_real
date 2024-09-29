
package graduation.petshop.domain.chat.controller;

import graduation.petshop.domain.chat.dto.request.ChatMessageRequestDto;
import graduation.petshop.domain.chat.dto.response.ChatMessageResponseDto;
import graduation.petshop.domain.chat.dto.response.ChatRoomDto;
import graduation.petshop.domain.chat.entity.ChatRoom;
import graduation.petshop.domain.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send/{roomId}")
    @SendTo("/topic/greetings")
    public void sendMessage(@PathVariable(value = "roomId") String roomId, @Payload ChatMessageRequestDto requestDto) {
        ChatMessageResponseDto responseDto = chatService.sendMessage(requestDto, requestDto.getRecipientId());
        messagingTemplate.convertAndSendToUser(String.valueOf(requestDto.getRecipientId()), "/queue/messages", responseDto);

        log.info("Message sent to room {}: {}", roomId, responseDto.toString());
    }

    @DeleteMapping("/chat/delete/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable(value = "messageId") Long messageId) {
        log.info("메시지 삭제  - 메시지 ID: {}", messageId);
        chatService.deleteMessage(messageId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chat/rooms/{userId}")
    public ResponseEntity<List<ChatRoomDto>> getChatRoomsByUser(@PathVariable(value = "userId") Long userId) {
        log.info("채팅방 조회 요청 - 사용자 ID: {}", userId);
        List<ChatRoomDto> chatRooms = chatService.getChatRoomsByUser(userId);
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/chat/rooms/{roomId}/messages")
    public ResponseEntity<List<ChatMessageResponseDto>> getMessagesByRoom(@PathVariable Long roomId) {
        log.info("채팅방 메시지 조회 요청 - 채팅방 ID: {}", roomId);
        List<ChatMessageResponseDto> messages = chatService.getMessagesByRoom(roomId);
        return ResponseEntity.ok(messages);
    }
    @DeleteMapping("/chat/rooms/{chatRoomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable Long chatRoomId) {
        log.info("채팅방 삭제 요청 - 채팅방 ID: {}", chatRoomId);
        chatService.deleteChatRoom(chatRoomId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chatting")
    public String ResponseEntity() {
        return "ChatView";
    }
}
