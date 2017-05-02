import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class WordCount {

    /**
     * Main method.
     * @param args Command line arguments.
     */
    public static void main(final String[] args) {
        if (args.length < 1) {
            System.err.println("Please provide the input file full path as argument");
            System.exit(0);
        }

        final SparkConf conf = new SparkConf()
            .setAppName("WordCount")
            .setMaster("local[4]");
        final JavaSparkContext context = new JavaSparkContext(conf);

        final String inputFile = args[0];
        final JavaRDD<String> textFile = context.textFile(inputFile);

        final List<Tuple2<String, Integer>> result = wordCount(textFile);

        result.forEach(System.out::println);
    }

    protected static List<Tuple2<String, Integer>> wordCount(final JavaRDD<String> textFile) {

        // TODO alter the map/reduce operation on textFile so that only words of length 5 are counted
        // The length of a string s can be found by using the method s.length()
        // RDDs have a method filter that takes a boolean test and returns a new RDD that contains
        // only the elements for which the testing function passed.
        // The elements of a pair, t, can be retrieved using the accessors t._1 and t._2.

        final JavaPairRDD<String, Integer> counter = textFile
            .flatMap(s -> Arrays.asList(s.split(" ")))
            .filter(s -> (s.equalsIgnoreCase("security") || s.equalsIgnoreCase("secure")))
            .mapToPair(s -> new Tuple2<>(s, 1))
            .reduceByKey((a, b) -> a + b);

        return counter.collect();
    }
}