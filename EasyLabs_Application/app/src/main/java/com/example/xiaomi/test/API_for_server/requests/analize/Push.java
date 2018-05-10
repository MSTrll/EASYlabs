package com.example.xiaomi.test.API_for_server.requests.analize;

import com.example.xiaomi.test.API_for_server.requests.Request;
import com.example.xiaomi.test.API_for_server.requests.analize.data_containers.Measurement;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class Push extends Request {

    private int labNum;
    private String labAuth;
    private int taskNum;
    List<Measurement> mments;

    public Push(String REQUEST_METHOD, int labNum, String labAuth,
                      int taskNum, List<Measurement> mments) throws MalformedURLException {
        super(REQUEST_METHOD);

        this.labNum = labNum;
        this.labAuth = labAuth;
        this.taskNum = taskNum;
        this.mments = mments;
    }

    public void push() throws IOException {

        // Convert measurements to JSON
        String json = (new GsonBuilder().create()).toJson(mments);

        // Do request, connect to the server
        this.setRqstValues("?request=push&lab_number=" + labNum +
                "&lab_author=" + labAuth + "&task_number=" + taskNum + "&data=" + json);
        super.Connection();
    }
}
