package assignment12;

import java.awt.image.BufferedImage;

public class CloseOp extends AbstractBufferedImageOp{

  int[] kernel;
  
  public CloseOp(int[] kernel){
    this.kernel = kernel;
  }
  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {    
    DilationOp dOp = new DilationOp(kernel);
    ErosionOp eOp  = new ErosionOp(kernel);
    
    dest = dOp.filter(src, dest);
    dest = eOp.filter(dest, null);
        
    return dest;
  }


}
