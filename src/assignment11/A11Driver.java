package assignment11;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class A11Driver {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    //    String name = "check.gif";
    //    String name = "bounadry.gif";
//        String name = "apple.gif";

    System.out.println(Arrays.toString(args));

    if(args.length < 1){
      System.out.println("Usage: java -jar Filename.jpg");
      System.out.println("Exiting ...");
      System.exit(0);
    }


    String name = args[0];

    String[] arr = name.split("\\.");
    String format = arr[1];


    BufferedImage image = ImageIO.read(new File(name));


    //    IPUtil.displayMatrix(IPUtil.readImageAsMatrix(image));

    ChainCode4 cc = new ChainCode4(image);
    int startX = cc.getStartX();
    int startY = cc.getStartY();
    System.out.println("Chain code is");
    for(String s:cc.getChainCode()){
      System.out.println(s);
    }
    AreaCalculator ac = new AreaCalculator();
    int area = ac.getAreaFromChainCode(cc.getChainCode(), startX);
    System.out.println("Area is = "+area);

  }
}
