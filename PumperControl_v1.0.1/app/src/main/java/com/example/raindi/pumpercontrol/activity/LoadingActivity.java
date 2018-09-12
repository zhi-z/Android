package com.example.raindi.pumpercontrol.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.example.raindi.pumpercontrol.R;
import com.example.raindi.pumpercontrol.data.InfoEntityData;
import com.example.raindi.pumpercontrol.http.QueryProtocol;

/**
 * Created by RD007 on 2018/9/6.
 */

public class LoadingActivity extends Activity{

    Runnable runnabl;
    QueryProtocol queryProtocol;
    boolean runing = true;
    Handler handler = new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loading_layout);

        startService(new Intent(LoadingActivity.this,LoadingService.class));
        queryProtocol = new QueryProtocol(getApplicationContext());

        InfoEntityData.setSendUrl("https://eiiman.raindi.net/api/pumperctl");
        InfoEntityData.setUrl("https://eiiman.raindi.net/api/pumper.json");
        getSharedPreferences("UNIT_SWITCH", Activity.MODE_PRIVATE).getBoolean("unitSwitchFlag",true);

        runnabl = new Runnable() {  //使用handler的postDelayed实现延时跳转
            public void run() {
                runing = false;
                Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        handler.postDelayed(runnabl,5000);//5秒后跳转至应用主界面MainActivity

        getInfoEntityData().setInfoEntity(queryProtocol.query());
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (runing){
                    if(getInfoEntityData().getInfoEntity() == null){
                        getInfoEntityData().setInfoEntity(queryProtocol.query());
                        System.out.println("-----------------------加载数据，加载数据。。。。");
                    }else {
                        runing = false;
                        handler.removeCallbacks(runnabl);
                        Intent intent = new Intent(LoadingActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }.start();

    }

    public InfoEntityData getInfoEntityData(){
        return (InfoEntityData)getApplicationContext();
    }
}
