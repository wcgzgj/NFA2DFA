package top.faroz.util;

import top.faroz.start.Start;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/4/27 下午1:42
 * @Version 1.0
 **/
public class FileUtil {
    public static final String BASE_PATH="src/main/resources/";

    /**
     * 获取 resource 下的文件
     * @param fileName
     * @return
     */
    public static File getFile(String fileName) {
        return new File(BASE_PATH+fileName);
    }


    /**
     * 将文件逐行读取，返回读取后的 list
     * @param fileName
     * @return
     */
    public static List<String> getContextList(String fileName) {
        File file = getFile(fileName);
        List<String> list = new ArrayList<String>();
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);
            String line;
            while ((line=bf.readLine()) !=null) {
                list.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
