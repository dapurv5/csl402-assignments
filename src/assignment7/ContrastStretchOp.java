package assignment7;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ContrastStretchOp extends AbstractBufferedImageOp{

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {

    int width  = src.getWidth();
    int height = src.getHeight();

    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }

    int min_val = 255;
    int max_val = 0;

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c = new Color(src.getRGB(x, y));
        if(c.getBlue() > max_val){
          max_val = c.getBlue();

        }
        if(c.getBlue() < min_val){
          min_val = c.getBlue();
        }
      }
    }
    
    int G   = 256;
    int[] T = new int[G];
    float a = 0;
    float b = G-1;
    float k   = (b - a)/(max_val - min_val);
    
    
    for(int p = 0; p < G; p++){
      T[p] = (int) ((p - min_val )*k + a);
    }

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c      = new Color(src.getRGB(x, y));
        int color    = c.getBlue();
        int newColor = T[color];
//        System.out.println(color+" to "+newColor);
        Color newC   = new Color(newColor, newColor, newColor);
        dest.setRGB(x, y, newC.getRGB());
      }
    }

    return dest;
  }

}
