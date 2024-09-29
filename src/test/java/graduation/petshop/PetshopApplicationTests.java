package graduation.petshop;

import graduation.petshop.domain.chat.dto.request.ChatMessageRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@SpringBootTest
class PetshopApplicationTests {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;

	private StompSession stompSession;

	@Test
	public void testWebSocketMessage() throws Exception {
		// WebSocket 클라이언트 설정
		WebSocketClient client = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(client);

		// WebSocket 서버 URL
		String url = "ws://localhost:8080/chat"; // WebSocket 엔드포인트 URL

		// 세션 연결
		stompSession = stompClient.connect(url, new StompSessionHandlerAdapter() {
			// 세션 핸들러 메서드 구현
		}).get();

		// 메시지 전송
		ChatMessageRequestDto requestDto = new ChatMessageRequestDto();
		requestDto.setSenderId(1L); // 수신자 ID 설정
		requestDto.setMessage("Hello, World!"); // 메시지 내용 설정

		stompSession.send("/app/send/1", requestDto); // 메시지 전송

		// 메시지 수신 테스트 (옵션)
//		stompSession.subscribe("/user/1/queue/messages", (message) -> {
//			// 메시지 수신 처리
//			System.out.println("Received message: " + message);
//		});

		// 추가적인 검증 로직 작성
	}

}
