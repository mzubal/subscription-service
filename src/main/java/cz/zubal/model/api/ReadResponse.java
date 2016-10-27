package cz.zubal.model.api;

import cz.zubal.model.domain.Message;
import cz.zubal.model.domain.Subscription;

import java.util.List;
import java.util.Map;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class ReadResponse {

    private Subscription subscription;
    private Map<String, Integer> messageTypeCardinality;
    private List<Message> messages;

    public ReadResponse(Subscription subscription, List<Message> messages, Map<String, Integer> messageTypeCardinality) {
        this.subscription = subscription;
        this.messages = messages;
        this.messageTypeCardinality = messageTypeCardinality;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public Map<String, Integer> getMessageTypeCardinality() {
        return messageTypeCardinality;
    }

    public List<Message> getMessages() {
        return messages;
    }
}
