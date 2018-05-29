package com.zcm.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by zcm on 2018/3/10.
 * 自定义可拓展的webView
 */

public class H5WebView extends WebView {
    WebSettings webSettings;
    PageStatusListener pageStatusListener;
    H5WebChromeClient h5WebChromeClient;
    H5WebViewClient h5WebViewClient;

    public H5WebView(Context context) {
        super(context);
        init();
    }

    public H5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public H5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        webSettings = H5WebView.this.getSettings();
        h5WebChromeClient = new H5WebChromeClient();
        h5WebViewClient = new H5WebViewClient();
        this.setWebChromeClient(h5WebChromeClient);
        this.setWebViewClient(h5WebViewClient);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(false);
        webSettings.setDatabaseEnabled(true);
    }

    public void setPageStatusListener(PageStatusListener listener) {
        this.pageStatusListener = listener;
    }

    //销毁Webview
    //在关闭了Activity时，如果Webview的音乐或视频，还在播放。就必须销毁Webview
    //但是注意：webview调用destory时,webview仍绑定在Activity上
    //这是由于自定义webview构建时传入了该Activity的context对象
    //因此需要先从父容器中移除webview,然后再销毁webview:
    public void release() {
        ViewParent parent = getParent();
        if (parent != null && parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this);
        }
        destroy();
    }

    public void backPressed(){
        if (canGoBack()){
            goBack();
        }
    }

    /**
     * 辅助 WebView 处理 Javascript 的对话框,网站图标,网站标题等等
     */
    class H5WebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (pageStatusListener!=null){
                pageStatusListener.onProgressChanged(view, newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (pageStatusListener!=null){
                pageStatusListener.onReceivedTitle(view,title);
            }
        }

        @Override
        public void onReceivedTouchIconUrl(WebView view, String url,
                                           boolean precomposed) {
            // TODO Auto-generated method stub
            super.onReceivedTouchIconUrl(view, url, precomposed);
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog,
                                      boolean isUserGesture, Message resultMsg) {
            // TODO Auto-generated method stub
            return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, false);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    class H5WebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (pageStatusListener != null) {
                pageStatusListener.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (pageStatusListener != null) {
                pageStatusListener.onPageFinished(view, url);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (pageStatusListener != null) {
                pageStatusListener.onReceivedError(view, errorCode, description, failingUrl);
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
            // 不要使用super，否则有些手机访问不了，因为包含了一条 handler.cancel()
            // super.onReceivedSslError(view, handler, error);
            // 接受所有网站的证书，忽略SSL错误，执行访问网页
            handler.proceed();
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
    }

    public interface PageStatusListener {
        void onPageStarted(WebView view, String url, Bitmap favicon);

        void onPageFinished(WebView view, String url);

        void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

        void onProgressChanged(WebView view, int newProgress);

        void onReceivedTitle(WebView view, String title);
    }
}
