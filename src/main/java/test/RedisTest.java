package test;

import java.util.ArrayList;
import java.util.List;

public class RedisTest {

    public static void main(String[] args) {


        CommandTransformImpl cmd = new CommandTransformImpl();

        System.out.println(cmd.optSet("hello", "world"));

        List<String> list = new ArrayList<>();
        for (int i=1;i<=10;i++){
            list.add(String.format("%s:%s","test",i));
        }
        System.out.println(cmd.optDel(list.toArray(new String[list.size()])));
    }
}
