package top.faroz.start;

import top.faroz.pojo.DFAPojo;
import top.faroz.service.Nfa2DfaService;
import top.faroz.util.FileUtil;

import java.util.List;

/**
 * @ClassName Start
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/4/27 下午2:43
 * @Version 1.0
 **/
public class Start {
    public static void main(String[] args) {
        Nfa2DfaService service = new Nfa2DfaService();
        List<DFAPojo> dfa = service.getDFA("1", new String[]{"a", "b"});
        for (DFAPojo dfaPojo : dfa) {
            System.out.println(dfaPojo.toString());
        }
    }
}
