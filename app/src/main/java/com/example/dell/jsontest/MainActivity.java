package com.example.dell.jsontest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "kevin";
    private List<MovieData> dataList = new ArrayList<>();
    public static final int UPDATE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sendRequest();
    }
    public void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://api.douban.com/v2/movie/top250")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    analyzeJson(responseData);
                    Message message = new Message();
                    message.what = UPDATE;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void analyzeJson(String movieData){
        try{
           JSONObject jsonObject = new JSONObject(movieData);
           JSONArray jsonArray = jsonObject.getJSONArray("subjects");
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String title = object.getString("title");
                JSONObject imageObject = object.getJSONObject("images");
                String image = imageObject.getString("small");
                MovieData data = new MovieData();
                data.setTitle(title);
                data.setImage(image);;
                dataList.add(data);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        MovieDataAdapter adapter = new MovieDataAdapter(dataList);
        recyclerView.setAdapter(adapter);
    }
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE:
                    showRecyclerView();
                    break;
                    default:
                        break;
            }

        }
    };
}
