package test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;

/**
 * 开区间指的是区间边界的两个值不包括在内 （a,b）a<x<b 取值不包括a、b
 * 闭区间指的是区间边界的两个值包括在内  [a,b] a<=x<=b 取值包括a、b
 * @author: lingqing.wan
 * @date: 2017-11-22 下午4:23
 */
public class RangeUtils {
    private static final Pattern RANGE_PATTERN = Pattern.compile("^[\\(|\\[](\\d+),(\\d+)[\\)|\\]]$");
    private static final char LEFT_OPEN_TOKEN = '(';
    private static final char LEFT_CLOSED_TOKEN = '[';
    private static final char RIGHT_OPEN_TOKEN = ')';
    private static final char RIGHT_CLOSED_TOKEN = ']';
    /**
     * 缓存range表达式转换后的range对象
     */
    private static final ConcurrentMap<String, Range<Double>> CACHE = new ConcurrentHashMap<>();

    /**
     * 将区间表达式字符串转成Range对象
     * (1,2) (1,3] [1,4] (44,199]
     */
    public static Range<Double> parse(final String rangeExpression) {
        final Range<Double> cachedRange = CACHE.get(rangeExpression);
        if (cachedRange != null) {
            return cachedRange;
        }

        Range<Double> range = null;
        final Matcher m = RANGE_PATTERN.matcher(rangeExpression);
        if (m.matches()) {
            final Double lower = Double.parseDouble(m.group(1));
            final Double upper = Double.parseDouble(m.group(2));
            final char lowerBoundChar = rangeExpression.charAt(0);
            final char upperBoundChar = rangeExpression.charAt(rangeExpression.length() - 1);

            if (lowerBoundChar == LEFT_OPEN_TOKEN) {
                if (upperBoundChar == RIGHT_OPEN_TOKEN) {
                    range = Range.open(lower, upper);
                } else if (upperBoundChar == RIGHT_CLOSED_TOKEN) {
                    range = Range.openClosed(lower, upper);
                }
            } else if (lowerBoundChar == LEFT_CLOSED_TOKEN) {
                if (upperBoundChar == RIGHT_OPEN_TOKEN) {
                    range = Range.closedOpen(lower, upper);
                } else if (upperBoundChar == RIGHT_CLOSED_TOKEN) {
                    range = Range.closed(lower, upper);
                }
            }
        }

        if (range == null) {
            throw new IllegalArgumentException("Range expression" + rangeExpression + " is not valid.");
        }

        CACHE.putIfAbsent(rangeExpression, range);
        return range;
    }

    public static String toString(Range<? extends Number> range) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return (range.lowerBoundType() == BoundType.OPEN ? "(" : "[")
                + decimalFormat.format(range.lowerEndpoint()) + ","
                + decimalFormat.format(range.upperEndpoint())
                + (range.upperBoundType() == BoundType.OPEN ? ")" : "]");
    }

    public static void main(final String[] args) {
        Range<Double> range = RangeUtils.parse("");
        System.out.println( BigDecimal.valueOf(Double.valueOf("1.0")).stripTrailingZeros().toPlainString());
        System.out.println(Arrays.asList(range.upperEndpoint().toString(),range.lowerEndpoint().toString()));
    }
}
