import java.io.IOException;
import java.util.Iterator;

/** Combiner combines the outputs corresponding to a single key from every mapper */
public static class Combine extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
  public void reduce(
      Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
      throws IOException {
    Text out = new Text();

    while (values.hasNext()) {
      Text t = values.next();
      out.append(t.getBytes(), 0, t.getLength());
    }
    output.collect(key, out);
  }
}