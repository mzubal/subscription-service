package cz.zubal;

import cz.zubal.model.domain.Message;
import cz.zubal.model.domain.Subscription;
import cz.zubal.service.api.SubscriptionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionServiceApplicationTests {

    @Autowired
    private SubscriptionService subscriptionService;

    @Test
    public void testCreate() {
        List<String> messageTypes = Arrays.asList("A", "B");

        Subscription subscription = subscriptionService.createSubscription(messageTypes);
        Assert.assertNotNull(subscription);
        Assert.assertTrue("Subscribed types are not as expected.", subscription.getSubscribedMessageTypes().equals(messageTypes));
        Assert.assertNotNull(subscription.getGuid());
    }

    @Test
    public void testRead() {
        List<String> messageTypes = Arrays.asList("A", "B", "C", "D", "E");
        Map<String, Message> sentMessages = new HashMap<>();

        Subscription subscription = subscriptionService.createSubscription(messageTypes);
        String guid = subscription.getGuid();

        messageTypes.forEach(type -> {
            Message message = subscriptionService.postMessage(type, type);
            sentMessages.put(message.getGuid(), message);
        });

        List<Message> obtainedMessages = subscriptionService.readMessages(guid);
        obtainedMessages.forEach(message -> {
            Message pairedMessage = sentMessages.get(message.getGuid());
            Assert.assertNotNull(pairedMessage);
            Assert.assertEquals(pairedMessage, message);
        });
    }

    @Test
    public void testUpdate() {
        List<String> messageTypes = Arrays.asList("A", "B");

        Subscription subscription = subscriptionService.createSubscription(messageTypes);
        String guid = subscription.getGuid();

        Subscription updatedSubscription = subscriptionService.updateSubscription(guid, Arrays.asList("C"));

        Assert.assertNotNull(updatedSubscription);
        Assert.assertEquals(guid, updatedSubscription.getGuid());
        Assert.assertTrue("Subscribed types are not as expected.", updatedSubscription.getSubscribedMessageTypes().equals(Arrays.asList("C")));
    }

}
