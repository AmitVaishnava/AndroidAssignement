package com.assignement.user;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("type")
    public String type;

    @SerializedName("id")
    public String id;

    @SerializedName("title")
    public String title;

    @SerializedName("dataMap")
    public DataMap dataMap;

    public boolean isCommentEnable;

    public String userComment;

    boolean haveImage;

    public Bitmap imageBitmap;

    public class DataMap{
        @SerializedName("options")
        public List<String> options;
    }
}
