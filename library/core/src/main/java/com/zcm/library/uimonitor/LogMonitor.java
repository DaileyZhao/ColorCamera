package com.zcm.library.uimonitor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

/**
 * Create by zcm on 2018/7/31 上午11:58
 * 一个检测UI线程卡顿的类。
 */
public class LogMonitor {
    private static LogMonitor instance=new LogMonitor();
    private HandlerThread mHandlerThread=new HandlerThread("LogMonitor");
    private Handler mHandler;
    private LogMonitor(){
        mHandlerThread.start();
        mHandler=new Handler(mHandlerThread.getLooper());
    }
    private static Runnable runnable=new Runnable() {
        @Override
        public void run() {
            StringBuilder sb=new StringBuilder();
            StackTraceElement[] stackTrace= Looper.getMainLooper().getThread().getStackTrace();
            sb.append("catch UI Block Caton \n");
            for (StackTraceElement s:stackTrace){
                sb.append(s.toString()+"\n");
            }
            Log.e(LogMonitor.class.getSimpleName(),sb.toString());
        }
    };
    public static LogMonitor getInstance(){
        return instance;
    }

    /**
     * 如果“>>>>> Dispatching to”信号发生了，我们就假定发生了卡顿（这里我们设定1秒钟的卡顿判定阈值），
     * 并且发送一个延迟1秒钟的任务，这个任务就用于在子线程打印出造成卡顿的UI线程里的堆栈信息。而如果没有卡顿，
     * 即在1秒钟之内我们检测到了“<<<<< Finished to”信号，就会移除这个延迟1秒的任务。
     */
    public void startMonitor(){
        mHandler.postDelayed(runnable,1000);
    }
    public void removeMonitor(){
        mHandler.removeCallbacks(runnable);
    }

    public void writeMonitor(){
        Looper.getMainLooper().setMessageLogging(new Printer() {
            private static final String START=">>>>> Dispatching";
            private static final String END="<<<<< Finished";
            @Override
            public void println(String x) {
                if (x.startsWith(START)){
                    LogMonitor.getInstance().startMonitor();
                }
                if (x.startsWith(END)){
                    LogMonitor.getInstance().removeMonitor();
                }
            }
        });
    }
}
