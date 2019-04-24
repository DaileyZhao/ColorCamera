package com.zcm.library.net;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetManager {
    private static NetManager netManager;
    private static Retrofit retrofit;
    private static Lock lock = new ReentrantLock();

    public static NetManager getInstance() {
        if (netManager == null) {
            lock.lock();
            try {
                if (netManager == null) {
                    netManager = new NetManager();
                }
            } finally {
                lock.unlock();
            }
        }
        return netManager;
    }

    public void init(){
        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new LogInterceptor())
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .writeTimeout(10000, TimeUnit.MILLISECONDS)
                .connectTimeout(10000,TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .hostnameVerifier(SecurityUtils.UnSafeHostnameVerifier)
                .build();
        retrofit=new Retrofit.Builder()
                .baseUrl("http://api.tianapi.com")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public Retrofit getRetrofit(){
        if (retrofit==null){
            throw new RuntimeException("please call init() method first!!!");
        }
        return retrofit;
    }
}
