package assignment12;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * ErosionOp implementation using moving window.
 * @author apurv
 *
 */
public class ErosionOp extends AbstractBufferedImageOp{


  int[] kernel;
  final int WHITE = new Color(255,255,255).getRGB();
  final int BLACK = new Color(0,0,0).getRGB();

  public ErosionOp(int[] kernel){
    this.kernel = kernel;
  }

  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {

    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }

    int width  = src.getWidth();
    int height = src.getHeight();
    int n = (int) Math.sqrt(kernel.length);

    for(int y = n/2; y < height-n/2; y++){
      for(int x = n/2; x < width-n/2; x++){
        
        boolean flag = true;
        
        for(int i = 0; i < kernel.length; i++){
          int xRel = i/n;
          int yRel = i%n;          
          
          int xAbs = x+xRel-n/2;
          int yAbs = y+yRel-n/2;
          
          Color c  = new Color(src.getRGB(xAbs, yAbs));          

          if(kernel[i] == 1  && getColorComp(c)!=255 ){
            flag = false;
            break;
          }
        }
        
        if(flag){
          dest.setRGB(x, y, WHITE);
        }
        else{
          dest.setRGB(x, y, BLACK);
        }
      }
    }
    return dest;
  }

}
