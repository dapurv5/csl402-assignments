package assignment7;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class A7Driver {

  public static void main(String[] args) throws IOException {
    String name = "hough.gif";
    
    System.out.println(Arrays.toString(args));
    
//    if(args.length < 1){
//      System.out.println("Usage: java -jar Filename.jpg");
//      System.out.println("Exiting ...");
//      System.exit(0);
//    }

    
//    String name = args[0];
    
    String[] arr = name.split("\\.");
    String format = arr[1];


    BufferedImage image    = ImageIO.read(new File(name));    
    BufferedImageOp hough  = new HoughTransform();
    BufferedImage houghImg = hough.filter(image, null);    
    File file = new File("hough-"+name);
    ImageIO.write(houghImg, format, file);
    
  }

}
