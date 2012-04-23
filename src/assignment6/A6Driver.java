package assignment6;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class A6Driver {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {

        String name = "rgb.jpeg";
    //    String name = "woman_blonde.tif";

    System.out.println(Arrays.toString(args));

//    if(args.length < 1){
//      System.out.println("Usage: java -jar Filename.jpg");
//      System.out.println("Exiting ...");
//      System.exit(0);
//    }


//    String name = args[0];

    String[] arr = name.split("\\.");
    String format = arr[1];



    BufferedImage image      = ImageIO.read(new File(name));

    SegmentationOp segmentOp  = new SegmentationOp();

    segmentOp.setMode(ColorMode.RED);
    BufferedImage segmentedImg = segmentOp.filter(image, null);
    File file1 = new File("redSegmented-"+name);
    ImageIO.write(segmentedImg, format, file1);

    segmentOp.setMode(ColorMode.GREEN);
    segmentedImg = segmentOp.filter(image, null);
    file1 = new File("greenSegmented-"+name);
    ImageIO.write(segmentedImg, format, file1);

    segmentOp.setMode(ColorMode.BLUE);
    segmentedImg = segmentOp.filter(image, null);
    file1 = new File("blueSegmented-"+name);
    ImageIO.write(segmentedImg, format, file1);

  }

}
