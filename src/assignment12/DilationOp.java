package assignment12;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class DilationOp extends AbstractBufferedImageOp{
  
  int[] kernel;
  
  public DilationOp(int[] kernel){
    this.kernel = kernel;
  }
  
  private BufferedImage[] getTranslates(BufferedImage src){
    ArrayList<BufferedImage> translatesList = new ArrayList<BufferedImage>();    
    
    int n = (int) Math.sqrt(kernel.length);
    for(int i = 0; i < kernel.length; i++){
      int row = i/n;
      int col = i%n;
      
      if(kernel[i] == 1){
        TranslateOp tOp        = new TranslateOp(row-n/2, col-n/2);
        BufferedImage transImg = tOp.filter(src, null);
        translatesList.add(transImg);
      }
    }    
    
    
    BufferedImage[] translatesArray = new BufferedImage[translatesList.size()];
    for(int i = 0; i < translatesList.size(); i++){      
      translatesArray[i] = translatesList.get(i);
    }
    return translatesArray;
  }

  
  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    BufferedImage[] translates = getTranslates(src);
    dest = translates[0];
    
    UnionOp uOp = new UnionOp();
    for(int i = 1; i < translates.length; i++){
      BufferedImage translate = translates[i];
      dest = uOp.union(dest, translate);
    }
    
    return dest;
  }
  
  /**
   * DilationOp as an edge detector. 
   */
  public static void main(String...args) throws IOException{
    String name = "wdg2thr3.gif";
    String[] arr = name.split("\\.");
    String format = arr[1];
    BufferedImage image = ImageIO.read(new File(name));
    
    int[] kernel = {
        1,1,1,
        1,1,1,
        1,1,1
    };
    DilationOp dilOp = new DilationOp(kernel);
    BufferedImage dilImg = dilOp.filter(image, null);
    
    
    DifferenceOp dOp = new DifferenceOp();
    BufferedImage diffImg = dOp.diff(dilImg, image);
    File file = new File("diff-"+name);
    ImageIO.write(diffImg, format, file);
  }


}
