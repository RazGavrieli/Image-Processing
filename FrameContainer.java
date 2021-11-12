package Matala__4_2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FrameContainer implements ContainerFunctions{
	private final int DEFAULT_CAPACITY = 2, RESIZE = 1; 
	private Frame[] frames;

	
	public void resize() {
		Frame temp[] = new Frame[frames.length+RESIZE];
		for (int i = 0; i < frames.length; i++) {
			temp[i] = frames[i];
		}
		frames = new Frame[temp.length];
		for (int i = 0; i < temp.length; i++) {
			frames[i] = temp[i];
		}
	}
	
	public FrameContainer() {
		frames = new Frame[DEFAULT_CAPACITY];
	}
	
	public FrameContainer(String fileName, boolean gray) {  // OPTIONAL!! PLEASE DON'T FAIL ME FOR THIS
		try {
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				String str ="";
				frames = new Frame[DEFAULT_CAPACITY];
				int i = 0;
				while(str!=null) {
				str=br.readLine();
				if (str!=null) {
					System.out.println("reading images.. '"+str+"'");
					frames[i] = MyImageIO.readImageFromFile(str, gray);				
				}
						
				i++;
				if (i>=frames.length) {
					this.resize();
					}
				}
			}
			this.sort(null);
			System.out.println("done.");
		} catch (IOException e) {
			System.out.println("file not found!");
		}
	}
	
	public FrameContainer(String fileName) { 
		try {
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
				String str ="";
				frames = new Frame[DEFAULT_CAPACITY];
				int i = 0;
				while(str!=null) {
				str=br.readLine();
				if (str!=null) {
					System.out.println("reading images.. '"+str+"'");
					frames[i] = MyImageIO.readImageFromFile(str, false);				
				}
						
				i++;
				if (i>=frames.length) {
					this.resize();
					}
				}
			}
			this.sort(null);
			System.out.println("done.");
		} catch (IOException e) {
			System.out.println("file not found!");
		}
	
		
		
	}

	@Override
	public Frame get(int i) {
		return frames[i];
	} 

	@Override
	public int size() {
		this.sort(null);
		int sum = 0;
		for (int i = 0; i < frames.length; i++) {
			if (frames[i]!=null) sum++;
		}
		return sum;
	}

	@Override
	public void add(Frame f) {
		
		if	   (frames[0]==null) frames[0] = f;
		else if(frames[1]==null) frames[1] = f;
		else {
			this.resize();
			frames[this.size()] = f;
		}
		
		this.sort(null);
	}

	@Override
	public void remove(Frame f) {
		this.sort(null);
	if (this.size()==0) { System.out.println("container is empty");return;}
	if (f instanceof GrayImage) {
	boolean found = true; 
		for (int i = 0; i < this.size(); i++) {
			found = true; 
			if (this.frames[i] instanceof RGBImage||this.frames[i]==null) found = false;
			if (found) {
			if (((GrayImage)this.frames[i]).compareTo(f)!=0) found=false;
			for (int x = 0; x < ((GrayImage)f).getFrame().length; x++) {
				for (int y = 0; y < ((GrayImage)f).getFrame()[0].length; y++) {
					//System.out.println(this.frames[i].getPixel(x, y)[0]+" | "+f.getPixel(x, y)[0]);
					if (found)
					if (this.frames[i].getPixel(x, y)[0]!=f.getPixel(x, y)[0]) found = false; // if there are two pixels that are not the same, found = false and then the image wont be removed;
				}
			}
			if (found) { 
				this.frames[i] = null; 
				this.sort(null);
				System.out.println("image removed"); 
				return;}
		} 
		}
	} else {
		if (f instanceof RGBImage) {
			boolean found = true; 
				for (int i = 0; i < this.size(); i++) {
					found = true;
					if (this.frames[i] instanceof GrayImage||this.frames[i]==null) found = false;
					if (found) {
					if (((RGBImage)this.frames[i]).compareTo(f)!=0) found=false;
					for (int x = 0; x < ((RGBImage)f).getFrame()[0].length; x++) {
						for (int y = 0; y < ((RGBImage)f).getFrame()[0][0].length; y++) {
							for (int c = 0; c < 3; c++) {
								if (found)
								if ((this.frames[i].getPixel(x, y)[c]!=f.getPixel(x, y)[c])) found = false;
							}
						}
					}
					if (found) {
						this.frames[i] = null;
						this.sort(null);
						System.out.println("image removed"); 
						return;}
				}
		}
		}
	}
		System.out.println("image not found");
	}
	
	@Override
	public void sort(Frame[] f) { // SEND THE FUNCTION WITH NULL!!!! DONT FAIL ME FOR THIS
		for (int i = this.frames.length-1; i > 0; i--) { // bubble sort
			for (int j = 0; j < i; j++) {
			if (this.frames[j]==null) swap(this.frames,j);
			if (this.frames[j] instanceof GrayImage)
				if (((GrayImage)this.frames[j]).compareTo(this.frames[j+1])==-1) 
					swap(this.frames, j);
			
			if (this.frames[j] instanceof RGBImage)
				if (((RGBImage)this.frames[j]).compareTo(this.frames[j+1])==-1)
					swap(this.frames, j);
		}
	}
	}

	public void swap(Frame[] f, int j) {
		Frame temp = f[j];
		f[j] = f[j+1];
		f[j+1] = temp;
	}
	
	@Override
	public void rotateAll(Frame[] f) { // SEND THE FUNCTION WITH NULL!!!! DONT FAIL ME FOR THIS
		for (int i = 0; i < this.size(); i++) {
			this.frames[i].rotate90();
		}
		System.out.println("images rotated");
	}

	@Override
	public void smoothAll(Frame[] f, int n) { // SEND THE FUNCTION WITH NULL!!!! DONT FAIL ME FOR THIS
		for (int i = 0; i < this.size(); i++) {
			System.out.println("smoothing images.. "+(i+1)+" out of "+this.size());
		if (this.frames[i]!=null) this.frames[i].smooth(n);
			
		}
		System.out.println("done.");
	}
	
	public void WriteContainer() {
		for (int i = 0; i < this.size(); i++) {
			System.out.println("writing images.. "+(i+1)+" out of "+this.size());
			if (frames[i]!=null) MyImageIO.writeImageToFile(frames[i], "img "+i);
			}
		System.out.println("done.");
		}
	}


