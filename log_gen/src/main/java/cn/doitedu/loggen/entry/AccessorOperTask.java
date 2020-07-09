package cn.doitedu.loggen.entry;

import cn.doitedu.loggen.logbean.AppChannelLog;
import cn.doitedu.loggen.pojo.*;
import cn.doitedu.loggen.sink.KafkaSink;
import cn.doitedu.loggen.sink.LogSink;
import cn.doitedu.loggen.utils.EventUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;

public class AccessorOperTask implements Runnable {
    BlockingQueue<AppChannelLog> accessors;
    Logger log = Logger.getLogger("roll");

    public AccessorOperTask(BlockingQueue<AppChannelLog> accessors) {
        this.accessors = accessors;
    }


    @Override
    public void run() {
        LogSink logSink = new LogSink();


        while (true) {
            try {
                AppChannelLog appChannelLog = accessors.take();
                int events = RandomUtils.nextInt(1, 20);
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
                    }

                    if (StringUtils.isBlank(appChannelLog.getAccount()) && event.needAccount) {
                        appChannelLog.setAccount(EventUtil.genAccount());
                    }

                    appChannelLog.setTimeStamp(System.currentTimeMillis());
                    appChannelLog.setEventId(eventId);
                    appChannelLog.setEventInfo(event);

                    String logContent = JSON.toJSONString(appChannelLog);
                    // 输出为日志
                    //LogSink.log(log,logContent);
                    // 输出到kafka
                    KafkaSink.sink(logContent);


                    Thread.sleep(RandomUtils.nextLong(1000, 10000));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
