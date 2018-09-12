package com.example.raindi.pumpercontrol.activity;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.raindi.pumpercontrol.R;

/**
 * Created by RD007 on 2018/9/12.
 */

public class UnitSwithcTitleLayout extends LinearLayout{

    public UnitSwithcTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.unit_switch_title_lalyout, this);
        //添加监听事件
        Button titleBack = (Button) findViewById(R.id.unit_switch_back);
        titleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束当前Activity
                ((Activity) getContext()).finish();
            }
        });
    }
}
