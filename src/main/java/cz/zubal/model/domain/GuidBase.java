package cz.zubal.model.domain;

import java.util.UUID;

/**
 * Created by milos.zubal on 27.10.2016.
 */
public class GuidBase {

    private String guid;

    public GuidBase() {
        guid = UUID.randomUUID().toString();
    }

    public String getGuid() {
        return guid;
    }
}
