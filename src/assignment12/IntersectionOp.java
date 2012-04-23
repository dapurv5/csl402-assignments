package assignment12;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class IntersectionOp extends AbstractBufferedImageOp{

  private final Color BLACK = new Color(0,0,0); 
  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    // TODO Auto-generated method stub
    return null;
  }

  public BufferedImage intersection(BufferedImage a, BufferedImage b){
    BufferedImage dest = createCompatibleDestImage(a, null);
    int width  = a.getWidth();
    int height = a.getHeight();

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color aColor = new Color(a.getRGB(x,y));
        Color bColor = new Color(b.getRGB(x,y));
        int color    = Math.max(getColorComp(aColor), getColorComp(bColor));
        Color dColor = new Color(color, color, color);

        if( getColorComp(aColor) > 0 && getColorComp(bColor) > 0 &&
            Math.abs(getColorComp(aColor) - getColorComp(bColor)) < 10){
//          System.out.println(getColorComp(dColor));
          dest.setRGB(x, y, dColor.getRGB());
        }
        else{
          dest.setRGB(x, y, BLACK.getRGB());
//          System.out.println(getColorComp(dColor));
        }
      }
    }
    return dest;
  }


  /**
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
