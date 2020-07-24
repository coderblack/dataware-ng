import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class FileRename {
    public static void main(String[] args) {


        String c = "abc";
        StringBuilder ss = new StringBuilder(c);
        String s = ss.toString();
        String s1 = s.intern();
        System.out.println(s == s1);
        System.out.println(s == c);

        String x = "aa";
        String x1 = x.intern();
        System.out.println(x==x1);

        String p1 = new String("11");
        String p2 = new String("11");
        System.out.println(p1==p2);
        System.out.println(p1.equals(p2));
        System.out.println(p1.intern() == p2.intern());

        String o1 = new String(new char[]{'a', 'b'});
        System.out.println(o1.intern() == o1);

        String g1 = new String("ab");
        System.out.println(g1.intern() == g1);
    }
}
