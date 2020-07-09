package cn.doitedu.loggen.entry;

import cn.doitedu.loggen.logbean.AppChannelLog;
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

        // 初始化在线访客队列
        int maxOnline = 100;
        BlockingQueue<AppChannelLog> accessors = new ArrayBlockingQueue<AppChannelLog>(100);

        // 启动添加访客的线程
        for(int i=0;i<20;i++) {
            new Thread(new AddAccessorTask(accessors)).start();
            new Thread(new AccessorOperTask(accessors)).start();
            new Thread(new AccessorOperTask(accessors)).start();
            new Thread(new AccessorOperTask(accessors)).start();
        }
    }
}
