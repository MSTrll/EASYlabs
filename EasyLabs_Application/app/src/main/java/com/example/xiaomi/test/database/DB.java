package com.example.xiaomi.test.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.xiaomi.test.activities.MainActivity.*;
import static com.example.xiaomi.test.Consts.*;


public class DB {

    private static final String DB_NAME = "mydb";
    private static final int DB_VERSION = 1;

    private static final String LABS_TABLE_CREATE = "create table "
            + LABS_TABLE + "(" + LABS_COLUMN_ID
            + " integer primary key, " + LABS_COLUMN_NAME + " text" + ");";

    private static final String LAB_TABLE_CREATE = "create table "
            + LAB_TABLE + "(" + LAB_COLUMN_ID
            + " integer primary key autoincrement, " + LAB_COLUMN_NAME
            + " text, " + LAB_COLUMN_CATEGORY + " integer, " + LABS_COLUMN_NUMBER + " text, "
            + LABS_COLUMN_TASKCOUNT + " integer, " + LABS_COLUMN_AUTHOR + " text, "
            + LABS_COLUMN_DONE + " integer" + ");";
    private static final String SINGLE_LAB_TABLE_CREATE = "create table " + SINGLE_LAB_TABLE
            + "(" + TASK_COLUMN_ID + " integer primary key autoincrement, " + TASK_COLUMN_NAME
            + " text, " + TASK_COLUMN_PARENT + " integer, " + TASK_COLUMN_DONE + " integer,"
            + TASK_COLUMN_NUMBER + " text, " + TASK_COLUMN_TEXT + " text, " + TASK_COLUMN_FORMULA
            + " text" + ");";

    private final Context mCtx;

    private DBHelper mDBHelper;
    private static SQLiteDatabase mDB;


    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открываем подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрываем подключение
    public void close() {
        if (mDBHelper != null)
            mDBHelper.close();
    }

    // данные по категариям
    public Cursor getLabsData() {
        return mDB.query(LABS_TABLE, null, null, null, null, null, null);
    }

    // данные по лабам конкретной категории
    public Cursor getLabData(long categoryID) {
        return mDB.query(LAB_TABLE, null, LAB_COLUMN_CATEGORY + " = "
                + categoryID, null, null, null, null);
    }

    public Cursor getTaskData(int labNumber) {
        return mDB.query(SINGLE_LAB_TABLE, null, TASK_COLUMN_PARENT + " = " + labNumber,
                null, null, null, null);
    }

    public Cursor getTaskData(int labNumber, String column) {
        return mDB.query(SINGLE_LAB_TABLE, new String[]{column}, TASK_COLUMN_PARENT + " = " + labNumber,
                null, null, null, null);
    }

    public Cursor getLabNumberByTitle(String title) {
        return mDB.query(LAB_TABLE, new String[]{LABS_COLUMN_NUMBER}, LAB_COLUMN_NAME + " = ?",
                new String[]{title}, null, null, null);
    }

    public int[] getProgress(String title) {
        Cursor cursor = getLabNumberByTitle(title);
        cursor.moveToFirst();
        String labNum = cursor.getString(0);
        cursor = getTaskData(Integer.parseInt(labNum), TASK_COLUMN_DONE);
        cursor.moveToFirst();
        int taskCount = cursor.getCount();
        int taskCountDone = 0;
        for (int i = 0; i < taskCount; i++) {
            taskCountDone += cursor.getInt(0);
            cursor.moveToNext();
        }
        return new int[]{taskCountDone, taskCount};
    }

    private void newLab(SQLiteDatabase db, String name, int num, int category) {
        ContentValues cv = new ContentValues();
        cv.put(LAB_COLUMN_CATEGORY, category);
        cv.put(LAB_COLUMN_NAME, name);
        cv.put(LABS_COLUMN_NUMBER, "" + num);
        cv.put(LABS_COLUMN_TASKCOUNT, 2);
        cv.put(LABS_COLUMN_AUTHOR, "Sergei");
        cv.put(LABS_COLUMN_DONE, 0);
        db.insert(LAB_TABLE, null, cv);
    }

    public static void newLab(String name, int category, int num) {
        ContentValues cv = new ContentValues();
        cv.put(LAB_COLUMN_CATEGORY, category);
        cv.put(LAB_COLUMN_NAME, name);
        cv.put(LABS_COLUMN_NUMBER, "" + num);
        cv.put(LABS_COLUMN_TASKCOUNT, 0);
        cv.put(LABS_COLUMN_AUTHOR, "Sergei");
        cv.put(LABS_COLUMN_DONE, 0);
        mDB.insert(LAB_TABLE, null, cv);
        setLast();
    }

    public void deleteLab(String name) {

        mDB.delete(LAB_TABLE, LAB_COLUMN_NAME + " = ?",
                new String[]{name});
        setLast();
    }

    public void updateLab(String name, String newName, int num) {
        ContentValues cv = new ContentValues();
        cv.put(LABS_COLUMN_NAME, newName);
        cv.put(LABS_COLUMN_NUMBER, "" + num);
        mDB.update(LAB_TABLE, cv, LAB_COLUMN_NAME + " = ?",new String[] { name });
    }

    public static void newTask(String name, String num, int parent,
                               String text, String formula) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_COLUMN_NAME, name);
        cv.put(TASK_COLUMN_DONE, 0);
        cv.put(TASK_COLUMN_PARENT, parent);
        cv.put(TASK_COLUMN_NUMBER, num);
        cv.put(TASK_COLUMN_TEXT, text);
        cv.put(TASK_COLUMN_FORMULA, formula);
        mDB.insert(SINGLE_LAB_TABLE, null, cv);
        Cursor cursor = mDB.query(LAB_TABLE, new String[] { LABS_COLUMN_TASKCOUNT },
                LABS_COLUMN_NUMBER + " = \"14\"", null,
                null, null, null);
        Log.d("DATABASE", "PARENT:    -----    " + parent);
        cursor.moveToFirst();
        int taskC = cursor.getInt(0);
        cv = new ContentValues();
        cv.put(LABS_COLUMN_TASKCOUNT, taskC);
        mDB.update(LAB_TABLE, cv, LABS_COLUMN_NUMBER + " = ?", new String[]{ "" + parent });
    }

    public static void newTask(SQLiteDatabase db, String name, int parent, String num,
                               String text, String formula) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_COLUMN_NAME, name);
        cv.put(TASK_COLUMN_PARENT, parent);
        cv.put(TASK_COLUMN_DONE, 0);
        cv.put(TASK_COLUMN_NUMBER, num);
        cv.put(TASK_COLUMN_TEXT, text);
        cv.put(TASK_COLUMN_FORMULA, formula);
        db.insert(SINGLE_LAB_TABLE, null, cv);
    }

    public void setTaskDone(String labNum, String taskNum) {
        ContentValues cv = new ContentValues();
        cv.put(TASK_COLUMN_DONE, 1);
        mDB.update(SINGLE_LAB_TABLE, cv, TASK_COLUMN_PARENT + " = ? AND "
                + TASK_COLUMN_NUMBER + " = ? ", new String[]{labNum, taskNum});
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            ContentValues cv = new ContentValues();

            // названия категорий
            String[] groups = new String[]{"My Labs", "All Labs"};

            // Создаём категории(MyLabs, AllLabs)
            db.execSQL(LABS_TABLE_CREATE);
            for (int i = 0; i < groups.length; i++) {
                cv.put(LABS_COLUMN_ID, i + 1);
                cv.put(LABS_COLUMN_NAME, groups[i]);
                db.insert(LABS_TABLE, null, cv);
            }


            // Создаём таблицу лаб
            db.execSQL(LAB_TABLE_CREATE);
            cv.clear();
            for (int i = 0; i < myLabs.length; i++) {
                newLab(db, myLabs[i], labNums[i], 1);
            }
            for (int i = 0; i < allLabs.length; i++) {
                newLab(db, allLabs[i], labNums[i + 3], 2);
            }

            // Создаём таблицы заданий
            db.execSQL(SINGLE_LAB_TABLE_CREATE);
            cv.clear();
            for (int i = 0; i < 6; i++) {
                String t1name = taskNames[0];
                String t2name = taskNames[1];
                String t1text = taskTexts[0];
                String t2text = taskTexts[1];
                newTask(db, t1name, labNums[i], "1", t1text, "E = mc^2");
                newTask(db, t2name, labNums[i], "2", t2text, "S = U*T");
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

}