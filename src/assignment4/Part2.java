package assignment4;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import assignment3.MedianFiltering;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class Part2 {
  
  public static void execute(String name) throws IOException {
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];

    String path = filename + "."+extension;

    BufferedImage bimg = ImageIO.read(new File(path));


    float k = 0.7f;
    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    int[][] rE = LaplaceOperator.applyLaplaceOperator(rM);
    
    rE = IPUtil.multiplyByFactor(rE, k);
    rE = IPUtil.subtract(rM, rE);

    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = LaplaceOperator.applyLaplaceOperator(gM);
    
    gE = IPUtil.multiplyByFactor(gE, k);
    gE = IPUtil.subtract(gM, gE);

    
    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = LaplaceOperator.applyLaplaceOperator(bM);
    
    bE = IPUtil.multiplyByFactor(bE, k);
    bE = IPUtil.subtract(bM, bE);


    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "sharp_laplace-"+path, extension);
    
    rE = MedianFiltering.medianFilter(rM);
    int[][] drE = IPUtil.subtract(rM, rE);
    rE = IPUtil.add(rM, drE);
    
    gE = MedianFiltering.medianFilter(gM);
    int[][] dgE = IPUtil.subtract(gM, gE);
    gE = IPUtil.add(gM, dgE);

    bE = MedianFiltering.medianFilter(bM);
    int[][] dbE = IPUtil.subtract(bM, bE);
    bE = IPUtil.add(bM, dbE);
    
    
    res = IPUtil.combineColors(drE, dgE, dbE);
    IPUtil.drawImage(res, "difference-"+path, extension);
    
    res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "unsharp_mask-"+path, extension);

    
  }

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String name = "sample.GIF";
    execute(name);
  }

}
