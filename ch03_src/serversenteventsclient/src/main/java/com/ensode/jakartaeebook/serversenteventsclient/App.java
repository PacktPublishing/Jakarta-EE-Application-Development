package com.ensode.jakartaeebook.serversenteventsclient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.sse.SseEventSource;

public class App {

  public static void main(String[] args) {
    App app = new App();
    app.listenForEvents();
  }

  public void listenForEvents() {

    final Client client = ClientBuilder.newClient();
    final WebTarget webTarget = client.target(
            "http://localhost:8080/serversentevents/"
            + "resources/serversentevents/subscribe");

    final SseEventSource.Builder sseEventSourceBuilder;
    final SseEventSource sseEventSource;

    sseEventSourceBuilder = SseEventSource.target(webTarget);
    sseEventSource = sseEventSourceBuilder.build();

    sseEventSource.register((inboundSseEvent) -> {
      System.out.println("Received the following event:");
      System.out.println(String.format("Event name: %s", inboundSseEvent.getName()));
      System.out.println(String.format("Event data: %s\n", inboundSseEvent.readData()));
    });

    sseEventSource.open();
  }
}
