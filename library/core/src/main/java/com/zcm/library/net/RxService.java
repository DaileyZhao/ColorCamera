package com.zcm.library.net;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by zcm on 2018/1/12.
 */

public class RxService {
    public static final long DEFAULT_TIMEOUT=30; //默认的超时时间

    private Application context; //全局上下文
    private Handler scheduler;  //切主线程调度器
    private OkHttpClient okHttpClient;  //okhttp实例
    private int mRetryCount;   //重试次数

    private RxService() {
        mRetryCount=3;
        scheduler=new Handler(Looper.getMainLooper());
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(loggingInterceptor);

        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);

        builder.retryOnConnectionFailure(true);

        SecurityUtils.SSLParams sslParams=SecurityUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager);
        builder.hostnameVerifier(SecurityUtils.UnSafeHostnameVerifier);
        okHttpClient=builder.build();
    }

    private static class RxHolder{
        private static RxService instance=new RxService();
    }

    public static RxService getSingleTon() {
        return RxHolder.instance;
    }

    public RxService init(Application app){
        context=app;
        return this;
    }

    public RxService setOkHttpClient(OkHttpClient client){
        this.okHttpClient=client;
        return this;
    }

    public RxService setRetryCount(int retryCount){
        mRetryCount=retryCount;
        return this;
    }

    public OkHttpClient getOkHttpClient(){
        if (okHttpClient==null) throw new NullPointerException("please set a OkHttpClient instance First!!!");
        return okHttpClient;
    }

    public void cancelTag(Object tag){
        if (tag==null) return;
        for (Call call:getOkHttpClient().dispatcher().queuedCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
        for (Call call:getOkHttpClient().dispatcher().runningCalls()){
            if (tag.equals(call.request().tag())){
                call.cancel();
            }
        }
    }

    public void cancelAll(){
        for (Call call:getOkHttpClient().dispatcher().queuedCalls()){
            call.cancel();
        }
        for (Call call:getOkHttpClient().dispatcher().runningCalls()){
            call.cancel();
        }
    }
}
