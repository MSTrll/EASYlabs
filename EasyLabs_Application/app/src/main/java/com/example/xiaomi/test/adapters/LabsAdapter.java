package com.example.xiaomi.test.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.example.xiaomi.test.R;

import static com.example.xiaomi.test.Consts.LABS_COLUMN_ID;
import static com.example.xiaomi.test.activities.MainActivity.database;
import static com.example.xiaomi.test.activities.MainActivity.setLast;



public class LabsAdapter extends SimpleCursorTreeAdapter {

    public LabsAdapter(Context context, Cursor cursor, int groupLayout,
                       String[] groupFrom, int[] groupTo, int childLayout,
                       String[] childFrom, int[] childTo) {
        super(context, cursor, groupLayout, groupFrom, groupTo,
                childLayout, childFrom, childTo);
    }

    protected Cursor getChildrenCursor(Cursor groupCursor) {
        // получаем курсор по элементам для конкретной группы
        int idColumn = groupCursor.getColumnIndex(LABS_COLUMN_ID);
        return database.getLabData(groupCursor.getInt(idColumn));
    }

    @Override
    public View newChildView(Context context, Cursor cursor, boolean isLastChild, ViewGroup parent) {
        return super.newChildView(context, cursor, isLastChild, parent);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View targetView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
        TextView tvLabName = (TextView) targetView.findViewById(R.id.text1);
        TextView fg = (TextView) parent.getRootView().findViewById(R.id.category);
        CheckBox cb = (CheckBox) targetView.findViewById(R.id.labDone);
        ProgressBar pb = (ProgressBar) targetView.findViewById(R.id.taskProgress);
        int[] progress = database.getProgress(tvLabName.getText().toString());
        if (progress[1] != 0) {
            int taskProgress = (progress[0] * 100) / progress[1];
            if (taskProgress != 0)
                taskProgress++;
            pb.setProgress(taskProgress);
            if (taskProgress >= 100)
                cb.setChecked(true);
        } else {
            pb.setProgress(0);
            cb.setChecked(false);
        }
        setLast();
        return targetView;
    }
}