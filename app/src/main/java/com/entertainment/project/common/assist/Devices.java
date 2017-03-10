package com.entertainment.project.common.assist;

import java.io.Serializable;

/**
 * Created by spf on 16/10/27.
 * 设备
 */
public class Devices implements Serializable{
    /**
     * 设备ID
     */
    private String deviceID;
    /**
     * 厂商
     */
    private String manufacturer;
    /**
     * 机器型号
     */
    private String devType;
    /**
     * 系统版本
     */
    private String osVersion;
    /**
     * 系统类型（“ios”, “android”）
     */
    private String osType;
    /**
     * 极光
     */
    private String registerID;
    /**
     * 设备信息
     */
    private String deviceInfo;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDevType() {
        return devType;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getRegisterID() {
        return registerID;
    }

    public void setRegisterID(String registerID) {
        this.registerID = registerID;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
}
