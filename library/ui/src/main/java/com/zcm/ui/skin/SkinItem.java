package com.zcm.ui.skin;

/**
 * Created by lichongyang on 2016/3/7.
 */
public class SkinItem {
    public static final int TYPE_DEFAULT = 0;
    public static final int TYPE_FREE = 1;
    public static final int TYPE_FESTIVAL = 2;

    /**
     * 皮肤的id
     */
    String id;
    /**
     * 皮肤包名
     */
    String pkgName;
    /**
     * 皮肤名称
     */
    String name;
    /**
     * 皮肤作者
     */
    String author;
    /**
     * 皮肤版本号
     */
    int version;

    /**
     * 0免费/1默认/2经典/3节日
     */
    int type;

    /**
     * 使用的fe皮肤模板id
     */
    String feSkinId = "";

    String filePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFeSkinId() {
        return feSkinId;
    }

    public void setFeSkinId(String feSkinId) {
        this.feSkinId = feSkinId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
