package com.ensode.jakartaeebook.ptpconsumer;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSConsumer;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class MessageReceiver implements Serializable{

    @Resource
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/JavaEE8BookQueue")
    private Queue queue;
    private static final Logger LOG = Logger.getLogger(MessageReceiver.class.getName());


    public void receiveMessages() {
        String message;
        boolean goodByeReceived = false;

        JMSContext jmsContext = connectionFactory.createContext();
        JMSConsumer jMSConsumer = jmsContext.createConsumer(queue);

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
