package assignment4;

import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;

public class A4Driver{

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    
    System.out.println(Arrays.toString(args));
    
//    if(args.length < 1){
//      System.out.println("Usage: java -jar Filename.jpg");
//      System.out.println("Exiting ...");
//      System.exit(0);
//    }
    
    args = new String[]{"check.gif"};
    
    LaplaceOperator.execute(args[0]);
    RobertsOperator.execute(args[0]);
    SobelOperator.execute(args[0]);
    Part2.execute(args[0]);
    

  }

}
