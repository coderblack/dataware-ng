import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class LoadMap {
    public static void main(String[] args) throws Exception {

        long ts1 = System.currentTimeMillis();
        ObjectInputStream objin = new ObjectInputStream(new FileInputStream("d:/map.obj"));
        HashMap<String,String> map = (HashMap<String, String>) objin.readObject();
        long ts2 = System.currentTimeMillis();
        System.out.println(ts2-ts1);
        System.out.println(map.size());
    }
}
