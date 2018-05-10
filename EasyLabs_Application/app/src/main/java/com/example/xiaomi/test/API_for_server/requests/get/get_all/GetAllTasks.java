package com.example.xiaomi.test.API_for_server.requests.get.get_all;


import com.example.xiaomi.test.API_for_server.annotations.Responsible;
import com.example.xiaomi.test.API_for_server.requests.get.GetTasks;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import static com.example.xiaomi.test.API_for_server.annotations.Responsible.ARRAY;


public class GetAllTasks extends GetTasks {

    public GetAllTasks(String REQUEST_METHOD) throws MalformedURLException {
        super(REQUEST_METHOD, 1488, "all");
        this.setRqstValues("?request=get_all_tasks");
    }

    @Responsible(type = ARRAY)
    public List<Entity> getAllTasks() throws IOException {
        return getEntity();
    }
}
