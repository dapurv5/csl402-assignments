package assignment5;

import java.awt.image.BufferedImage;

import com.pearsoneduc.ip.op.FFTException;
import com.pearsoneduc.ip.op.ImageFFT;

public class MedianPassFilter extends AbstractBufferedImageOp{
    
  private float f_low;
  private float f_high;
  
  public MedianPassFilter(){
    f_low  = 0.1f;
    f_high = 0.8f;
  }
  
  public MedianPassFilter(float f_low, float f_high){
    this.f_low  = f_low;
    this.f_high = f_high;
  }

  
  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest){
    int width  = src.getWidth();
    int height = src.getHeight();

    int L = (int) (f_low*Math.min(width, height));
    int H = (int) (f_high*Math.min(width, height));

    if(dest == null){            
      dest = createCompatibleDestImage(src, null);
    }
    
    ImageFFT fft = null;
    BufferedImage fftImg = null;
    
    try {
      fft = new ImageFFT(src);
      fft.transform();
      fftImg = fft.getSpectrum();

    } catch (FFTException e) {
      e.printStackTrace();
    }    
    
    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        if(x >= width/2-L && x <=width/2+L && y>=-L+height/2 && y<=L+height/2){
          fftImg.setRGB(x, y, 0);          
        }
        else if(x <= width/2-H || x >= width/2+H || y<=-H+height/2 && y>=H+height/2){
          fftImg.setRGB(x, y, 0);
        }
        else{
          fftImg.setRGB(x, y, fftImg.getRGB(x, y));
        }        
      }
    }

    //Inverse Transform
    fft.transform();    
    try {
      fft.toImage(dest);
      
    } catch (FFTException e) {
      e.printStackTrace();
    }
   return dest; 
  }
  


}
