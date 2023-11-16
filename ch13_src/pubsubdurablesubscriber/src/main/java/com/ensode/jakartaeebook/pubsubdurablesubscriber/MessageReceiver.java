package com.ensode.jakartaeebook.pubsubdurablesubscriber;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Topic;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class MessageReceiver {

    @Resource(mappedName = "jms/JavaEE8BookDurableConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/JavaEE8BookTopic")
    private Topic topic;
    private static final Logger LOG = Logger.getLogger(MessageReceiver.class.getName());

    public void receiveMessages() {
        String message;
        boolean goodByeReceived = false;

        JMSContext jmsContext = connectionFactory.createContext();
        JMSConsumer jMSConsumer = jmsContext.createDurableConsumer(topic, "Subscriber1");

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
