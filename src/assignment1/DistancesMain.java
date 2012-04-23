package assignment1;

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

public class DistancesMain {
	
	private static void displayMatrix(int[][] A){
		for(int i = 0; i < A.length; i++){
			System.out.println(Arrays.toString(A[i]));
		}
		System.out.println();
	}

	/** 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		BufferedImage bimg = ImageIO.read(new File("a.bmp"));
		int width		   = bimg.getWidth();
		int height		   = bimg.getHeight();
		int[][] imgMatrix  = new int[height][width];		
		
		for(int y = 0; y < height; y++){
			for(int x = 0; x < width; x++){
				Color c			= new Color(bimg.getRGB(x,y));
				imgMatrix[y][x]	= c.getRed()/255;
			}
		}
		
//		imgMatrix = new int[][]{{0,0,0,0,0,0,1,0}
//		   ,{0,0,0,0,0,1,0,0}
//		   ,{0,0,0,0,0,1,0,0}
//		   ,{0,0,0,0,0,1,0,0}
//		   ,{0,1,1,0,0,0,1,0}
//		   ,{0,1,0,0,0,0,0,1}
//		   ,{0,1,0,0,0,0,0,0}
//		   ,{0,1,0,0,0,0,0,0}
//		   };


		
		displayMatrix(imgMatrix);
		DistanceTransform dt = new DistanceTransform(imgMatrix);
		drawImage(dt.D4(), "d4.bmp");
		drawImage(dt.D8(),"d8.bmp");
//		displayMatrix(dt.D8());
		
		//System.out.println(Arrays.toString(rgbs));		
	}
	
	
	private static void drawImage(int[][] output, String filename) throws IOException{
		int h = output.length;
		int w = output[0].length;
		
		GraphicsEnvironment ge		= GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gs			= ge.getDefaultScreenDevice();
		GraphicsConfiguration gc	= gs.getDefaultConfiguration();
		
		BufferedImage outImg		= gc.createCompatibleImage(w, h, Transparency.OPAQUE);
		
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				int comp = output[y][x];
				Color c  = new Color(comp , comp, comp,255);
				outImg.setRGB(x, y, c.getRGB());
			}
		}
		
		File file = new File(filename);
		ImageIO.write(outImg, "bmp", file);
		
	}

}
