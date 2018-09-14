package com.example.raindi.pumpercontrol.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.raindi.pumpercontrol.R;

public class NetworkSettingsActivity extends AppCompatActivity {

    final String URL = "url";
    final String SENDURL = "sendUrl";

    EditText uriEditText;
    EditText sendUrlEditText;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_network_settings);

        uriEditText = findViewById(R.id.get_url);
        sendUrlEditText = findViewById(R.id.send_url);

        preferences = getSharedPreferences("NETWORK_SETTING", Activity.MODE_PRIVATE);
        editor = preferences.edit();

        final String beforeUrl = preferences.getString(URL,"https://eiiman.raindi.net/api/pumper.json");
        final String beforeSnedUrl = preferences.getString(SENDURL,"https://eiiman.raindi.net/api/pumperctl");

        uriEditText.setText(beforeUrl);
        sendUrlEditText.setText(beforeSnedUrl);


        findViewById(R.id.btn_network_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((uriEditText.getText().toString().equals(beforeUrl))&(sendUrlEditText.getText().toString().equals(beforeSnedUrl))){
                    System.out.println("------------------------------不需要修改");
                }
                else {
                    editor.putString(URL,uriEditText.getText().toString());
                    editor.putString(SENDURL,sendUrlEditText.getText().toString());

                    if(editor.commit()){
                        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

//    public void saveLastUpdateDataTime(String date){
//        SharedPreferences sp =  getSharedPreferences("UPDATE_DATA_TIME", Activity.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("LastUpdateTime", date);
//        editor.commit();
//    }
}
