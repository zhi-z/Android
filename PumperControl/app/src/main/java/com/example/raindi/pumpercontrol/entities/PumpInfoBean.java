package com.example.raindi.pumpercontrol.entities;

/**
 * Created by Administrator on 2018/2/7.
 */
/*{"notifyType":"deviceDataChanged",
        "deviceId":"fd3fd274-586d-49fc-a71c-5707611a6478",
        "gatewayId":"fd3fd274-586d-49fc-a71c-5707611a6478",
        "requestId":null,
        "service":
        {"serviceId":"Pumper",
        "serviceType":"Pumper",
        "data":{"Speed":0,
        "WaterPressure":0,
        "Power":0},
        "eventTime":"20180207T032533Z"
        }*/
public class PumpInfoBean {
    private String deviceId;
    private String gatewayId;
    private String requestId;
    private ServiceBean service;

    public ServiceBean getService() {
        return service;
    }

    public void setService(ServiceBean service) {
        this.service = service;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
