package com.example.xiaomi.test.API_for_server.interfaces.get;


import com.example.xiaomi.test.API_for_server.annotations.Responsible;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;
import java.io.IOException;
import java.util.ArrayList;

public interface GetAll extends Get {

    @Responsible(type = "array")
    public ArrayList<Entity> getAll() throws IOException;

}
