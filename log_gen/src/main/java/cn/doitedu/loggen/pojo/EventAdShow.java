package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;
import com.alibaba.fastjson.annotation.JSONField;

public class EventAdShow extends Event{
    @JSONField(serialize = false,deserialize = false)
    public boolean needAccount = false;

    private String pageId;
    private String adId;
    private String adLocation;  // 运营位
    private String adCampain;   // 所属活动

    public static EventAdShow get(){
        EventAdShow eventAdShow = new EventAdShow();
        eventAdShow.pageId = EventUtil.genPageId();
        eventAdShow.adId = EventUtil.genAdId();
        eventAdShow.adLocation = EventUtil.genAdLocation();  // 运营位
        eventAdShow.adCampain = EventUtil.genAdCampain();   //所属活动

        return eventAdShow;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

    public String getAdLocation() {
        return adLocation;
    }

    public void setAdLocation(String adLocation) {
        this.adLocation = adLocation;
    }

    public String getAdCampain() {
        return adCampain;
    }

    public void setAdCampain(String adCampain) {
        this.adCampain = adCampain;
    }
}
