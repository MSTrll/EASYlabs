package com.example.xiaomi.test.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.xiaomi.test.API_for_server.requests.get.GetTasks;
import com.example.xiaomi.test.API_for_server.threads.RequestThread;
import com.example.xiaomi.test.JSONclasses.entities.Lab;
import com.example.xiaomi.test.JSONclasses.entities.Task;
import com.example.xiaomi.test.R;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import static com.example.xiaomi.test.activities.DownloadActivity.labs;
import static com.example.xiaomi.test.activities.MainActivity.database;

/**
 * Created by Xiaomi on 01.05.2018.
 */

public class DownloadAdapter extends SimpleAdapter {

    public DownloadAdapter(Context context, List<Map<String, Object>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View targetView = super.getView(position, convertView, parent);
        TextView tw = (TextView) targetView.findViewById(R.id.dwnb);
        TextView tw2 = (TextView) targetView.findViewById(R.id.dwnNumber);
        tw.setOnClickListener(myClickList);
        Lab lab = new Lab(0, ((TextView) targetView.findViewById(R.id.dwnTitle))
                .getText().toString(), Integer.parseInt(tw2.getText().toString()), "", 0);
        tw.setTag(lab);
        return targetView;
    }

    View.OnClickListener myClickList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int labNum = ((Lab) v.getTag()).getNumber();
            String labAuth = null;
            Log.d("myLog","Number:  -- " + labNum);
            for (int i = 0; i < labs.size() - 1; i++) {
                if (labs.get(i).getNumber() == labNum)
                    labAuth = labs.get(i).getAuthor();
            }
            try {
                RequestThread rThread = new RequestThread(new GetTasks(
                        "GET", labNum, labAuth));
                rThread.start();
                while(rThread.isAlive()) {}
                List<Task> tasks = rThread.getTaskResponse();
//                Log.d("TASKS", tasks.isEmpty() + "");
                int taskC = 0;
                for (int i=0; i < tasks.size() - 1; i++) {
                    database.newTask(tasks.get(i).getName(), "" + tasks.get(i).getNumber(),
                            tasks.get(i).getParent(), tasks.get(i).getText(), tasks.get(i).getFormula());
                    taskC++;
                }
                Log.d("REQ TH", "taskC     ------    " + taskC);
                database.newLab(((Lab) v.getTag()).getTitle(), 2, labNum);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    };
}
