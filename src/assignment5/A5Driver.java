package assignment5;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class A5Driver {

  /**
   * It was allowed to use an in-built or borrowed implementation of FFT.
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
//    String name = "livingroom.tif";
    
    System.out.println(Arrays.toString(args));
    
    if(args.length < 1){
      System.out.println("Usage: java -jar Filename.jpg");
      System.out.println("Exiting ...");
      System.exit(0);
    }

    
    String name = args[0];

    BufferedImage image      = ImageIO.read(new File(name));
    
    BufferedImageOp lowPass  = new LowPassFilter();
    BufferedImage lowPassImg = lowPass.filter(image, null);    
    File file1 = new File("lowpass-"+name);
    ImageIO.write(lowPassImg, "tif", file1);
    
    BufferedImageOp highPass  = new HighPassFilter();
    BufferedImage highPassImg = highPass.filter(image, null);    
    File file2 = new File("highpass-"+name);
    ImageIO.write(highPassImg, "tif", file2);
    
    BufferedImageOp medianPass  = new MedianPassFilter();
    BufferedImage medianPassImg = medianPass.filter(image, null);    
    File file3 = new File("medianpass-"+name);
    ImageIO.write(medianPassImg, "tif", file3);
  }

}
