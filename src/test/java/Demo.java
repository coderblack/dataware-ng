import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) throws IOException {
        Person person = new Person();
        person.setName("zas");
        ArrayList<String> teles = new ArrayList<>();
        teles.add("13888888");
        teles.add("13888899");
        person.setTeles(teles);

        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream("d:/p.obj"));
        oout.writeObject(person);
        oout.close();

    }
}
