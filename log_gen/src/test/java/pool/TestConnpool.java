package pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnpool {
    public static void main(String[] args) throws Exception {
        ConnectionPool connectionPool = new ConnectionPool();
        System.out.println(connectionPool.pool.size());

        Connection conn = connectionPool.getConnection();

        System.out.println(connectionPool.pool.size());


        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("show databases");
        while(rs.next()){
            System.out.println(rs.getString(1));
        }

        conn.close();
        System.out.println(connectionPool.pool.size());

        System.out.println("haha");

    }
}
