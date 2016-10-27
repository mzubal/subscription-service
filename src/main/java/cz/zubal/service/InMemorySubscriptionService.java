package cz.zubal.service;

import cz.zubal.exception.SubscriptionNotFoundException;
import cz.zubal.model.domain.Message;
import cz.zubal.model.domain.Subscription;
import cz.zubal.service.api.SubscriptionService;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * In memory implementation of the @{@link SubscriptionService}
 * Created by milos.zubal on 27.10.2016.
 */
@Component
public class InMemorySubscriptionService implements SubscriptionService {

    // Map of subscription (indexed by their guid)
    Map<String, Subscription> subscriptionsMap = new HashMap<>();
    // Map of Messages lists (indexed by their type)
    Map<String, List<Message>> messagesMap = new HashMap<>();

    @Override
    public Subscription createSubscription(List<String> messageTypes) {
        Subscription subscription = new Subscription(messageTypes);
        subscriptionsMap.put(subscription.getGuid(), subscription);
        return subscription;
    }

    @Override
    public Subscription getSubscription(String subscriptionGuid) {
        Subscription subscription = subscriptionsMap.get(subscriptionGuid);
        if (subscription == null) {
            throw new SubscriptionNotFoundException(subscriptionGuid);
        }
        return subscription;
    }

    @Override
    public List<Message> readMessages(String subscriptionGuid) {
        Subscription subscription = subscriptionsMap.get(subscriptionGuid);
        if (subscription == null) {
            throw new SubscriptionNotFoundException(subscriptionGuid);
        }
        List<String> subscribedTypes = subscription.getSubscribedMessageTypes();
        return subscribedTypes
            .stream()
            .filter(type -> {return messagesMap.containsKey(type);})
            .map(type ->  {return messagesMap.get(type);})
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    @Override
    public synchronized Subscription updateSubscription(String subscriptionGuid, List<String> messageTypes) {
        Subscription subscription = subscriptionsMap.get(subscriptionGuid);
        if (subscription == null) {
            throw new SubscriptionNotFoundException(subscriptionGuid);
        }
        subscription.setSubscribedMessageTypes(messageTypes);
        return subscription;
    }

    @Override
    public synchronized Message postMessage(String messageType, String messageContent) {
        Message message = new Message(messageType, messageContent);
        List<Message> messageList = messagesMap.get(messageType);
        if (messageList == null) {
            messageList = new ArrayList<>();
            messagesMap.put(messageType, messageList);
        }
        messageList.add(message);
        return message;
    }
}
