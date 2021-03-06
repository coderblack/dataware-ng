package cn.doitedu.loggen.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataHolder {

    public static List<String> accountLst = null;
    public static List<String> devices = null;
    public static List<String> channels = null;
    public static List<String> eventIds = null;
    public static List<String> gpsLst = null;
    public static String[] refTypes = {"1","2","3","4","5"}; // 商品浏览来源： 搜索，运营位，买了又买，看了又看，猜你喜欢

    public static String[] appVersions = {"2.0","2.2","3.0","3.2","3.4","4.0"};

    public static List<String> loadAccounts() throws Exception {

        if(accountLst != null) return accountLst;
        accountLst = new ArrayList<>();
        Connection conn = DbUtil.getConn();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select account from user_info");
        while(rs.next()){
            accountLst.add(rs.getString(1));
        }
        stmt.close();
        conn.close();
        return accountLst;

    }

    public static List<String> loadGps() throws  Exception{
        if(gpsLst !=null) return gpsLst;
        gpsLst = new ArrayList<>();
        Connection conn = DbUtil.getConn();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("select BD09_LNG,BD09_LAT from t_md_areas");
        while(rs.next()){
            double lng = rs.getDouble(1);
            double lat = rs.getDouble(2);
            gpsLst.add(lng+","+lat);
        }
        stmt.close();
        conn.close();
        return gpsLst;
    }


    public static List<String> loadDevices() throws IOException {
        if(devices != null) return devices;
        String path = ConfHolder.getProperty("initdata.phoneinfo");
        devices = FileUtils.readLines(new File(path), "utf-8");
        return devices;
    }


    public static List<String> loadReleaseChannel() throws IOException {
        if(channels != null) return channels;
        String path = ConfHolder.getProperty("initdata.releasechannel");
        channels = FileUtils.readLines(new File(path), "utf-8");
        return channels;
    }

    public static List<String> loadEventIds() throws IOException {
        if(eventIds != null) return eventIds;
        String path = ConfHolder.getProperty("initdata.eventIds");
        eventIds = FileUtils.readLines(new File(path), "utf-8");
        return eventIds;
    }
}
