package com.example.xiaomi.test.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.xiaomi.test.API_for_server.requests.get.get_all.GetAllLabs;
import com.example.xiaomi.test.API_for_server.threads.RequestThread;
import com.example.xiaomi.test.JSONclasses.entities.Lab;
import com.example.xiaomi.test.R;
import com.example.xiaomi.test.adapters.DownloadAdapter;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DownloadActivity extends AppCompatActivity {
    SimpleAdapter dwnAdapter;
    ListView dwnList;

    public static List<Lab> labs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        try {
            RequestThread rThread = new RequestThread(new GetAllLabs(
                    "GET"));
            rThread.start();

            //waiting for concluding
            while (rThread.isAlive()) {}

            labs = rThread.getBigResponse();
            Log.d("", "LABS SIZE:   ---   " + labs.get(0).getTitle());

            List<Map<String, Object>> downloadList = new ArrayList<>();
            Map<String, Object> map;
            for (int i = 0; i < labs.size() - 1; i++) {
                map = new HashMap<String, Object>();
                Log.d("", " IIIIIII     ---- " + i);
                String title = labs.get(i).getTitle();
                int num = labs.get(i).getNumber();
                map.put("title", title);
                map.put("number", num);
//                map.put("author", l.getAuthor());
                downloadList.add(map);
            }

            String[] from = { "title", "number" };
            int[] to = { R.id.dwnTitle, R.id.dwnNumber,  };


            // создаем адаптер и настраиваем список
            dwnAdapter = new DownloadAdapter(this, downloadList, R.layout.download_item,
                    from, to);
            dwnAdapter.setViewBinder(new DownloadBinder());

            dwnList = (ListView) findViewById(R.id.downloadList);
            dwnList.setAdapter(dwnAdapter);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}

class DownloadBinder implements SimpleAdapter.ViewBinder {

    @Override
    public boolean setViewValue(View view, Object data, String textRepresentation) {

        switch (view.getId()) {
            case R.id.dwnTitle:
                ((TextView) view).setText(data.toString());
                break;
            case R.id.dwnNumber:
                String num = data.toString();
                ((TextView) view).setText(num);
        }
        return true;
    }
}