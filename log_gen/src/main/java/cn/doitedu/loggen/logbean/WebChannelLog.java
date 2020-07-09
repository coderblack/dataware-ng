package cn.doitedu.loggen.logbean;

import java.util.HashMap;

/**
 * web端日志
 */
public class WebChannelLog {
    private long timeStamp;
    private String sessionId;
    private String account;

    private String deviceId;   // pc端无，移动设备有
    private String deviceType;   // pc端无，移动设备有
    private String resolution;
    private String osName;
    private String osVersion;
    private String browser; // 浏览器

    /*
    private String appId;
    private String appVersion;
    private String releaseChannel;

    private double latitude;
    private double longitude;
    */

    private String ip;

    private String netType;   // pc端无，移动设备有
    private String carrier;   // pc端无，移动设备有

    private String eventId;
    private HashMap<String,String> eventInfo;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public HashMap<String, String> getEventInfo() {
        return eventInfo;
    }

    public void setEventInfo(HashMap<String, String> eventInfo) {
        this.eventInfo = eventInfo;
    }
}
