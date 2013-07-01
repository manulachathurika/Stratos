package org.apache.stratos.lb.endpoint.subscriber;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.stratos.lb.endpoint.util.ConfigHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TopologyListener implements MessageListener {

	private static final Log log = LogFactory.getLog(TopologyListener.class);

	@Override
    public void onMessage(Message message) {
		TextMessage receivedMessage = (TextMessage) message;
        try {
            
            ConfigHolder.getInstance().getSharedTopologyDiffQueue().add(receivedMessage.getText());

        } catch (JMSException e) {
        	log.error(e.getMessage(), e);
        }

    }

}
