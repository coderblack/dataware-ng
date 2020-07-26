package cn.doitedu.loggen.entry;

import cn.doitedu.loggen.logbean.AppAccessorInfo;
import cn.doitedu.loggen.opertasks.AppAccessorOperTask;
import cn.doitedu.loggen.opertasks.AddAppAccessorTask;
import cn.doitedu.loggen.utils.ConfHolder;
import cn.doitedu.loggen.utils.DataHolder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GenAppLog {
    public static void main(String[] args) throws Exception {

        // 初始化静态数据持有器
        DataHolder.loadEventIds();
        DataHolder.loadDevices();
        DataHolder.loadEventIds();
        DataHolder.loadReleaseChannel();
        DataHolder.loadAccounts();
        DataHolder.loadGps();

        // 初始化在线访客队列
        int maxOnline = Integer.parseInt(ConfHolder.getProperty("online.max.num"));
        BlockingQueue<AppAccessorInfo> accessors = new ArrayBlockingQueue<AppAccessorInfo>(1000);

        // 启动添加访客的线程
        new Thread(new AddAppAccessorTask(accessors)).start();
        /*while(accessors.size()<999) {
            Thread.sleep(1000);
            System.out.println(accessors.size());
        }*/
        // 启动访客操作线程
        for(int i=0;i<maxOnline;i++) {
            new Thread(new AppAccessorOperTask(accessors)).start();
        }
    }
}
