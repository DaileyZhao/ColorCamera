package com.zcm.library.net.model;

import android.os.Build;
import android.text.TextUtils;

import com.zcm.library.net.RxService;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.internal.Version;

/**
 * Create by zcm on 2018/5/21 下午5:56
 */
public class HttpHeaders implements Serializable {
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

    public LinkedHashMap<String, String> headersMap;
    private static String acceptLanguage;
    private static String userAgent;

    private void init() {
        headersMap = new LinkedHashMap<>();
    }

    public HttpHeaders() {
        init();
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            headersMap.put(key, value);
        }
    }

    public String get(String key) {
        return headersMap.get(key);
    }

    public String remove(String key) {
        return headersMap.remove(key);
    }

    public void clear() {
        headersMap.clear();
    }

    public static long getDate(String gmtTime) {
        try {
            return parseGMTToMillis(gmtTime);
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getDate(long millseconds) {
        return formatMillisToGMT(millseconds);
    }

    public static long getExpiration(String expiresTime) {
        try {
            return parseGMTToMillis(expiresTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getLastModified(String lastModified) {
        try {
            return parseGMTToMillis(lastModified);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getCacheControl(String cacheControl, String pragma) {
        // first http1.1, second http1.0
        if (cacheControl != null) return cacheControl;
        else if (pragma != null) return pragma;
        else return null;
    }

    public static void setAcceptLanguage(String language) {
        acceptLanguage = language;
    }

    /**
     * Accept-Language: zh-CN,zh;q=0.8
     */
    public static String getAcceptLanguage() {
        if (TextUtils.isEmpty(acceptLanguage)) {
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            String country = locale.getCountry();
            StringBuilder acceptLanguageBuilder = new StringBuilder(language);
            if (!TextUtils.isEmpty(country)) {
                acceptLanguageBuilder.append('-').append(country).append(',').append(language).append(";q=0.8");
            }
            acceptLanguage = acceptLanguageBuilder.toString();
            return acceptLanguage;
        }
        return acceptLanguage;
    }

    public static void setUserAgent(String agent) {
        userAgent = agent;
    }

    /**
     * User-Agent: Mozilla/5.0 (Linux; U; Android 5.0.2; zh-cn; Redmi Note 3 Build/LRX22G) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36
     */
    public static String getUserAgent() {
        if (TextUtils.isEmpty(userAgent)) {
            String webUserAgent = null;
            Class<?> sysResCls = null;
            try {
                sysResCls = Class.forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = RxService.getSingleTon().getContext().getString(resId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(webUserAgent)) {
                webUserAgent = Version.userAgent();
            }

            Locale locale = Locale.getDefault();
            StringBuffer buffer = new StringBuffer();
            final String version = Build.VERSION.RELEASE;
            if (version.length() > 0) {
                buffer.append(version);
            } else {
                buffer.append("1.0");
            }
            buffer.append(";");
            final String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase(locale));
                final String country = locale.getCountry();
                if (!TextUtils.isEmpty(country)) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase(locale));
                }
            } else {
                buffer.append("en");
            }
            if ("REL".equals(Build.VERSION.CODENAME)) {
                final String model = Build.MODEL;
                if (model.length() > 0) {
                    buffer.append(";");
                    buffer.append(model);
                }
            }
            final String id = Build.ID;
            if (id.length() > 0) {
                buffer.append("Build/");
                buffer.append(id);
            }
            userAgent = String.format(webUserAgent, buffer, "Mobile");
            return userAgent;
        }
        return userAgent;
    }

    public static long parseGMTToMillis(String gmtTime) throws ParseException {
        if (TextUtils.isEmpty(gmtTime)) return 0;
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US);
        formatter.setTimeZone(GMT_TIME_ZONE);
        Date date = formatter.parse(gmtTime);
        return date.getTime();
    }

    public static String formatMillisToGMT(long milliseconds) {
        Date date = new Date(milliseconds);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_HTTP_DATA, Locale.US);
        simpleDateFormat.setTimeZone(GMT_TIME_ZONE);
        return simpleDateFormat.format(date);
    }

    @Override
    public String toString() {
        return "HttpHeaders{" + "headersMap=" + headersMap + '}';
    }
}
