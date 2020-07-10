package cn.doitedu.loggen.sink;

import org.apache.log4j.Logger;

import java.io.IOException;

public class LogSink implements Sinker{
    Logger logger = Logger.getLogger("roll");
    /*public static void log(Logger logger, String msg){
        logger.info(msg);
    }*/

    @Override
    public void sink(String msg) throws IOException {
        logger.info(msg);
    }
}
