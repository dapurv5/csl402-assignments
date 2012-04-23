package assignment1;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class DistanceTransformTest extends TestCase{
		

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int[][] imgMatrix = new int[][]{{0,0,0,0,0,0,1,0}
		   							   ,{0,0,0,0,0,1,0,0}
		   							   ,{0,0,0,0,0,1,0,0}
		   							   ,{0,0,0,0,0,1,0,0}
		   							   ,{0,1,1,0,0,0,1,0}
		   							   ,{0,1,0,0,0,0,0,1}
		   							   ,{0,1,0,0,0,0,0,0}
		   							   ,{0,1,0,0,0,0,0,0}
		  							   };

		DistanceTransform dt = new DistanceTransform(imgMatrix);

		int[][] expRes = {
					  	 {5, 4, 4, 3, 2, 1, 0, 1},
					  	 {4, 3, 3, 2, 1, 0, 1, 2},
					  	 {3, 2, 2, 2, 1, 0, 1, 2},
					  	 {2, 1, 1, 2, 1, 0, 1, 2},
					  	 {1, 0, 0, 1, 2, 1, 0, 1},
					  	 {1, 0, 1, 2, 3, 2, 1, 0},
					  	 {1, 0, 1, 2, 3, 3, 2, 1},
					  	 {1, 0, 1, 2, 3, 4, 3, 2}
					  	 };
		int h = expRes.length;
		int w = expRes[0].length;
		
		int[][] res = dt.D4();
		
		boolean flag = false;
		
		for(int y = 0; y < h; y++){
			for(int x = 0; x < w; x++){
				if(res[y][x] != expRes[y][x]){
					flag = true;
					break;
				}
			}
		}
		
		if(flag)
			fail("Fail ...");
	}

}
