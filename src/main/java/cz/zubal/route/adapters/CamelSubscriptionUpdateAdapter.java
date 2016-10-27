package cz.zubal.route.adapters;

import cz.zubal.model.api.SubscriptionResponse;
import cz.zubal.model.api.ReadResponse;
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
public class CamelSubscriptionUpdateAdapter {

    @Autowired
    private SubscriptionService subscriptionService;

    public SubscriptionResponse updateSubscription(@Header(CustomHeaders.HEADER_SUBSCRIPTION_GUID) String subscriptionGuid, @Header(CustomHeaders.HEADER_MESSAGE_TYPES) String messageTypes) {
        Assert.notNull(subscriptionGuid);
        Assert.notNull(messageTypes);
        Subscription subscription = subscriptionService.updateSubscription(subscriptionGuid, Arrays.asList(messageTypes.split(",")));
        return new SubscriptionResponse(subscription);
    }

}
