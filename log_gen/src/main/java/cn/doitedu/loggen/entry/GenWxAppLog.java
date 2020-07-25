package cn.doitedu.loggen.entry;

import cn.doitedu.loggen.logbean.AppChannelLog;
import cn.doitedu.loggen.logbean.WeixinAppChannelLog;
import cn.doitedu.loggen.opertasks.AccessorOperTask;
import cn.doitedu.loggen.opertasks.AddAppAccessorTask;
import cn.doitedu.loggen.opertasks.AddWxAccessorTask;
import cn.doitedu.loggen.opertasks.WxAccessorOperTask;
import cn.doitedu.loggen.utils.DataHolder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class GenWxAppLog {
    public static void main(String[] args) throws Exception {

        // 初始化静态数据持有器
        DataHolder.loadEventIds();
        DataHolder.loadDevices();
        DataHolder.loadEventIds();
        DataHolder.loadReleaseChannel();
        DataHolder.loadAccounts();
        DataHolder.loadGps();

        // 初始化在线访客队列
        int maxOnline = 100;
        BlockingQueue<WeixinAppChannelLog> accessors = new ArrayBlockingQueue<WeixinAppChannelLog>(1000);

        // 启动添加访客的线程
        new Thread(new AddWxAccessorTask(accessors)).start();

        // 启动访客操作线程
        for(int i=0;i<1000;i++) {
            new Thread(new WxAccessorOperTask(accessors)).start();
        }
    }
}
