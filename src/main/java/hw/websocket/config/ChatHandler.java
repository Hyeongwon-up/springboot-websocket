package hw.websocket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import hw.websocket.ChatMessage;
import hw.websocket.ChatRoom;
import hw.websocket.ChatService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
class ChatHandler extends TextWebSocketHandler {
  private final ObjectMapper objectMapper;
  private final ChatService chatService;

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String payload = message.getPayload();
    log.info("payload {}", payload);

    ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
    ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
    room.handleActions(session, chatMessage, chatService);
  }
}