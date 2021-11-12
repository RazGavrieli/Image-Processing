package Matala__4_2;


public class GrayImage implements Frame, Comparable<Frame> {
	

	private int[][] frame;
	
	
	public GrayImage(int[][] f) { // constructor
		frame = new int[f.length][f[0].length];
		for (int i = 0; i < f.length; i++) {
			for (int j = 0; j < f[0].length; j++) {
				frame[i][j] = f[i][j]; // amount of gray
			}
		}
	}
	
	public GrayImage(GrayImage f) { // copy constructor
		frame = new int[f.frame.length][f.frame[0].length];
		for (int i = 0; i < f.frame.length; i++) {
			for (int j = 0; j < f.frame[0].length; j++) {
				frame[i][j] = f.frame[i][j]; // amount of gray
			}
		}
	}
	
	public int[][] getFrame() {
		return frame;
	}
	
	@Override
	public void rotate90() { // WORKS 
		int[][] rotated = new int[this.frame[0].length][this.frame.length];
		for (int i = 0; i < rotated.length; i++) {
			for (int j = 0; j < rotated[0].length; j++) {
				rotated[i][rotated[0].length-j-1] = this.frame[j][i];
			}
		}
		
		frame = new int[rotated.length][rotated[0].length];
		for (int i = 0; i < rotated.length; i++) {
			for (int j = 0; j < rotated[0].length; j++) {
				this.frame[i][j] = rotated[i][j];
			}
		}
		
		
	}


	@Override
	public void smooth(int n) { // WORKS 
		if (n%2==0) n--;
		int area=n/2;
		int pixelsInside = 0;
		int sum = 0;
		int[][] smooth = new int[frame.length][frame[0].length]; 
		for (int i=0; i<frame.length; i++) {
			for (int j=0; j < frame[0].length; j++) {
					for (int i2 = -area; i2<=area; i2++) {
						for (int j2 = -area; j2 <=area; j2++) {
							if (isPixelInside((i+i2), (j+j2), frame)) {sum+=frame[i+i2][j+j2]; pixelsInside++;}
						}
					}

				if (pixelsInside!=0) smooth[i][j] = sum/pixelsInside;
				area=n/2;
				sum=0;
				pixelsInside=0;
			}
		}
		
		for (int i = 0; i < smooth.length; i++) {
			for (int j = 0; j < smooth[0].length; j++) {
				frame[i][j] = smooth[i][j];
			}
		}
	}
	
	private boolean isPixelInside(int i, int j, int[][] f) { // WORKS 
		if (i<0 || i>=frame.length) return false; 
		else if (j<0 || j>=frame[0].length) return false;
		else return true; 
		
	}

	@Override
	public int[] getPixel(int x, int y) { // WORKS 
		int[] Pixel = new int[1];
		Pixel[0] = frame[x][y];
		return Pixel;
	} 

	@Override
	public void crop(int x, int y) { // WORKS
		int[][] cropped = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				cropped[i][j] = frame[i][j];
			}
		}
		
		frame = new int[x][y];
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				frame[i][j] = cropped[i][j];
			}
		}
	}

	@Override
	public void addFrom(Frame f) { // WORKS
		if (!(f instanceof GrayImage)) {System.out.println("ERROR - NOT THE SAME TYPE"); return; }
		if (((GrayImage)f).frame.length!=this.frame.length||((GrayImage)f).frame[0].length!=this.frame[0].length) {System.out.println("ERROR - NOT THE SAME SIZE"); return; }
		for (int i = 0; i < frame.length; i++) {
			for (int j = 0; j < frame[0].length; j++) {
				this.frame[i][j]+=((GrayImage)f).frame[i][j];
			}
		}
	}
	
	
	@Override
	public int compareTo(Frame f) { //WORKS
		if (f==null) return 1;
		if (f instanceof GrayImage) {
		if (((GrayImage)f).getSize()<this.getSize()) return 1;
		else if (((GrayImage)f).getSize()>this.getSize()) return -1;
		else return 0; 
		} else {
			if (((RGBImage)f).getSize()<this.getSize()) return 1;
			else if (((RGBImage)f).getSize()>this.getSize()) return -1;
			else return 0;
		}
	}
 	
	public int getSize() {
		return frame.length*frame[0].length;
	}
	

	
	
	
}
