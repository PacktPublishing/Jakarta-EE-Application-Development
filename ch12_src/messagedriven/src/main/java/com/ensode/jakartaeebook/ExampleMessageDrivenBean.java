package com.ensode.jakartaeebook;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@JMSDestinationDefinition(
  name="java:global/queue/JakartaEEBookQueue",
  interfaceName="jakarta.jms.Queue",
  destinationName = "JakartaEEBookQueue"
)

@MessageDriven(activationConfig = {
  @ActivationConfigProperty(propertyName = "destinationLookup",
      propertyValue = "java:global/queue/JakartaEEBookQueue"),
  @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue")
})
public class ExampleMessageDrivenBean implements MessageListener {

  private static final Logger LOG = Logger.getLogger(ExampleMessageDrivenBean.class.getName());


  public void onMessage(Message message) {
    TextMessage textMessage = (TextMessage) message;
    try {
      LOG.log(Level.INFO, "Received the following message: ");
      LOG.log(Level.INFO, textMessage.getText());
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }
}
