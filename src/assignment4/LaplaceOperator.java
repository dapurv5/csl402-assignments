package assignment4;

import static tao.image.IPUtil.applyOperator;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import tao.image.ColorMode;
import tao.image.IPUtil;

public class LaplaceOperator {

  /**
   * http://en.wikipedia.org/wiki/Roberts_Cross
   * @param imgMatrix
   * @return
   */
  public static int[][] applyLaplaceOperator(int[][] imgMatrix){

    int[][] hOp = {{1,1,1},{1,-8,1},{1,1,1}};

    int h = imgMatrix.length;
    int w = imgMatrix[0].length;        
    int[][] tImgMatrix = new int[h][w];

    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = Math.abs(applyOperator(imgMatrix, i-1, i+1, j-1, j+1, hOp));        
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
    int[][] rE = LaplaceOperator.applyLaplaceOperator(rM);

    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = IPUtil.readImageAsMatrix(bimg);
    int[][] gE = LaplaceOperator.applyLaplaceOperator(gM);

    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = IPUtil.readImageAsMatrix(bimg);
    int[][] bE = LaplaceOperator.applyLaplaceOperator(bM);

    Color[][] res = IPUtil.combineColors(rE, gE, bE);
    IPUtil.drawImage(res, "laplace-"+path, extension);
  }

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String name = "Bikesgray.jpg";
    execute(name);

  }

}
