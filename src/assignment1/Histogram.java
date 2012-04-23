package assignment1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Histogram {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String filename = "bhramaputra.jpg";

		//		BufferedImage bimg	= ImageIO.read(new File("a.bmp"));
		BufferedImage bimg	= ImageIO.read(new File(filename));
		int[] freqR			= getRedHistogram(bimg);
		int[] freqG			= getGreenHistogram(bimg);
		int[] freqB			= getBlueHistogram(bimg);

		System.out.println(Arrays.toString(freqR));
		System.out.println(Arrays.toString(freqG));
		System.out.println(Arrays.toString(freqB));		
	}

	
	public static int[] getRedHistogram(BufferedImage bimg){
		int w				= bimg.getWidth();
		int h				= bimg.getHeight();
		int[] rgbs			= new int[w*h];
		int[] freqR			= new int[256];
		bimg.getRGB(0, 0, w, h, rgbs, 0, w);
		
		for(int i = 0; i < rgbs.length; i++){
			Color c = new Color(rgbs[i]);
			freqR[c.getRed()]++;
		}
		
		return freqR;
	}


	public static int[] getGreenHistogram(BufferedImage bimg){
		int w				= bimg.getWidth();
		int h				= bimg.getHeight();
		int[] rgbs			= new int[w*h];
		int[] freqG			= new int[256];
		bimg.getRGB(0, 0, w, h, rgbs, 0, w);
		
		for(int i = 0; i < rgbs.length; i++){
			Color c = new Color(rgbs[i]);
			freqG[c.getGreen()]++;
		}
		
		return freqG;
	}

	public static int[] getBlueHistogram(BufferedImage bimg){
		int w				= bimg.getWidth();
		int h				= bimg.getHeight();
		int[] rgbs			= new int[w*h];
		int[] freqB			= new int[256];
		bimg.getRGB(0, 0, w, h, rgbs, 0, w);
		
		for(int i = 0; i < rgbs.length; i++){
			Color c = new Color(rgbs[i]);
			freqB[c.getBlue()]++;
		}
		
		return freqB;
	}




}