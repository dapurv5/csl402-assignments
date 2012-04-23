package assignment12;

import static java.lang.Math.abs;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DifferenceOp extends AbstractBufferedImageOp{

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  public BufferedImage diff(BufferedImage A, BufferedImage B) {
    BufferedImage diff = createCompatibleDestImage(A, null);
    for(int y = 0; y < A.getHeight(); y++){
      for(int x = 0; x < A.getWidth(); x++){
        int diffColor = abs(getColorComp(new Color(A.getRGB(x, y)))) 
                     - abs(getColorComp(new Color(B.getRGB(x, y))));
//        if(diffColor<0){
//          System.out.println(diffColor);
//        }
        diffColor = Math.max(diffColor, 0);
        diff.setRGB(x, y, new Color(diffColor, diffColor, diffColor).getRGB());
      }
    }
    return diff;
  }


  public static void main(String...args) throws IOException{
    String name = "square.jpg";
    String[] arr = name.split("\\.");
    String format = arr[1];
    BufferedImage image = ImageIO.read(new File(name));
    DifferenceOp dOp = new DifferenceOp();
    BufferedImage diffImg = dOp.diff(image, image);
    File file = new File("diff-"+name);
    ImageIO.write(diffImg, format, file);
  }
}
