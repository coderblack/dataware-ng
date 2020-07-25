package cn.doitedu.loggen.init;

import cn.doitedu.loggen.utils.ConfHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AreaUtil {
    public static HashMap<String, List<String>> loadAll() throws IOException {

        String path = ConfHolder.getProperty("init.user.area");
        String s = FileUtils.readFileToString(new File(path), "utf-8");
        JSONObject jsonObject = JSON.parseObject(s);
        HashMap<String, List<String>> res = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<HashMap<String, List<String>>>() {
        });
        return res;
    }
}
