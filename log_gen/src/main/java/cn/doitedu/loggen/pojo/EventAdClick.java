package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;
import com.alibaba.fastjson.annotation.JSONField;

public class EventAdClick extends Event{
    @JSONField(serialize = false,deserialize = false)
    public boolean needAccount = false;
    private String pageId;
    private String adId;
    private String adLocation;  // 运营位
    private String adCampain;   // 所属活动

    public static EventAdClick get(){
        EventAdClick eventAdClick = new EventAdClick();
        eventAdClick.pageId = EventUtil.genPageId();
        eventAdClick.adId = EventUtil.genAdId();
        eventAdClick.adLocation = EventUtil.genAdLocation();  // 运营位
        eventAdClick.adCampain = EventUtil.genAdCampain();   //所属活动
        return eventAdClick;
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
