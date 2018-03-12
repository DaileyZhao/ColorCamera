package com.zcm.library.net;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by zcm on 2018/1/12.
 */

public class RxService {
    public static final long DEFAULT_TIMEOUT=30000; //默认的超时时间

    private Application context; //全局上下文
    private Handler scheduler;  //切主线程调度器
    private OkHttpClient okHttpClient;  //okhttp实例
    private int mRetryCount;   //重试次数

    private RxService() {
        mRetryCount=3;
        scheduler=new Handler(Looper.getMainLooper());
        OkHttpClient.Builder builder=new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(loggingInterceptor);

        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.connectTimeout(DEFAULT_TIMEOUT,TimeUnit.MILLISECONDS);

        builder.retryOnConnectionFailure(true);

        SecurityUtils.SSLParams sslParams=SecurityUtils.getSslSocketFactory();
        builder.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager);
        builder.hostnameVerifier(SecurityUtils.UnSafeHostnameVerifier);
        okHttpClient=builder.build();
    }

    private static RxService singleNet = null;

    public static synchronized RxService getSingleTon() {
        if (singleNet == null) {
            synchronized (RxService.class) {
                if (singleNet == null) {
                    singleNet = new RxService();
                }
            }
        }
        return singleNet;
    }
    public OkHttpClient getOkHttpClient(){
        if (okHttpClient==null) throw new NullPointerException("please set a OkHttpClient instance First!!!");
        return okHttpClient;
    }
    public OkHttpClient.Builder getBuilder(){
        return okHttpClient.newBuilder();
    }
}
