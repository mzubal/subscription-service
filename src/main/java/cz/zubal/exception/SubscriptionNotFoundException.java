package cz.zubal.exception;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class SubscriptionNotFoundException extends RuntimeException {

    public SubscriptionNotFoundException(String subscriptionGuid) {
        super("Subscription with guid: " + subscriptionGuid + " was not found!");
    }
}
