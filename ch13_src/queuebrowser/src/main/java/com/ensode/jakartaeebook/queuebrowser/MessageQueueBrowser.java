package com.ensode.jakartaeebook.queuebrowser;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import jakarta.jms.QueueBrowser;
import jakarta.jms.TextMessage;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;


@Named
@RequestScoped
public class MessageQueueBrowser {

    @Resource
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/JavaEE8BookQueue")
    private Queue queue;
    private static final Logger LOG = Logger.getLogger(MessageQueueBrowser.class.getName());

    public void browseMessages() {
        try {
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
        } catch (JMSException e) {
            LOG.log(Level.SEVERE, "JMS Exception caught", e);
        }
    }

    public static void main(String[] args) {
        new MessageQueueBrowser().browseMessages();
    }
}
