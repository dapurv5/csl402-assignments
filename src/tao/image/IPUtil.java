package tao.image;

import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

/**
 * Additional resources: http://homepages.inf.ed.ac.uk/rbf/HIPR2/flatjavasrc/
 * @author apurv
 *
 */
public class IPUtil {

  private static ColorMode mode = ColorMode.RED;
  public final static int G     = 256;

  public static void setMode(ColorMode mode){
    IPUtil.mode = mode;
  }


  public static int getColor(Color c){
    float[] compArray = new float[4];
    c.getColorComponents(compArray);

    return (int) (compArray[mode.ordinal()]*255);

  }


  public static int[][] readImageAsMatrix(BufferedImage bimg){
    int width                  = bimg.getWidth();
    int height                 = bimg.getHeight();
    int[][] imgMatrix  = new int[height][width];            

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c                 = new Color(bimg.getRGB(x,y));                                       
        imgMatrix[y][x] = getColor(c);
      }
    }
    return imgMatrix;
  }


  public static void displayMatrix(int[][] matrix){
    for(int i = 0; i < matrix.length; i++){
      System.out.println(Arrays.toString(matrix[i]));
    }
  }


  /** 
   * @param format can be anything of {"jpg", "bmp"}
   * @throws IOException
   */
  public static void drawImage(Color[][] output, String filename, String format) throws IOException{
    int h = output.length;
    int w = output[0].length;

    GraphicsEnvironment ge   = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice gs        = ge.getDefaultScreenDevice();
    GraphicsConfiguration gc = gs.getDefaultConfiguration();

    BufferedImage outImg     = gc.createCompatibleImage(w, h, Transparency.OPAQUE);

    for(int y = 0; y < h; y++){
      for(int x = 0; x < w; x++){
        Color c = output[y][x];
        outImg.setRGB(x, y, c.getRGB());
      }
    }

    File file = new File(filename);
    ImageIO.write(outImg, format, file);

  }

  public static Color[][] combineColors(int[][] red, int[][] green, int[][] blue){
    assert (red.length == green.length);
    assert (red.length == blue.length);
    assert (red[0].length == green[0].length);
    assert (red[0].length == blue[0].length);

    int h = red.length;
    int w = red[0].length;


    Color[][] cM = new Color[h][w];
    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        cM[i][j] = new Color(red[i][j], green[i][j], blue[i][j], 255);
      }
    }
    return cM;
  }


  public static int[] getHistogram(BufferedImage bimg){
    int w                           = bimg.getWidth();
    int h                           = bimg.getHeight();
    int[] rgbs                      = new int[w*h];
    int[] freq                      = new int[G];
    bimg.getRGB(0, 0, w, h, rgbs, 0, w);

    for(int i = 0; i < rgbs.length; i++){
      Color c = new Color(rgbs[i]);
      freq[getColor(c)]++;
    }
    return freq;
  }
  
  public static int[] getHistogram(int[][] imgMatrix){
    int h = imgMatrix.length;
    int w = imgMatrix[0].length;
    int[] freq = new int[G];
    
    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        freq[imgMatrix[i][j]]++;
      }
    }
    return freq;
  }

  
  public static int[] cumulate(int[] H){
    int[] Hc = new int[H.length];
    Hc[0]    = 0;
    
    for(int i = 1; i < H.length; i++){
      Hc[i] = Hc[i-1] + H[i];
    }
    return Hc;
  }
  
  
  public static int[][] equaliseHistogram(int[][] imgMatrix){
    float h = imgMatrix.length;
    float w = imgMatrix[0].length;
    
    int[] H  = IPUtil.getHistogram(imgMatrix);
    int[] Hc = cumulate(H);
    
    int[][] tImgMatrix = new int[(int)h][(int)w]; 
    
    int[] T = new int[G];
    float k   = (G-1)/(h*w);
    for(int p = 0; p < 256; p++){
      T[p] = (int)(k * Hc[p]);
    }
    
    for(int i = 0; i < h; i++){
      for(int j = 0; j < w; j++){
        tImgMatrix[i][j] = T[imgMatrix[i][j]];
      }
    }    
    return tImgMatrix;
  }
  
  
  public static int maxPixelValue(int[] H){
    int max = -1;
    for(int i = 0; i < H.length; i++){
      max = (H[i]>0)?i:max;
    }
    return max;
  }
  
  public static int minPixelValue(int[] H){
    int min = G+1;
    for(int i = H.length-1; i >= 0; i--){
      min = (H[i]>0)?i:min;
    }
    return min;
  }
  
  public static int weightedMean(int[][] imgMatrix){
    int mean;
    float sigmaF = 0;
    float sigmaFI = 0;
    int[] H = IPUtil.getHistogram(imgMatrix);
    
    for(int i = 0; i < H.length; i++){
      sigmaF += H[i];
      sigmaFI += i*H[i];
    }
    
    mean = (int)(sigmaFI/sigmaF);
    return mean;
  }
    

  public static int applyOperator(int[][] imgMatrix, int Ii, int If, int Ji, int Jf, int[][] operator){
    int value  = 0;
    int height = imgMatrix.length;
    int width  = imgMatrix[0].length;

    for(int i = Ii; i <= If; i++){
      for(int j = Ji; j <= Jf; j++){
        if(i < 0 || i >= height || j < 0 || j >= width){continue;}
        value += operator[i-Ii][j-Ji]*imgMatrix[i][j]; 
      }
    }
    return value;
  }

  public static int[][] multiplyByFactor(int[][] imgMatrix, float f){
    int height = imgMatrix.length;
    int width  = imgMatrix[0].length;
    
    int[][] tImgMatrix = new int[height][width];
    
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        tImgMatrix[i][j] = (int)f*imgMatrix[i][j];
      }
    }
    return tImgMatrix;
  }
  
  public static int[][] subtract(int[][] A, int[][] B){
    int height = A.length;
    int width  = A[0].length;
    
    int[][] tImgMatrix = new int[height][width];
    
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){        
        tImgMatrix[i][j] = A[i][j] - B[i][j];
        tImgMatrix[i][j] = (tImgMatrix[i][j]<0)?0:tImgMatrix[i][j]; 
      }
    }
    
    return tImgMatrix;
  }
  
  public static int[][] add(int[][] A, int[][] B){
    int height = A.length;
    int width  = A[0].length;
    
    int[][] tImgMatrix = new int[height][width];
    
    for(int i = 0; i < height; i++){
      for(int j = 0; j < width; j++){
        tImgMatrix[i][j] = A[i][j] + B[i][j];
        tImgMatrix[i][j] = (tImgMatrix[i][j]>255)?255:tImgMatrix[i][j]; 
      }
    }
    
    return tImgMatrix;
  }

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    String filename = "noise_1.tif";

    //              BufferedImage bimg      = ImageIO.read(new File("a.bmp"));
    BufferedImage bimg = ImageIO.read(new File(filename));

    IPUtil.setMode(ColorMode.RED);
    int[][] rM = readImageAsMatrix(bimg);

    IPUtil.setMode(ColorMode.GREEN);
    int[][] gM = readImageAsMatrix(bimg);

    IPUtil.setMode(ColorMode.BLUE);
    int[][] bM = readImageAsMatrix(bimg);

    System.out.println(Arrays.toString(getHistogram(bimg)));
    System.out.println(Arrays.toString(getHistogram(bM)));


    drawImage(combineColors(rM, gM, bM),"out-"+filename, "tif");

  }

}
