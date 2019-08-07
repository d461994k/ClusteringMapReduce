import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

/** calculate the new centroid based on current clusters */
public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
  public void reduce(
      Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
      throws IOException {
    Text out = new Text();

    // get the centroid of the points
    StringTokenizer tokenizer = new StringTokenizer(key.toString());
    Point curCentroid, newCentroid;
    curCentroid =
        new Point(Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken()));
    newCentroid = new Point(curCentroid.x, curCentroid.y);
    int points = 1;

    while (values.hasNext()) {
      Text t = values.next();
      tokenizer = new StringTokenizer(t.toString());
      newCentroid.x += Float.parseFloat(tokenizer.nextToken());
      newCentroid.y += Float.parseFloat(tokenizer.nextToken());
      points++;
      out.append(t.getBytes(), 0, t.getLength());
    }
    newCentroid.x /= points;
    newCentroid.y /= points;

    Text newCenter = new Text(String.valueOf(newCentroid.x) + " " + String.valueOf(newCentroid.y));
    output.collect(newCenter, out);
  }
}