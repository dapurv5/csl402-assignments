package assignment8;

import indiji.mlplot.MLPlot;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class A8Driver {

  public static void main(String[] args) throws IOException {
//    String name = "house.tif";
//    String name = "lena_color_256.tif";
    
    System.out.println(Arrays.toString(args));
    
    if(args.length < 1){
      System.out.println("Usage: java -jar Filename.jpg");
      System.out.println("Exiting ...");
      System.exit(0);
    }

    
    String name = args[0];
    
    String[] arr = name.split("\\.");
    String format = arr[1];


    BufferedImage image        = ImageIO.read(new File(name));    
    RegionMergeOp segmentOp    = new RegionMergeOp();
    
    segmentOp.filter(image, null);
    
    MLPlot p = new MLPlot();
    p.imagesc(segmentOp.getRegionsArray());
    p.save(new File("segmented-"+name));
    
  }

}
