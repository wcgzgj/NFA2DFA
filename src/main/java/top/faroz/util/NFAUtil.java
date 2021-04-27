package top.faroz.util;

import java.util.*;

/**
 * @ClassName NFAUtil
 * @Description 实现获取 epsilon 闭包
 *              和 获取 move 的方法
 * @Author FARO_Z
 * @Date 2021/4/27 下午3:03
 * @Version 1.0
 **/
public class NFAUtil {
    /**
     * 读取文件，获取数据文件中内容，依照行的个数，划分成 List
     */
    private static List<String>list=FileUtil.getContextList("data2.txt");


    /**
     * 获得指定数的 epsilon 闭包
     * @param key
     * @return
     */
    public static Set<String> getEpsilonClosure(String key) {
        Set<String> res = new HashSet<String>();
        for (String str : list) {
            String[] arr = str.split(" ");
            //判断是否是需要的数，并且是不是 epsilon 路径
            if (arr[0].equals(key) && arr[1].equals("*")) {
                res.add(arr[2]);
            }
        }
        if (res!=null && res.size()>0 ) {
            /**
             * 因为 forEach 循环不能在中途修改容器内容
             * 所以需要一个容器用来暂时存放子节点的 epsilonClosure
             */
            Set<String> tmpSet = new HashSet<String>();
            for (String next : res) {
                Set<String> closure = getEpsilonClosure(next);
                tmpSet.addAll(closure);
            }
            res.addAll(tmpSet);
        }
        //最后还要带上自己
        res.add(key);
        return res;
    }

    /**
     * 获取set 中的所有元素，通过 path，可以到达的路径节点
     * @param set 起始点集合
     * @param path 指定的路径 (a,b,*)
     * @return
     */
    public static Set<String> getMove(Set<String>set,String path) {
        Set<String> res = new HashSet<String>();
        for (String setElem : set) {
            for (String str : list) {
                String[] arr = str.split(" ");
                //          path equals        source equals
                if (arr[1].equals(path) && arr[0].equals(setElem)) {
                    res.add(arr[2]);
                }
            }
        }
        return res;
    }





}
