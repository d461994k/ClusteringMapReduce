import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.apache.hadoop.fs.*;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;

public class Clustering {
  static final int numberOfClusters = 12;

  /** Find the distance between two points */
  static float findDistance(Point p1, Point p2) {
    float xdiff = p1.x - p2.x;
    float ydiff = p1.y - p2.y;
    float dist = (float) Math.sqrt(xdiff * xdiff + ydiff * ydiff); // Euclidean distance
    return dist;
  }

  /**
   * Finds and returns the index of the cluster center with minimum distance from the current point
   */
  static String findIndex(Point p, String center) {
    float minDist = Float.MAX_VALUE;
    String index = null;
    StringTokenizer tokenizer = new StringTokenizer(center);
    while (tokenizer.hasMoreTokens()) {
      Point pCenter =
          new Point(
              Float.parseFloat(tokenizer.nextToken()), Float.parseFloat(tokenizer.nextToken()));
      float dist = findDistance(p, pCenter);
      if (dist < minDist) {
        minDist = dist;
        index = String.valueOf(pCenter.x) + " " + String.valueOf(pCenter.y);
      }
    }
    return index;
  }

  /** Decode the updated centers passed as string from the previous iteration */
  static Point[] getUpdatedCenters(String path) throws IOException {
    Point[] center = new Point[numberOfClusters];

    for (int i = 0; i < numberOfClusters; i++) {
      center[i] = new Point(0, 0);
    }

    Path pt = new Path(path);
    FileSystem fs = FileSystem.get(new Configuration());
    BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(pt)));

    String line;
    line = br.readLine();

    int i = 0;
    while (line != null && i < numberOfClusters) {
      StringTokenizer tokenize = new StringTokenizer(line);
      center[i].x = Float.parseFloat(tokenize.nextToken());
      center[i].y = Float.parseFloat(tokenize.nextToken());
      line = br.readLine();
      i++;
    }

    return center;
  }

  /** Main Function */
  public static void main(String[] args) throws Exception {
    int iteration = 0;

    JobConf conf = getJobConf(iteration, args);
    Point[] center =
        getUpdatedCenters(
            new String(FileInputFormat.getInputPaths(conf)[0].toString() + "/points.txt"));
    String centers = new String();
    for (int i = 0; i < k; i++) {
      centers += String.valueOf(center[i].x) + " " + String.valueOf(center[i].y) + " ";
    }
    System.out.println("Center : " + centers);
    conf.set("path", centers); // make the centers available to all mappers as a string of data
    JobClient.runJob(conf);

    while (iteration < 4) {
      iteration++; // iteration counter
      System.out.println("ITERATION : " + iteration);
      JobConf prevConf = conf;
      conf = getJobConf(iteration, args);
      center =
          getUpdatedCenters(
              new String(FileOutputFormat.getOutputPath(prevConf).toString() + "/part-00000"));
      centers = new String();
      for (int i = 0; i < k; i++) {
        centers += String.valueOf(center[i].x) + " " + String.valueOf(center[i].y) + " ";
      }
      System.out.println("Center : " + centers);
      conf.set("path", centers);
      JobClient.runJob(conf);
    }
    return;
  }

  /** Get the job configuration for each iteration */
  protected static JobConf getJobConf(int iteration, String[] args) throws Exception {
    JobConf conf = new JobConf(Clustering.class);
    conf.setJobName("Clustering" + iteration);
    conf.setOutputKeyClass(Text.class);
    conf.setOutputValueClass(Text.class);

    conf.setMapperClass(Map.class);
    conf.setCombinerClass(Combine.class);
    conf.setReducerClass(Reduce.class);
    conf.setNumMapTasks(2);
    conf.setNumReduceTasks(1);

    conf.setInputFormat(TextInputFormat.class);
    conf.setOutputFormat(TextOutputFormat.class);

    FileInputFormat.setInputPaths(conf, new Path(args[0]));
    FileOutputFormat.setOutputPath(conf, new Path(args[1] + Integer.toString(iteration)));

    return conf;
  }
}

