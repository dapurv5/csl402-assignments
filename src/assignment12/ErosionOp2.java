package assignment12;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * ErosionOp implementation using intersection of translates.
 * @author apurv
 *
 */
public class ErosionOp2 extends AbstractBufferedImageOp{
  
  int[] kernel;
  
  public ErosionOp2(int[] kernel){
    this.kernel = kernel;
  }
  
  private BufferedImage[] getTranslates(BufferedImage src){
    ArrayList<BufferedImage> translatesList = new ArrayList<BufferedImage>();    
    
    int n = (int) Math.sqrt(kernel.length);
    for(int i = 0; i < kernel.length; i++){
      int row = i/n;
      int col = i%n;
      
      if(kernel[i] == 1){
        TranslateOp tOp        = new TranslateOp(n/2-row, n/2-col);
        BufferedImage transImg = tOp.filter(src, null);
        translatesList.add(transImg);
      }
    }    
    
    
    BufferedImage[] translatesArray = new BufferedImage[translatesList.size()];
    for(int i = 0; i < translatesList.size(); i++){      
      translatesArray[i] = translatesList.get(i);
    }
    return translatesArray;
  }

  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    BufferedImage[] translates = getTranslates(src);
    dest = translates[0];
    
    IntersectionOp iOp = new IntersectionOp();
    for(int i = 1; i < translates.length; i++){
      BufferedImage translate = translates[i];
      dest = iOp.intersection(dest, translate);
    }
    
    return dest;
  }
}
