package assignment10;

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
  
    
  public ArrayList<Complex> getChainCodePixels(){
    ArrayList<Complex> list = new ArrayList<Complex>();

    int x,y;
    x = y = 0;

    outer:
    for(y =0;y < height; y++){
      for(x=0 ;x < width; x++){
        Color c = new Color(image.getRGB(x, y));
        if(c.getBlue() > 0){
          list.addAll(f(x,y));
          break outer;
        }
      }
    }
    return list;
  }
  
  
  
  private ArrayList<Complex> f(int x, int y) {
    ArrayList<Complex> code = new ArrayList<Complex>();

    Dimension firstCell  = new Dimension(x,y);
    Dimension d1         = firstCell;
    code.add(new Complex(x,y)); //Add cell1
    
    Dimension secondCell = getNextCell8(x, y);
    Dimension  d2        = secondCell;    

    do{
      x = (int) d2.getWidth();
      y = (int) d2.getHeight();
            
      d1 = d2;
      d2 = getNextCell8(x, y);
      code.add(new Complex(x,y));
      
    }while(!d1.equals(firstCell) && !d2.equals(secondCell) && d2!=null);
    
    code.remove(0); //Since cell1 got added twice.    
    return code;
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

}
