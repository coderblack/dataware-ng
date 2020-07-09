package cn.doitedu.loggen.pojo;

import cn.doitedu.loggen.utils.EventUtil;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2020/7/5
 **/
public class EventSearch extends Event{
    public boolean needAccount = false;

    String keywords;
    public static EventSearch of(){
        EventSearch eventSearch = new EventSearch();
        eventSearch.keywords = EventUtil.genKeyWords();

        return eventSearch;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

