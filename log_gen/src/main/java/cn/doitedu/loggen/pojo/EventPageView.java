package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;

public class EventPageView extends Event{
    public boolean needAccount = false;

    String pageId;
    String refUrl;
    String url;
    String title;
    String utm_source;
    String utm_loctype;
    String utm_campain;

    public static EventPageView get(){
        EventPageView eventPageView = new EventPageView();
        eventPageView.pageId = EventUtil.genPageId();
        eventPageView.refUrl = EventUtil.genPageId();
        eventPageView.url = EventUtil.genUrl();
        eventPageView.title = EventUtil.genTitle();
        eventPageView.utm_source = EventUtil.genUtmSource();
        eventPageView.utm_loctype = EventUtil.genUtmLocType();
        eventPageView.utm_campain = EventUtil.genAdCampain();

        return eventPageView;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getRefUrl() {
        return refUrl;
    }

    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUtm_source() {
        return utm_source;
    }

    public void setUtm_source(String utm_source) {
        this.utm_source = utm_source;
    }

    public String getUtm_loctype() {
        return utm_loctype;
    }

    public void setUtm_loctype(String utm_loctype) {
        this.utm_loctype = utm_loctype;
    }

    public String getUtm_campain() {
        return utm_campain;
    }

    public void setUtm_campain(String utm_campain) {
        this.utm_campain = utm_campain;
    }
}
