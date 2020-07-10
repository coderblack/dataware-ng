package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class AccessorInfo {
    public String account;
    public String deviceId;
    public String deviceType;
    public String resolution;
    public String osName;
    public String osVersion;
    public String appId;
    public String appVersion;
    public String releaseChannel;
    public double latitude;
    public double longitude;
    public String ip;
    public String netType;
    public String carrier;

    public static AccessorInfo get() throws Exception {
        AccessorInfo accessorInfo = new AccessorInfo();

        // 70% 概率模拟登陆状态
        if(RandomUtils.nextInt(0,11)>3){
            accessorInfo.account = EventUtil.genAccount();
        }else{
            accessorInfo.account = "";
        }

        accessorInfo.deviceId = RandomStringUtils.random(12,true,true);

        String device = EventUtil.genDevice();
        String[] split = device.split(",");
        accessorInfo.deviceType = split[0];
        accessorInfo.osName = split[1];
        accessorInfo.osVersion = split[2];
        accessorInfo.resolution = split[3];

        accessorInfo.releaseChannel = EventUtil.genReleaseChannel();
        accessorInfo.appId = "cn.doitedu.app1";
        accessorInfo.appVersion = EventUtil.genAppVersions();

        accessorInfo.longitude = EventUtil.genLongitude();
        accessorInfo.latitude = EventUtil.genLatitude();

        accessorInfo.ip = EventUtil.genIp();

        accessorInfo.netType = EventUtil.genNetType();
        accessorInfo.carrier = EventUtil.genCarrier();

        return accessorInfo;
    }
}
