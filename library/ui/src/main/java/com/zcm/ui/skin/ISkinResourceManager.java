package com.zcm.ui.skin;

import android.content.res.Resources;

import com.zcm.ui.skin.callback.ISkinChangedListener;
import com.zcm.ui.skin.callback.ISkinChangingCallback;


/**
 * Created by lichongyang on 2016/3/8.
 */
public interface ISkinResourceManager {
    /**
     * 重置皮肤为默认皮肤
     */
    void resetSkin();

    /**
     * 切换皮肤
     * @param skinPluginPath 皮肤包全路径
     * @param pkgName 皮肤包包名
     * @param callback 处理回调
     */
    void changeSkin(final String skinPluginPath, final String pkgName, ISkinChangingCallback callback);

    /**
     * 添加皮肤变更监听
     * @param listener
     */
    void addChangedListener(ISkinChangedListener listener);

    /**
     * 移除皮肤变更监听
     * @param listener
     */
    void removeChangedListener(ISkinChangedListener listener);

    /**
     * 获取当前ResourceManager
     * @return
     */
    ResourceManager getResourceManager();

    /**
     * 获取默认Resources
     * @return
     */
    Resources getDefResources();

    /**
     * 是否使用的默认皮肤
     * @return
     */
    boolean isUseDefaultSkin();
}
