package cn.doitedu.loggen.logbean;

import cn.doitedu.loggen.pojo.Event;

import java.util.HashMap;

/**
 * 微信小程序日志
 */
public class WeixinAppChannelLog {
    private long timeStamp;
    private String sessionId;
    private String openid;  // 微信id
    private String account; // 账号

    private String deviceId;
    private String deviceType;
    private String resolution;
    private String osName;
    private String osVersion;

    private double latitude;
    private double longitude;
    private String ip;

    private String netType;
    private String carrier;

    private String eventId;
    private Event properties;

    public void set(long timeStamp, String sessionId, String openid,String account, String deviceId, String deviceType, String resolution, String osName, String osVersion, double latitude, double longitude, String ip, String netType, String carrier, String eventId, Event eventInfo) {
        this.timeStamp = timeStamp;
        this.sessionId = sessionId;
        this.account = account;
        this.account = openid;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.resolution = resolution;
        this.osName = osName;
        this.osVersion = osVersion;

        this.latitude = latitude;
        this.longitude = longitude;
        this.ip = ip;
        this.netType = netType;
        this.carrier = carrier;
        this.eventId = eventId;
        this.properties = eventInfo;
    }


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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public Event getProperties() {
        return properties;
    }

    public void setProperties(Event properties) {
        this.properties = properties;
    }
}
