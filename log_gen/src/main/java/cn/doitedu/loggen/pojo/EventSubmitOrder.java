package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;
import com.alibaba.fastjson.annotation.JSONField;

public class EventSubmitOrder extends Event{
    @JSONField(serialize = false,deserialize = false)
    public boolean needAccount = true;
    String refType; // 订单来源：搜索购，运营位购，推荐购，

    public static EventSubmitOrder get(){

        EventSubmitOrder eventSubmitOrder = new EventSubmitOrder();
        eventSubmitOrder.refType = EventUtil.genRefType();
        return eventSubmitOrder;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }
}
