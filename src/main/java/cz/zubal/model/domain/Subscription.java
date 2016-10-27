package cz.zubal.model.domain;

import java.util.List;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class Subscription extends GuidBase {

    private List<String> subscribedMessageTypes;

    public Subscription(List<String> subscribedMessageTypes) {
        this.subscribedMessageTypes = subscribedMessageTypes;
    }

    public List<String> getSubscribedMessageTypes() {
        return subscribedMessageTypes;
    }

    public void setSubscribedMessageTypes(List<String> subscribedMessageTypes) {
        this.subscribedMessageTypes = subscribedMessageTypes;
    }
}
