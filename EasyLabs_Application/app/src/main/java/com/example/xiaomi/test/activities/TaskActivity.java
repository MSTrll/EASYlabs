package com.example.xiaomi.test.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xiaomi.test.R;
import com.example.xiaomi.test.adapters.TaskAdapter;

import static com.example.xiaomi.test.Consts.TASK_COLUMN_DONE;
import static com.example.xiaomi.test.Consts.TASK_COLUMN_NAME;
import static com.example.xiaomi.test.Consts.TASK_COLUMN_NUMBER;
import static com.example.xiaomi.test.Consts.TASK_COLUMN_TEXT;
import static com.example.xiaomi.test.activities.MainActivity.database;

public class TaskActivity extends AppCompatActivity {
    private int labNum;
    public static int taskDoneID = R.id.taskDone;

    TextView tvLabName;
    Button btnNewTask;
    ListView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Intent intent = getIntent();
        String slabNum = intent.getStringExtra("labNum");
//        String[] string = intent.getStringArrayExtra("valuesNames"); //
//        TreeMap<String, double[]> stringd = (TreeMap<String, double[]>) intent.getSerializableExtra("data");
//        double[] speed = stringd.get(string[1]);
//        double[][] arrs
//        for (int i=0;i<string.length;i++) {

        labNum = Integer.parseInt(slabNum);

        // Настраиваем View
        tvLabName = (TextView) findViewById(R.id.tvLabName);
        tvLabName.setText(intent.getStringExtra("labName"));
        btnNewTask = (Button) findViewById(R.id.btnNewTask);
        taskList = (ListView) findViewById(R.id.taskList);

        Cursor cursor = database.getTaskData(labNum);
        startManagingCursor(cursor);

        String[] from = new String[] { TASK_COLUMN_NUMBER, TASK_COLUMN_NAME,
                                        TASK_COLUMN_TEXT, TASK_COLUMN_DONE};
        int[] to = new int[] { R.id.taskNum, R.id.taskName, R.id.taskText,
                                R.id.taskDone};

        TaskAdapter scAdapter = new TaskAdapter(this, R.layout.task_item,
                cursor, from, to, labNum + "");
        scAdapter.setViewBinder(new MyViewBinder());
        taskList.setAdapter(scAdapter);



//        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CheckBox tv = (CheckBox) view.findViewById(R.id.taskDone);
//
//            }
//        });

    }

    public class MyViewBinder implements ViewBinder {

        @Override
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            int i = 0;
            switch (view.getId()) {
                // LinearLayout
                case R.id.taskNum:
                    ((TextView)view).setText(cursor.getString(columnIndex));
                    return true;
                // ProgressBar
                case R.id.taskName:
                    ((TextView)view).setText(cursor.getString(columnIndex));
                    return true;
                case R.id.taskText:
                    ((TextView)view).setText(cursor.getString(columnIndex));
                    return true;
                case R.id.taskDone:
                    String check = cursor.getString(columnIndex);
                    i = Integer.parseInt(check);
                    ((CheckBox)view).setChecked((i == 1));
                    return true;
            }
            return false;
        }
    }
}
