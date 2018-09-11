package com.example.raindi.pumpercontrol.managers;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.example.raindi.pumpercontrol.interfaces.callBack;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrator on 2017/11/8.
 */

public class MqttClientManager {
    private static final String TAG = "MqttClientManager";
    private String url = "tcp://iot.raindi.net:1883";
    private String clientId = Build.SERIAL;
    public String topic = "pumperinfo";
    private MqttAndroidClient client;
    private static MqttClientManager mInstance;

    public static MqttClientManager getInstance(){
        if (mInstance == null){
            mInstance = new MqttClientManager();
        }
        return mInstance;
    }

    public void init(Context context, final callBack callback){
        client = new MqttAndroidClient(context,url,clientId);
        client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                if (reconnect){
                    subscribeToTopic(topic,2);
                    Log.d(TAG,"reconnect to "+serverURI);
                }else {
                    Log.d(TAG,"connect to "+serverURI);
                }
                callback.success();
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d(TAG,"connectionLost");
                callback.failed();
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d(TAG,message.toString());
                callback.messageArrived(topic,message);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(false);
        try {
            client.connect(connectOptions, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(TAG,"connect success");
                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    client.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic(topic,2);
                    callback.success();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d(TAG,"connect fail");
                    callback.failed();
                }
            });
        } catch (MqttException e) {
            Log.d(TAG,"connect error");
            e.printStackTrace();
            callback.failed();
        }
    }

    public void disconnect(){
        if (client != null){
            try {
                client.disconnect();
                client.unregisterResources();
            } catch (MqttException e) {
                e.printStackTrace();
            }
            client = null;
        }
    }

    public void subscribeToTopic(String topic,int qos){
        if (client != null){
            try {
                client.subscribe(topic, qos, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.d(TAG,"subscribe success");
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.d(TAG,"subscribe fail");
                    }
                });
            } catch (MqttException e) {
                Log.d(TAG,"subscribe error");
                e.printStackTrace();
            }
        }
    }

    public void publish(String topic,String message){
        if (client != null){
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(message.getBytes());
            try {
                client.publish(topic,mqttMessage);
                if (!client.isConnected()){
                    Log.d(TAG,client.getBufferedMessageCount()+" messages in buffer");
                }
            } catch (MqttException e) {
                Log.d(TAG,"publish error");
                e.printStackTrace();
            }
        }
    }

    public void publish(String message){
        if (client != null){
            MqttMessage mqttMessage = new MqttMessage();
            mqttMessage.setPayload(message.getBytes());
            try {
                if (client.isConnected()) {
                    client.publish(topic,mqttMessage);
                }
            } catch (MqttException e) {
                Log.d(TAG,"publish error");
                e.printStackTrace();
            }
        }
    }
}
