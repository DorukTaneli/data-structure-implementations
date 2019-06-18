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
 * AUTHORS: Baris Akgun
 *
 * DO NOT MODIFY 
 * 
 * */

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import autograder.Autograder;
import code.ImageSegmenter;
import given.Image.PixelCoordinate;

public class GradeImagePart {
  
  static String imageFolder = "images";
  
  public static HashMap<Integer,HashSet<PixelCoordinate>> getSegmentsFromSegmentedImage(Image segmentedImage) {
    HashMap<Integer,HashSet<PixelCoordinate>> segments = new  HashMap<Integer,HashSet<PixelCoordinate>>();
    for (int r = 0; r < segmentedImage.getHeight(); ++r) {
      for (int c = 0; c < segmentedImage.getWidth(); ++c) {
        // Get all possible neighbors of pixel at row r and column c for fun
        Image.PixelCoordinate pc = new Image.PixelCoordinate(r, c);
        int color = segmentedImage.getColor(r, c);
        HashSet<PixelCoordinate> hs = segments.get(color);
        if(hs == null) {
          segments.put(color, new HashSet<PixelCoordinate>());
          hs = segments.get(color);
        }
        hs.add(pc);
      }
    }
    return segments;
  }
  
  // This is a terrible way to compare. It works for this homework but do not use it elsewhere
  public static boolean compareMaps( HashMap<Integer,HashSet<PixelCoordinate>> hm1,  HashMap<Integer,HashSet<PixelCoordinate>> hm2) {
    if(hm1.size() != hm2.size()) {
      System.out.println(hm1.size() + " " + hm2.size());
      return false;
    }
    for(HashSet<PixelCoordinate> hs1 : hm1.values()) {
      for(HashSet<PixelCoordinate> hs2 : hm2.values()) {
        if(hs1.size() == hs2.size())
          if(!hs1.equals(hs2))
            return false;
      }
    }
    
    return true;
  }

  /**
   * This is main function. It loads images calls segmentImage function. Then it
   * saves segmented image output.
   * 
   * @param args
   * @throws IOException
   */
  public static void main(String args[]) throws IOException {
    boolean iDidIt = false;
    if (!Autograder.initializedOnce) {
      Autograder.init();
      iDidIt = true;
    }
    Autograder.Log("Starting implicit graph (image segmentation) testing");
    ImageSegmenter segmenter = new ImageSegmenter();
    
    // Image file names
    String[] imageFiles = {"twitter.png", "Simple.png" };
    // Epsilon threshold values
    double[] epsilons = {5e-2, 1e-2 };
    // double[] epsilons = { 1e0, 1e0, 1e0, 1e0};

    int i = 0;
    // Iterate over images and color connected components on output image.
    for (String filename : imageFiles) {
      Image input = new Image(imageFolder + "/input/" + filename);
      Image output = segmenter.segmentImage(input, epsilons[i]);
      output.saveImage(imageFolder + "/output/" + filename);
      
      Image segmented = new Image(imageFolder + "/output/" + filename);
      Image reference = new Image(imageFolder + "/reference/" + filename);
      
      HashMap<Integer,HashSet<PixelCoordinate>> hm1 = getSegmentsFromSegmentedImage(segmented);
      HashMap<Integer,HashSet<PixelCoordinate>> hm2 = getSegmentsFromSegmentedImage(reference);
      
      if(compareMaps(hm1,hm2))
        Autograder.addGrade(8);
      else
        Autograder.Log("Segmentation for the image " + filename + " does not match the reference");
      ++i;
    }
    Autograder.Log("Finished implicit graph (image segmentation) testing");
    if (iDidIt) {
      Autograder.printLog();
      Autograder.printGrade(16);
    }
  }

}
