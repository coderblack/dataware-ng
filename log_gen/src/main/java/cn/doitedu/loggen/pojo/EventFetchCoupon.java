package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;

public class EventFetchCoupon extends Event{

    public boolean needAccount = true;
    private String couponId;
    private String pageId;

    public static EventFetchCoupon get(){
        EventFetchCoupon eventFetchCoupon = new EventFetchCoupon();
        eventFetchCoupon.couponId = EventUtil.genCouponId();
        eventFetchCoupon.pageId = EventUtil.genPageId();
        return eventFetchCoupon;

    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }
}
