package assignment12;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class UnionOp extends AbstractBufferedImageOp{

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    // TODO Auto-generated method stub
    return null;
  }
  
  public BufferedImage union(BufferedImage a, BufferedImage b){
    BufferedImage dest = createCompatibleDestImage(a, null);
    int width  = a.getWidth();
    int height = a.getHeight();
    
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color aColor = new Color(a.getRGB(x,y));
        Color bColor = new Color(b.getRGB(x,y));
        
        int color    = Math.max(getColorComp(aColor), getColorComp(bColor));
        Color dColor = new Color(color, color, color);
        
        dest.setRGB(x, y, dColor.getRGB());
      }
    }
    return dest;
  }
  
}
