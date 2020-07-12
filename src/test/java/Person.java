import java.io.Serializable;
import java.util.List;

public class Person implements Serializable ,Comparable<Person>{
    private String name;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

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

    @Override
    public int compareTo(Person o) {
        return 0;
    }
}
