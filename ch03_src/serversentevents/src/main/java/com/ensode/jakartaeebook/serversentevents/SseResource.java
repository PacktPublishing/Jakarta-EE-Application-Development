package com.ensode.jakartaeebook.serversentevents;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@ApplicationScoped
@Path("serversentevents")
public class SseResource {

  private static final Logger LOG = Logger.getLogger(SseResource.class.getName());

  private SseBroadcaster sseBroadcaster;
  private OutboundSseEvent.Builder eventBuilder;

  private ScheduledExecutorService scheduledExecutorService;

  private Double stockValue = 10.0;

  @PostConstruct
  public void init() {
    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    sendEvents();
  }

  @PreDestroy
  public void cleanup() {
    scheduledExecutorService.shutdown();
  }

  @Context
  public void setSse(Sse sse) {
    this.eventBuilder = sse.newEventBuilder();
    this.sseBroadcaster = sse.newBroadcaster();
  }

  @GET
  @Path("subscribe")
  @Produces(MediaType.SERVER_SENT_EVENTS)
  public void subscribe(@Context SseEventSink sseEventSink) {
    LOG.info(String.format("%s.subscribe() invoked", this.getClass().getName()));
    this.sseBroadcaster.register(sseEventSink);
  }

  public void sendEvents() {
    scheduledExecutorService.scheduleAtFixedRate(() -> {
      final OutboundSseEvent outboundSseEvent = eventBuilder
              .name("ENSD stock ticker value")
              .data(String.class, String.format("%.2f", stockValue))
              .build();
      LOG.info(String.format("broadcasting event: %.2f", stockValue));
      sseBroadcaster.broadcast(outboundSseEvent);
      stockValue += 0.9;
    }, 5, 5, TimeUnit.SECONDS);
  }
}
