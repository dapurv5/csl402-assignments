package tao.image;

import java.awt.Color;
import java.awt.image.BufferedImage;


public class InvertOp extends AbstractBufferedImageOp{


  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    
    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }

    short[] invert = new short[256];

    for (int i = 0; i < 256; i++) {
      invert[i] = (short) (255 - i);
    }
    
    int width  = src.getWidth();
    int height = src.getHeight();

    
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c = new Color(src.getRGB(x, y));
        int colorComp    = getColorComp(c);
        int invColorComp = invert[colorComp];
        
        Color invColor = new Color(invColorComp,invColorComp,invColorComp);
        
        dest.setRGB(x, y, invColor.getRGB());
      }
    }


    return dest;
  }

}
