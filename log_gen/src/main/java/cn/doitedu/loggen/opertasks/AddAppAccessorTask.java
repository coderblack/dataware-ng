package cn.doitedu.loggen.opertasks;

import cn.doitedu.loggen.logbean.AppChannelLog;
import cn.doitedu.loggen.pojo.AccessorInfo;
import cn.doitedu.loggen.utils.EventUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.BlockingQueue;

public class AddAppAccessorTask implements Runnable {
    BlockingQueue<AppChannelLog> accessors;

    public AddAppAccessorTask(BlockingQueue<AppChannelLog> accessors) {
        this.accessors = accessors;
    }

    @Override
    public void run() {
        while(true) {
            AccessorInfo accessorInfo = null;
            try {
                accessorInfo = AccessorInfo.get();

                AppChannelLog appChannelLog = new AppChannelLog();
                appChannelLog.set(0, EventUtil.genSessionId(), accessorInfo.account, accessorInfo.deviceId,
                        accessorInfo.deviceType, accessorInfo.resolution, accessorInfo.osName, accessorInfo.osVersion,
                        accessorInfo.appId, accessorInfo.appVersion, accessorInfo.releaseChannel, accessorInfo.latitude,
                        accessorInfo.longitude, accessorInfo.ip, accessorInfo.netType, accessorInfo.carrier, null, null);
                accessors.put(appChannelLog);

                Thread.sleep(RandomUtils.nextLong(1000,3000));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
