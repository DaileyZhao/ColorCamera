package com.zcm.library.net.model;

/**
 * Create by zcm on 2018/6/1 下午5:46
 */
public enum HttpMethod {
    GET("GET"),

    POST("POST"),

    PUT("PUT"),

    DELETE("DELETE"),

    HEAD("HEAD"),

    PATCH("PATCH"),

    OPTIONS("OPTIONS"),

    TRACE("TRACE");

    private final String value;

    HttpMethod(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public boolean hasBody() {
        switch (this) {
            case POST:
            case PUT:
            case PATCH:
            case DELETE:
            case OPTIONS:
                return true;
            default:
                return false;
        }
    }
}
