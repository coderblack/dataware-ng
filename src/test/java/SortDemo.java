import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortDemo<T> {
    public static void main(String[] args) {
        ArrayList<Person> lst = new ArrayList<>();
        Person p1 = new Person();
        p1.setName("a");
        p1.setAge(38);


        Person p2 = new Person();
        p2.setName("b");
        p2.setAge(28);

        lst.add(p1);
        lst.add(p2);

        Collections.sort(lst);

        Collections.sort(lst, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return 0;
            }
        });


    }


    public static <T extends Comparable<? super T>> void sort(List< T> lst ){
        for(int i=0;i<lst.size()-1;i++){
            for(int j=i+1;j<lst.size();j++){

                if(lst.get(i).compareTo((T)lst.get(j))>0){
                    T ii = (T) lst.get(i);
                    T jj = (T) lst.get(j);
                    lst.set(i,jj);
                }
            }
        }
    }


    public void sort(List<T> lst , Comparator cmptor){
        for(int i=0;i<lst.size()-1;i++){
            for(int j=i+1;j<lst.size();j++){

                if(cmptor.compare(lst.get(i),lst.get(j))>0){
                    T ii = (T) lst.get(i);
                    T jj = (T) lst.get(j);
                    lst.set(i,jj);
                }
            }
        }
    }
}
