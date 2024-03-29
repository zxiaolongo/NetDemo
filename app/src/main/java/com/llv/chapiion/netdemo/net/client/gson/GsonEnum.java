package com.llv.chapiion.netdemo.net.client.gson;

import com.google.gson.JsonElement;

public interface GsonEnum<E extends Enum<E>> {

    String serialize();

    E deserialize(JsonElement jsonEnum);
}