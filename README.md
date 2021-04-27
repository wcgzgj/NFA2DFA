# NFA 转 DFA

姓名：张健

学号：19180319



## NDF 转 DFA 的过程

这里的测试数据，我使用的是 ==书P39 例3.5==的数据

![image-20210427171458057](https://gitee.com/faro/images/raw/master/img/20210427171458.png)



**测试数据，我放在 data2.txt 中：**

其中，“ * ” 表示的是 epsilon

```txt
1 * 2
1 * 3
2 * 4
2 a 2
3 * 4
3 b 3
4 b 5
5 * 6
6 * #
6 b 7
7 a 6
```



**并且在程序执行的时候，就读取完，放在 list 中：**

![image-20210427171842635](https://gitee.com/faro/images/raw/master/img/20210427171842.png)

```java
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
```





### 1. 获取起始点的 Epsilon 闭包

![image-20210427171609222](https://gitee.com/faro/images/raw/master/img/20210427171609.png)

```java
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
              	//递归
                Set<String> closure = getEpsilonClosure(next);
                tmpSet.addAll(closure);
            }
            res.addAll(tmpSet);
        }
        //最后还要带上自己
        res.add(key);
        return res;
    }
```





### 2. 获取 move

书上跳了一步，这里，我们需要借助==move==函数

获取当前节点集合，根据指定路径（比如说路径 a），可以到达的节点集合

**其过程如下图：**

![image-20210427172335040](https://gitee.com/faro/images/raw/master/img/20210427172335.png)

```java
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
```





### 3. 获取所有新生成的状态，同时获取新的路径

![image-20210427172611394](https://gitee.com/faro/images/raw/master/img/20210427172611.png)





这里，需要借助递归，获取所有新生成的状态，并且还要在生成的过程中，记录新生成的状态，根据指定路径，到达另一节点的记录

![image-20210427172824119](https://gitee.com/faro/images/raw/master/img/20210427172824.png)

```java
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
```



**获取新生状态的过程中，获取路径：**

![image-20210427172945762](https://gitee.com/faro/images/raw/master/img/20210427172945.png)



**初始状态下，要获取起始点的 epsilon 闭包：**

![image-20210427173035112](https://gitee.com/faro/images/raw/master/img/20210427173035.png)





### 4. 测试代码

**起始类如下：**

我们要自定义起始位置，和除了 epsilon 以外的路径

```java
public class Start {
    public static void main(String[] args) {
        Nfa2DfaService service = new Nfa2DfaService();
        List<DFAPojo> dfa = service.getDFA("1", new String[]{"a", "b"});
        for (DFAPojo dfaPojo : dfa) {
            System.out.println(dfaPojo.toString());
        }
    }
}
```



**结果如下：**

其中的 “#” 表示终止，在书中给出的条件中，终止符号应该为 8

![image-20210427173256358](https://gitee.com/faro/images/raw/master/img/20210427173256.png)



与==书P40 表3.5==最后的结果一致

![image-20210427173400457](https://gitee.com/faro/images/raw/master/img/20210427173400.png)



最后，只要将新状态分别命名为 A,B,C…，然后将包含 “#” 的新状态的集合，定义为新的终止态，就完成了 NFA 到 DFA 的转换