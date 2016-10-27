package cz.zubal.route.adapters;

import cz.zubal.model.api.MessageResponse;
import cz.zubal.model.api.SubscriptionResponse;
import cz.zubal.model.domain.Message;
import cz.zubal.model.domain.Subscription;
import cz.zubal.route.CustomHeaders;
import cz.zubal.service.api.SubscriptionService;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;

/**
 * Created by milos.zubal on 27.10.2016.
 */
@Component
public class CamelPostMessageAdapter {

    @Autowired
    private SubscriptionService subscriptionService;

    public MessageResponse updateSubscription(@Header(CustomHeaders.HEADER_MESSAGE_TYPE) String messageType, @Header(CustomHeaders.HEADER_MESSAGE_CONTENT) String messageContent) {
        Assert.notNull(messageType);
        Assert.notNull(messageContent);
        Message message = subscriptionService.postMessage(messageType, messageContent);
        return new MessageResponse(message);
    }

}
