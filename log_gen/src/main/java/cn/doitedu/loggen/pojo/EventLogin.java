package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.DataHolder;
import cn.doitedu.loggen.utils.EventUtil;
import com.alibaba.fastjson.annotation.JSONField;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2020/7/5
 **/
public class EventLogin extends Event{
    @JSONField(serialize = false,deserialize = false)
    public boolean needAccount = false;

    String account;

    public static EventLogin get() throws Exception {
        EventLogin login = new EventLogin();

        login.account = EventUtil.genAccount();

        return login;
    }

    public boolean isNeedAccount() {
        return needAccount;
    }

    public void setNeedAccount(boolean needAccount) {
        this.needAccount = needAccount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
