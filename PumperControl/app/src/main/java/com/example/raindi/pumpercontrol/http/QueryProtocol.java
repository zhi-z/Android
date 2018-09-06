package com.example.raindi.pumpercontrol.http;

import android.content.Context;
import android.util.Log;

import com.example.raindi.pumpercontrol.entities.JsonType;
import com.example.raindi.pumpercontrol.entities.PumperControlEntity;
import com.example.raindi.pumpercontrol.entities.PumperDisplayEntity;
import com.example.raindi.pumpercontrol.utils.Logger;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/4/18.
 */

public class QueryProtocol {
    public static String URL = "https://eiiman.raindi.net/api/pumper.json";
    public static String SEND_URL = "https://eiiman.raindi.net/api/pumperctl";
    public static String mUrl;
    private Gson gson;
    Context mContext;
    public QueryProtocol(Context context){
        gson = new Gson();
        mContext = context;
        mUrl = URL;
    }
    private <T> T parseJson(String json, Class<T> tClass) {
        json = checkJson(json);
        if (json != null){
            return gson.fromJson(json,tClass);
        }
        return null;
    }

    public PumperDisplayEntity query() {
        String json;
        if (!HttpUtils.isConnectAvailable(mContext)){
            return null;
        }
        //请求服务器
        if (HttpUtils.isConnectAvailable(mContext)) {
            json = loadServer();
        } else {
            return null;
        }
        if (json != null){
            System.out.println("------------------------------------json-----------------------------------------");
            Log.d("yjx",json);
            System.out.println("------------------------------------json-----------------------------------------");
        }
        return parseJson(json,PumperDisplayEntity.class);
    }

    public String sendControl(PumperControlEntity entity){
        if (!HttpUtils.isConnectAvailable(mContext)){
            return null;
        }

        String json = gson.toJson(entity);
        Logger.D(json);
        try {
            HttpHelper.HttpResult result = HttpHelper.post(SEND_URL,json);
            return result.getString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setUrl(int suffix) {
        if (suffix == 0){
            mUrl = URL;
        }else {
            mUrl=URL+suffix;
        }
    }

    public static String getUrl() {
        return mUrl;
    }

    //加载服务器
    private String loadServer() {
        HttpHelper.HttpResult httpResult = HttpHelper.get(getUrl());
        String json = null;
        try{
            json = httpResult.getString();
        }catch (Exception e){
            e.printStackTrace();
            json = null;
        }
        return json;
    }

    private String checkJson(String json){
        if (JsonType.getJSONType(json)== JsonType.JSON_TYPE.JSON_TYPE_ARRAY){
            JSONArray arr = null;
            try {
                arr = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (arr.length()>0){
                try {
                    JSONObject jsonObject = (JSONObject) arr.get(0);
                    String data = jsonObject.toString();
                    return data;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else {
            return json;
        }
        return null;
    }

}
