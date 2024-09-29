package graduation.petshop.domain.chat.service;

import graduation.petshop.domain.chat.dto.request.ChatMessageRequestDto;
import graduation.petshop.domain.chat.dto.response.ChatMessageResponseDto;
import graduation.petshop.domain.chat.dto.response.ChatRoomDto;
import graduation.petshop.domain.chat.entity.ChatMessage;
import graduation.petshop.domain.chat.entity.ChatRoom;
import graduation.petshop.domain.chat.repository.ChatMessageRepository;
import graduation.petshop.domain.chat.repository.ChatRoomRepository;
import graduation.petshop.domain.profile.entity.Profile;
import graduation.petshop.domain.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final ProfileRepository profileRepository;
    private final ChatRoomRepository chatRoomRepository;



    // 채팅 보내기.
    @Transactional
    public ChatMessageResponseDto sendMessage(final ChatMessageRequestDto requestDto, Long recipientId) {
        Profile sender = profileRepository.findById(requestDto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("발신자의 프로필을 찾을 수 없습니다. 프로필 ID = " + requestDto.getSenderId()));

        Profile recipient = profileRepository.findById(recipientId)
                .orElseThrow(() -> new IllegalArgumentException("수신자의 프로필을 찾을 수 없습니다. 프로필 ID = " + recipientId));

        ChatRoom chatRoom = findOrCreateChatRoom(sender, recipient);
        ChatMessage chatMessage = requestDto.toEntity(sender, recipient, chatRoom);
        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return new ChatMessageResponseDto(savedMessage);
    }

    @Transactional
    public ChatRoom findOrCreateChatRoom(Profile sender, Profile recipient) {
        return chatRoomRepository.findByUser1AndUser2(sender, recipient)
                .orElseGet(() -> {
                    ChatRoom newChatRoom = ChatRoom.builder()
                            .user1(sender)
                            .user2(recipient)
                            .build();
                    return chatRoomRepository.save(newChatRoom);
                });
    }


    @Transactional
    public void deleteMessage(Long messageId) {
        chatMessageRepository.deleteById(messageId);
    }

    @Transactional(readOnly = true)
    public List<ChatRoomDto> getChatRoomsByUser(Long userId) {
        Profile user = profileRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. 사용자 ID = " + userId));
        return chatRoomRepository.findByUser1OrUser2(user, user).stream()
                .map(chatRoom -> new ChatRoomDto(chatRoom, userId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteChatRoom(Long chatRoomId) {

        chatRoomRepository.deleteById(chatRoomId);
    }

    @Transactional(readOnly = true)
    public Optional<ChatMessage> searchMessagesContaining(String message) {
        return chatMessageRepository.findByMessageContaining(message);
    }
    @Transactional(readOnly = true)
    public List<ChatMessageResponseDto> getMessagesByRoom(Long roomId) {
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomId(roomId);
        return messages.stream()
                .map(ChatMessageResponseDto::new)
                .collect(Collectors.toList());
    }
}