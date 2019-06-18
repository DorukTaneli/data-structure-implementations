package given;

/*
 * Copyright 2018 Baris Akgun
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted 
 * provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of 
 * conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of 
 * conditions and the following disclaimer in the documentation and/or other materials provided 
 * with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to 
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR 
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL 
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, 
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT 
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Most importantly, this software is provided for educational purposes and should not be used for
 * anything else.
 * 
 * AUTHORS: Baris Akgun, Kurmanbek Kaiyrbekov
 *
 * DO NOT MODIFY 
 * 
 * */

import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Image {
  private BufferedImage image = null;
  private int height;
  private int width;
  private double[][] normPixels;

  public static class PixelCoordinate {
    public int r; // row value
    public int c; // column value

    public PixelCoordinate(int r, int c) {
      this.r = r;
      this.c = c;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      
      if (this.getClass() != obj.getClass())
        return false;
      PixelCoordinate tmp = (PixelCoordinate) obj;
      if (this.r == tmp.r && this.c == tmp.c)
        return true;
      else
        return false;
    }

    @Override
    public int hashCode() {
      return r + 1001 * c;
    }
    
    @Override
    public String toString() {
      return "(" + r + ", " + c + ")";
    }
  }

  public Image(String filename) {
    image = initImage(filename);
    height = image.getHeight();
    width = image.getWidth();
    normPixels = new double[height][width];
    normalizePix(normPixels);
  }

  public Image(int height, int width) {
    image = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
  }

  private void normalizePix(double[][] normPixels) {
    double oldMax = Integer.MIN_VALUE;
    double oldMin = Integer.MAX_VALUE;
    for (int r = 0; r < height; ++r) {
      for (int c = 0; c < width; ++c) {
        oldMax = Math.max(oldMax, image.getRGB(r, c));
        oldMin = Math.min(oldMin, image.getRGB(r, c));
      }
    }
    double oldRange = oldMax - oldMin;
    double newMax = 1, newMin = 0;
    double newRange = newMax - newMin;
    for (int r = 0; r < height; ++r) {
      for (int c = 0; c < width; ++c) {
        normPixels[r][c] = ((image.getRGB(r, c) - oldMin) * newRange / oldRange) + newMin;
      }
    }
  }

  private BufferedImage initImage(String filename) {
    BufferedImage image = null;
    File f = null;
    try {
      f = new File(filename);
      image = ImageIO.read(f);

    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
    return image;// filtered;
  }

  /**
   * Saves the image.
   * 
   * @param filename
   */
  public void saveImage(String filename) {
    String[] split = filename.split("\\.");
    try {
      File f = new File(filename);
      ImageIO.write(image, split[split.length - 1], f);
      //System.out.println("Output image is saved as " + filename);
    } catch (IOException e) {
      System.out.println("Error: " + e);
    }
  }

  /**
   * Returns number of rows in image.
   * 
   * @return height
   */
  public int getHeight() {
    return height;
  }

  /**
   * This method return number of columns in image.
   * 
   * @return width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Returns pixel value at row r and column c.
   * 
   * @param r
   *          - row number
   * @param c
   *          - column number
   * @return
   */
  public double getPixelVal(int r, int c) {
    return normPixels[r][c];
  }

  /**
   * Returns pixel value PixelCoordinate pc.
   * 
   * @param pc
   *          - PixelCoordinate
   * @return
   */
  public double getPixelVal(PixelCoordinate pc) {
    return getPixelVal(pc.r, pc.c);
  }

  /**
   * Sets color of the image pixel at row r and column c to curCol. (Equivalent to
   * setVisited)
   * 
   * @param r
   * @param c
   * @param curCol
   */
  public void setColor(int r, int c, Color curCol) {
    image.setRGB(r, c, curCol.getRGB());
  }

  /**
   * Sets color of the image pixel at PixelCoordinate pc to curCol. (Equivalent to
   * setVisited)
   * 
   * @param pc
   * @param curCol
   */
  public void setColor(Image.PixelCoordinate pc, Color curCol) {
    setColor(pc.r, pc.c, curCol);
  }
  
  public int getColor(int r, int c) {
    return image.getRGB(r, c);
  }
}
