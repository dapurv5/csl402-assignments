package tao.image;

import java.awt.Color;
import java.awt.image.BufferedImage;


import static java.lang.Math.abs;

public class AbsSumOp extends AbstractBufferedImageOp{

  public BufferedImage sum(BufferedImage A, BufferedImage B, BufferedImage sum) {
    if(sum == null){
      sum = createCompatibleDestImage(A, null);
    }
    for(int y = 0; y < A.getHeight(); y++){
      for(int x = 0; x < A.getWidth(); x++){
        int sumColor = abs(getColorComp(new Color(A.getRGB(x, y)))) 
                     + abs(getColorComp(new Color(B.getRGB(x, y))));
        
        sumColor = Math.min(sumColor, 255);
        sum.setRGB(x, y, new Color(sumColor, sumColor, sumColor).getRGB());
      }
    }
    return sum;
  }

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {
    // TODO Auto-generated method stub
    return null;
  }

}
