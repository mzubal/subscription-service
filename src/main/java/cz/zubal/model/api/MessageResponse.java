package cz.zubal.model.api;

import cz.zubal.model.domain.Message;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class MessageResponse {

    private Message message;

    public MessageResponse(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}
