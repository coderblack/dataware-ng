package cn.doitedu.loggen.sink;

import org.apache.log4j.Logger;

public class LogSink {
    public static void log(Logger logger, String msg){
        logger.info(msg);
    }
}
