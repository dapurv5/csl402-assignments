package assignment9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;

import tao.image.IPUtil;

public class ChainCode {

  BufferedImage image;
  int width;
  int height;

  private int direction = 7;
  

  public ChainCode(BufferedImage image){
    this.image  = image;
    this.width  = image.getWidth();
    this.height = image.getHeight();
  }
  
  public ArrayList<String> getNormalizedChainCode(){
    ArrayList<String> list = getChainCode();
    ArrayList<String> normList = new ArrayList<String>();
    for(String code:list){
      normList.add(findLowestPerm(code));
    }
    return normList;
  }
  
  private String findLowestPerm(String code){
    String minCode = code;
    BigInteger min = new BigInteger(minCode);
    
    for(int i = 0; i < code.length(); i++){
      code = rotateRight(code);
      BigInteger n = new BigInteger(code);
      if(n.compareTo(min) < 0){
        minCode = code;
        min     = n;
      }
    }    
    return minCode;
  }
  
  private String rotateRight(String code){
    return code.charAt(code.length()-1) + code.substring(0,code.length()-1);
  }

  public ArrayList<String> getChainCode(){
    ArrayList<String> list = new ArrayList<String>();

    int x,y;
    x = y = 0;

    outer:
    for(y =0;y < height; y++){
      for(x=0 ;x < width; x++){
        Color c = new Color(image.getRGB(x, y));
        if(c.getBlue() > 0){
          list.add(f(x,y));
          break outer;
        }
      }
    }
    return list;
  }
  
  private String f(int x, int y) {
    String code = "";

    Dimension firstCell  = new Dimension(x,y);
    Dimension d1         = firstCell;
    Dimension secondCell = getNextCell8(x, y);
    Dimension  d2        = secondCell;
    
    code = code + direction;

    do{
      x = (int) d2.getWidth();
      y = (int) d2.getHeight();
            
      d1 = d2;
      d2 = getNextCell8(x, y);
      code = code + direction;
      
    }while(!d1.equals(firstCell) && !d2.equals(secondCell) && d2!=null);
    
    return code.substring(0,code.length()-1);
  }

  /**
   * 321
   * 4_0
   * 567 
   */
  private Dimension getNextCell8(int x, int y){
    int startDirection = ((direction & 1) == 0)?((direction+7) % 8):((direction+6) % 8);
    int i = 0;
    Dimension d = null;
    while(i<7){
      d = getCellInDirection(x, y, startDirection);      
      if(d != null){
        direction = startDirection;
        break;
      }
      startDirection = mod ((startDirection +1), 8); 
      i++;
    }
    return d;
  }
  
  

  private Dimension getCellInDirection(int x, int y, int dir){
    switch(dir){
      case 0:
        return (inBounds(x+1,y) && isObjectPixel(x+1,y))?new Dimension(x+1, y):null;
      case 1:
        return(inBounds(x+1,y-1) && isObjectPixel(x+1,y-1))?new Dimension(x+1, y-1):null;
      case 2:
        return(inBounds(x,y-1) && isObjectPixel(x,y-1))?new Dimension(x, y-1):null;
      case 3:
        return(inBounds(x-1,y-1) && isObjectPixel(x-1,y-1))?new Dimension(x-1, y-1):null;
      case 4:
        return(inBounds(x-1,y) && isObjectPixel(x-1,y))?new Dimension(x-1, y):null;
      case 5:
        return(inBounds(x-1,y+1) && isObjectPixel(x-1,y+1))?new Dimension(x-1, y+1):null;
      case 6:
        return(inBounds(x,y+1) && isObjectPixel(x,y+1))?new Dimension(x, y+1):null;
      case 7:
        return(inBounds(x+1,y+1) && isObjectPixel(x+1,y+1))?new Dimension(x+1, y+1):null;
      default:
        return null;
    }
  }
  
  
  
  private boolean inBounds(int x, int y){
    return ((x>=0 && x<width) && (y>=0 && y<height));
  }  

  private boolean isObjectPixel(int x, int y){
    return (new Color(image.getRGB(x, y)).getBlue()>0);
  }
 
   private int mod(int a, int b){
    assert(b>0);    
    int k = (int) Math.ceil((float)Math.abs(a)/b);
    a += (a<0)?k*b:0;
    return a % b;
  }

  public String getDerivativeChainCode(String s) {
    String sCpy = s+s.charAt(0);
    String derivCode = "";
    for(int i = 0; i < s.length(); i++){
      derivCode = derivCode + Math.abs(sCpy.charAt(i)-sCpy.charAt(i+1));
    }
    return derivCode;
  }
}
