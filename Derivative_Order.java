import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import java.util.Arrays;
import java.lang.Math; 

public class Derivative_Order implements PlugIn {
	
	static int mul;
	
	public void run(String arg) {

		ImagePlus imp = IJ.getImage();
		ImageProcessor resIp = imp.getProcessor();
		ImageStack stack = imp.getStack();
		
		String[] points = new String[5];
		points[0] = "3";
		points[1] = "5";
		points[2] = "7";
		points[3] = "9";
		points[4] = "11";
		
		String[] order = new String[2];
		order[0] = "1";
		order[1] = "2";

		
		GenericDialog dialog = new GenericDialog("Options");
		dialog.addChoice("Derivative order", order, order[0]);
		dialog.addChoice("Points", points, points[0]);
		dialog.addNumericField("Multiply derivative results by: ", mul);

 		dialog.showDialog();
		
 		if(dialog.wasCanceled()) {
 			return;
 		}
		
		String getOrder = dialog.getNextChoice();
		String getPoints = dialog.getNextChoice();
		mul = (int)dialog.getNextNumber();
		

		ImageStack resStack = stack.duplicate();
		ImagePlus newImg = new ImagePlus(getOrder + "order_derivative_" + getPoints + "points_" +  imp.getTitle(), resStack);
		ImageProcessor ip = newImg.getProcessor();
		
		int width = newImg.getWidth();
		int height = newImg.getHeight();
		int slices = newImg.getNSlices();


		
		if(getOrder=="1"){
		
			int choice = Integer.valueOf(getPoints);
			
			switch(choice){
				case 3:
					derivative3p1o(newImg, resStack, width, height, slices, mul);
					break;
				case 5:
					derivative5p1o(newImg, resStack, width, height, slices, mul);
					break;
				case 7:
					derivative7p1o(newImg, resStack, width, height, slices, mul);
					break;
				case 9:
					derivative9p1o(newImg, resStack, width, height, slices, mul);
					break;
				case 11:
					derivative11p1o(newImg, resStack, width, height, slices, mul);
					break;
				
			}		
		}else{
			
			int choice = Integer.valueOf(getPoints);
			
			switch(choice){
				case 3:
					derivative3p2o(newImg, resStack, width, height, slices, mul);
					break;
				case 5:
					derivative5p2o(newImg, resStack, width, height, slices, mul);
					break;
				case 7:
					derivative7p2o(newImg, resStack, width, height, slices, mul);
					break;
				case 9:
					derivative9p2o(newImg, resStack, width, height, slices, mul);
					break;
				case 11:
					derivative11p2o(newImg, resStack, width, height, slices, mul);
					break;
				
			}
		}
		
		newImg.show();
	}
	
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// 2ND ORDER DERIVATIVES /////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void derivative3p2o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=2;w<slices;w++){							

						out[w] = mul * (f_[w+1]+f_[w-1] - (2*f_[w]));
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
									
					resIp = stack.getProcessor(1);
					out[1] = mul * (f_[1]-2*f_[1+1]+ f_[1+2]);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul*(f_[slices-2]-2*f_[slices-1] + f_[slices]);
					resIp.setf(x,y,(out[slices]));						
			}
			
		}

	}
	
	
	public void derivative5p2o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=3;w<slices-1;w++){							

						out[w] = mul * ((-1*f_[w-2]+16*f_[w-1]-30*f_[w]+16*f_[w+1]-f_[w+2])/12);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
						
					resIp = stack.getProcessor(1);
					out[1] = mul * (f_[1]-2*f_[1+1]+ f_[1+2]);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * (f_[2+1]+f_[2-1] - (2*f_[2]));
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul*(f_[slices-2]-2*f_[slices-1] + f_[slices]);
					resIp.setf(x,y,(out[slices]));	

					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * (f_[slices]+f_[slices-2] - (2*f_[slices-1]));
					resIp.setf(x,y,(out[slices-1]));
			}
			
		}
	}
	
	
	public void derivative7p2o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		 
		 ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=4;w<slices-2;w++){							

						out[w] = mul * ((2*f_[w-3]-27*f_[w-2]+270*f_[w-1]-490*f_[w]+270*f_[w+1]-27*f_[w+2]+2*f_[w+3])/180);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
						
					resIp = stack.getProcessor(1);
					out[1] = mul * (f_[1]-2*f_[1+1]+ f_[1+2]);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * (f_[2+1]+f_[2-1] - (2*f_[2]));
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(3);
					out[3] = mul * ((-1*f_[3-2]+16*f_[3-1]-30*f_[3]+16*f_[3+1]-f_[3+2])/12);
					resIp.setf(x,y,(out[3]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul*(f_[slices-2]-2*f_[slices-1] + f_[slices]);
					resIp.setf(x,y,(out[slices]));
					
					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * (f_[slices]+f_[slices-2] - (2*f_[slices-1]));
					resIp.setf(x,y,(out[slices-1]));
					
					resIp = stack.getProcessor(slices-2);
					out[slices-2] = mul * ((-1*f_[slices-4]+16*f_[slices-3]-30*f_[slices-2]+16*f_[slices-1]-f_[slices])/12);
					resIp.setf(x,y,(out[slices-2]));					
			}
			
		}
	 }
	 
	 
	public void derivative9p2o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=5;w<slices-3;w++){							

						out[w] = mul * ((-9*f_[w-4]+128*f_[w-3]-1008*f_[w-2]+8064*f_[w-1]-14350*f_[w+0]+8064*f_[w+1]-1008*f_[w+2]+128*f_[w+3]-9*f_[w+4])/5040);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
						
					resIp = stack.getProcessor(1);
					out[1] = mul * (f_[1]-2*f_[1+1]+ f_[1+2]);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * (f_[2+1]+f_[2-1] - (2*f_[2]));
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(3);
					out[3] = mul * ((-1*f_[3-2]+16*f_[3-1]-30*f_[3]+16*f_[3+1]-f_[3+2])/12);
					resIp.setf(x,y,(out[3]));
					
					resIp = stack.getProcessor(4);
					out[4] = mul * ((2*f_[4-3]-27*f_[4-2]+270*f_[4-1]-490*f_[4]+270*f_[4+1]-27*f_[4+2]+2*f_[4+3])/180);
					resIp.setf(x,y,(out[4]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul*(f_[slices-2]-2*f_[slices-1] + f_[slices]);
					resIp.setf(x,y,(out[slices]));
					
					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * (f_[slices]+f_[slices-2] - (2*f_[slices-1]));
					resIp.setf(x,y,(out[slices-1]));
					
					resIp = stack.getProcessor(slices-2);
					out[slices-2] = mul * ((-1*f_[slices-2-2]+16*f_[slices-2-1]-30*f_[slices-2]+16*f_[slices-2+1]-f_[slices-2+2])/12);
					resIp.setf(x,y,(out[slices-2]));
					
					resIp = stack.getProcessor(slices-3);
					out[slices-3] = mul * ((2*f_[slices-3-3]-27*f_[slices-3-2]+270*f_[slices-3-1]-490*f_[slices-3]+270*f_[slices-3+1]-27*f_[slices-3+2]+2*f_[slices-3+3])/180);
					resIp.setf(x,y,(out[slices-3]));				
			}
			
		}
	}
	
	
	public void derivative11p2o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=6;w<slices-4;w++){							

						out[w] = mul * ((8*f_[w-5]-125*f_[w-4]+1000*f_[w-3]-6000*f_[w-2]+42000*f_[w-1]-73766*f_[w]+42000*f_[w+1]-6000*f_[w+2]+1000*f_[w+3]-125*f_[w+4]+8*f_[w+5])/25200);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
						
					resIp = stack.getProcessor(1);
					out[1] = mul * (f_[1]-2*f_[1+1]+ f_[1+2]);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * (f_[2+1]+f_[2-1] - (2*f_[2]));
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(3);
					out[3] = mul * ((-1*f_[3-2]+16*f_[3-1]-30*f_[3]+16*f_[3+1]-f_[3+2])/12);
					resIp.setf(x,y,(out[3]));
					
					resIp = stack.getProcessor(4);
					out[4] = mul * ((2*f_[4-3]-27*f_[4-2]+270*f_[4-1]-490*f_[4]+270*f_[4+1]-27*f_[4+2]+2*f_[4+3])/180);
					resIp.setf(x,y,(out[4]));
					
					resIp = stack.getProcessor(5);
					out[5] = mul * ((-9*f_[5-4]+128*f_[5-3]-1008*f_[5-2]+8064*f_[5-1]-14350*f_[5]+8064*f_[5+1]-1008*f_[5+2]+128*f_[5+3]-9*f_[5+4])/5040);
					resIp.setf(x,y,(out[5]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul*(f_[slices-2]-2*f_[slices-1] + f_[slices]);
					resIp.setf(x,y,(out[slices]));
					
					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * (f_[slices]+f_[slices-2] - (2*f_[slices-1]));
					resIp.setf(x,y,(out[slices-1]));
					
					resIp = stack.getProcessor(slices-2);
					out[slices-2] = mul * ((-1*f_[slices-2-2]+16*f_[slices-2-1]-30*f_[slices-2]+16*f_[slices-2+1]-f_[slices-2+2])/12);
					resIp.setf(x,y,(out[slices-2]));
					
					resIp = stack.getProcessor(slices-3);
					out[slices-3] = mul * ((2*f_[slices-3-3]-27*f_[slices-3-2]+270*f_[slices-3-1]-490*f_[slices-3]+270*f_[slices-3+1]-27*f_[slices-3+2]+2*f_[slices-3+3])/180);
					resIp.setf(x,y,(out[slices-3]));
					
					resIp = stack.getProcessor(slices-4);
					out[slices-4] = mul * ((-9*f_[slices-8]+128*f_[slices-7]-1008*f_[slices-6]+8064*f_[slices-5]-14350*f_[slices-4]+8064*f_[slices-3]-1008*f_[slices-2]+128*f_[slices-1]-9*f_[slices])/5040);
					resIp.setf(x,y,(out[slices-4]));					
			}
			
		}
	}
	
	
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////// 1ST ORDER DERIVATIVES /////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void derivative3p1o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=2;w<slices;w++){							

						out[w] = mul * ((-1*f_[w-1] + 0* f_[w] + f_[w+1])/2);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
									
					resIp = stack.getProcessor(1);
					out[1] = mul * ((-3*f_[1]+4*f_[1+1]-1*f_[1+2])/2);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul * ((f_[slices-2]-4*f_[slices-1] + 3*f_[slices])/2);
					resIp.setf(x,y,(out[slices]));						
			}
			
		}

	}
	
	
	public void derivative5p1o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=3;w<slices-1;w++){							

						out[w] = mul * ((1*f_[w-2]-8*f_[w-1]+0*f_[w+0]+8*f_[w+1]-1*f_[w+2])/12);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
						
					resIp = stack.getProcessor(1);
					out[1] = mul * ((-3*f_[1]+4*f_[1+1]-1*f_[1+2])/2);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * ((-1*f_[2-1] + 0* f_[2] + f_[2+1])/2);
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul * ((f_[slices-2]-4*f_[slices-1] + 3*f_[slices])/2);
					resIp.setf(x,y,(out[slices]));	

					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * ((-1*f_[slices-2] + 0* f_[slices-1] + f_[slices])/2);
					resIp.setf(x,y,(out[slices-1]));
			}
			
		}
	}
	
	
	public void derivative7p1o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		 
		 ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=4;w<slices-2;w++){							

						out[w] = mul * ((-1*f_[w-3]+9*f_[w-2]-45*f_[w-1]+0*f_[w+0]+45*f_[w+1]-9*f_[w+2]+1*f_[w+3])/60);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	

					resIp = stack.getProcessor(1);
					out[1] = mul * ((-3*f_[1]+4*f_[1+1]-1*f_[1+2])/2);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * ((-1*f_[2-1] + 0* f_[2] + f_[2+1])/2);
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(3);
					out[3] = mul * ((1*f_[3-2]-8*f_[3-1]+0*f_[3+0]+8*f_[3+1]-1*f_[3+2])/12);
					resIp.setf(x,y,(out[3]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul * ((f_[slices-2]-4*f_[slices-1] + 3*f_[slices])/2);
					resIp.setf(x,y,(out[slices]));
					
					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * ((-1*f_[slices-2] + 0* f_[slices-1] + f_[slices])/2);
					resIp.setf(x,y,(out[slices-1]));
					
					resIp = stack.getProcessor(slices-2);
					out[slices-2] = mul * ((1*f_[slices-4]-8*f_[slices-3]+0*f_[slices-2]+8*f_[slices-1]-1*f_[slices])/12);
					resIp.setf(x,y,(out[slices-2]));		
			}
			
		}
	 }
	 
		
	public void derivative9p1o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=5;w<slices-3;w++){							

						out[w] = mul * ((3*f_[w-4]-32*f_[w-3]+168*f_[w-2]-672*f_[w-1]+0*f_[w+0]+672*f_[w+1]-168*f_[w+2]+32*f_[w+3]-3*f_[w+4])/840);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
						
					resIp = stack.getProcessor(1);
					out[1] = mul * ((-3*f_[1]+4*f_[1+1]-1*f_[1+2])/2);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * ((-1*f_[2-1] + 0* f_[2] + f_[2+1])/2);
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(3);
					out[3] = mul * ((1*f_[3-2]-8*f_[3-1]+0*f_[3+0]+8*f_[3+1]-1*f_[3+2])/12);
					resIp.setf(x,y,(out[3]));
					
					resIp = stack.getProcessor(4);
					out[4] = mul * ((-1*f_[4-3]+9*f_[4-2]-45*f_[4-1]+0*f_[4]+45*f_[4+1]-9*f_[4+2]+1*f_[4+3])/60);
					resIp.setf(x,y,(out[4]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul * ((f_[slices-2]-4*f_[slices-1] + 3*f_[slices])/2);
					resIp.setf(x,y,(out[slices]));
					
					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * ((-1*f_[slices-2] + 0* f_[slices-1] + f_[slices])/2);
					resIp.setf(x,y,(out[slices-1]));
					
					resIp = stack.getProcessor(slices-2);
					out[slices-2] = mul * ((1*f_[slices-4]-8*f_[slices-3]+0*f_[slices-2]+8*f_[slices-1]-1*f_[slices])/12);
					resIp.setf(x,y,(out[slices-2]));
					
					resIp = stack.getProcessor(slices-3);
					out[slices-3] = mul * ((-1*f_[slices-6]+9*f_[slices-5]-45*f_[slices-4]+0*f_[slices-3]+45*f_[slices-2]-9*f_[slices-1]+1*f_[slices])/60);
					resIp.setf(x,y,(out[slices-3]));		
			}
			
		}
	}
	
	// HERE GO ON
	public void derivative11p1o(ImagePlus img, ImageStack stack, int width, int height, int slices, int mul){
		ImageProcessor resIp = img.getProcessor();

		float f_[] = new float[slices+1];
		float out[] = new float[slices+1];

		for(int x = 0; x < height; x++){
			for(int y = 0 ; y < width; y++){
					for(int z=1;z<slices+1;z++){
						
						resIp = stack.getProcessor(z);
						f_[z] = resIp.getf(x,y);						
					}	
						 
					for(int w=6;w<slices-4;w++){							

						out[w] = mul * ((-2*f_[w-5]+25*f_[w-4]-150*f_[w-3]+600*f_[w-2]-2100*f_[w-1]+0*f_[w+0]+2100*f_[w+1]-600*f_[w+2]+150*f_[w+3]-25*f_[w+4]+2*f_[w+5])/2520);
						resIp = stack.getProcessor(w);
						resIp.setf(x,y,out[w]);
					}	
						
					resIp = stack.getProcessor(1);
					out[1] = mul * ((-3*f_[1]+4*f_[1+1]-1*f_[1+2])/2);
					resIp.setf(x,y,(out[1]));
					
					resIp = stack.getProcessor(2);
					out[2] = mul * ((-1*f_[2-1] + 0* f_[2] + f_[2+1])/2);
					resIp.setf(x,y,(out[2]));
					
					resIp = stack.getProcessor(3);
					out[3] = mul * ((1*f_[3-2]-8*f_[3-1]+0*f_[3+0]+8*f_[3+1]-1*f_[3+2])/12);
					resIp.setf(x,y,(out[3]));
					
					resIp = stack.getProcessor(4);
					out[4] = mul * ((-1*f_[4-3]+9*f_[4-2]-45*f_[4-1]+0*f_[4]+45*f_[4+1]-9*f_[4+2]+1*f_[4+3])/60);
					resIp.setf(x,y,(out[4]));
					
					resIp = stack.getProcessor(5);
					out[5] = mul * ((3*f_[5-4]-32*f_[5-3]+168*f_[5-2]-672*f_[5-1]+0*f_[5+0]+672*f_[5+1]-168*f_[5+2]+32*f_[5+3]-3*f_[5+4])/840);
					resIp.setf(x,y,(out[5]));
					
					resIp = stack.getProcessor(slices);
					out[slices] = mul * ((f_[slices-2]-4*f_[slices-1] + 3*f_[slices])/2);
					resIp.setf(x,y,(out[slices]));
					
					resIp = stack.getProcessor(slices-1);
					out[slices-1] = mul * ((-1*f_[slices-2] + 0* f_[slices-1] + f_[slices])/2);
					resIp.setf(x,y,(out[slices-1]));
					
					resIp = stack.getProcessor(slices-2);
					out[slices-2] = mul * ((1*f_[slices-4]-8*f_[slices-3]+0*f_[slices-2]+8*f_[slices-1]-1*f_[slices])/12);
					resIp.setf(x,y,(out[slices-2]));
					
					resIp = stack.getProcessor(slices-3);
					out[slices-3] = mul * ((-1*f_[slices-6]+9*f_[slices-5]-45*f_[slices-4]+0*f_[slices-3]+45*f_[slices-2]-9*f_[slices-1]+1*f_[slices])/60);
					resIp.setf(x,y,(out[slices-3]));	
					
					resIp = stack.getProcessor(slices-4);
					out[slices-4] = mul * ((3*f_[slices-8]-32*f_[slices-7]+168*f_[slices-6]-672*f_[slices-5]+0*f_[slices-4]+672*f_[slices-3]-168*f_[slices-2]+32*f_[slices-1]-3*f_[slices])/840);
					resIp.setf(x,y,(out[slices-4]));	
			}
			
		}
	}
}
