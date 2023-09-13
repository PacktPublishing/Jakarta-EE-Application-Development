package com.ensode.websocketjavaclient;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.DeploymentException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 *
 * @author heffel
 */
@ClientEndpoint
public class WebSocketClient {

    private String userName;
    private Session session;
    private final WebSocketJavaClientFrame webSocketJavaClientFrame;

    public WebSocketClient(WebSocketJavaClientFrame webSocketJavaClientFrame) {
        this.webSocketJavaClientFrame = webSocketJavaClientFrame;

        try {
            WebSocketContainer webSocketContainer = ContainerProvider.getWebSocketContainer();
            webSocketContainer.connectToServer(this, new URI("ws://localhost:8080/websocketchat/websocketchat"));
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }

    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("onOpen() invoked");
        this.session = session;
    }

    @OnClose
    public void onClose(CloseReason closeReason) {
        System.out.println("Connection closed, reason: "
                + closeReason.getReasonPhrase());
    }

    @OnError
    public void onError(Throwable throwable) {
        System.out.println("onError() invoked");
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("onMessage() invoked");
        webSocketJavaClientFrame.getChatWindowTextArea().setText(webSocketJavaClientFrame.getChatWindowTextArea().getText() + "\n" + message);
    }

    public void sendMessage(String message) {
        try {
            System.out.println("sendMessage() invoked, message = " + message);
            session.getBasicRemote().sendText(userName + ": " + message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
