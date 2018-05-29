package com.zcm.library.net.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.TimeZone;

/**
 * Create by zcm on 2018/5/21 下午5:56
 */
public class HttpHeaders implements Serializable{
    private static final long serialVersionUID = -4758545054248276033L;

    public static final String FORMAT_HTTP_DATA = "EEE, dd MMM y HH:mm:ss 'GMT'";
    public static final TimeZone GMT_TIME_ZONE = TimeZone.getTimeZone("GMT");

    public static final String HEAD_KEY_RESPONSE_CODE = "ResponseCode";
    public static final String HEAD_KEY_RESPONSE_MESSAGE = "ResponseMessage";
    public static final String HEAD_KEY_ACCEPT = "Accept";
    public static final String HEAD_KEY_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEAD_VALUE_ACCEPT_ENCODING = "gzip, deflate";
    public static final String HEAD_KEY_ACCEPT_LANGUAGE = "Accept-Language";
    public static final String HEAD_KEY_CONTENT_TYPE = "Content-Type";
    public static final String HEAD_KEY_CONTENT_LENGTH = "Content-Length";
    public static final String HEAD_KEY_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEAD_KEY_CONTENT_DISPOSITION = "Content-Disposition";
    public static final String HEAD_KEY_CONTENT_RANGE = "Content-Range";
    public static final String HEAD_KEY_RANGE = "Range";
    public static final String HEAD_KEY_CACHE_CONTROL = "Cache-Control";
    public static final String HEAD_KEY_CONNECTION = "Connection";
    public static final String HEAD_VALUE_CONNECTION_KEEP_ALIVE = "keep-alive";
    public static final String HEAD_VALUE_CONNECTION_CLOSE = "close";
    public static final String HEAD_KEY_DATE = "Date";
    public static final String HEAD_KEY_EXPIRES = "Expires";
    public static final String HEAD_KEY_E_TAG = "ETag";
    public static final String HEAD_KEY_PRAGMA = "Pragma";
    public static final String HEAD_KEY_IF_MODIFIED_SINCE = "If-Modified-Since";
    public static final String HEAD_KEY_IF_NONE_MATCH = "If-None-Match";
    public static final String HEAD_KEY_LAST_MODIFIED = "Last-Modified";
    public static final String HEAD_KEY_LOCATION = "Location";
    public static final String HEAD_KEY_USER_AGENT = "User-Agent";
    public static final String HEAD_KEY_COOKIE = "Cookie";
    public static final String HEAD_KEY_COOKIE2 = "Cookie2";
    public static final String HEAD_KEY_SET_COOKIE = "Set-Cookie";
    public static final String HEAD_KEY_SET_COOKIE2 = "Set-Cookie2";

    public LinkedHashMap<String,String> headersMap;
    private static String acceptLanguage;
    private static String userAgent;

    private void init(){
        headersMap=new LinkedHashMap<>();
    }

    public HttpHeaders(){
        init();
    }
    public void put(String key,String value){
        if (key!=null&&value!=null){
            headersMap.put(key, value);
        }
    }

    public String get(String key){
        return headersMap.get(key);
    }

    public String remove(String key){
        return headersMap.remove(key);
    }

    public void clear(){
        headersMap.clear();
    }
}
