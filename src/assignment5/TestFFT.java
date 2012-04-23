package assignment5;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.pearsoneduc.ip.op.FFTException;
import com.pearsoneduc.ip.op.ImageFFT;

public class TestFFT {

  /**
   * @param args
   * @throws IOException 
   * @throws FFTException 
   */
  public static void main(String[] args) throws IOException, FFTException {
    
//    String name = "cln1.gif";
    String name = "livingroom.tif";

    BufferedImage image = ImageIO.read(new File(name));
    ImageFFT fft        = new ImageFFT(image);
    fft.transform();
    BufferedImage fftImg  = fft.getSpectrum();
    
    File file = new File("fft-"+name);
    ImageIO.write(fftImg, "tif", file);
    
    
//    BufferedImage image2  = ImageIO.read(new File("fft-"+name));
//    ImageFFT fft2         = new ImageFFT(image2);
//    fft2.transform();
//    BufferedImage origImg = fft2.getSpectrum();
//    File file2            = new File("origFFT-"+name);
//    ImageIO.write(origImg, "tif", file2);
    
    fft.transform();
    BufferedImage origImg = fft.toImage(null);
    File file2            = new File("origFFT-"+name);
    ImageIO.write(origImg, "tif", file2);

  }

}
