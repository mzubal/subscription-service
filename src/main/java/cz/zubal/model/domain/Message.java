package cz.zubal.model.domain;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class Message extends GuidBase {

    private String messageType;
    private String content;

    public Message(String messageType, String content) {
        this.messageType = messageType;
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getContent() {
        return content;
    }

}
