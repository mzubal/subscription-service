package cz.zubal.service.api;

import cz.zubal.model.domain.Message;
import cz.zubal.model.domain.Subscription;

import java.util.List;

/**
 * Created by milos.zubal on 27.10.2016.
 * Subscription Service performs the requested operations.
 */
public interface SubscriptionService {

    /**
     * Creates the Subscription
     * @param messageTypes must be not null, can be empty string (no subscription needed)
     * @return the resulting Subscription containg the subscription guid, which is used to call other methods
     */
    Subscription createSubscription(List<String> messageTypes);

    /**
     * Returns the subscription for given guid
     * @param subscriptionGuid the guid of subscription to be returned
     * @return the resulting Subscription
     * @throws @{@link cz.zubal.exception.SubscriptionNotFoundException} in case the subscription guid is not found
     */
    Subscription getSubscription(String subscriptionGuid);

    /**
     * Reads message for the given Subscription guid
     * @param subscriptionGuid the guid of subscription to return messages for
     * @return the list of messages matching the subscription,
     * @throws @{@link cz.zubal.exception.SubscriptionNotFoundException} in case the subscription guid is not found
     */
    List<Message> readMessages(String subscriptionGuid);

    /**
     * Updates the subscription for given guid
     * @param subscriptionGuid the guid of subscription to be updated
     * @param messageTypes list of messages types to be updated
     * @return the resulting Subscription, throw
     * @throws @{@link cz.zubal.exception.SubscriptionNotFoundException} in case the subscription guid is not found
     */
    Subscription updateSubscription(String subscriptionGuid, List<String> messageTypes);

    /**
     * Store the message of given type and content
     * @param messageType type of message to be stored
     * @param messageContent content of message to be stored
     * @return
     */
    Message postMessage(String messageType, String messageContent);

}
