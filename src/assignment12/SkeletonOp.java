package assignment12;

import java.awt.Color;
import java.awt.image.BufferedImage;

import tao.image.IPUtil;

public class SkeletonOp extends AbstractBufferedImageOp{
  
  int[] kernel;
  
  public SkeletonOp(int[] kernel){
    this.kernel = kernel;
  }

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {    
    UnionOp uOp = new UnionOp();
    DifferenceOp diffOp = new DifferenceOp();
    OpenOp openOp = new OpenOp(kernel);
    ErosionOp2 erosionOp = new ErosionOp2(kernel);
    
    dest = diffOp.diff(src, src);
    
    int k = 0;
    BufferedImage S_k =  null;
    BufferedImage erodedImg = src;
    
    while(any(erodedImg)){
      k++;      
      BufferedImage erodedOpen = openOp.filter(erodedImg, null);
      
      S_k = diffOp.diff(erodedImg, erodedOpen);
      dest = uOp.union(dest, S_k);
      erodedImg = erosionOp.filter(erodedImg, null);
    }
    System.out.println("NUMBER OF STEPS: "+k);
    return dest;
  }
  
  
  private boolean any(BufferedImage img){
    int width  = img.getWidth();
    int height = img.getHeight();
    
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        if(getColorComp(new Color(img.getRGB(x, y))) > 0){
          return true;
        }
      }
    }
    return false;
  }
}
