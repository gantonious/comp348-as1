package part1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by George on 2017-12-25.
 */
public class StringUtils {
    public static final Pattern SPLIT_PATTERN = Pattern.compile("([^\"]\\S*|\".+?\")\\s*");

    public static List<String> splitOnSpacesUnlessInQuotes(String input) {
        List<String> spaceSeparatedValues = new ArrayList<>();

        Matcher lineMatcher = SPLIT_PATTERN.matcher(input);
        while (lineMatcher.matches()) {
            spaceSeparatedValues.add(lineMatcher.group(0));
        }

        return spaceSeparatedValues;
    }
}
