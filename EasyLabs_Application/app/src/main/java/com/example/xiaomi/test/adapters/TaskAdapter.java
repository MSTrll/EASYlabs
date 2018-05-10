package com.example.xiaomi.test.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.xiaomi.test.R;

import static com.example.xiaomi.test.activities.MainActivity.database;

public class TaskAdapter extends SimpleCursorAdapter {
    String labNum;

    public TaskAdapter(Context context, int layout, Cursor c, String[] from, int[] to, String labNumber) {
        super(context, layout, c, from, to);
        labNum = labNumber;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View targetView = super.getView(position, convertView, parent);
        CheckBox cb = (CheckBox) targetView.findViewById(R.id.taskDone);
        cb.setOnCheckedChangeListener(myCheckChangeList);
        cb.setTag(position);
        Log.d("myLog", "getView, checkBox pos: " + position + " isChecked" + cb.isChecked());
        return targetView;
    }

    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (isChecked) {
                int taskNumber = (int)buttonView.getTag() + 1;
                String taskNum = "" + taskNumber;
                database.setTaskDone(labNum, taskNum);
                Log.d("myLog", "CheckBox from: lab #" + labNum + ", Task #" + taskNum);
            }
        }
    };
}
