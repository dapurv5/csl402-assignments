package assignment7;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import assignment6.SegmentationOp;

import static java.lang.Math.*;



public class HoughTransform extends AbstractBufferedImageOp{

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {

    src = new LaplaceOp().filter(src, null);
    src = new SegmentationOp().filter(src, null);

    int width  = src.getWidth();
    int height = src.getHeight();

    int theta_min = -90;
    int theta_max =  90;
    int r_max     = (int) sqrt(width*width + height*height);
    
    int destWidth  = 2*theta_max+1;
    int destHeight = 2*r_max+1;

    int[][] acc   = new int[destHeight][destWidth];

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c = new Color(src.getRGB(x, y));
        if(c.getBlue() == 255){
          for(int t = theta_min; t <= theta_max; t++){
            int r = (int) (y*cos(t*PI/180.0) + x*sin(t*PI/180.0));
            acc[r+r_max][t+90]++;
          }
        }
      }
    }

    ColorModel dstCM = src.getColorModel();
    dest = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster
        (destWidth, destHeight), dstCM.isAlphaPremultiplied(), null);
    
    for(int y = 0; y < destHeight; y++){
      for(int x = 0; x < destWidth; x++){
        int lambda = Math.min(18*acc[y][x], 255);
        Color c    = new Color(lambda, lambda, lambda);        
        dest.setRGB(x, y, c.getRGB());
      }
    }
    
    return dest;//new ContrastStretchOp().filter(dest, null);
  }

  /**
   * @param args
   */
  public static void main(String[] args) {

  }


}
