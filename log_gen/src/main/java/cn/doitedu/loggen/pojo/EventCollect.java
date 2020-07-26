package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;
import com.alibaba.fastjson.annotation.JSONField;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2020/7/5
 **/
public class EventCollect  extends Event{
    @JSONField(serialize = false,deserialize = false)
    public boolean needAccount = true;
    String productId;
    String pageId;
    String url;
    String title;

    public static EventCollect get(){
        EventCollect eventThumbup = new EventCollect();
        eventThumbup.productId = EventUtil.genProductId();
        eventThumbup.pageId = EventUtil.genPageId();
        eventThumbup.url = EventUtil.genUrl();
        eventThumbup.title = EventUtil.genTitle();

        return eventThumbup;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
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
}
