package assignment11;

import java.util.ArrayList;

public class AreaCalculator {
  
  public int getAreaFromChainCode(ArrayList<String> chainCode, int startX){
    int area = 0;
    int verticalPos = startX;
    for(char c:chainCode.get(0).toCharArray()){
//      System.out.println("char= "+c);
      switch(c){
        case '0':
          area -= verticalPos;
          break;
        case '1':
          verticalPos += 1;
          break;
        case '2':
          area += verticalPos;
          break;
        case '3':
          verticalPos -= 1;
          break;
      }
    }
    return area;
  }

}
