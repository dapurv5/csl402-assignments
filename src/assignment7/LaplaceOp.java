package assignment7;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class LaplaceOp extends AbstractBufferedImageOp{

  private final Kernel kernel = new Kernel(3,3, new float[]{
      1, 1, 1,
      1,-8, 1,
      1, 1, 1
  }); 

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }
    ConvolveOp convOp = new ConvolveOp(kernel);
    convOp.filter(src, dest);
    return dest;
  }

}
