package com.example.xiaomi.test.API_for_server.requests.get;


import com.example.xiaomi.test.API_for_server.interfaces.get.Get;
import com.example.xiaomi.test.API_for_server.requests.Request;
import com.example.xiaomi.test.JSONclasses.entities.Task;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.xiaomi.test.JSONclasses.manipulations.tasksToEntities;

public class GetTasks extends Request implements Get {

    public int labNum;
    public String labAuth;

    public GetTasks(String REQUEST_METHOD, int number,
                    String author) throws MalformedURLException {
        super(REQUEST_METHOD);
        this.labNum = number;
        this.labAuth = author;
    }

    @Override
    public List<Entity> getEntity() throws IOException {
        if (labNum == 1488 && labAuth == "all") {
            this.setRqstValues("?request=get_all_tasks");
        } else {
            this.setRqstValues("?request=get_tasks&parent=" + labNum + "&author=" + labAuth);
//            Log.d("mLog", "?request=get_tasks&parent=" + labNum + "&author=" + labAuth);
        }

        super.Connection();
        StringBuffer response = getResponse();

        ArrayList<Task> tasks = gson.fromJson(response.toString(),
                new TypeToken<List<Task>>(){}.getType());
//        Log.d("GET ENTITY", "tasks.size:   ----   " + tasks.get(0).getName());

        return tasksToEntities(tasks);
    }

    @Override
    public void RC() throws IOException {
        super.rqstCondition();
    }

    @Override
    public List<Entity> getResp() throws IOException {
        return getEntity();
    }
}
