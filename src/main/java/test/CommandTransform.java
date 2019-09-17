package test;

/**
 * @author kmz
 */
public interface CommandTransform {

    String optSet(String key, String value);

    String optDel(String... args);
}
