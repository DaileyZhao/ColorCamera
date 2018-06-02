package com.zcm.camera;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zcm.library.net.RxService;
import com.zcm.ui.basearch.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zcm on 2017/12/29.
 */

public class TestActivity extends BaseActivity {
    ImageView imgSplash;
    Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imgSplash=findViewById(R.id.img_splash);
        httpRequest();
        handler=new Handler();
    }
    public void httpRequest(){
        Request request=new Request.Builder().url("http://api.huceo.com/meinv/?key=86a28560b4dd8234233b390691884b36&num=1").build();
        RxService.getSingleTon().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: "+e.getMessage() );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: "+Thread.currentThread().getName() );
                String url="";
                try {
                    JSONObject json=new JSONObject(response.body().string());
                    JSONArray jsonArray=json.optJSONArray("newslist");
                    for (int i=0;i<jsonArray.length();i++){
                        if (i==0){
                            url=jsonArray.optJSONObject(i).optString("picUrl");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String finalUrl = url;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(TestActivity.this).load(finalUrl).into(imgSplash);
                    }
                });
            }
        });
    }
}
