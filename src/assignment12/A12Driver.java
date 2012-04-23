package assignment12;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class A12Driver {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    
    System.out.println(Arrays.toString(args));

    if(args.length < 1){
      System.out.println("Usage: java -jar Filename.jpg");
      System.out.println("Exiting ...");
      System.exit(0);
    }
  String name = args[0];

    
//    String name = "square.jpg";
    String[] arr = name.split("\\.");
    String format = arr[1];
    BufferedImage image = ImageIO.read(new File(name));
    
    int[] kernel = {
        0,1,0,
        1,1,1,
        0,1,0
    };
    
    SkeletonOp skeletonOp = new SkeletonOp(kernel);
    BufferedImage dest = skeletonOp.filter(image, null);
    
    File file = new File("skeleton-"+name);
    ImageIO.write(dest, format, file);        
  }
}
