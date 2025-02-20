package com.viplogistics.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link ChatHandler} is a WebSocket handler responsible for handling chat messages between connected WebSocket clients.
 * <p>
 * This handler manages a set of active WebSocket sessions and ensures that any text message received from a client
 * is broadcast to all other connected clients. The handler also manages session establishment and closure.
 * </p>
 */
@Component
public class ChatHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    /**
     * This method is called when a new WebSocket connection is established.
     * The session representing the connected client is added to the set of sessions.
     *
     * @param session The WebSocket session representing the connected client.
     * @throws Exception If there is an error during the connection establishment.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }

    /**
     * This method handles incoming text messages from clients. When a message is received from a client, it is broadcast
     * to all other connected clients. Each client will receive the same message as the sender.
     *
     * @param session The WebSocket session from which the message was received.
     * @param message The text message that was sent by the client.
     * @throws Exception If there is an error while processing the message.
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        for (WebSocketSession s : sessions) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(payload));
            }
        }
    }

    /**
     * This method is called when a WebSocket connection is closed. The session representing the disconnected client
     * is removed from the set of sessions.
     *
     * @param session The WebSocket session representing the client that closed the connection.
     * @param status The status of the connection closure.
     * @throws Exception If there is an error while handling the connection closure.
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
    }
}