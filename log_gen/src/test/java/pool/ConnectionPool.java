package pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

public class ConnectionPool {
    ArrayBlockingQueue<Connection> pool;

    public ConnectionPool() throws Exception {
        pool = new ArrayBlockingQueue<>(10);

        for (int i = 0; i < 3; i++) {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "root");

            Connection pconn = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (method.getName().equals("close")) {
                        pool.put((Connection) proxy);
                    } else {
                        return method.invoke(conn, args);
                    }

                    return null;
                }
            });


            pool.put(pconn);
        }
    }

    public Connection getConnection() throws Exception {
        return pool.take();
    }


}
