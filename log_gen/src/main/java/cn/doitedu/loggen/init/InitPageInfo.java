package cn.doitedu.loggen.init;


import cn.doitedu.loggen.utils.DbUtil;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * 页面id,频道id,栏目id,页面类别
 * pg01,pd01,ch01,ch_home
 */
public class InitPageInfo {
    public static void main(String[] args) throws IOException, SQLException {
        // 加载页面类别维表
        ArrayList<Integer> pgtypeIds = DbUtil.getAllIds("dim_pgtype");

        // 加载栏目ids
        ArrayList<Integer> lanmuIds = DbUtil.getAllIds("dim_lanmu");

        // 加载频道ids
        ArrayList<Integer> pindaoIds = DbUtil.getAllIds("dim_pindao");


        // 生成页面id，并入库
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/realtimedw", "root", "root");
        PreparedStatement ps = conn.prepareStatement("insert into dim_pginfo (pindao,lanmu,pgtype) values(?,?,?)");
        for(int i =0; i<2000; i++){
            int lanmuId = lanmuIds.get(RandomUtils.nextInt(0,lanmuIds.size()));
            int pgtypeId = pgtypeIds.get(RandomUtils.nextInt(0,pgtypeIds.size()));
            int pindaoId = pindaoIds.get(RandomUtils.nextInt(0,pindaoIds.size()));

            ps.setInt(1,pindaoId);
            ps.setInt(2,lanmuId);
            ps.setInt(3,pgtypeId);

            ps.execute();
        }

        ps.close();
        conn.close();
    }
}
