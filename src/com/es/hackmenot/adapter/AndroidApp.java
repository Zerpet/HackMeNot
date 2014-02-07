package com.es.hackmenot.adapter;

import android.graphics.drawable.Drawable;

public class AndroidApp {
    private Drawable imageId;
    private String title;
    private String pkg;
    private String [] perms;
 
    public AndroidApp(Drawable imageId, String title, String pkg, String [] perms) {
        this.imageId = imageId;
        this.title = title;
        this.pkg = pkg;
        this.perms = perms;
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
    
    public String[] getPerms() {
        return perms;
    }
    public void setPerms(String[] perms) {
        this.perms = perms;
    }
    
    @Override
    public String toString() {
        return title + "\n" + pkg;
    }
}