package cn.doitedu.loggen.opertasks;

import cn.doitedu.loggen.logbean.WeixinAppAccessorInfo;
import cn.doitedu.loggen.pojo.AccessorInfo;
import cn.doitedu.loggen.utils.EventUtil;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.BlockingQueue;

public class AddWxAccessorTask implements Runnable {
    BlockingQueue<WeixinAppAccessorInfo> accessors;

    public AddWxAccessorTask(BlockingQueue<WeixinAppAccessorInfo> accessors) {
        this.accessors = accessors;
    }

    @Override
    public void run() {
        while(true) {
            AccessorInfo accessorInfo = null;
            try {
                // 公共属性用父类来获取
                accessorInfo = AccessorInfo.get();

                // 构造具体终端类型访客的实例，增减属性设置
                WeixinAppAccessorInfo accessor = new WeixinAppAccessorInfo();
                accessor.set(0,
                        EventUtil.genSessionId(),
                        EventUtil.getOpenId(),
                        accessorInfo.account,
                        accessorInfo.deviceId,
                        accessorInfo.deviceType,
                        accessorInfo.resolution,
                        accessorInfo.osName,
                        accessorInfo.osVersion,
                        accessorInfo.latitude,
                        accessorInfo.longitude,
                        accessorInfo.ip,
                        accessorInfo.netType,
                        accessorInfo.carrier,
                        null,
                        null);

                accessors.put(accessor);

                Thread.sleep(RandomUtils.nextLong(1000,3000));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
