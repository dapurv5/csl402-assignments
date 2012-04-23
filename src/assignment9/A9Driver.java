package assignment9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import assignment4.SobelOperator;

import tao.image.IPUtil;

public class A9Driver {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    //    String name = "check.gif";
    //    String name = "bounadry.gif";
    //    String name = "apple.gif";

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

    ChainCode cc = new ChainCode(image);
    System.out.println("Chain code is");
    for(String s:cc.getChainCode()){
      System.out.println(s);
    }

    System.out.println("\nNormalized Chain code is");
    for(String s:cc.getNormalizedChainCode()){
      System.out.println(s);
      System.out.println("\nDerivative chain code is");
      System.out.println(cc.getDerivativeChainCode(s));

    }    
  }
}