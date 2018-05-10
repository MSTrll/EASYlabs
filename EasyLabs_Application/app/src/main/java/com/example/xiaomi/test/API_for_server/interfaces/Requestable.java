package com.example.xiaomi.test.API_for_server.interfaces;


import com.example.xiaomi.test.JSONclasses.interfaces.Entity;

import java.io.IOException;
import java.util.List;

// classes which send requests to the server
public interface Requestable {

//    public void doRequest() throws IOException;
    public List<Entity> getResp() throws IOException;
}
