package cn.doitedu.loggen.opertasks;

import cn.doitedu.loggen.logbean.AppChannelLog;
import cn.doitedu.loggen.logbean.WeixinAppChannelLog;
import cn.doitedu.loggen.pojo.AccessorInfo;
import cn.doitedu.loggen.utils.EventUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.BlockingQueue;

public class AddWxAccessorTask implements Runnable {
    BlockingQueue<WeixinAppChannelLog> accessors;

    public AddWxAccessorTask(BlockingQueue<WeixinAppChannelLog> accessors) {
        this.accessors = accessors;
    }

    @Override
    public void run() {
        while(true) {
            AccessorInfo accessorInfo = null;
            try {
                accessorInfo = AccessorInfo.get();

                WeixinAppChannelLog wxChannelLog = new WeixinAppChannelLog();
                wxChannelLog.set(0, EventUtil.genSessionId(), EventUtil.getOpenId(),accessorInfo.account, accessorInfo.deviceId,
                        accessorInfo.deviceType, accessorInfo.resolution, accessorInfo.osName, accessorInfo.osVersion,
                        accessorInfo.latitude,
                        accessorInfo.longitude, accessorInfo.ip, accessorInfo.netType, accessorInfo.carrier, null, null);
                accessors.put(wxChannelLog);

                Thread.sleep(RandomUtils.nextLong(1000,3000));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
