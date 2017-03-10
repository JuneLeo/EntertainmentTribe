package com.entertainment.project.application;

/**
 * Created by Sick on 2016/10/27.
 */
public class CrashReport {

    private Long appUserId=0l;
    private String crashTime="";
    private String deviceInfo="";
    private String model="";
    private String appVersionName="";
    private String appVersionCode="";
    private String crashTrace="";
    private String androidSdkVer="";
    private String androidOSVer="";
    private String manufacturer="";

    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long appUserId) {
        this.appUserId = appUserId;
    }

    public String getCrashTime() {
        return crashTime;
    }

    public void setCrashTime(String crashTime) {
        this.crashTime = crashTime;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAppVersionName() {
        return appVersionName;
    }

    public void setAppVersionName(String appVersionName) {
        this.appVersionName = appVersionName;
    }

    public String getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(String appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getCrashTrace() {
        return crashTrace;
    }

    public void setCrashTrace(String crashTrace) {
        this.crashTrace = crashTrace;
    }

    public String getAndroidSdkVer() {
        return androidSdkVer;
    }

    public void setAndroidSdkVer(String androidSdkVer) {
        this.androidSdkVer = androidSdkVer;
    }

    public String getAndroidOSVer() {
        return androidOSVer;
    }

    public void setAndroidOSVer(String androidOSVer) {
        this.androidOSVer = androidOSVer;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
}
