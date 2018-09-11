package com.example.raindi.pumpercontrol.entities;

import java.util.List;

/**
 * Created by Administrator on 2018/8/17.
 */

public class PumperDisplayEntity {
    /**
     * count : 2
     * next : null
     * previous : null
     * results : [{"deviceId":"5142e95d-31e0-4ad0-8949-7a3d86aff3e7","sn":"863703034511191","deviceName":"NB05_1191","location":"深圳市南山区阳光二路","customer":"雨滴科技","error":1,"mode":0,"key":0,"power":0,"target":280,"pressure":0,"updated":"2018-08-17T18:09:11.891244+08:00"},{"deviceId":"f053145b-d2d4-4f68-8377-06ff0c0708fa","sn":null,"deviceName":"NB05_1292","location":"Shenzhen","customer":null,"error":0,"mode":0,"key":0,"power":0,"target":65537,"pressure":65537,"updated":"2018-08-17T18:07:32.677202+08:00"}]
     */

    private int count;
    private String next;
    private String previous;
    private List<ResultsBean> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * deviceId : 5142e95d-31e0-4ad0-8949-7a3d86aff3e7
         * sn : 863703034511191
         * deviceName : NB05_1191
         * location : 深圳市南山区阳光二路
         * customer : 雨滴科技
         * status: null,
         * statusDetail: null,
         * error : 1
         * mode : 0
         * key : 0
         * power : 0
         * target : 280
         * pressure : 0
         * updated : 2018-08-17T18:09:11.891244+08:00
         */

        private String deviceId;
        private String sn;
        private String deviceName;
        private String location;
        private String customer;
        private String status;
        private String statusDetail;
        private int error;
        private int mode;
        private int key;
        private int power;
        private int target;
        private int pressure;
        private String updated;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public void setDeviceName(String deviceName) {
            this.deviceName = deviceName;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatusDetail() {
            return statusDetail;
        }

        public void setStatusDetail(String statusDetail) {
            this.statusDetail = statusDetail;
        }

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public int getMode() {
            return mode;
        }

        public void setMode(int mode) {
            this.mode = mode;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public int getPower() {
            return power;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public int getPressure() {
            return pressure;
        }

        public void setPressure(int pressure) {
            this.pressure = pressure;
        }

        public String getUpdated() {
            return updated;
        }

        public void setUpdated(String updated) {
            this.updated = updated;
        }
    }
}
