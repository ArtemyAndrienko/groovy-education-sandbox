@Grapes(
        @Grab('com.google.guava:guava:32.1.1-jre')
)
import com.google.common.base.CaseFormat
import com.google.common.base.Splitter

static void testSplitter() {
    System.out.println(Splitter.on(',')
            .trimResults()
            .omitEmptyStrings()
            .split("the ,quick, ,brown, fox, jumps, over, the, lazy, little dog."));
}

static void testCaseFormat() {
    String data = "the_quick_brown_fox_jumps_over_the_lazy_little_dog";
    System.out.println(CaseFormat.LOWER_HYPHEN.to(CaseFormat.LOWER_CAMEL, "the-quick-brown-fox-jumps-over-the-lazy-little-dog"));
    System.out.println(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "the_quick_brown_fox_jumps_over_the_lazy_little_dog"));
    System.out.println(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, "the_quick_brown_fox_jumps_over_the_lazy_little_dog"));
}


testSplitter()
testCaseFormat()