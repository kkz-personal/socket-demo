package socket;

import com.google.common.collect.Range;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("1000000000");

        BigDecimal dive = bigDecimal.divide(new BigDecimal(8));

        System.out.println(dive);

        System.out.println(dive.divide(new BigDecimal(1024*1024)));

    }
}
