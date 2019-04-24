package com.zcm.ui.widget;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;

import com.zcm.ui.basearch.BaseActivity;
import com.zcm.ui.widget.H5WebView.PageStatusListener;

/**
 * Create by zcm on 2018/5/16 下午3:14
 */
public class H5Activity extends BaseActivity implements PageStatusListener{

    H5WebView h5WebView;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.zcm.ui.R.layout.activity_h5_layout);
        h5WebView=findViewById(com.zcm.ui.R.id.h5_webview);
        h5WebView.setPageStatusListener(this);
        url=getIntent().getStringExtra("H5Url");
        h5WebView.loadUrl(url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {

    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        h5WebView.backPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        h5WebView.release();
    }
}
