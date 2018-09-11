package com.example.raindi.pumpercontrol.interfaces;

import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * Created by Administrator on 2018/2/7.
 */

public interface callBack {
    public void messageArrived(String topic, MqttMessage message);
    public void success();
    public void failed();
}
