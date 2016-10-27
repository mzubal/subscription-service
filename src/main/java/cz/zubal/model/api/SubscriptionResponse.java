package cz.zubal.model.api;

import cz.zubal.model.domain.Subscription;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class SubscriptionResponse {

    private Subscription subscription;

    public SubscriptionResponse(Subscription subscription) {
        this.subscription = subscription;
    }

    public Subscription getSubscription() {
        return subscription;
    }

}
