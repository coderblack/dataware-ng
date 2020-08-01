package cn.doitedu.beans;

import java.util.HashMap;
import java.util.List;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2020/8/1
 **/
public class Bean {
    private String deviceid;
    private List<HashMap<String,String>>  lst;
    private String guid;

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public List<HashMap<String, String>> getLst() {
        return lst;
    }

    public void setLst(List<HashMap<String, String>> lst) {
        this.lst = lst;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }
}
