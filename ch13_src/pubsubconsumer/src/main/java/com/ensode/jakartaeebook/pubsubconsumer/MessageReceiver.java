package com.ensode.jakartaeebook.pubsubconsumer;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.Topic;
import java.util.logging.Level;
import java.util.logging.Logger;

@JMSDestinationDefinition(
    name = "java:global/topic/JakartaEEBookTopic",
    interfaceName = "jakarta.jms.Topic"
)

@Named
@RequestScoped
public class MessageReceiver {

  @Resource
  private ConnectionFactory connectionFactory;
  @Resource(mappedName = "java:global/topic/JakartaEEBookTopic")
  private Topic topic;
  private static final Logger LOG = Logger.getLogger(MessageReceiver.class.getName());

  public void receiveMessages() {
    String message;
    boolean goodByeReceived = false;

    JMSContext jmsContext = connectionFactory.createContext();
    JMSConsumer jMSConsumer = jmsContext.createConsumer(topic);

    LOG.log(Level.INFO, "Waiting for messages...");
    while (!goodByeReceived) {
      message = jMSConsumer.receiveBody(String.class);

      if (message != null) {
        LOG.log(Level.INFO, "Received the following message: {0}", message);
        if (message.equals("Good bye!")) {
          goodByeReceived = true;
        }
      }
    }
  }
}
