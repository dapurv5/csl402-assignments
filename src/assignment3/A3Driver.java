package assignment3;

import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;

public class A3Driver{

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    
    System.out.println(Arrays.toString(args));
    
    if(args.length < 1){
      System.out.println("Usage: java -jar Filename.jpg");
      System.out.println("Exiting ...");
      System.exit(0);
    }
    
//    args = new String[]{"balloons_noisy.png"};
    
    RotatingMask.execute(args[0]);
    MedianFiltering.execute(args[0]);
    

  }

}
