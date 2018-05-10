package com.example.xiaomi.test.API_for_server.requests.get.get_all;


import com.example.xiaomi.test.API_for_server.annotations.Responsible;
import com.example.xiaomi.test.API_for_server.requests.get.GetLab;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;
import com.example.xiaomi.test.API_for_server.interfaces.get.GetAll;
import com.example.xiaomi.test.JSONclasses.entities.Lab;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.example.xiaomi.test.API_for_server.annotations.Responsible.ARRAY;
import static com.example.xiaomi.test.JSONclasses.manipulations.labsToEntities;

@Responsible
public class GetAllLabs extends GetLab implements GetAll {

    public GetAllLabs(String REQUEST_METHOD) throws MalformedURLException {
        super(REQUEST_METHOD, 0, null);
        this.setRqstValues("?request=get_all_labs");
    }

    @Responsible(type = ARRAY)
    public ArrayList<Entity> getAll() throws IOException {

        super.Connection();
        StringBuffer response = getResponse();

        ArrayList<Lab> labs = gson.fromJson(response.toString(),
                new TypeToken<List<Lab>>(){}.getType());

        return labsToEntities(labs);
    }
}
