package com.example.xiaomi.test.API_for_server.requests.analize;

import com.example.xiaomi.test.API_for_server.requests.Request;
import com.example.xiaomi.test.API_for_server.requests.analize.data_containers.Results;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class GetResults extends Request {
    private int labNum;
    private String labAuth;
    private int taskNum;

    public GetResults(String REQUEST_METHOD, int labNum, String labAuth,
                      int taskNum) throws MalformedURLException {
        super(REQUEST_METHOD);

        this.labNum = labNum;
        this.labAuth = labAuth;
        this.taskNum = taskNum;
    }

    public List<Results> getResults() throws IOException {

        this.setRqstValues("?request=get_results&lab_number=" + labNum +
                "&lab_author=" + labAuth + "&task_number=" + taskNum);

        super.Connection();
        StringBuffer response = getResponse();

        ArrayList<Results> results = (new GsonBuilder().create()).fromJson(response.toString(),
                new TypeToken<List<Results>>(){}.getType());

        return results;
    }
}
