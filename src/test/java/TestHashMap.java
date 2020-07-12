import org.apache.commons.lang3.RandomStringUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class TestHashMap {
    public static void main(String[] args) throws Exception {
        HashMap<String, String> map = new HashMap<>();
        long ts1 = System.currentTimeMillis();
        for(int i=0;i<20000000;i++){
            map.put(RandomStringUtils.randomAlphabetic(6),RandomStringUtils.randomAlphabetic(7));
        }
        long ts2 = System.currentTimeMillis();
        System.out.println("hashmap构建耗时: "+(ts2-ts1));

        ts1 = System.currentTimeMillis();
        ObjectOutputStream objout = new ObjectOutputStream(new FileOutputStream("d:/map.obj"));
        objout.writeObject(map);
        objout.close();
        ts2 = System.currentTimeMillis();
        System.out.println("序列化存储耗时: "+(ts2-ts1));


        ts1 = System.currentTimeMillis();
        for(int i=0;i<10000;i++){
            map.get(RandomStringUtils.randomAlphabetic(6));
        }
        ts2 = System.currentTimeMillis();
        System.out.println("10000次查询耗时: "+(ts2-ts1));
    }
}
