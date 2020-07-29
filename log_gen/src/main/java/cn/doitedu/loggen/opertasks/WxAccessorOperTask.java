package cn.doitedu.loggen.opertasks;

import cn.doitedu.loggen.logbean.WeixinAppAccessorInfo;
import cn.doitedu.loggen.pojo.*;
import cn.doitedu.loggen.sink.KafkaSink;
import cn.doitedu.loggen.sink.LogSink;
import cn.doitedu.loggen.sink.Sinker;
import cn.doitedu.loggen.utils.ConfHolder;
import cn.doitedu.loggen.utils.EventUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class WxAccessorOperTask implements Runnable {
    BlockingQueue<WeixinAppAccessorInfo> accessors;
    Sinker sinker;

    public WxAccessorOperTask(BlockingQueue<WeixinAppAccessorInfo> accessors) throws IOException {
        this.accessors = accessors;
        String sinkType = ConfHolder.getProperty("sink.type");
        if("logger".equals(sinkType)){
            sinker = new LogSink();
        }
        if("kafka".equals(sinkType)){
            sinker = new KafkaSink();
        }
    }


    @Override
    public void run() {
        LogSink logSink = new LogSink();


        while (true) {
            try {
                WeixinAppAccessorInfo wxAppChannelLog = accessors.take();
                wxAppChannelLog.setSessionId(EventUtil.genSessionId());
                int events = RandomUtils.nextInt(1, 60);
                for (int j = 0; j < events; j++) {
                    long timeStamp = System.currentTimeMillis();
                    Event event = null;

                    String eventId = EventUtil.genEventId();
                    if (eventId.equals("pageView")) {
                        event = EventPageView.get();
                    } else if (eventId.equals("productView")) {
                        event = EventProductView.get();
                    } else if (eventId.equals("adShow")) {
                        event = EventAdShow.get();
                    } else if (eventId.equals("adClick")) {
                        event = EventAdClick.get();
                    } else if (eventId.equals("fetchCoupon")) {
                        event = EventFetchCoupon.get();
                    } else if (eventId.equals("submitOrder")) {
                        event = EventSubmitOrder.get();
                    } else if (eventId.equals("addCart")) {
                        event = EventAddCart.get();
                    } else if (eventId.equals("search")) {
                        event = EventSearch.of();
                    } else if (eventId.equals("collect")) {
                        event = EventCollect.get();
                    } else if (eventId.equals("thumbup")) {
                        event = EventThumbup.get();
                    } else if (eventId.equals("share")) {
                        event = EventShare.get();
                    } else if(eventId.equals("login")){
                        event = EventLogin.get();
                    }

                    if (StringUtils.isBlank(wxAppChannelLog.getAccount()) && event.needAccount) {
                        // 发起一个登陆事件
                        EventLogin eventLogin = EventLogin.get();
                        // 拼装登录日志
                        wxAppChannelLog.setTimeStamp(System.currentTimeMillis());
                        wxAppChannelLog.setEventId("login");
                        wxAppChannelLog.setProperties(eventLogin);

                        wxAppChannelLog.setAccount(eventLogin.getAccount());

                        String logContent = JSON.toJSONString(wxAppChannelLog);
                        // 输出登录日志
                        sinker.sink(logContent);
                        Thread.sleep(RandomUtils.nextLong(500, 1500));
                    }

                    wxAppChannelLog.setTimeStamp(System.currentTimeMillis());
                    wxAppChannelLog.setEventId(eventId);
                    wxAppChannelLog.setProperties(event);

                    String logContent = JSON.toJSONString(wxAppChannelLog);
                    // 输出日志
                    sinker.sink(logContent);

                    Thread.sleep(RandomUtils.nextLong(1000, 5000));
                }

                // 以一定几率将本次的访客实体，放回队列
                if(RandomUtils.nextInt(1,100) > 30) {
                    accessors.offer(wxAppChannelLog);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
