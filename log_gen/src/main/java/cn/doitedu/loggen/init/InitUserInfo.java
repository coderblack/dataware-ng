package cn.doitedu.loggen.init;

import cn.doitedu.loggen.pojo.UserInfo;
import cn.doitedu.loggen.utils.DbUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class InitUserInfo {
    public static void main(String[] args) throws Exception {

        int userTotal = 50;
        UserInfo userInfo = new UserInfo();

        /**
         * 加载省市数据
         */
        HashMap<String, List<String>> res = AreaUtil.loadAll();
        Set<String> keys = res.keySet();
        ArrayList<String> provinces = new ArrayList<>();
        provinces.addAll(keys);

        Connection conn = DbUtil.getConn();
        PreparedStatement ps = conn.prepareStatement("insert into user_info (account,gender,province,city,birthday,phone,vip_level,email) values(?,?,?,?,?,?,?,?)");

        for(int i=0;i<userTotal;i++) {
            String province = provinces.get(RandomUtils.nextInt(0, provinces.size()));

            List<String> citys = res.get(province);
            String city = citys.get(RandomUtils.nextInt(0, citys.size()));

            String birthday = RandomUtils.nextInt(1970, 2010) + "-" + StringUtils.leftPad(RandomUtils.nextInt(1, 13) + "", 2, "0") + "-" + StringUtils.leftPad(RandomUtils.nextInt(1, 29) + "", 2,"0");

            String account = RandomStringUtils.randomAlphanumeric(4, 10);

            int gender = RandomUtils.nextInt(0, 2);

            String[] phonePrefix = {"133", "135", "136", "137", "138", "139", "158", "159", "188", "189", "172", "176"};
            String phone = phonePrefix[RandomUtils.nextInt(0, phonePrefix.length)] + RandomStringUtils.randomNumeric(8);

            String email = RandomStringUtils.randomAlphanumeric(3, 8) + "@" + RandomStringUtils.randomAlphanumeric(2, 6) + ".com";

            String[] levels = {"1","2","3","4","5"};
            String level = levels[RandomUtils.nextInt(0,levels.length)];

            userInfo.set(account, province, city, phone, email, birthday,level);

            ps.setString(1,account);
            ps.setInt(2,gender);
            ps.setString(3,province);
            ps.setString(4,city);
            ps.setString(5,birthday);
            ps.setString(6,phone);
            ps.setString(7,level);
            ps.setString(8,email);

            ps.execute();

        }

        ps.close();
        conn.close();


    }
}
