package com.ensode.jakartaeebook.ptpproducer;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSDestinationDefinition;
import jakarta.jms.JMSProducer;
import jakarta.jms.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

@JMSDestinationDefinition(
    name = "java:global/queue/JakartaEEBookQueue",
    interfaceName = "jakarta.jms.Queue"
)

@Named
@RequestScoped
public class MessageSender {

  @Resource
  private ConnectionFactory connectionFactory;

  @Resource(mappedName = "java:global/queue/JakartaEEBookQueue")
  private Queue queue;

  private static final Logger LOG = Logger.getLogger(MessageSender.class.getName());

  public void produceMessages() {
    JMSContext jmsContext = connectionFactory.createContext();
    JMSProducer jmsProducer = jmsContext.createProducer();

    String msg1 = "Testing, 1, 2, 3. Can you hear me?";
    String msg2 = "Do you copy?";
    String msg3 = "Good bye!";

    LOG.log(Level.INFO, "Sending the following message: {0}", msg1);
    jmsProducer.send(queue, msg1);
    LOG.log(Level.INFO, "Sending the following message: {0}", msg2);
    jmsProducer.send(queue, msg2);
    LOG.log(Level.INFO, "Sending the following message: {0}", msg3);
    jmsProducer.send(queue, msg3);

  }
}
