package com.example.raindi.pumpercontrol.activity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.raindi.pumpercontrol.data.InfoEntityData;
import com.example.raindi.pumpercontrol.entities.PumperDisplayEntity;
import com.example.raindi.pumpercontrol.http.QueryProtocol;


public class LoadingService extends Service {
    boolean runing = false;
    QueryProtocol queryProtocol;
    public LoadingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        runing = true;
        queryProtocol = new QueryProtocol(getApplicationContext());
        System.out.println("--------------------------------------------------开启服务");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (runing) {
                    getInfoEntity().setInfoEntity(queryProtocol.query());
                    PumperDisplayEntity infoEntity = getInfoEntity().getInfoEntity();
                    if (infoEntity != null){
                        runing = false;
                        System.out.println("--------------------------------------------------数据获取成功。");
                    }
                    System.out.println("--------------------------------------------------服务正在运行...");
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public InfoEntityData getInfoEntity(){
        return (InfoEntityData)getApplicationContext();
    }
}


