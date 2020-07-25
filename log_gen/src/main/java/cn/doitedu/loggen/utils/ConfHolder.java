package cn.doitedu.loggen.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfHolder {
    private static Properties props;

    public static String getProperty(String key){
        if(props == null){
            props = new Properties();
            try {
                props.load(ConfHolder.class.getClassLoader().getResourceAsStream("other.properties"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return props.getProperty(key);
    }
}
