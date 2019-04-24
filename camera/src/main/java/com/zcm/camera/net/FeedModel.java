package com.zcm.camera.net;

import java.util.ArrayList;
import java.util.List;

public class FeedModel {
    public List<FeedItem> newslist = new ArrayList<>();

    public static class FeedItem {
        public String ctime = "";
        public String title = "";
        public String description = "";
        public String picUrl = "";
        public String url = "";
    }
}