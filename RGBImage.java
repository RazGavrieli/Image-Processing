package Matala__4_2;


public class RGBImage implements Frame, Comparable<Frame>{
	
	
	private int frame[][][];

	public RGBImage (int[][][] f) {
		frame = new int[3][f[0].length][f[0][0].length];
		for (int i = 0; i < f[0].length; i++) {
			for (int j = 0; j < f[0][0].length; j++) {
				frame[0][i][j] = f[0][i][j]; //R
				frame[1][i][j] = f[1][i][j]; //G
				frame[2][i][j] = f[2][i][j]; //B
			}
		}
	}
	
	public RGBImage (RGBImage f) {

		frame = new int[3][f.frame[0].length][f.frame[0][0].length];
		for (int i = 0; i < f.frame[0].length; i++) {
			for (int j = 0; j < f.frame[0][0].length; j++) {
				frame[0][i][j] = f.frame[0][i][j]; //R
				frame[1][i][j] = f.frame[1][i][j]; //G
				frame[2][i][j] = f.frame[2][i][j]; //B
			}
		}
	}
	
	public int[][][] getFrame() {
		return frame;
	}

	@Override
	public void rotate90() { // WORKS 
		int[][][] rotated = new int[3][this.frame[0][0].length][this.frame[0].length];
		for (int i = 0; i < rotated[0].length; i++) {
			for (int j = 0; j < rotated[0][0].length; j++) {
				rotated[0][i][rotated[0][0].length-j-1] = this.frame[0][j][i];
				rotated[1][i][rotated[0][0].length-j-1] = this.frame[1][j][i];
				rotated[2][i][rotated[0][0].length-j-1] = this.frame[2][j][i];
			}
		}
		
		frame = new int[3][rotated[0].length][rotated[0][0].length];
		for (int i = 0; i < rotated[0].length; i++) {
			for (int j = 0; j < rotated[0][0].length; j++) {
				this.frame[0][i][j] = rotated[0][i][j];
				this.frame[1][i][j] = rotated[1][i][j];
				this.frame[2][i][j] = rotated[2][i][j];
			}
		}
		
	}
	@Override
	public void smooth(int n) { // works
		if (n%2==0) n--;
		int area=n/2;
		int pixelsInside = 0;
		int sum = 0;
		int[][][] smooth = new int[3][frame[0].length][frame[0][0].length]; 
	for (int c = 0; c < 3; c++) {
		for (int i=0; i<frame[0].length; i++) {
			for (int j=0; j < frame[0][0].length; j++) {
					for (int i2 = -area; i2<=area; i2++) {
						for (int j2 = -area; j2 <=area; j2++) {
							if (isPixelInside((i+i2), (j+j2), frame)) {sum+=frame[c][i+i2][j+j2]; pixelsInside++;}
						}
					}

				if (pixelsInside!=0) smooth[c][i][j] = sum/pixelsInside;
				area=n/2;
				sum=0;
				pixelsInside=0;
			}
		}
	}
for (int c=0; c<3; c++) {
	for (int i = 0; i < smooth[0].length; i++) {
		for (int j = 0; j < smooth[0][0].length; j++) {
			frame[c][i][j] = smooth[c][i][j];
		}
	}
	}
}


	private boolean isPixelInside(int i, int j, int[][][] f) { // WORKS 
		if (i<0 || i>=frame[0].length) return false; 
		else if (j<0 || j>=frame[0][0].length) return false;
		else return true; 
		
	}
	
	@Override
	public int[] getPixel(int x, int y) { //WORKS
		int[] Pixel = new int[3];
		Pixel[0] = frame[0][x][y];
		Pixel[1] = frame[1][x][y];
		Pixel[2] = frame[2][x][y];
		return Pixel;
	}

	@Override
	public void crop(int x, int y) { // WORKS
		int[][][] cropped = new int[3][x][y];
		for (int c=0; c<3; c++) {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				cropped[c][i][j] = frame[c][i][j];
			}
		}
		}
		
		frame = new int[3][x][y];
		for (int c=0; c<3; c++) {
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				frame[c][i][j] = cropped[c][i][j];
			}
		}
		}
	}

	@Override
	public void addFrom(Frame f) { // WORKS
		if (!(f instanceof RGBImage)) {System.out.println("ERROR - NOT THE SAME TYPE"); return; }
		if (((RGBImage)f).frame[0].length!=this.frame[0].length||((RGBImage)f).frame[0][0].length!=this.frame[0][0].length) {System.out.println("ERROR - NOT THE SAME SIZE"); return; }
		for (int c=0; c<3; c++) {
		for (int i = 0; i < frame[0].length; i++) {
			for (int j = 0; j < frame[0][0].length; j++) {
				if (this.frame[c][i][j]+((RGBImage)f).frame[c][i][j]>255)this.frame[c][i][j]=255;
				else this.frame[c][i][j]+=((RGBImage)f).frame[c][i][j];
			}
		}
		}
	}		
	
	
	@Override
	public int compareTo(Frame f) { //WORKS
		if (f==null) return 1;
		if (f instanceof RGBImage) {
		if (((RGBImage)f).getSize()<this.getSize()) return 1;
		else if (((RGBImage)f).getSize()>this.getSize()) return -1;
		else return 0; }
		else { 
			if (((GrayImage)f).getSize()<this.getSize()) return 1;
			else if (((GrayImage)f).getSize()>this.getSize()) return -1;
			else return 0; 
		}
	}
	
	public int getSize() {
		return frame[0].length*frame[0][0].length;
	}

	
	
	

}
