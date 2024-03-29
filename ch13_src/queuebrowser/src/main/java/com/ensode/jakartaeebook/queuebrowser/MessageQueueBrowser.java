package com.ensode.jakartaeebook.queuebrowser;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.QueueBrowser;
import jakarta.jms.TextMessage;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

@JMSDestinationDefinition(
    name = "java:global/queue/JakartaEEBookQueue",
    interfaceName = "jakarta.jms.Queue"
)

@Named
@RequestScoped
public class MessageQueueBrowser {

  @Resource
  private ConnectionFactory connectionFactory;
  @Resource(mappedName = "java:global/queue/JakartaEEBookQueue")
  private Queue queue;
  private static final Logger LOG = Logger.getLogger(MessageQueueBrowser.class.getName());

  public void browseMessages() throws JMSException {
    Enumeration messageEnumeration;
    TextMessage textMessage;
    JMSContext jmsContext = connectionFactory.createContext();
    QueueBrowser browser = jmsContext.createBrowser(queue);

    messageEnumeration = browser.getEnumeration();

    if (messageEnumeration != null) {
      if (!messageEnumeration.hasMoreElements()) {
        LOG.log(Level.INFO, "There are no messages "
            + "in the queue.");
      } else {
        LOG.log(Level.INFO,
            "The following messages are in the queue:");
        while (messageEnumeration.hasMoreElements()) {
          textMessage = (TextMessage) messageEnumeration.
              nextElement();
          LOG.log(Level.INFO, textMessage.getText());
        }
      }
    }
  }
}
