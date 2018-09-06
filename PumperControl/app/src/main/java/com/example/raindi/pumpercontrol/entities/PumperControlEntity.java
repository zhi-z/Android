package com.example.raindi.pumpercontrol.entities;

/**
 * Created by Administrator on 2018/8/18.
 */

public class PumperControlEntity {

    /**
     * deviceId : f053145b-d2d4-4f68-8377-06ff0c0708fa
     * paras : {"Mode":1,"MsgType":2,"WaterPressur":3,"Status":4}
     */

    private String deviceId;
    private ParasBean paras;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ParasBean getParas() {
        return paras;
    }

    public void setParas(ParasBean paras) {
        this.paras = paras;
    }

    public static class ParasBean {
        /**
         * Mode : 1
         * MsgType : 2
         * WaterPressur : 3
         * Status : 4
         */

        private int Mode;
        private int MsgType;
        private int WaterPressur;
        private int Status;

        public int getMode() {
            return Mode;
        }

        public void setMode(int Mode) {
            this.Mode = Mode;
        }

        public int getMsgType() {
            return MsgType;
        }

        public void setMsgType(int MsgType) {
            this.MsgType = MsgType;
        }

        public int getWaterPressur() {
            return WaterPressur;
        }

        public void setWaterPressur(int WaterPressur) {
            this.WaterPressur = WaterPressur;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }
    }
}
