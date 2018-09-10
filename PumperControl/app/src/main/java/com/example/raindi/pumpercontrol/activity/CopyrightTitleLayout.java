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
 * Created by RD007 on 2018/9/10.
 */

public class CopyrightTitleLayout extends LinearLayout{

    public CopyrightTitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.copyright_title_layout, this);
        //添加监听事件
        Button titleBack = (Button) findViewById(R.id.title_back);
        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束当前Activity
                ((Activity) getContext()).finish();
            }
        });
    }
}
