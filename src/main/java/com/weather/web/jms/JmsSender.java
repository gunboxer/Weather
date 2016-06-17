package com.weather.web.jms;

import javax.jms.Destination;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author ryagudin
 */
@Service("jmsSender")
public class JmsSender {

    private final JmsTemplate template;
    private Destination queue;

    public JmsSender() {
        template = null;
    }

    public JmsSender(JmsTemplate template, Destination queue) {
        this.template = template;
        this.queue = queue;
    }

    public void send(String msg) {
        template.convertAndSend(queue, msg);
    }
}
