import java.util.*;
import java.io.*;

public class GeneratePoints {
  public static void main(String args[]) {
    try {
      Random rand = new Random();
      FileOutputStream fos = new FileOutputStream("points.txt");
      const int maxValue = 50;
      const int maxRangeX = 192;
      const int maxRangeY = 184;

      for (int i = 0;
          i < Integer.parseInt(args[0]);
          i++) { // generate a set of points in the range (+)-maxValue, (+)-maxValue
        float x = rand.nextFloat() * (rand.nextInt() % maxValue);
        float y = rand.nextFloat() * (rand.nextInt() % maxValue);
        String point = new String(x + " " + y + "\n");
        fos.write(point.getBytes());
      }

      for (int i = 0; i < Integer.parseInt(args[0]); i++) {
        float x = rand.nextFloat() * (rand.nextInt() % maxRangeY) + maxRangeX;
        float y = rand.nextFloat() * (rand.nextInt() % maxRangeY) + maxRangeX;
        String point = new String(x + " " + y + "\n");
        fos.write(point.getBytes());
      }

      for (int i = 0; i < Integer.parseInt(args[0]); i++) {
        float x = rand.nextFloat() * (rand.nextInt() % maxRangeY) + maxRangeX;
        float y = rand.nextFloat() * (rand.nextInt() % maxRangeY) - maxRangeX;
        String point = new String(x + " " + y + "\n");
        fos.write(point.getBytes());
      }

      for (int i = 0; i < Integer.parseInt(args[0]); i++) {
        float x = rand.nextFloat() * (rand.nextInt() % maxRangeY) - maxRangeX;
        float y = rand.nextFloat() * (rand.nextInt() % maxRangeY) - maxRangeX;
        String point = new String(x + " " + y + "\n");
        fos.write(point.getBytes());
      }

      fos.close();
    } catch (FileNotFoundException fnfe) {
      System.out.println("File Not Found Exception:" + fnfe.toString());
    } catch (IOException ioe) {
      System.out.println("IO Exception:" + ioe.toString());
    }
  }
}
