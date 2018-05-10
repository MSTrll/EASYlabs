package com.example.xiaomi.test.API_for_server.requests.get;


import com.example.xiaomi.test.API_for_server.annotations.Responsible;
import com.example.xiaomi.test.API_for_server.interfaces.get.Get;
import com.example.xiaomi.test.API_for_server.requests.Request;
import com.example.xiaomi.test.JSONclasses.entities.Lab;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.xiaomi.test.API_for_server.annotations.Responsible.SIMPLE;

public class GetLab extends Request implements Get{

    private HttpURLConnection connection = null;

    public int labNum;
    public String labAuth;

    public GetLab(String REQUEST_METHOD, int number, String author
                    ) throws MalformedURLException {
        super(REQUEST_METHOD);
        this.labNum = number;
        this.labAuth = author;
    }

    @Override
    @Responsible(type = SIMPLE)
    public List<Entity> getEntity() throws IOException {
        // Do request, connect to the server, get response
        this.setRqstValues("?request=get_lab&number=" + labNum + "&author=" + labAuth);
        super.Connection();
        StringBuffer response = getResponse();

        // Convert JSON response to the Entity(ies)
        Lab lab = gson.fromJson(response.toString(),
                Lab.class);
        ArrayList<Entity> alEntity = new ArrayList<>();
        alEntity.add(lab);
        return alEntity;
    }

    @Override
    public List<Entity> getResp() throws IOException {
        return getEntity();
    }

    @Override
    public void RC() throws IOException {
        super.rqstCondition();
    }

    @Override
    public int getResponseCode() throws IOException {
        return super.getResponseCode();
    }

    @Override
    public StringBuffer getResponse() throws IOException {
        return super.getResponse();
    }
}
