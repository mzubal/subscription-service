package cz.zubal.service;

import cz.zubal.model.domain.Message;
import cz.zubal.model.report.MessageTypeCardinality;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by milos.zubal on 27.10.2016.
 * Class performing various analyzes of messages
 */
@Component
public class MessagesAnalysisService {

    /**
     * Performs analysis of cardinality for each message types
     * @param messages messages to be analyzed
     * @return @{@link MessageTypeCardinality} object with results;
     */
    public MessageTypeCardinality analyzeCardinalities(List<Message> messages) {
        Map<String, Integer> cardinality = new HashMap<>();
        messages.forEach(message -> {
            String messageType = message.getMessageType();
            Integer count = cardinality.get(messageType);
            if (count == null) {
                cardinality.put(messageType, 1);
            } else {
                cardinality.put(messageType, count + 1);
            }
        });
        return new MessageTypeCardinality(cardinality);
    }

}
