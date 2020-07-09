import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class FileRename {
    public static void main(String[] args) {

        String dir = "E:\\BaiduNetdiskDownload\\【友凡22085】基于Flink+ClickHouse构建亿级电商实时数据分析平台（PC、移动、小程序）\\";
        File file = new File(dir);
        File[] files = file.listFiles();
        for (File f : files) {
            String name = f.getName();
            String[] split = name.split("、");
            if(split.length==2){
                String pre = StringUtils.leftPad(split[0],3,"0");
                f.renameTo(new File(dir+pre+"、"+split[1]));
            }
        }
    }
}
