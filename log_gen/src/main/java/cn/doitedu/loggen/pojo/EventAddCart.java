package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;
import org.apache.commons.lang3.RandomUtils;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2020/7/5
 **/
public class EventAddCart extends Event{
    public boolean needAccount = true;
    String refType;
    String productId;
    String productNumb;
    String pageId;
    String refUrl;
    String url;

    public static EventAddCart get(){
        EventAddCart eventAddCart = new EventAddCart();
        eventAddCart.productId = EventUtil.genProductId();
        eventAddCart.productNumb = RandomUtils.nextInt(1,10)+"";
        eventAddCart.pageId = EventUtil.genPageId();
        eventAddCart.refUrl = EventUtil.genUrl();
        eventAddCart.url = EventUtil.genUrl();

        return eventAddCart;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNumb() {
        return productNumb;
    }

    public void setProductNumb(String productNumb) {
        this.productNumb = productNumb;
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
}
