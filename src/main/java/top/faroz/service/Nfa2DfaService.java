package top.faroz.service;

import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.PrivateKeyResolver;
import sun.security.util.Length;
import top.faroz.pojo.DFAPojo;
import top.faroz.util.NFAUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName Nfa2DfaService
 * @Description 通过 NFAUtil，实现 NFA 到 DFA 的转换
 * @Author FARO_Z
 * @Date 2021/4/27 下午4:08
 * @Version 1.0
 **/
public class Nfa2DfaService {
    private List<DFAPojo> DFA=new ArrayList<DFAPojo>();

    /**
     * 更根据起始节点，获取所有新生成的状态
     * @param startPoint 起始节点
     * @param paths 指定的路径，要排除 epsilon 路径（在这里，就是不能有 "*"）
     * @return
     */
    public Set<Set<String>> getNewStatus(String startPoint,String[] paths) {
        Set<Set<String>> res = new HashSet<Set<String>>();
        //起始点的 Epsilon 闭包
        Set<String> startPointEpsilonClosure = NFAUtil.getEpsilonClosure(startPoint);
        //要将起始点的 Epsilon 闭包加到结果中
        res.add(startPointEpsilonClosure);
        //指定路径
        getNewStatus(res,startPointEpsilonClosure,paths);
        return res;
    }


    private void getNewStatus(Set<Set<String>> res,Set<String>sourcePoints,String[] paths) {
        for (int i = 0; i < paths.length; i++) {
            //定义某一路径上的 epsilon 闭包
            Set<String> onePathEpsilonSet = new HashSet<String>();
            //指定路径上的可达点
            Set<String> move = NFAUtil.getMove(sourcePoints, paths[i]);
            for (String s : move) {
                //获取当前可达点的 epsilon 闭包
                Set<String> tmpSet = NFAUtil.getEpsilonClosure(s);
                //将当前可达点的 epsilon 闭包,放在指定路径的集合里
                onePathEpsilonSet.addAll(tmpSet);
            }

            DFAPojo dfaPojo = new DFAPojo(sourcePoints, paths[i], onePathEpsilonSet);
            DFA.add(dfaPojo);

            /**
             * 如果当前获得的路径，在之前没有出现过
             */
            if (!res.contains(onePathEpsilonSet) && onePathEpsilonSet.size()>0) {
                //添加当前集合
                res.add(onePathEpsilonSet);
                //递归
                getNewStatus(res,onePathEpsilonSet,paths);
            }
        }
    }

    public List<DFAPojo> getDFA(String startPoint,String[] paths) {
        //所有新生成状态的获取，其实没有什么实际意义，就是为了在过程中判断之前是否有生成的
        Set<Set<String>> newStatus = getNewStatus(startPoint, paths);
        return DFA;
    }
}
