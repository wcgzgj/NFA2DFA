import org.junit.jupiter.api.Test;
import top.faroz.service.Nfa2DfaService;
import top.faroz.start.Start;
import top.faroz.util.FileUtil;
import top.faroz.util.NFAUtil;

import javax.xml.transform.Source;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName MyTest
 * @Description TODO
 * @Author FARO_Z
 * @Date 2021/4/27 下午1:37
 * @Version 1.0
 **/
public class MyTest {
    @Test
    void hashSetTest() {
        /**
         * 通过 equals 方法，可以判断两个 set 中的元素是否相等
         */
        // HashSet<Integer> set1 = new HashSet<Integer>();
        // HashSet<Integer> set2 = new HashSet<Integer>();
        //
        // set1.add(1);
        // set1.add(12);
        // set1.add(123);
        //
        // set2.add(1);
        // set2.add(12);
        // set2.add(123);
        // set2.add(12);
        //
        // System.out.println(set1.equals(set2));

        Set<Set<String>> mset = new HashSet<Set<String>>();
        Set<String> set1 = new HashSet<String>();
        set1.add("1");
        set1.add("2");
        mset.add(set1);

        Set<String> set2 = new HashSet<String>();
        set2.add("1");
        set2.add("2");
        mset.add(set2);

        System.out.println(mset.size());


    }

    @Test
    void getFileTest() {
        List<String> context = FileUtil.getContextList("data2.txt");
        System.out.println(context.toString());
    }

    @Test
    void setTest2() {
        Set<String> set = new HashSet<String>();
        set.add("1");
        set.add("2");
        set.add("3");
        set.add("4");

        for (String s : set) {
            System.out.println(s);
        }

        Set<String> set2 = new HashSet<String>();
        set2.addAll(set);
        System.out.println(set2.toString());
    }

    @Test
    void getEpsilonClosureTest() {
        Set<String> set = NFAUtil.getEpsilonClosure("5");
        System.out.println(set.toString());
    }


    @Test
    void getMoveTest() {
        Set<String> set = NFAUtil.getEpsilonClosure("1");
        Set<String> a = NFAUtil.getMove(set, "a");
        Set<String> res = new HashSet<String>();
        for (String s : a) {
            res.addAll(NFAUtil.getEpsilonClosure(s));
        }
        System.out.println(res.toString());

    }


    /**
     * 测试成功，能够获取所有新生成的状态
     */
    @Test
    void getNewStatusTest() {
        Nfa2DfaService service = new Nfa2DfaService();
        Set<Set<String>> res = service.getNewStatus("1", new String[]{"a", "b"});
        for (Set<String> re : res) {
            System.out.println(re.toString());
        }

    }



}
