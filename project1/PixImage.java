/* PixImage.java */

/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  Descriptions of the methods you must implement appear below.
 *  They include a constructor of the form
 *
 *      public PixImage(int width, int height);
 *
 *  that creates a black (zero intensity) image of the specified width and
 *  height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 *  All methods in this class must be implemented to complete Part I.
 *  See the README file accompanying this project for additional details.
 */

import java.util.Arrays;

public class PixImage {

  /**
   *  Define any variables associated with a PixImage object here.  These
   *  variables MUST be private.
   */
    private int width = 0;
    private int height = 0;
    private short[][][] pixels;


  /**
   * PixImage() constructs an empty PixImage with a specified width and height.
   * Every pixel has red, green, and blue intensities of zero (solid black).
   *
   * @param width the width of the image.
   * @param height the height of the image.
   */
  public PixImage(int width, int height) {
      this.width = width;
      this.height = height;
      pixels = new short[width][height][3];
  }

    /**
     * PixImage() constructs a PixImage same as the PixImage other.
     *
     * @param other an PixImage.
     */
    public PixImage(PixImage other) {
	this(other.width, other.height);
	for (int x = 0; x < width; x++) {
	    for (int y = 0; y < height; y++) {
		this.setPixel(x, y, other.getRed(x, y), other.getGreen(x, y), other.getBlue(x, y));
	    }
	}
    }

  /**
   * getWidth() returns the width of the image.
   *
   * @return the width of the image.
   */
  public int getWidth() {
      return width;
  }

  /**
   * getHeight() returns the height of the image.
   *
   * @return the height of the image.
   */
  public int getHeight() {
      return height;
  }

  /**
   * getRed() returns the red intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the red intensity of the pixel at coordinate (x, y).
   */
  public short getRed(int x, int y) {
      return pixels[x][y][0];
  }

  /**
   * getGreen() returns the green intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the green intensity of the pixel at coordinate (x, y).
   */
  public short getGreen(int x, int y) {
      return pixels[x][y][1];
  }

  /**
   * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the blue intensity of the pixel at coordinate (x, y).
   */
  public short getBlue(int x, int y) {
      return pixels[x][y][2];
  }

  /**
   * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
   * and blue intensities.
   *
   * If any of the three color intensities is NOT in the range 0...255, then
   * this method does NOT change any of the pixel intensities.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param red the new red intensity for the pixel at coordinate (x, y).
   * @param green the new green intensity for the pixel at coordinate (x, y).
   * @param blue the new blue intensity for the pixel at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
      if (0 <= red + green + blue && red + green + blue <= 255 * 3)
	  pixels[x][y] = new short[] {red, green, blue};
  }

  /**
   * toString() returns a String representation of this PixImage.
   *
   * This method isn't required, but it should be very useful to you when
   * you're debugging your code.  It's up to you how you represent a PixImage
   * as a String.
   *
   * @return a String representation of this PixImage.
   */
  public String toString() {
      String s = new String();
      for (int i = 0; i < width; i++) {
	  for (int j = 0; j < height; j++) {
	      s = s + Arrays.toString(pixels[i][j]) + " ";
	  }
	  s = s + "\n";
      }
    return s;
  }

    /**
     * inImage() returns true if the pixel at coordinate (x, y) exists in this PixImage
     * and returns false if the pixel does not exist in this PixImage.
     *
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return whether the pixel at coordinate (x, y) exists in this PixImage.
     */
    private boolean inImage(int x, int y) {
	return !(x < 0 || x >= width || y < 0 || y >= height);
    }

    /**
     * getPixelAvg() returns the average red, green, and blue intensities of 
     * the neighboring pixels of the pixel at coordinate (x, y) in this PixImage.
     * 
     * @param x the x-coordinate of the pixel.
     * @param y the y-coordinate of the pixel.
     * @return the average red, green, and blue intensities for the pixel at coordinate (x, y)
     */
    public short[] getPixelAvg(int x, int y) {
	int red = 0, green = 0, blue = 0;
	int num = 0;  // number of neighbors
	for (int i = x - 1; i <= x + 1; i++) {
	    for (int j = y - 1; j <= y + 1; j++) {
		if (inImage(i, j)) {
		    red += this.getRed(i, j);
		    green += this.getGreen(i, j);
		    blue += this.getBlue(i, j);
		    num++;
		}
	    }
	}
	short[] res = new short[] {(short) (red/num), (short) (green/num), (short) (blue/num)};
	return res;
    }

  /**
   * boxBlur() returns a blurred version of "this" PixImage.
   *
   * If numIterations == 1, each pixel in the output PixImage is assigned
   * a value equal to the average of its neighboring pixels in "this" PixImage,
   * INCLUDING the pixel itself.
   *
   * A pixel not on the image boundary has nine neighbors--the pixel itself and
   * the eight pixels surrounding it.  A pixel on the boundary has six
   * neighbors if it is not a corner pixel; only four neighbors if it is
   * a corner pixel.  The average of the neighbors is the sum of all the
   * neighbor pixel values (including the pixel itself) divided by the number
   * of neighbors, with non-integer quotients rounded toward zero (as Java does
   * naturally when you divide two integers).
   *
   * Each color (red, green, blue) is blurred separately.  The red input should
   * have NO effect on the green or blue outputs, etc.
   *
   * The parameter numIterations specifies a number of repeated iterations of
   * box blurring to perform.  If numIterations is zero or negative, "this"
   * PixImage is returned (not a copy).  If numIterations is positive, the
   * return value is a newly constructed PixImage.
   *
   * IMPORTANT:  DO NOT CHANGE "this" PixImage!!!  All blurring/changes should
   * appear in the new, output PixImage only.
   *
   * @param numIterations the number of iterations of box blurring.
   * @return a blurred version of "this" PixImage.
   */
  public PixImage boxBlur(int numIterations) {
      if (numIterations <= 0) 
	  return this;
      PixImage res = new PixImage(width, height);
      PixImage old = new PixImage(this);
      int iter = 0;
      while (iter < numIterations) {
	  for (int x = 0; x < width; x++) {
	      for (int y = 0; y < height; y++) {
		  short[] avg = old.getPixelAvg(x, y);
		  res.setPixel(x, y, avg[0], avg[1], avg[2]);  
	      }
	  }
	  old = new PixImage(res);
	  iter++;
      }
      return res;
  }

  /**
   * mag2gray() maps an energy (squared vector magnitude) in the range
   * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
   * is logarithmic, but shifted so that values of 5,080 and below map to zero.
   *
   * DO NOT CHANGE THIS METHOD.  If you do, you will not be able to get the
   * correct images and pass the autograder.
   *
   * @param mag the energy (squared vector magnitude) of the pixel whose
   * intensity we want to compute.
   * @return the intensity of the output pixel.
   */
  private static short mag2gray(long mag) {
    short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

    // Make sure the returned intensity is in the range 0...255, regardless of
    // the input value.
    if (intensity < 0) {
      intensity = 0;
    } else if (intensity > 255) {
      intensity = 255;
    }
    return intensity;
  }
    
    /**
     * reflectImage() returns a reflected version of "this" PixImage
     * where the image is reflected across each image boundary. For example, 
     * we treat the pixel (-1, 2) as if it had the same RGB intensities as (0, 2).
     * The resulted image has a dimension of width + 2 by height + 2.
     *
     * @return the reflected image of "this" PixImage
     */
    private PixImage reflectImage() {
	PixImage res = new PixImage(width + 2, height + 2);
	// four corners
	res.pixels[0][0] = this.pixels[0][0];
	res.pixels[0][height + 1] = this.pixels[0][height - 1];
	res.pixels[width + 1][0] = this.pixels[width - 1][0];
	res.pixels[width + 1][height + 1] = this.pixels[width - 1][height - 1];

	for (int x = 1; x <= width; x++) {  // reflect boundary columns
	    res.pixels[x][0] = this.pixels[x - 1][0];
	    res.pixels[x][height + 1] = this.pixels[x - 1][height - 1];
	}
	for (int y = 1; y <= height; y++) {  // reflect boundary rows
	    res.pixels[0][y] = this.pixels[0][y - 1];
	    res.pixels[width + 1][y] = this.pixels[width - 1][y - 1];
	}
	for (int x = 1; x <= width; x++) {  // fill in middle
	    for (int y = 1; y <= height; y++) {
		res.pixels[x][y] = this.pixels[x - 1][y - 1];
	    }
	}
	return res;
    }
    
    /**
     * gx() returns the gradient gx for the red, green, and blue intensities
     * of the pixel at coordinate (x, y) in "this" PixImage
     * 
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the gradient gx for the RGB intensities of the pixel (x, y)
     */
    private int[] gx(int x, int y) {
	int red = 0, green = 0, blue = 0;
	int[] coef = new int[] {1, 0, -1, 2, 0, -2, 1, 0, -1};
	int idx = -1;
	for (int i = x - 1; i <= x + 1; i++) {
	    for (int j = y - 1; j <= y + 1; j++) {
		idx++;
		red += this.getRed(i, j) * coef[idx];
		green += this.getGreen(i, j) * coef[idx];
		blue += this.getBlue(i, j) * coef[idx];
	    }
	}
	int[] grad = new int[] {red, green, blue};
	return grad;
    }

    /**
     * gy() returns the gradient gy for the red, green, and blue intensities
     * of the pixel at coordinate (x, y) in "this" PixImage
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the gradient gy for the RGB intensities of the pixel (x, y)
     */
    private int[] gy(int x, int y) {
        int red = 0, green = 0, blue = 0;
        int[] coef = new int[] {1, 2, 1, 0, 0, 0, -1, -2, -1};
        int idx = -1;
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                idx++;
                red += this.getRed(i, j) * coef[idx];
                green += this.getGreen(i, j) * coef[idx];
                blue += this.getBlue(i, j) * coef[idx];
            }
        }
        int[] grad = new int[] {red, green, blue};
        return grad;
    }

    /**
     * energy() returns the energy of the pixel at coordinate (x, y) in "this" 
     * PixImage, which is defined to be the sum of the red, green, and blue energies 
     * of the pixel at coordinate (x, y) in "this" PixImage. 
     * The energy of a gradient vector is defined to be the square of its lengths.
     *
     * @param x the x-coordinate of the pixel
     * @param y the y-coordinate of the pixel
     * @return the energy of the pixel at coordinate (x, y) in "this" PixImage
     */
    private long energy(int x, int y) {
	int[] gradx = this.gx(x, y);
	int[] grady = this.gy(x, y);
	long res = (long) gradx[0] * (long) gradx[0] + (long) grady[0] * (long) grady[0] + 
	    (long) gradx[1] * (long) gradx[1] + (long) grady[1] * (long) grady[1] + 
	    (long) gradx[2] * (long) gradx[2] + (long) grady[2] * (long) grady[2];
	return res;
    }

  /**
   * sobelEdges() applies the Sobel operator, identifying edges in "this"
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */
  public PixImage sobelEdges() {
      PixImage ref = this.reflectImage();
      PixImage res = new PixImage(width, height);
      for (int x = 1; x <= width; x++) {
	  for (int y = 1; y <= height; y++) {
	      short intensity = mag2gray(ref.energy(x, y));
	      res.setPixel(x - 1, y - 1, intensity, intensity, intensity);
	  }
      }
      return res;
  }


  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * equals() checks whether two images are the same, i.e. have the same
   * dimensions and pixels.
   *
   * @param image a PixImage to compare with "this" PixImage.
   * @return true if the specified PixImage is identical to "this" PixImage.
   */
  public boolean equals(PixImage image) {
    int width = getWidth();
    int height = getHeight();

    if (image == null ||
        width != image.getWidth() || height != image.getHeight()) {
      return false;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (! (getRed(x, y) == image.getRed(x, y) &&
               getGreen(x, y) == image.getGreen(x, y) &&
               getBlue(x, y) == image.getBlue(x, y))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * main() runs a series of tests to ensure that the convolutions (box blur
   * and Sobel) are correct.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 10, 240 },
                                                   { 30, 120, 250 },
                                                   { 80, 250, 255 } });
    System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
                       "Input image:");
    System.out.print(image1);
    doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
           "Incorrect image width and height.");
    
    System.out.println("Testing blurring on a 3x3 image.");
    doTest(image1.boxBlur(1).equals(
           array2PixImage(new int[][] { { 40, 108, 155 },
                                        { 81, 137, 187 },
                                        { 120, 164, 218 } })),
           "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
    doTest(image1.boxBlur(2).equals(
           array2PixImage(new int[][] { { 91, 118, 146 },
                                        { 108, 134, 161 },
                                        { 125, 151, 176 } })),
           "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
    doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
           "Incorrect box blur (1 rep + 1 rep):\n" +
           image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));

    System.out.println("Testing edge detection on a 3x3 image.");
    doTest(image1.sobelEdges().equals(
           array2PixImage(new int[][] { { 104, 189, 180 },
                                        { 160, 193, 157 },
                                        { 166, 178, 96 } })),
           "Incorrect Sobel:\n" + image1.sobelEdges());


    PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
                                                   { 0, 0, 100 } });
    System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
                       "Input image:");
    System.out.print(image2);
    doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 2x3 image.");
    doTest(image2.boxBlur(1).equals(
           array2PixImage(new int[][] { { 25, 50, 75 },
                                        { 25, 50, 75 } })),
           "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

    System.out.println("Testing edge detection on a 2x3 image.");
    doTest(image2.sobelEdges().equals(
           array2PixImage(new int[][] { { 122, 143, 74 },
                                        { 74, 143, 122 } })),
           "Incorrect Sobel:\n" + image2.sobelEdges());
  }
   
}
