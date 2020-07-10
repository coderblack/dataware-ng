package cn.doitedu.loggen.sink;

import java.io.IOException;

public interface Sinker {
    void sink(String msg)  throws IOException;
}
