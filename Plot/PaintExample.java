import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Graphics.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;


public class PaintExample
{
	public static void main(String args[])
	{
		JFrame	f;
		f=new PaintExampleFrame();
		f.setVisible(true);
	}
}
// =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
class PaintExampleFrame extends JFrame {

	private const int horizontalSpacing = 512;
	private const int verticalSpacing = 384;
	private const int originX = 1;
	private const int originY = 1;
	private const String fileName = "Clusters.txt";

	private const String frameTitle = "Paint Attempts";
	private const int frameSizeX = 1024;
	private const int frameSizeY = 768;
	private const int frameLocationX = 500;
	private const int frameLocationY = 500;

  PaintExampleFrame() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setTitle(frameTitle);
    setSize(frameSizeX, frameSizeY);
    setLocation(new Point(frameLocationX, frameLocationY));
    setVisible(true);
    setBackground(Color.black);
  }

  public void paint(Graphics g) {
    Color colors[] = new Color[13];

    colors[12] = Color.black;
    colors[0] = Color.blue;
    colors[1] = Color.red;
    colors[2] = Color.green;
    colors[3] = Color.cyan;
    colors[4] = Color.darkGray;
    colors[5] = Color.gray;
    colors[6] = Color.lightGray;
    colors[7] = Color.magenta;
    colors[8] = Color.orange;
    colors[9] = Color.pink;
    colors[11] = Color.white;
    colors[10] = Color.yellow;

    try {
      Scanner sc = new Scanner(new File(fileName));
      int iteration = 0;

      while (sc.hasNextLine()) {
        String points = sc.nextLine();
        StringTokenizer tok = new StringTokenizer(points);

        int counter = 0;

        g.setColor(colors[iteration]);
        while (tok.hasMoreTokens()) {
          int xCoordinate = (int) Float.parseFloat(tok.nextToken());
          int yCoordinate = (int) Float.parseFloat(tok.nextToken());
          if (counter > 0) {
            System.out.println(xCoordinate + " " + yCoordinate);
            g.drawRect(xCoordinate + horizontalSpacing, yCoordinate + verticalSpacing, originX, originY);
          }
          counter++;
        }
        iteration++;
      }
    } catch (Exception e) {
      System.out.println("File Not Found Exception:" + e.toString());
    }
  }
}

