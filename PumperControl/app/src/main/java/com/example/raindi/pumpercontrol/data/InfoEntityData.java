package com.example.raindi.pumpercontrol.data;

import android.app.Application;

import com.example.raindi.pumpercontrol.entities.PumperDisplayEntity;

/**
 * Created by RD007 on 2018/9/5.
 */

public class InfoEntityData extends Application{
    private PumperDisplayEntity infoEntity;
    private static String Url = "";
    private static String sendUrl = "";

    public static String getUrl() {
        return Url;
    }

    public static void setUrl(String url) {
        Url = url;
    }
    public static void setSendUrl(String sendUrl1) {
        sendUrl = sendUrl1;
    }

    public static String getSendUrl() {
        return sendUrl;
    }
    public PumperDisplayEntity getInfoEntity() {
        return infoEntity;
    }

    public void setInfoEntity(PumperDisplayEntity infoEntity) {
        this.infoEntity = infoEntity;
    }
}
