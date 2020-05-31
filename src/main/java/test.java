import sun.security.ec.CurveDB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class test {
    public static void main(String[] args) {
        System.out.println("这是我的测试");
        System.out.println("所以这是我们要做的工作");
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");
        List<String> filtered = strings.stream()
                .filter(string -> !string.equals("abcd"))
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) {
                        return s+"   mytest";
                    }
                }).collect(Collectors.toList());
        System.out.println(filtered.toString());
        List my=new ArrayList();
        my.add("这是");
        String t="这个";
        System.out.println("5");
        System.out.println(my.getClass());
        test myt=new test();
        yy y=(info)-> System.out.println(info+"都");
        y.pr(t);
        MyThread mt=new MyThread();
        mt.run();
        synchronized (my){
            my.add("mytest");
            my.add("这个就是我最简单的东西");
        }
        System.out.println(my);
        System.out.println(my+" test");
        System.out.println(my);
        System.out.println(my);
        ClassLoader classLoadere1=CurveDB.class.getClassLoader();
        System.out.printf(String.valueOf(classLoadere1));
    }
}
