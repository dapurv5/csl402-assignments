package assignment12;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Assuming the background is black.
 * Constructed for binary images.
 * @author apurv
 *
 */
public class TranslateOp extends AbstractBufferedImageOp{

  private int a;
  private int b;
  private final Color BLACK = new Color(0,0,0); 

  public TranslateOp(int a, int b){
    this.a = a;
    this.b = b;
  }    

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }

    int width  = src.getWidth();
    int height = src.getHeight();
    
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        dest.setRGB(x, y, getColor(x,y,src).getRGB());
      }
    }    
    return dest;
  }
  
  /**
   *TODO: Unnecessarily made the return type as Color. int would have been simpler. 
   */
  private Color getColor(int x, int y, BufferedImage src){
    int width  = src.getWidth();
    int height = src.getHeight();
    return (x-a>=0 && x-a<width && y-b>=0 && y-b<height)?new Color(src.getRGB(x-a, y-b)):BLACK;
  }
  
  

}
