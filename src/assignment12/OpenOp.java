package assignment12;

import java.awt.image.BufferedImage;

public class OpenOp extends AbstractBufferedImageOp{

  int[] kernel;
  
  public OpenOp(int[] kernel){
    this.kernel = kernel;
  }
  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    ErosionOp eOp  = new ErosionOp(kernel);
    DilationOp dOp = new DilationOp(kernel);
    
    dest = eOp.filter(src, dest);
    dest = dOp.filter(dest, null);
        
    return dest;
  }


}
