import java.io.IOException;
import java.util.StringTokenizer;

/** Mapper assigns the points to a cluster based on minimum distance from cluster centers */
public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
  private Text word1 = new Text();
  private Text word2 = new Text();
  static String center;

  public void configure(JobConf conf) {
    center = conf.get("path"); // get the centers passed to the mappers
  }

  public void map(
      LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
      throws IOException {
    String line = value.toString();
    StringTokenizer tokenizer = new StringTokenizer(line);
    while (tokenizer.hasMoreTokens()) {
      Point p =
          new Point(
              Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken()));
      word1.set(findIndex(p, center));
      word2.set(p.x + " " + p.y + " ");
      output.collect(word1, word2);
    }
  }
}