package cn.doitedu.loggen.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DbUtil {

    public static final String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/realtimedw?useUnicode=true&characterEncoding=utf8&useSSL=false";
    public static final String jdbcUser = "root";
    public static final String jdbcPwd = "root";

    public static Connection getConn(){
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPwd);
        }catch (Exception e){
            e.printStackTrace();
        }
        return conn;
    }


    public static HashMap<Integer,String> getAll(String table,int fields){
        HashMap<Integer, String> res = new HashMap<Integer, String>();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPwd);
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select * from " + table);
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                StringBuilder sb = new StringBuilder();
                for(int i=2;i<=fields;i++){
                    sb.append(resultSet.getString(i)).append(",");
                }
                String value = sb.toString().substring(0, sb.length() - 1);
                res.put(id,value);
            }
            stmt.close();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return res;
    }


    public static  ArrayList<Integer> getAllIds(String table){
        ArrayList<Integer> ids = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(jdbcUrl,jdbcUser,jdbcPwd);
            stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery("select id from " + table);
            while(resultSet.next()){
                ids.add(resultSet.getInt(1));
            }
            stmt.close();
            conn.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ids;
    }

}
