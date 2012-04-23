package assignment2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;

public class A2Driver{

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
    
//    args = new String[]{"peppers_color.tif"};
    
    BinaryImage.execute(args[0]);
    ChangeIntensity.execute(args[0]);
    ContrastStretch.execute(args[0]);
    HistogramEqualization.execute(args[0]);

  }

}
