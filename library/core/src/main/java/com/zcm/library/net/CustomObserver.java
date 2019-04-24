package com.zcm.library.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import io.reactivex.disposables.Disposable;

public abstract class CustomObserver<T> extends BaseObserver<T> {
    private Context mContext;
    private Disposable d;

    public CustomObserver(Context context) {
        mContext=context;
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        if (!isConnected(mContext)) {
            Toast.makeText(mContext, "未连接网络", Toast.LENGTH_SHORT).show();
            if (d.isDisposed()) {
                d.dispose();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        if (d.isDisposed()) {
            d.dispose();
        }
        super.onError(e);
    }

    @Override
    public void onComplete() {
        if (d.isDisposed()) {
            d.dispose();
        }
        super.onComplete();
    }


    /**
     * 是否有网络连接，不管是wifi还是数据流量
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        boolean available = info.isAvailable();
        return available;
    }
}
