package assignment10;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;


public class A10Driver {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
//    String name = "fourier2.png";
//    double factor  = 0.3;

    System.out.println(Arrays.toString(args));

    if(args.length < 1){
      System.out.println("Usage: java -jar Filename.jpg");
      System.out.println("Exiting ...");
      System.exit(0);
    }


    String name = args[0];
    double factor = Double.parseDouble(args[1]);

    String[] arr = name.split("\\.");
    String format = arr[1];


    BufferedImage image  = ImageIO.read(new File(name));
    BoundaryFilterOp bOp = new BoundaryFilterOp(factor);
    BufferedImage bImg   = bOp.filter(image, null);
    //    IPUtil.displayMatrix(IPUtil.readImageAsMatrix(image));
    File file = new File("boundaryFilter-"+name);
    ImageIO.write(bImg, format, file);

  }

}
