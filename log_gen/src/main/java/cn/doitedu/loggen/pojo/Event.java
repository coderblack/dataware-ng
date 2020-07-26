package cn.doitedu.loggen.pojo;

import com.alibaba.fastjson.annotation.JSONField;

public class Event {
    @JSONField(serialize = false,deserialize = false)
    public boolean needAccount = false;
}
