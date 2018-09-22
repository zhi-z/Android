package com.example.raindi.pumpercontrol.data;

import android.app.Application;
import android.content.Context;

import com.example.raindi.pumpercontrol.entities.PumperDisplayEntity;

/**
 * Created by RD007 on 2018/9/5.
 */

public class InfoEntityData extends Application{
    private PumperDisplayEntity infoEntity;
    private static String Url = "https://eiiman.raindi.net/api/pumper.json";
    private static String sendUrl = "https://eiiman.raindi.net/api/pumperctl";
    private static int msgType = 0;
    private static long time = 1000;
    public static Context context;  // 选择时间对话框

    public static void setTime(long time) {
        InfoEntityData.time = time;
    }

    public static long getTime() {
        return time;
    }

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

    public static int getMsgType() {
        return msgType;
    }

    public static void setMsgType(int msgType) {
        InfoEntityData.msgType = msgType;
    }




    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    /**
     * 获取上下文
     * @return Context
     */
    public static Context getContext() {
        return context;
    }


}


