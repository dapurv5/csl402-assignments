package assignment5;

import java.awt.image.BufferedImage;

import com.pearsoneduc.ip.op.FFTException;
import com.pearsoneduc.ip.op.ImageFFT;

public class LowPassFilter extends AbstractBufferedImageOp{
    
  private float f;
  
  public LowPassFilter(){
    f = 0.1f;
  }
  
  public LowPassFilter(float f){
    this.f = f;
  }

  
  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest){
    int width  = src.getWidth();
    int height = src.getHeight();

    int L = (int) (f*Math.min(width, height));

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
        else{
          fftImg.setRGB(x, y, fftImg.getRGB(x, y));
        }        
      }
    }

    fft.transform();    
    try {
      fft.toImage(dest);
      
    } catch (FFTException e) {
      e.printStackTrace();
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
