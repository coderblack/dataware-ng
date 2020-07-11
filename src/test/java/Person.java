import java.io.Serializable;
import java.util.List;

public class Person implements Serializable {
    private String name;
    private List<String> teles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTeles() {
        return teles;
    }

    public void setTeles(List<String> teles) {
        this.teles = teles;
    }
}
