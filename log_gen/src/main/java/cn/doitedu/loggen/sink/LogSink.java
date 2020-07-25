package cn.doitedu.loggen.sink;

import cn.doitedu.loggen.utils.ConfHolder;
import org.apache.log4j.Logger;

import java.io.IOException;

public class LogSink implements Sinker{

    Logger logger = Logger.getLogger(ConfHolder.getProperty("logger.type"));
    /*public static void log(Logger logger, String msg){
        logger.info(msg);
    }*/

    @Override
    public void sink(String msg) throws IOException {
        logger.info(msg);
    }
}
