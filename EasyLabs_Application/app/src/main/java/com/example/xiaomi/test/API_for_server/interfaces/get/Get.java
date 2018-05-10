package com.example.xiaomi.test.API_for_server.interfaces.get;

import com.example.xiaomi.test.API_for_server.interfaces.Requestable;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

public interface Get extends Requestable {

    public static final Gson gson = new GsonBuilder().create();

    public void RC() throws IOException;

    public int getResponseCode() throws IOException;

    public StringBuffer getResponse() throws IOException;

    public List<Entity> getEntity() throws IOException;
}
