package assignment8;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;



public class RegionMergeOp extends AbstractBufferedImageOp{

  private final int        EPS = 20;
  private int       currentAvg = 0; //the avg pixel value in the current region.
  private int       currentSum = 0; //the sum of pixel values in the current region.
  private int         numPixel = 0; //the number of pixels in the current region.
  private double[][] regionNum = null;
  private int    regionCounter = 0;
  
  public double[][] getRegionsArray(){
    return regionNum;
  }

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {

    int width  = src.getWidth();
    int height = src.getHeight();

    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }

    regionNum = new double[height][width];

    for(double[] A:regionNum){
      Arrays.fill(A, -1);
    }

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        if(regionNum[y][x] != -1){continue;}//This pixel is already a part of a region

        regionNum[y][x] = regionCounter;
        Color c    = new Color(src.getRGB(x, y));
        numPixel   = 1;
        currentSum = c.getBlue();
        currentAvg = c.getBlue();
        bfsImage(x, y, src);
        regionCounter++;
      }
    }

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        dest.setRGB(x, y, (int)regionNum[x][y]);
      }
    }
    System.out.println("Number of region "+regionCounter);
    return dest;
  }

  
  @Deprecated
  private void dfsImage(int x, int y, BufferedImage src){

    for(Dimension d:getNeigbors(x, y, src)){
      int n_x = (int)d.getWidth();
      int n_y = (int) d.getHeight();
      Color c   = new Color(src.getRGB(n_x,n_y));
      int color = c.getBlue();

      if(Math.abs(color - currentAvg) <= EPS && 
          regionNum[n_y][n_x] == -1){

        regionNum[n_y][n_x] = regionCounter;
        numPixel++;
        currentSum += color;
        currentAvg  = currentSum/numPixel;
        dfsImage(n_x, n_y, src);
      }
    }
  }

  
  private void bfsImage(int start_x, int start_y, BufferedImage src){
    Dimension start    = new Dimension(start_x, start_y);
    Queue<Dimension> q = new LinkedList<Dimension>();
    q.add(start);

    while(!q.isEmpty()){
      Dimension cell = q.poll();
      int x = (int) cell.getWidth();
      int y = (int) cell.getHeight();

      for(Dimension d:getNeigbors(x, y, src)){
        int n_x = (int)d.getWidth();
        int n_y = (int) d.getHeight();
        Color c   = new Color(src.getRGB(n_x,n_y));
        int color = c.getBlue();

        if(Math.abs(color - currentAvg) <= EPS && 
            regionNum[n_y][n_x] == -1){
          
          regionNum[n_y][n_x] = regionCounter;
          numPixel++;
          currentSum += color;
          currentAvg  = currentSum/numPixel;
          q.add(d);
        }
      }
    }
  }

  
  public ArrayList<Dimension> getNeigbors(int x, int y, BufferedImage src){
    ArrayList<Dimension> neighbors = new ArrayList<Dimension>();
    if(y>0){
      neighbors.add(new Dimension(x, y-1));
      if(x>0){
        neighbors.add(new Dimension(x-1, y-1));
      }
      if(x < src.getWidth()-1){
        neighbors.add(new Dimension(x+1, y-1));
      }
    }
    if(y < src.getHeight()-1){
      neighbors.add(new Dimension(x, y+1));
      if(x>0){
        neighbors.add(new Dimension(x-1, y+1));
      }
      if(x < src.getWidth()-1){
        neighbors.add(new Dimension(x+1, y+1));
      }
    }
    if(x>0){
      neighbors.add(new Dimension(x-1, y));
    }
    if(x < src.getWidth()-1){
      neighbors.add(new Dimension(x+1, y));
    }
    return neighbors;
  }

  
//  public static void main(String args[]){
//    RegionMergeOp rop = new RegionMergeOp();
//    BufferedImage src = new BufferedImage(4, 4, BufferedImage.TYPE_3BYTE_BGR);
//    
//    src.setRGB(0, 0, new Color(232,232,232).getRGB());
//    src.setRGB(0, 1, new Color(232,232,232).getRGB());
//    src.setRGB(1, 0, new Color(232,232,232).getRGB());
//    src.setRGB(1, 1, new Color(232,232,232).getRGB());
//    
//    src.setRGB(2, 2, new Color(200,200,200).getRGB());
//    src.setRGB(2, 3, new Color(200,200,200).getRGB());
//    src.setRGB(3, 2, new Color(200,200,200).getRGB());
//    src.setRGB(3, 3, new Color(200,200,200).getRGB());
//    
//
//    IPUtil.displayMatrix(IPUtil.readImageAsMatrix(src));
//    rop.filter(src, null);
//  }

}
