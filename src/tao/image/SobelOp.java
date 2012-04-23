package tao.image;

import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;


public class SobelOp extends AbstractBufferedImageOp{

  private final Kernel Gx = new Kernel(3,3, new float[]{
      -1, 0, 1,
      -2, 0, 2,
      -1, 0, 1
  });
  
  private final Kernel Gy = new Kernel(3,3, new float[]{
       1, 2, 1,
       0, 0, 0,
      -1,-2,-1
  }); 



  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }
    
    ConvolveOp convOp1 = new ConvolveOp(Gx);
    ConvolveOp convOp2 = new ConvolveOp(Gy);
    BufferedImage A = convOp1.filter(src, null);
    BufferedImage B = convOp2.filter(src, null);
    
    AbsSumOp sumOp = new AbsSumOp();    
    sumOp.sum(A, B, dest);

    return dest;
  }

}
