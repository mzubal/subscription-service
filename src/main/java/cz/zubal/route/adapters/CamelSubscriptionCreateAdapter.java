package cz.zubal.route.adapters;

import cz.zubal.model.api.SubscriptionResponse;
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
public class CamelSubscriptionCreateAdapter {

    @Autowired
    private SubscriptionService subscriptionService;

    public SubscriptionResponse createSubscription(@Header(CustomHeaders.HEADER_MESSAGE_TYPES) String messageTypes) {
        Assert.notNull(messageTypes);
        Subscription subscription = subscriptionService.createSubscription(Arrays.asList(messageTypes.split(",")));
        return new SubscriptionResponse(subscription);
    }

}
