package com.example.xiaomi.test.API_for_server.threads;


import android.util.Log;

import com.example.xiaomi.test.API_for_server.interfaces.Requestable;
import com.example.xiaomi.test.API_for_server.requests.analize.GetResults;
import com.example.xiaomi.test.API_for_server.requests.analize.Push;
import com.example.xiaomi.test.API_for_server.requests.analize.data_containers.Results;
import com.example.xiaomi.test.API_for_server.requests.get.GetLab;
import com.example.xiaomi.test.API_for_server.requests.get.GetTasks;
import com.example.xiaomi.test.API_for_server.requests.get.get_all.GetAllLabs;
import com.example.xiaomi.test.JSONclasses.entities.Lab;
import com.example.xiaomi.test.JSONclasses.entities.Task;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;

import java.io.IOException;
import java.util.List;

import static com.example.xiaomi.test.JSONclasses.manipulations.entitiesToLabs;
import static com.example.xiaomi.test.JSONclasses.manipulations.entitiesToTasks;

// Thread for server requests
public class RequestThread extends Thread {

    // object of request class
    Requestable rqst;

    // for responses
    Entity entity;
    List<Entity> entities;

    //for analize
    List<Results> results;

    public RequestThread(Requestable rqst) {
        this.rqst = rqst;
    }

    @Override
    public void run() {
        if (rqst.getClass() == GetLab.class) {
            try {
                List<Entity> listE = rqst.getResp();

                Lab lab = (Lab) listE.get(0);
                int num = lab.getNumber();
                String nam = lab.getTitle();
                String auth = lab.getAuthor();

                Log.d("mLog", "REQThread :  ---  " + num + "   " + nam + "   " + auth);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (rqst.getClass() == GetAllLabs.class){
            try {
                entities = ((GetAllLabs) rqst).getAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (rqst.getClass() == GetTasks.class) {
            try {
//                Log.d("mLog", "rqst.getClass = GetTasks");
                entities = rqst.getResp();
//                Log.d("REQ THREAD", "entities.size:   -------   " + entities.isEmpty());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (rqst.getClass() == Push.class) {
            try {
                ((Push) rqst).push();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (rqst.getClass() == GetResults.class) {
            try {
                results = ((GetResults) rqst).getResults();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Log.d("RequestThread", "All done now");
    }

    public List<Lab> getBigResponse() {
        return entitiesToLabs(entities);
    }

    public List<Task> getTaskResponse() {
        return entitiesToTasks(entities);
    }

    public Entity getLabResponse() {
        return entity;
    }

    public List<Results> getResults() {
        return results;
    }
}
