package com.uc3m.hackmenot;

import android.graphics.drawable.Drawable;

public class RowItem {
    private Drawable imageId;
    private String title;
    private String pkg;
 
    public RowItem(Drawable imageId, String title, String pkg) {
        this.imageId = imageId;
        this.title = title;
        this.pkg = pkg;
    }
    public Drawable getImageId() {
        return imageId;
    }
    public void setImageId(Drawable imageId) {
        this.imageId = imageId;
    }
    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return title + "\n" + pkg;
    }
}