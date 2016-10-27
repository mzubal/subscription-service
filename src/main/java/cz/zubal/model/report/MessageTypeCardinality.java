package cz.zubal.model.report;

import java.util.Map;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class MessageTypeCardinality {

    // Map from type to count of message with given type
    Map<String, Integer> messageTypeCardinality;

    public MessageTypeCardinality(Map<String, Integer> messageTypeCardinality) {
        this.messageTypeCardinality = messageTypeCardinality;
    }

    public Map<String, Integer> getMessageTypeCardinality() {
        return messageTypeCardinality;
    }

}
