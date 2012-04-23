package assignment10;

import static assignment10.FFT.fft1D;
import static assignment10.FFT.ifft1D;
import static assignment10.FFT.fftShift1D;
import static assignment10.FFT.ifftShift1D;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import tao.image.AbstractBufferedImageOp;

public class BoundaryFilterOp extends AbstractBufferedImageOp{
  
  double factor;
  
  public BoundaryFilterOp(double factor){
    this.factor = factor;
  }

  
  public Complex[] clip1D(Complex[] fourier){
    int n = fourier.length;
    Complex[] clippedT = new Complex[n];
    int L = (int) (factor*n);
    for(int i = 0; i < n; i++){
      if(i >= n/2-L && i <= n/2+L){
        clippedT[i] = fourier[i];
      }
      else{
        clippedT[i] = new Complex(0,0);
      }
    }
    return clippedT;
  }

  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    
    ChainCode cc = new ChainCode(src);
    ArrayList<Complex> pixels = cc.getChainCodePixels();    
    int i = 0;
    int n = pixels.size();
    Complex[] signal = new Complex[n];    
    
    for(Complex pixel:pixels){
      signal[i] = pixel;
      i++;
    }
    
    Complex[] fourier = fftShift1D(fft1D(signal));
    Complex[] tSignal = ifft1D(ifftShift1D(clip1D(fourier)));
    
    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }

    for(Complex c:tSignal){
      int x = (int) Math.round(c.getReal());
      int y = (int) Math.round(c.getImaginary());
      dest.setRGB(x, y, new Color(255,255,255).getRGB());
    }
        
    return dest;
  }
}
