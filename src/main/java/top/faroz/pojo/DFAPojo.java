package top.faroz.pojo;

import java.util.Set;

/**
 * @ClassName DFA
 * @Description DFA 存储状态
 * @Author FARO_Z
 * @Date 2021/4/27 下午4:53
 * @Version 1.0
 **/
public class DFAPojo {
    private Set<String> from;
    private String path;
    private Set<String> to;

    public DFAPojo(Set<String> from, String path, Set<String> to) {
        this.from = from;
        this.path = path;
        this.to = to;
    }

    @Override
    public String toString() {
        return from.toString()+"  --"+path+"-->  "+to.toString();
    }
}
