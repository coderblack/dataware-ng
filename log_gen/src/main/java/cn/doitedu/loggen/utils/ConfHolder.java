package cn.doitedu.loggen.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfHolder {
    private static Properties props;

    public static String getProperty(String key) throws IOException {
        if(props == null){
            props = new Properties();
            props.load(ConfHolder.class.getClassLoader().getResourceAsStream("other.properties"));
        }
        return props.getProperty(key);
    }
}
