package assignment4;

import static tao.image.IPUtil.applyOperator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class SobelOperator {

  /**
   * http://en.wikipedia.org/wiki/Roberts_Cross
   * @param imgMatrix
   * @return
   */
  public static int[][] applySobelOperator(int[][] imgMatrix){

    int[][] Gx = {{-1,0,1},{-2,0,2},{-1,0,1}};
    int[][] Gy = {{1,2,1},{0,0,0},{-1,-2,-1}};

    int h = imgMatrix.length;
    int w = imgMatrix[0].length;        
    int[][] tImgMatrix = new int[h][w];

    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = Math.abs(applyOperator(imgMatrix, i-1, i+1, j-1, j+1, Gx))+
            Math.abs(applyOperator(imgMatrix, i-1, i+1, j-1, j+1, Gy));        
        tImgMatrix[i][j] = (tImgMatrix[i][j] > 255)?255:tImgMatrix[i][j];
      }
    }

    return tImgMatrix;
  }

  public static void execute(String name) throws IOException {
    String[] arg = name.split("\\.");
    String filename = arg[0];
    String extension = arg[1];

    String path = filename + "."+extension;

    BufferedImage bimg = ImageIO.read(new File(path));


    IPUtil.setMode(ColorMode.RED);
    int[][] rM = IPUtil.readImageAsMatrix(bimg);
    int[][] rE = SobelOperator.applySobelOperator(rM);

    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = SobelOperator.applySobelOperator(gM);

    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = SobelOperator.applySobelOperator(bM);

    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "sobel-"+path, extension);
  }

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String name = "cln1.gif";
    execute(name);

  }

}
