package cndoitedu.utils;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2020/8/2
 **/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateUtil {

    private final static SimpleDateFormat shortSdf = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat longSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * 日期+1
     * @param specifiedDay
     * @return
     */
    public static String getNowDate(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);
        String dayBefore = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return dayBefore;
    }
    /**
     * 当前时间是星期几
     *
     * @return
     */
    public static int getWeekDay(String specifiedDay) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.DAY_OF_WEEK);
        week_of_year = week_of_year - 1;
        if (week_of_year == 0) {
            week_of_year = 7;
        }
        return week_of_year;
    }
    /**
     * 当年的第几周
     * @param specifiedDay
     * @return
     */
    public static int getWeekofYear(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        int week = c.get(Calendar.WEEK_OF_YEAR);
        return week;
    }
    /**
     * 属于本年第几周
     *
     * @return
     */
    public static int getYearWeekIndex(String specifiedDay) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    /**
     * 获取yyyymmdd
     * @param start_day
     * @return
     * @throws ParseException
     */
    public static String yyyymmdd(String start_day,String patten) throws ParseException{
        Date date= new SimpleDateFormat("yyyyMMdd").parse(start_day);
        return new SimpleDateFormat(patten).format(date);
    }
    /**
     * 获取月多少天
     *
     * @param date
     * @return
     */
    public static int getMDaycnt(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_MONTH);
        return i;
    }

    /**
     * 获取月旬 三旬: 上旬1-10日 中旬11-20日 下旬21-31日
     *
     * @param date
     * @return
     */
    public static int getTenDay(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_MONTH);
        if (i < 11)
            return 1;
        else if (i < 21)
            return 2;
        else
            return 3;
    }
    /**
     * 获取月旬 三旬: 上旬1-10日 中旬11-20日 下旬21-31日
     *
     * @param date
     * @return
     */
    public static String getTenDays(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_MONTH);
        if (i < 11)
            return "上旬";
        else if (i < 21)
            return "中旬";
        else
            return "下旬";
    }
    /**
     * 获取月旬 三旬: 每旬多少天
     *
     * @param date
     * @return
     */
    public static int getTenDayscnt(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_MONTH);
        if (i < 11){
            return 10;
        } else if (i < 21) {
            return 10;
        }else{
            return  Integer.valueOf(getMonthEndTime(start_day).substring(6,8))-20;
        }
    }
    /**
     * 当前时间季度
     *
     * @return
     */
    public static int getQuarter(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        int dt = 0;
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                dt=1;
            else if (currentMonth >= 4 && currentMonth <= 6)
                dt=2;
            else if (currentMonth >= 7 && currentMonth <= 9)
                dt=3;
            else if (currentMonth >= 10 && currentMonth <= 12)
                dt=4;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }
    /**
     * 当前时间季度天数
     *
     * @return
     */
    public static int getQuarterCntday(String start_day) {
        int cnt_d=0;
        int q= getQuarter(start_day);
        if (q==1) {
            start_day = start_day.substring(0,4);
            start_day = getMonthEndTime(start_day+"0201");
            cnt_d = getMDaycnt(start_day);
            cnt_d=31+cnt_d+31;
        }else if (q == 2) {
            cnt_d=30+31+30;
        }else if (q == 3) {
            cnt_d=31+31+30;
        }else if (q == 4) {
            cnt_d=31+30+31;
        }
        return cnt_d;
    }
    /**
     * 当前时间年多少天
     *
     * @return
     */
    public static int getYearCntday(String start_day) {
        int cnt_d=0;
        start_day = start_day.substring(0,4);
        start_day = getMonthEndTime(start_day+"0201");
        cnt_d = getMDaycnt(start_day);
        cnt_d=31+cnt_d+31+30+31+30+31+31+30+31+30+31;

        return cnt_d;
    }
    /**
     * 当前前/后半年天数
     *
     * @return
     */
    public static int getHyearCntday(String start_day) {
        int cnt_d=0;
        int q= getHalfYear(start_day);
        if (q==1) {
            start_day = start_day.substring(0,4);
            start_day = getMonthEndTime(start_day+"0201");
            cnt_d = getMDaycnt(start_day);
            cnt_d=31+cnt_d+31+30+31+30;
        }else if (q == 2) {
            cnt_d=31+31+30+31+30+31;
        }
        return cnt_d;
    }
    /**
     * 当前时间前/后半年
     *
     * @return
     */
    public static int getHalfYear(String start_day) {
        int dt = getQuarter(start_day);
        if (dt<=2) {
            dt=1;
        }else if (dt>2) {
            dt=2;
        }
        return dt;
    }
    public static String getHalfYears(String start_day) {
        int dt = getQuarter(start_day);
        if (dt<=2) {
            return "上半年";
        }
        return "下半年";
    }
    /**
     * 获得本月的开始时间
     *
     * @return
     */
    public static String getMonthStartTime(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dt = "";
        try {
            c.set(Calendar.DATE, 1);
            dt = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }
    /**
     * 本月的结束时间
     *
     * @return
     */
    public static String getMonthEndTime(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String dt = "";
        try {
            c.set(Calendar.DATE, 1);
            c.add(Calendar.MONTH, 1);
            c.add(Calendar.DATE, -1);
            dt = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 获得本周的第一天，周一
     *
     * @return
     */
    public static String getWeekStartTime(String start_day,String pattern) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK) - 2;
            c.add(Calendar.DATE, -weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 00:00:00.000"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(pattern).format(c.getTime());
    }

    /**
     * 获得本周的最后一天，周日
     *
     * @return
     */
    public static String getWeekEndTime(String start_day,String pattern) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        try {
            int weekday = c.get(Calendar.DAY_OF_WEEK);
            c.add(Calendar.DATE, 8 - weekday);
            c.setTime(longSdf.parse(shortSdf.format(c.getTime()) + " 23:59:59.999"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(pattern).format(c.getTime());
    }
    /**
     * 获取所属旬开始时间
     *
     * @param date
     * @return
     */
    public static String getTenDayStartTime(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int ten = getTenDay(start_day);
        if (ten == 1) {
            return getMonthStartTime(start_day);
        } else if (ten == 2) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMM11");
            return df.format(date);
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMM21");
            return df.format(date);
        }

    }

    /**
     * 获取所属旬结束时间
     *
     * @param date
     * @return
     */
    public static String getTenDayEndTime(String start_day) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int ten = getTenDay(start_day);
        if (ten == 1) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMM10");
            return df.format(date);
        } else if (ten == 2) {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMM20");
            return df.format(date);
        } else {
            return getMonthEndTime(start_day);
        }
    }
    /**
     * 当前季度的开始时间
     *
     * @return
     * @throws ParseException
     */
    public static String getQuarterStartTime(String start_day) throws ParseException {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String dt = "";
        try {
            if (currentMonth >= 1 && currentMonth <= 3)
                c.set(Calendar.MONTH, 0);
            else if (currentMonth >= 4 && currentMonth <= 6)
                c.set(Calendar.MONTH, 3);
            else if (currentMonth >= 7 && currentMonth <= 9)
                c.set(Calendar.MONTH, 6);
            else if (currentMonth >= 10 && currentMonth <= 12)
                c.set(Calendar.MONTH, 9);
            c.set(Calendar.DATE, 1);
            dt =  new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 当前季度的结束时间
     *
     * @return
     * @throws ParseException
     */
    public static String getQuarterEndTime(String start_day) throws ParseException {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMdd").parse(start_day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int currentMonth = c.get(Calendar.MONTH) + 1;
        String dt = "";
        try {
            if (currentMonth >= 1 && currentMonth <= 3) {
                c.set(Calendar.MONTH, 2);
                c.set(Calendar.DATE, 31);
            } else if (currentMonth >= 4 && currentMonth <= 6) {
                c.set(Calendar.MONTH, 5);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 7 && currentMonth <= 9) {
                c.set(Calendar.MONTH, 8);
                c.set(Calendar.DATE, 30);
            } else if (currentMonth >= 10 && currentMonth <= 12) {
                c.set(Calendar.MONTH, 11);
                c.set(Calendar.DATE, 31);
            }
            dt = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dt;
    }

    /**
     * 是否节假日
     *
     * @return
     * @throws Exception
     * @throws ParseException
     */
    public static int getJjr(String start_day,String j) throws Exception  {
        String f[] = { "0101" , "0405" , "0501" , "1001" };
        String sub=start_day.substring(4,8);
        if (!"1".equals(j)) {
            for (int k = 0 ; k < f.length ; k++ ) {
                if(f[k].equals(sub)){
                    return 1;
                }
            }
            return 0;
        }else{
            return 1;
        }
    }
    /**
     * 节假日名称
     *http://timor.tech/api/holiday?date=20190505
     * @return
     * @throws Exception
     * @throws ParseException
     */
    public static String getJjrname(String start_day) throws Exception  {
        String f[] = { "0101" , "0405" , "0501" , "1001" };
        String f1[] = { "元旦节" , "清明节" , "劳动节" , "国庆节" };
        String sub=start_day.substring(4,8);
        for (int k = 0 ; k < f.length ; k++ ) {
            if(f[k].equals(sub)){
                return f1[k];
            }
        }
        return "-";
    }

    /**
     * 是否上班
     *
     * @return
     * @throws Exception
     * @throws ParseException
     */
    public static int iswork(String start_day,String j) throws Exception  {
        String f[] = { "0101" , "0405" , "0501" , "1001" };
        String sub=start_day.substring(4,8);
        if ("0".equals(j) || "2".equals(j)) {
            for (int k = 0 ; k < f.length ; k++ ) {
                if(f[k].equals(sub)){
                    return 0;
                }
            }
            return 1;
        }else{
            return 0;
        }
    }
    /**
     * 循环计算
     * @param start_day
     * @param end_day
     * @return
     * @throws Exception
     */
    public static void anyDate(String start_day,String end_day,int i) throws Exception {
        //int i = 0;
        while (true) {
            i++;
            start_day = getNowDate(start_day);
            if (start_day.equals(end_day)) {
                break;
            }else{
                System.out.println(start_day);
                StringBuffer rel = new StringBuffer();
                //Thread.sleep(500);
                int id=i;// 自增主键id
                String year_s=start_day.substring(0, 4);
                String month_s=start_day.substring(4, 6);
                String day_s=start_day.substring(6, 8);
                String day_code = start_day;//  日代码 yyyymmdd
                String day_long_desc = yyyymmdd(day_code,"yyyy年MM月dd日");// 日完整名称 yyyy年MM月dd日
                String day_medium_desc =  yyyymmdd(day_code,"dd日");// 日中等长度名 dd日
                String day_short_desc =  yyyymmdd(day_code,"yyyy-MM-dd");// 日短名 yyyy-MM-dd
                int  ws=getWeekofYear(day_code);
                String n_year=year_s;
                if (ws==1 && "12".equals(month_s)){
                    n_year=(Integer.valueOf(year_s)+1) + "";
                }
                String week_code = n_year + "W" + ws;// 周代码  2019W02
                String week_long_desc = n_year + "年第" + ws + "周";          //周完整名称String  2019年第02周
                String week_medium_desc  = "第" + ws + "周";                  //周中等长度名String  第02周
                String week_short_desc = n_year + "-W" + ws ;               //周短名String
                String week_name =  yyyymmdd(day_code,"EEEE");
                //旬代码String
                String ten_day_code =year_s + month_s + "X" + getTenDay(start_day);
                //旬完整名称String
                String ten_day_long_desc = year_s +"年"+ month_s + "月" + getTenDays(start_day);
                //旬中等长度名String
                String ten_day_medium_desc = getTenDays(start_day);
                //旬短名String
                String ten_day_short_desc = year_s +"-"+ month_s + "-X" + getTenDay(start_day);
                //月代码String
                String month_code = year_s + month_s;
                //月完整名称String
                String month_long_desc = year_s + "年" + month_s + "月";
                //月中等长度名String
                String month_medium_desc = month_s + "月";
                //月短名String
                String month_short_desc = year_s + "-" + month_s;
                //季代码String
                String qu=getQuarter(start_day)+"";
                String quarter_code = year_s +"Q"+qu;
                //季完整名称String
                String quarter_long_desc = year_s + "年第" + qu + "季度";
                //季中等长度名String
                String quarter_medium_desc = "第" + qu + "季度";
                //季短名String
                String quarter_short_desc = year_s + "-Q" + qu;
                //半年代码String
                String half_year_code =year_s + "H" + getHalfYear(start_day);
                //半年完整名称String
                String half_long_desc = year_s + "年" +getHalfYears(start_day);
                //半年中等长度名String
                String half_medium_desc = getHalfYears(start_day);
                //半年短名String
                String half_short_desc = year_s + "-H" + getHalfYear(start_day);
                //年代码String
                String year_code = year_s;
                //年完整名称String
                String year_long_desc = year_s + "年";
                //年中等长度名String
                String year_medium_desc = year_s + "年";
                //年短名String
                String year_short_desc = year_s;
                //全部时间代码String
                String all_time_code = "ALL";
                //全部时间名称String
                String all_time_desc = "ALL_TIME";
                //日时间跨天String
                String day_timespan = "1";
                //结束日期String
                String day_end_date = start_day;
                //周跨天数String
                String week_timespan = "7";
                //周结束日期String
                String week_end_date = getWeekEndTime(start_day,"yyyy-MM-dd");;
                //旬跨天数String
                String ten_day_timespan = getTenDayscnt(start_day)+"";
                //旬结束日期String
                String ten_day_end_date = getTenDayEndTime(start_day);
                //月跨天数String
                String month_timespan = getMonthEndTime(start_day).substring(6,8)+"";
                //月结束日期String
                String month_end_date = getMonthEndTime(start_day);
                //季跨天数String
                String quarter_timespan = getQuarterCntday(start_day)+"";
                //季结束日期String
                String quarter_end_date = getQuarterEndTime(start_day);
                //半年跨天数String
                String half_year_timespan = getHyearCntday(start_day)+"";
                //半年结束日期String
                String half_year_end_date = year_s + "0630";
                //年跨天数String
                String year_timespan = getYearCntday(start_day)+"";
                //年结束日期String
                String year_end_date = year_s + "1231";
                //周开始日期String
                String week_start_date = getWeekStartTime(start_day,"yyyyMMdd");
                //月开始时间String
                String month_start_date = getMonthStartTime(start_day);
                //季度开始时间String
                String quarter_start_date = getQuarterStartTime(start_day);
                //是否工作日:1.是 0.否
                String workday_flag = getWeekDay(start_day) <=5 ? "1" : "0" ;
                //是否周末:1.是 0.否
                String weekend_flag = getWeekDay(start_day) >5 ? "1" : "0";
                String  getWorkDays = getWorkDays(start_day);/*0正常工作 1法定节假日 2节假日调休补班 3休息日*/
                //是否为节假日 1：是，0：否
                String holiday_flag = getJjr(start_day,getWorkDays)+"";
                //节假日名称String
                String holiday_name = getJjrname(start_day);
                //是否上班 1：是，0：否
                Date d=new Date();
                String y=new SimpleDateFormat("yyyy").format(d);//当前年
                int ys=Integer.valueOf(y)+1;//明年
                String is_workday ="";
                if (Integer.valueOf(year_s) < 2016 || Integer.valueOf(year_s)>=Integer.valueOf(ys) ) {
                    is_workday = getWeekDay(start_day) <=5 ? "1" : "0";
                }else{
                    is_workday = iswork(start_day,getWorkDays) + "";
                }

                String load_time = "2019-06-26 12:00:00";  //加载时间
                String last_week_day = getWeekEndTime(start_day,"yyyy-MM-dd"); //周的最后一天String
                String last_month_day = yyyymmdd(getMonthEndTime(start_day),"yyyy-MM-dd"); //月的最后一天String
                rel.append(id+"\t");
                rel.append(day_code                +"\t");
                rel.append(day_long_desc           +"\t");
                rel.append(day_medium_desc         +"\t");
                rel.append(day_short_desc          +"\t");
                rel.append(week_code               +"\t");
                rel.append(week_long_desc          +"\t");
                rel.append(week_medium_desc        +"\t");
                rel.append(week_short_desc         +"\t");
                rel.append(week_name               +"\t");
                rel.append(ten_day_code            +"\t");
                rel.append(ten_day_long_desc       +"\t");
                rel.append(ten_day_medium_desc     +"\t");
                rel.append(ten_day_short_desc      +"\t");
                rel.append(month_code              +"\t");
                rel.append(month_long_desc         +"\t");
                rel.append(month_medium_desc       +"\t");
                rel.append(month_short_desc        +"\t");
                rel.append(quarter_code            +"\t");
                rel.append(quarter_long_desc       +"\t");
                rel.append(quarter_medium_desc     +"\t");
                rel.append(quarter_short_desc      +"\t");
                rel.append(half_year_code          +"\t");
                rel.append(half_long_desc          +"\t");
                rel.append(half_medium_desc        +"\t");
                rel.append(half_short_desc         +"\t");
                rel.append(year_code               +"\t");
                rel.append(year_long_desc          +"\t");
                rel.append(year_medium_desc        +"\t");
                rel.append(year_short_desc         +"\t");
                rel.append(all_time_code           +"\t");
                rel.append(all_time_desc           +"\t");
                rel.append(day_timespan            +"\t");
                rel.append(day_end_date            +"\t");
                rel.append(week_timespan           +"\t");
                rel.append(week_end_date           +"\t");
                rel.append(ten_day_timespan        +"\t");
                rel.append(ten_day_end_date        +"\t");
                rel.append(month_timespan          +"\t");
                rel.append(month_end_date          +"\t");
                rel.append(quarter_timespan        +"\t");
                rel.append(quarter_end_date        +"\t");
                rel.append(half_year_timespan      +"\t");
                rel.append(half_year_end_date      +"\t");
                rel.append(year_timespan           +"\t");
                rel.append(year_end_date           +"\t");
                rel.append(week_start_date         +"\t");
                rel.append(month_start_date        +"\t");
                rel.append(quarter_start_date      +"\t");
                rel.append(workday_flag            +"\t");
                rel.append(weekend_flag            +"\t");
                rel.append(holiday_flag            +"\t");
                rel.append(holiday_name            +"\t");
                rel.append(is_workday              +"\t");
                rel.append(load_time               +"\t");
                rel.append(last_week_day           +"\t");
                rel.append(last_month_day);
                writeFile(rel);
            }
        }
        //System.out.println(rel);
        //return rel;
    }

    /**
     * 获取是否节假日
     * @param day
     * @return
     * @throws Exception
     */
    public static String  getWorkDays(String day) throws Exception {
        /**1、接口地址：http://api.goseek.cn/Tools/holiday?date=数字日期
         2、返回数据：正常工作日对应结果为 0, 法定节假日对应结果为 1, 节假日调休补班对应的结果为 2，休息日对应结果为 3 
         3、节假日数据说明：本接口包含2017年起的中国法定节假日数据，数据来源国务院发布的公告，每年更新1次，确保数据最新
         4、示例：
         http://api.goseek.cn/Tools/holiday?date=20170528
         返回数据：
         {"code":10000,"data":1}     
         **/
        Date d=new Date();
        String y=new SimpleDateFormat("yyyy").format(d);//当前年
        int ys=Integer.valueOf(y)+1;//明年
        /*2016之前接口查询不到  明年的数据接口查不到*/
        if (Integer.valueOf(day)<20160101 || Integer.valueOf(day.substring(0,4))>=Integer.valueOf(ys)) {
            return "0";
        }
        // String httpUrl = "http://apis.baidu.com/xiaogg/holiday/holiday";
        // String httpUrl = "http://www.easybots.cn/api/holiday.php";
        String httpUrl = "http://api.goseek.cn/Tools/holiday";
        String fdate = "date=" +day;
        String jsonResult = request(httpUrl, fdate);
        // 判断是否是节假日
        // if ("0".equals(jsonResult.trim())) {
        //    return "1";
        //}
        return jsonResult.substring(21,22);
    }

    /**
     * @param urlAll :请求接口
     * @param httpArg :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            // connection.setRequestProperty("apikey", "abfa5282a89706affd2e4ad6651c9648");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void writeFile(StringBuffer rel)     {
        String file_path = "d:/test.txt" ;
        FileWriter fw_s = null;
        PrintWriter pw_s = null;
        try {
            File f_hive = new File(file_path);/* 生成路径 */
            fw_s = new FileWriter(f_hive, true);
            pw_s = new PrintWriter(fw_s);
            pw_s.println(rel);
            pw_s.flush();
            fw_s.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (pw_s != null)
                pw_s.close();
            if (fw_s != null) {
                try {
                    fw_s.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String start_day = "20220212";/**开始日期*/
        String end_day = "20400101";/**结束日期*/
        anyDate(start_day,end_day,4426);//开始时间-结束时间-续跑ID
        System.out.println("success !");
        ///System.out.println(getWorkDays("20130101"));
    }

}
