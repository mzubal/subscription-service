package cz.zubal.route.adapters;

import cz.zubal.model.api.ReadResponse;
import cz.zubal.model.domain.Message;
import cz.zubal.model.report.MessageTypeCardinality;
import cz.zubal.model.domain.Subscription;
import cz.zubal.route.CustomHeaders;
import cz.zubal.service.MessagesAnalysisService;
import cz.zubal.service.api.SubscriptionService;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by milos.zubal on 27.10.2016.
 */
@Component
public class CamelSubscriptionReadAdapter {

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private MessagesAnalysisService messagesAnalysisService;

    public ReadResponse readSubscription(@Header(CustomHeaders.HEADER_SUBSCRIPTION_GUID) String subscriptionGuid) {
        Assert.notNull(subscriptionGuid);
        Subscription subscription = subscriptionService.getSubscription(subscriptionGuid);
        List<Message> messages = subscriptionService.readMessages(subscriptionGuid);
        MessageTypeCardinality cardinality = messagesAnalysisService.analyzeCardinalities(messages);
        return new ReadResponse(subscription, messages, cardinality.getMessageTypeCardinality());
    }

}
