package com.example.xiaomi.test;

import java.util.ArrayList;

public abstract class Consts {
    // Usages
    public static ArrayList<Integer> NumsList = new ArrayList<Integer>();


    // имя таблицы categories, поля и запрос создания
    public static final String LABS_TABLE = "labs";
    public static final String LABS_COLUMN_ID = "_id";
    public static final String LABS_COLUMN_NAME = "name";

    // имя таблицы labs, поля и запрос создания
    public static final String LAB_TABLE = "lab";
    public static final String LAB_COLUMN_ID = "_id";
    public static final String LAB_COLUMN_NAME = "name";
    public static final String LAB_COLUMN_CATEGORY = "labs";
    public static final String LABS_COLUMN_NUMBER = "number";
    public static final String LABS_COLUMN_DONE = "done";
    public static final String LABS_COLUMN_TASKCOUNT = "taskCount";
    public static final String LABS_COLUMN_AUTHOR = "author";

    // Single lab table
    public static final String SINGLE_LAB_TABLE = "single_lab";
    public static final String TASK_COLUMN_ID = "_id";
    public static final String TASK_COLUMN_NAME = "name";
    public static final String TASK_COLUMN_PARENT = "parent";
    public static final String TASK_COLUMN_DONE = "done";
    public static final String TASK_COLUMN_NUMBER = "number";
    public static final String TASK_COLUMN_TEXT = "taskText";
    public static final String TASK_COLUMN_FORMULA = "formula";








    public static final int[] labNums = new int[] {1,2,6,3,4,5};
    public static final String[] taskNames = new String[] {"Подогреть",
                                                            "Охладить"};
    public static final String[] taskTexts = new String[] {"Осторожно поднести горелку к донышку",
                                                            "Закрыть фитль колпачком и поместить колбу в подставку"};

}



