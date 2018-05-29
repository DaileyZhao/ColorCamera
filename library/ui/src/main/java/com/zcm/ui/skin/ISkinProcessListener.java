package com.zcm.ui.skin;

/**
 * Created by lichongyang on 2016/3/7.
 * 启用皮肤回调接口，各个回调函数均在主线程执行
 */
public interface ISkinProcessListener {
    /**
     * 启用皮肤失败
     */
    int ERROR_ENABLE_ERROR = -1;
    /**
     * 启用低版本皮肤
     */
    int ERROR_ENABLE_LOWER_VERSION = -2;

    /**
     * 文件不存在
     */
    int ERROR_FILE_NOT_EXIST = -3;

    /**
     * 文件拷贝失败
     */
    int ERROR_FILE_COPY_ERROR = -4;
    /**
     * 皮肤包解析失败
     */
    int ERROR_FILE_PARSE_ERROR = -5;

    /**
     * 皮肤版本低于min_skin_version
     */
    int ERROR_INVALID_VERSION = -6;

    /**
     * 开始启用
     */
    int STATE_ENABLE_START = 1;

    void onProcess(String id, int version, int state);

    void onSuccess(String id, int version);

    void onError(String id, int version, int error);
}
