package com.example.raindi.pumpercontrol.entities;

import java.util.List;

/**
 * Created by Administrator on 2018/2/7.
 */

public class PumperInfoEntity {

    /**
     * connectionInfo : {"protocolType":null}
     * nodeType : GATEWAY
     * deviceInfo : {"signalStrength":null,"mute":"FALSE","supportedSecurity":null,"nodeId":"863703034511191","isSecurity":null,"fwVersion":null,"deviceType":"Pumper","location":"Shenzhen","sigVersion":null,"bridgeId":null,"manufacturerId":"Raindi","batteryLevel":null,"hwVersion":null,"status":"ONLINE","description":null,"protocolType":"CoAP","swVersion":null,"statusDetail":"NONE","mac":null,"manufacturerName":"RaindiTechnologies","name":"demo03","serialNumber":null,"model":"WLF"}
     * services : [{"serviceType":"Pumper","serviceId":"Pumper","data":{"WaterPressure":33,"Speed":2900,"Power":82},"eventTime":"20180207T070740Z","serviceInfo":null}]
     * deviceId : 8477613e-b146-4762-b85d-fe9fc6701999
     * gatewayId : 8477613e-b146-4762-b85d-fe9fc6701999
     * lastModifiedTime : 20180207T070739Z
     * devGroupIds : []
     * createTime : 20180206T122711Z
     */

    private ConnectionInfoBean connectionInfo;
    private String nodeType;
    private DeviceInfoBean deviceInfo;
    private String deviceId;
    private String gatewayId;
    private String lastModifiedTime;
    private String createTime;
    private List<ServicesBean> services;
    private List<?> devGroupIds;

    public ConnectionInfoBean getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfoBean connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public DeviceInfoBean getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfoBean deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<ServicesBean> getServices() {
        return services;
    }

    public void setServices(List<ServicesBean> services) {
        this.services = services;
    }

    public List<?> getDevGroupIds() {
        return devGroupIds;
    }

    public void setDevGroupIds(List<?> devGroupIds) {
        this.devGroupIds = devGroupIds;
    }

    public static class ConnectionInfoBean {
        /**
         * protocolType : null
         */

        private String protocolType;

        public String getProtocolType() {
            return protocolType;
        }

        public void setProtocolType(String protocolType) {
            this.protocolType = protocolType;
        }
    }

    public static class DeviceInfoBean {
        /**
         * signalStrength : null
         * mute : FALSE
         * supportedSecurity : null
         * nodeId : 863703034511191
         * isSecurity : null
         * fwVersion : null
         * deviceType : Pumper
         * location : Shenzhen
         * sigVersion : null
         * bridgeId : null
         * manufacturerId : Raindi
         * batteryLevel : null
         * hwVersion : null
         * status : ONLINE
         * description : null
         * protocolType : CoAP
         * swVersion : null
         * statusDetail : NONE
         * mac : null
         * manufacturerName : RaindiTechnologies
         * name : demo03
         * serialNumber : null
         * model : WLF
         */

        private String signalStrength;
        private String mute;
        private String supportedSecurity;
        private String nodeId;
        private String isSecurity;
        private String fwVersion;
        private String deviceType;
        private String location;
        private String sigVersion;
        private String bridgeId;
        private String manufacturerId;
        private String batteryLevel;
        private String hwVersion;
        private String status;
        private String description;
        private String protocolType;
        private String swVersion;
        private String statusDetail;
        private String mac;
        private String manufacturerName;
        private String name;
        private String serialNumber;
        private String model;

        public String getSignalStrength() {
            return signalStrength;
        }

        public void setSignalStrength(String signalStrength) {
            this.signalStrength = signalStrength;
        }

        public String getMute() {
            return mute;
        }

        public void setMute(String mute) {
            this.mute = mute;
        }

        public String getSupportedSecurity() {
            return supportedSecurity;
        }

        public void setSupportedSecurity(String supportedSecurity) {
            this.supportedSecurity = supportedSecurity;
        }

        public String getNodeId() {
            return nodeId;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public String getIsSecurity() {
            return isSecurity;
        }

        public void setIsSecurity(String isSecurity) {
            this.isSecurity = isSecurity;
        }

        public String getFwVersion() {
            return fwVersion;
        }

        public void setFwVersion(String fwVersion) {
            this.fwVersion = fwVersion;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public void setDeviceType(String deviceType) {
            this.deviceType = deviceType;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getSigVersion() {
            return sigVersion;
        }

        public void setSigVersion(String sigVersion) {
            this.sigVersion = sigVersion;
        }

        public String getBridgeId() {
            return bridgeId;
        }

        public void setBridgeId(String bridgeId) {
            this.bridgeId = bridgeId;
        }

        public String getManufacturerId() {
            return manufacturerId;
        }

        public void setManufacturerId(String manufacturerId) {
            this.manufacturerId = manufacturerId;
        }

        public String getBatteryLevel() {
            return batteryLevel;
        }

        public void setBatteryLevel(String batteryLevel) {
            this.batteryLevel = batteryLevel;
        }

        public String getHwVersion() {
            return hwVersion;
        }

        public void setHwVersion(String hwVersion) {
            this.hwVersion = hwVersion;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getProtocolType() {
            return protocolType;
        }

        public void setProtocolType(String protocolType) {
            this.protocolType = protocolType;
        }

        public String getSwVersion() {
            return swVersion;
        }

        public void setSwVersion(String swVersion) {
            this.swVersion = swVersion;
        }

        public String getStatusDetail() {
            return statusDetail;
        }

        public void setStatusDetail(String statusDetail) {
            this.statusDetail = statusDetail;
        }

        public String getMac() {
            return mac;
        }

        public void setMac(String mac) {
            this.mac = mac;
        }

        public String getManufacturerName() {
            return manufacturerName;
        }

        public void setManufacturerName(String manufacturerName) {
            this.manufacturerName = manufacturerName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public void setSerialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }

    public static class ServicesBean {
        /**
         * serviceType : Pumper
         * serviceId : Pumper
         * data : {"WaterPressure":33,"Speed":2900,"Power":82}
         * eventTime : 20180207T070740Z
         * serviceInfo : null
         */

        private String serviceType;
        private String serviceId;
        private DataBean data;
        private String eventTime;
        private String serviceInfo;

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
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

        public String getServiceInfo() {
            return serviceInfo;
        }

        public void setServiceInfo(String serviceInfo) {
            this.serviceInfo = serviceInfo;
        }

        public static class DataBean {
            /**
             * WaterPressure : 33
             * Speed : 2900
             * Power : 82
             */

            private int WaterPressure;
            private int Speed;
            private int Power;

            public int getWaterPressure() {
                return WaterPressure;
            }

            public void setWaterPressure(int WaterPressure) {
                this.WaterPressure = WaterPressure;
            }

            public int getSpeed() {
                return Speed;
            }

            public void setSpeed(int Speed) {
                this.Speed = Speed;
            }

            public int getPower() {
                return Power;
            }

            public void setPower(int Power) {
                this.Power = Power;
            }
        }
    }
}
