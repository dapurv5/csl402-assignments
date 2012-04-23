package assignment1;

/**
 * Calculates the city block distance from each pixel to nearest non-zero pixel.
 * @author apurv
 *
 */
public class DistanceTransform {
	
	private int[][] input;
	private int w;
	private int h;
	
	private int INF;
	
	public DistanceTransform(int[][] input){
		this.input = input;
		this.w	   = input[0].length;
		this.h	   = input.length;
		this.INF   = w*h;
	}
	
	
	public int[][] D4(){
		int[][] d = new int[h][w];
		
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){								
				d[y][x] = (input[y][x] == 1)?0:INF;
			}
		}
		
		for(int y = 0; y < h; y++){
			int i = (y>0)?1:0;
			for(int x = 0; x < w; x++){
				int j	 = (x>0)?1:0;
				int left = d[y][x-j];
				int top  = d[y-i][x];				
				int t	 = Math.min(left, top);
				d[y][x]	 = Math.min(t+1, d[y][x]);
			}
		}
		
		for(int y = h-1; y>=0; y--){
			int i = (y<h-1)?1:0;
			for(int x = w-1; x>=0 ; x--){
				int j		= (x < w-1)?1:0;
				int right	= d[y][x+j];
				int bottom	= d[y+i][x];
				int t		= Math.min(right, bottom);
				d[y][x]		= Math.min(t+1, d[y][x]);
			}
		}

		return d;
	}
	
	
	public int[][] D8(){
		int[][] d = new int[h][w];
		
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){								
				d[y][x] = (input[y][x] == 1)?0:INF;
			}
		}
		
		for(int y = 0; y < h; y++){
			int i = (y>0)?1:0;
			int k = (y<h-1)?1:0;
			for(int x = 0; x < w; x++){
				int j			= (x>0)?1:0;
				int left	 	= d[y][x-j];
				int top 		= d[y-i][x];
				int topleft		= d[y-i][x-j];
				int bottomleft	= d[y+k][x-j];
				
				int t		 	= Math.min(left, top);
				t				= Math.min(Math.min(topleft, bottomleft), t);
				d[y][x]	 		= Math.min(t+1, d[y][x]);
			}
		}
		
		for(int y = h-1; y>=0; y--){
			int i = (y<h-1)?1:0;
			int k = (y>0)?1:0;
			for(int x = w-1; x>=0 ; x--){
				int j			= (x < w-1)?1:0;
				int right		= d[y][x+j];
				int bottom		= d[y+i][x];
				int bottomright	= d[y+i][x+j];
				int topright	= d[y-k][x+j];
				
				int t			= Math.min(right, bottom);
				t				= Math.min(Math.min(topright, bottomright),t);
				d[y][x]			= Math.min(t+1, d[y][x]);
			}
		}

		return d;		
	}

}
