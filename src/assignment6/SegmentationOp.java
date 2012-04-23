package assignment6;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SegmentationOp extends AbstractBufferedImageOp{
  
  public void setMode(ColorMode mode){
    super.setMode(mode);
  }
    
  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest){

    int width  = src.getWidth();
    int height = src.getHeight();
    
    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }
    
    int threshold = 0;
    int sigma     = 0;
    
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c = new Color(src.getRGB(x, y));
        sigma+= getColorComp(c);
      }
    }
    
    int newThreshold = sigma/(width*height);
       
    int sigmaFore = 0;
    int sigmaBack = 0;
    int numFore   = 0;
    int numBack   = 0;    

    while(newThreshold - threshold > 0){      
      sigmaFore = sigmaBack = numFore = numBack = 1;

      for(int y = 0; y < height; y++){
        for(int x = 0; x < width; x++){
          Color c = new Color(src.getRGB(x, y));
          if(getColorComp(c) > threshold){
            sigmaFore += getColorComp(c);
            numFore++;
          }
          else{
            sigmaBack += getColorComp(c);
            numBack++;
          }
        }
      }
      
      threshold    = newThreshold;
      newThreshold = (sigmaFore/numFore + sigmaBack/numBack)/2;            
    }

    Color cFore = new Color(255, 255, 255);
    Color cBack = new Color(0, 0, 0);
    
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c = new Color(src.getRGB(x, y));
        if(getColorComp(c) > threshold){
          dest.setRGB(x, y, cFore.getRGB());
        }
        else{
          dest.setRGB(x, y, cBack.getRGB());
        }
      }
    }

    return dest;
  }


}
