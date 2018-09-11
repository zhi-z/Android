package com.example.raindi.pumpercontrol.entities;

/**
 * Created by Administrator on 2018/2/7.
 */

public class ServiceBean {

    /**
     * serviceId : Pumper
     * serviceType : Pumper
     * data : {"Speed":0,"WaterPressure":0,"Power":0}
     * eventTime : 20180207T032533Z
     */

    private String serviceId;
    private String serviceType;
    private DataBean data;
    private String eventTime;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public static class DataBean {
        /**
         * Speed : 0
         * WaterPressure : 0
         * Power : 0
         */

        private int Speed;
        private int WaterPressure;
        private int Power;

        public int getSpeed() {
            return Speed;
        }

        public void setSpeed(int Speed) {
            this.Speed = Speed;
        }

        public int getWaterPressure() {
            return WaterPressure;
        }

        public void setWaterPressure(int WaterPressure) {
            this.WaterPressure = WaterPressure;
        }

        public int getPower() {
            return Power;
        }

        public void setPower(int Power) {
            this.Power = Power;
        }

        public String toString(){
            return "Speed="+Speed+","+"WaterPressure="+WaterPressure+","+"Power="+Power;
        }
    }
}
