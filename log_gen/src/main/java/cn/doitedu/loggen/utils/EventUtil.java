package cn.doitedu.loggen.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EventUtil {

    public static String genPageId(){
        return RandomUtils.nextInt(1, 1001)+"";
    }

    public static String genAdId(){
        return RandomUtils.nextInt(1, 21)+"";
    }

    public static String genAdLocation(){
        return RandomUtils.nextInt(1, 11)+"";  // 运营位
    }

    public static String genAdCampain(){
        return RandomUtils.nextInt(1, 21)+"";   //所属活动
    }

    public static String genCouponId(){
        return RandomUtils.nextInt(1, 21)+"";
    }

    public static String genUrl(){
        return RandomStringUtils.randomAlphabetic(3)+"/"+RandomStringUtils.randomAlphabetic(3);
    }

    public static String genTitle(){
        return RandomStringUtils.randomAlphabetic(3)+" "+RandomStringUtils.randomAlphabetic(3)+" "+RandomStringUtils.randomAlphabetic(3);
    }

    public static String genUtmSource(){
        return RandomUtils.nextInt(1, 11)+"";
    }

    public static String genUtmLocType(){
        return RandomUtils.nextInt(1, 6)+"";
    }

    public static String genProductId(){
        return RandomUtils.nextInt(1, 1001)+"";
    }

    public static String genIp(){
        return RandomUtils.nextInt(10,250)+"."+RandomUtils.nextInt(10,250)+"."+RandomUtils.nextInt(10,250)+"."+RandomUtils.nextInt(10,250);
    }

    public static String genReleaseChannel() throws IOException {
        List<String> chs = DataHolder.loadReleaseChannel();
        return chs.get(RandomUtils.nextInt(0,chs.size()));

    }

    public static String genDevice() throws IOException {
        List<String> devices = DataHolder.loadDevices();
        return devices.get(RandomUtils.nextInt(0,devices.size()));
    }
    public static String genAppVersions() throws IOException {
        String[] appVersions = DataHolder.appVersions;
        return appVersions[RandomUtils.nextInt(0,appVersions.length)];
    }

    public static double genLongitude() {
        return RandomUtils.nextDouble(40.00,140.00);
    }

    public static double genLatitude() {
        return RandomUtils.nextDouble(20.00,70.00);
    }

    public static String genNetType() {
        String[] netTypes = {"3G","4G","5G","WIFI"};
        return netTypes[RandomUtils.nextInt(0,netTypes.length)];
    }

    public static String genCarrier() {
        String[] netTypes = {"中国移动","中国电信","中国联通","小米移动","腾讯移动"};
        return netTypes[RandomUtils.nextInt(0,netTypes.length)];
    }

    public static String genSessionId(){
        return RandomStringUtils.random(11,true,true);
    }

    public static String genEventId() throws IOException {
        List<String> eventIds = DataHolder.loadEventIds();
        return eventIds.get(RandomUtils.nextInt(0,eventIds.size()));
    }

    public static String genRefType() {
        String[] refTypes = DataHolder.refTypes;
        return refTypes[RandomUtils.nextInt(0,refTypes.length)];
    }
    public static String genShareMethod() {
        String[] shareMethods = {"微信朋友圈","微博","qq空间"};
        return shareMethods[RandomUtils.nextInt(0,shareMethods.length)];
    }

    public static String genKeyWords() {

        return RandomStringUtils.randomAlphabetic(2,5)+" " +RandomStringUtils.randomAlphabetic(2,5);
    }

    public static String genAccount() throws SQLException {
        List<String> accounts = DataHolder.loadAccounts();
        return accounts.get(RandomUtils.nextInt(0,accounts.size()));
    }
}
