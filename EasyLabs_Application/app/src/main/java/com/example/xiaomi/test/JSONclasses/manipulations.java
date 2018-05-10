package com.example.xiaomi.test.JSONclasses;


import com.example.xiaomi.test.JSONclasses.entities.Lab;
import com.example.xiaomi.test.JSONclasses.entities.Task;
import com.example.xiaomi.test.JSONclasses.interfaces.Entity;

import java.util.ArrayList;
import java.util.List;

public abstract class manipulations {

    public static ArrayList<Entity> labsToEntities(ArrayList<Lab> labs) {
        ArrayList<Entity> finalLabs = new ArrayList<>();
        for (Lab lab : labs) {
            finalLabs.add(lab);
        }
        return finalLabs;
    }

    public static ArrayList<Entity> tasksToEntities(ArrayList<Task> tasks) {
        ArrayList<Entity> finalTasks = new ArrayList<>();
        for (Task task : tasks) {
            finalTasks.add(task);
        }
        return finalTasks;
    }

    public static List<Lab> entitiesToLabs(List<Entity> le) {
        List<Lab> finalLabs = new ArrayList<>();
        for (Entity e : le) {
            finalLabs.add((Lab) e);
        }
        return finalLabs;
    }

    public static List<Task> entitiesToTasks(List<Entity> le) {
        List<Task> finalTasks = new ArrayList<>();
//        if (le == null) {
//            Log.d("NULL", "FINAL  TASKS:     -----   nuuuuul!!!");
//        }
        for (Entity e : le) {
            finalTasks.add((Task) e);
        }
        return finalTasks;
    }
}
