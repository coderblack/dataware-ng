package cn.doitedu.loggen.init;

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

        String s = FileUtils.readFileToString(new File("initdata/area.txt"), "utf-8");
        JSONObject jsonObject = JSON.parseObject(s);
        HashMap<String, List<String>> res = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<HashMap<String, List<String>>>() {
        });
        return res;
    }
}
