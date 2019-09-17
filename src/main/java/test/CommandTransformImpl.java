package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kmz
 */
public class CommandTransformImpl implements CommandTransform {

    private static final String HUANHANG = "/r/n";

    @Override
    public String optSet(String key, String value) {
        StringBuffer cmd = new StringBuffer();
        List<String> list = new ArrayList<>();

        list.add("set");
        list.add(key);
        list.add(value);
        cmd.append("*").append(list.size()).append(HUANHANG);
        for (String str : list) {
            cmd.append("$").append(str.getBytes().length).append(HUANHANG)
                    .append(str).append(HUANHANG);

        }
        return cmd.toString();
    }

    @Override
    public String optDel(String... args) {

        StringBuffer cmd = new StringBuffer();
        List<String> list = new ArrayList<>();
        list.add("del");
        list.addAll(Arrays.asList(args));

        cmd.append("*").append(list.size()).append(HUANHANG);
        for (String str : list){
            cmd.append("$").append(str.getBytes().length).append(HUANHANG)
                    .append(str).append(HUANHANG);
        }

        return cmd.toString();
    }
}
