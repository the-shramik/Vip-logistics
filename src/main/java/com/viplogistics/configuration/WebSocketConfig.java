package com.viplogistics.configuration;

import com.viplogistics.handler.ChatHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocketConfig - Configures WebSocket support for real-time communication.
 *
 * <p>This configuration enables WebSocket communication and registers handlers
 * to manage WebSocket connections.</p>
 *
 * <p><strong>Key Features:</strong></p>
 * - Uses {@link EnableWebSocket} to enable WebSocket support.
 * - Registers a WebSocket handler {@link ChatHandler} for real-time chat.
 * - Allows connections from any origin using `setAllowedOrigins("*")`.
 *
 * @author Shramik Masti
 * @author Shubham Lohar
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    /**
     * Constructor to inject dependencies.
     *
     * @param chatHandler The WebSocket handler for managing chat connections.
     */
    public WebSocketConfig(ChatHandler chatHandler) {
        this.chatHandler = chatHandler;
    }

    /**
     * Registers WebSocket handlers for handling WebSocket connections.
     *
     * @param registry The {@link WebSocketHandlerRegistry} to register WebSocket handlers.
     */
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*");
    }
}
