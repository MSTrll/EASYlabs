package com.example.xiaomi.test.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.TextView;

import com.example.xiaomi.test.R;
import com.example.xiaomi.test.database.DB;

import static com.example.xiaomi.test.Consts.LABS_COLUMN_ID;
import static com.example.xiaomi.test.Consts.LABS_COLUMN_NAME;
import static com.example.xiaomi.test.Consts.LABS_COLUMN_NUMBER;
import static com.example.xiaomi.test.Consts.LAB_COLUMN_NAME;

public class MainActivity extends Activity implements View.OnClickListener {
    private static final int CM_DELETE_ID = 1;
    private static final int CM_UPDATE_ID = 2;
    private static final int DIALOG_NEW = 1;
    private static final int DIALOG_UPDATE = 2;

    public static String[] myLabs;
    public static String[] allLabs;
    Button btnNew;
    FloatingActionButton btnDownload;

    ExpandableListView elvMain;
    SimpleCursorTreeAdapter sctAdapter;
    public static DB database;


    // Last lab
    public static String lastLabName;
    public static TextView lastName;
    public static CheckBox lastDone;
    public static ProgressBar lastProgress;

    // Update Lab
    public String nameToUpdate;
    TextView group;

    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lastName = (TextView) MainActivity.this.findViewById(R.id.lastName);
        lastDone = (CheckBox) MainActivity.this.findViewById(R.id.lastDone);
        lastProgress = (ProgressBar) MainActivity.this.findViewById(R.id.lastProgress);

        myLabs = getResources().getStringArray(R.array.mylabs);
        allLabs = getResources().getStringArray(R.array.alllabs);

        // подключаемся к БД
        database = new DB(this);
        database.open();

        setLast();

        // готовим данные по группам для адаптера
        Cursor cursor = database.getLabsData();
        startManagingCursor(cursor);
        // сопоставление данных и View для групп
        String[] groupFrom = { LABS_COLUMN_NAME };
        int[] groupTo = { R.id.category};
        // сопоставление данных и View для элементов
        String[] childFrom = { LAB_COLUMN_NAME };
        int[] childTo = { R.id.text1 };

        // создаем адаптер и настраиваем список
        sctAdapter = new LabsAdapter(this, cursor,
                R.layout.big_item, groupFrom,
                groupTo, R.layout.item, childFrom,
                childTo);
        elvMain = (ExpandableListView) findViewById(R.id.elvMain);
        elvMain.setAdapter(sctAdapter);
        registerForContextMenu(elvMain);

        elvMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Log.d("mLog", "onChildClick groupPosition = " + groupPosition +
                        " childPosition = " + childPosition +
                        " id = " + id);
                TextView tv = (TextView) v.findViewById(R.id.text1);
                String labName = tv.getText().toString();
                Cursor c = database.getLabNumberByTitle(labName);
                int columnIndex = c.getColumnIndex(LABS_COLUMN_NUMBER);
                Log.d("mLog", "Column index: " + columnIndex);
                c.moveToFirst();
                int labNum = c.getInt(0);
                Intent intent = new Intent(MainActivity.this, TaskActivity.class);
                String slabNum = "" + labNum;
                intent.putExtra("labNum", slabNum);
                intent.putExtra("labName", labName);
                startActivity(intent);
                return true;
            }
        });
        btnNew = (Button) findViewById(R.id.btnNew);
        btnNew.setOnClickListener(this);
        btnDownload = (FloatingActionButton) findViewById(R.id.download_btn);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,
                                            DownloadActivity.class);
                startActivity(intent);
            }
        });

    }

    public static void setLast() {
        Cursor cursor = database.getLabData(1);
        cursor.moveToLast();
        String labTitle = cursor.getString(cursor.getColumnIndexOrThrow(LAB_COLUMN_NAME));
        lastLabName = labTitle;
        lastName.setText(labTitle);
        cursor.moveToLast();
        int[] labprog = database.getProgress(labTitle);
        if (labprog[1] != 0) {
            int taskProgress = (labprog[0]*100)/labprog[1];
            if (taskProgress != 0)
                taskProgress++;
            lastProgress.setProgress(taskProgress);
            if (taskProgress >= 100)
                lastDone.setChecked(true);
            Log.d("mLog", "setLast");
        } else {
            lastProgress.setProgress(0);
            lastDone.setChecked(false);
        }

    }

    @Override
    public void onClick(View v) {
        Dialog dialog = new Dialog(MainActivity.this);
        showDialog(DIALOG_NEW);
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_NEW) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog, null);
            final EditText etNum = (EditText) view.findViewById(R.id.etNum);
            final EditText etTitle = (EditText) view.findViewById(R.id.etTitle);

            adb.setView(view);
            adb.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (etNum.getText().toString() != "" &&
                            etTitle.getText().toString() != "") {
                        String number = etNum.getText().toString();
                        String title = etTitle.getText().toString();
                        database.newLab(number + " " + title, 1, Integer.parseInt(number));
                    }
                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            return adb.create();
        }

        if (id == DIALOG_UPDATE) {
            AlertDialog.Builder adb = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog, null);
            final EditText etNum = (EditText) view.findViewById(R.id.etNum);
            final EditText etTitle = (EditText) view.findViewById(R.id.etTitle);

            adb.setView(view);
            adb.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (etNum.getText().toString() != "" &&
                            etTitle.getText().toString() != "") {
                        String number = etNum.getText().toString();
                        String title = etTitle.getText().toString();
                        database.updateLab(nameToUpdate,
                                "№" + number + " " + title, Integer.parseInt(number));
                    }
                }
            });
            adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            return adb.create();
        }
        return super.onCreateDialog(id);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Delete lab");
        menu.add(0, CM_UPDATE_ID, 0, "Update lab");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            ExpandableListView.ExpandableListContextMenuInfo acmi = (ExpandableListView.ExpandableListContextMenuInfo)
                    item.getMenuInfo();
            View v = acmi.targetView;
            TextView tv = (TextView) v.findViewById(R.id.text1);
            String labName = tv.getText().toString();
            Log.d("mLog", labName);
            database.deleteLab(labName);
            sctAdapter.notifyDataSetChanged();
            return true;
        } else if (item.getItemId() == CM_UPDATE_ID){
            ExpandableListView.ExpandableListContextMenuInfo acmi = (ExpandableListView.ExpandableListContextMenuInfo)
                    item.getMenuInfo();
            View v = acmi.targetView;
            TextView tv = (TextView) v.findViewById(R.id.text1);
            String nameToUpdate = tv.getText().toString();
            Log.d("mLog", nameToUpdate);
            Dialog dialog = new Dialog(MainActivity.this);
            showDialog(DIALOG_UPDATE);
            sctAdapter.notifyDataSetChanged();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

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
            group = (TextView) parent.findViewById(R.id.category);
            Log.d("PARENT:  ", group.getText().toString() + (isLastChild?" true":""));

            return super.newChildView(context, cursor, isLastChild, parent);
        }

        @Override
        protected void bindChildView(View view, Context context, Cursor cursor, boolean isLastChild) {
            super.bindChildView(view, context, cursor, isLastChild);
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View targetView = super.getChildView(groupPosition, childPosition, isLastChild, convertView, parent);
            TextView tvLabName = (TextView) targetView.findViewById(R.id.text1);
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
}