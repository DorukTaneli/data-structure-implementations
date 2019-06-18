package code;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import given.Image.PixelCoordinate;
import given.Image;

public class ImageSegmenter {

	// Colors to use while coloring
	private static Color[] colors = { Color.BLACK, Color.BLUE, Color.GREEN, Color.GRAY, Color.MAGENTA, Color.RED,
			Color.CYAN, Color.LIGHT_GRAY, Color.ORANGE, Color.PINK, Color.YELLOW, Color.DARK_GRAY };

	/*
	 * 
	 * YOU CAN ADD MORE FIELDS HERE
	 * 
	 */

	/**
	 * Segment image by finding connected components. Pixels in same component must
	 * have the same color. Coloring should be done on a new image which should be
	 * returned.
	 * 
	 * You can use any graph traversal method you like.
	 * 
	 * @param epsilon
	 *          - threshold value to decide connectedness of two neighboring pixels.
	 */
	public Image segmentImage(Image input, double epsilon) {
		Image output = new Image(input.getHeight(), input.getWidth());

		// TODO: IMPLEMENT THIS
		int colorIndx = 0;
		int numColors = colors.length;
		Color nextColorToUse = colors[colorIndx % numColors];
		ArrayList<PixelCoordinate> visited = new ArrayList<PixelCoordinate>();
		for (int r = 0; r < input.getHeight(); ++r) {
			for (int c = 0; c < input.getWidth(); ++c) {
				PixelCoordinate pc = new PixelCoordinate(r, c);
				if (!visited.contains(pc)) {
					nextColorToUse = colors[colorIndx % numColors];
					pixelDFS(input, output, pc, nextColorToUse, epsilon, visited);
					colorIndx++;
				}
			}
		}
		return output;
	}

	private boolean isInImage(PixelCoordinate pc, Image img) {
		return 	pc.r < img.getHeight() &&
				pc.r >= 0 &&
				pc.c < img.getWidth() &&
				pc.c >= 0;
	}
	
	private List<PixelCoordinate> pixelDFS(Image input, Image output, PixelCoordinate startVertex, Color nextColorToUse,
											double epsilon, ArrayList<PixelCoordinate> visited) {
		List<PixelCoordinate> A = new ArrayList<PixelCoordinate>();
		Stack<PixelCoordinate> frontier = new Stack<PixelCoordinate>();
		frontier.push(startVertex);
		while (!frontier.isEmpty()) {
			PixelCoordinate u = frontier.pop();
			if (!A.contains(u)) {
				A.add(u);
				visited.add(u);
				output.setColor(u, nextColorToUse);
				for(PixelCoordinate w : connectedNeighbors(input, u, epsilon)) {
					if (!A.contains(w))
						frontier.push(w);
				}
			}
		}
		return A;
	}
	
	private Iterable<PixelCoordinate> connectedNeighbors(Image img, PixelCoordinate pc, double epsilon) {
		ArrayList<PixelCoordinate> A = new ArrayList<PixelCoordinate>();
		PixelCoordinate upper = new PixelCoordinate(pc.r-1, pc.c);
		PixelCoordinate below = new PixelCoordinate(pc.r+1, pc.c);
		PixelCoordinate right = new PixelCoordinate(pc.r, pc.c+1);
		PixelCoordinate left = new PixelCoordinate(pc.r, pc.c-1);
		if (isInImage(upper, img) && Math.abs(img.getPixelVal(pc) - img.getPixelVal(upper)) < epsilon)
			A.add(upper);
		if (isInImage(below, img) && Math.abs(img.getPixelVal(pc) - img.getPixelVal(below)) < epsilon)
			A.add(below);
		if (isInImage(right, img) && Math.abs(img.getPixelVal(pc) - img.getPixelVal(right)) < epsilon)
			A.add(right);
		if (isInImage(left, img) && Math.abs(img.getPixelVal(pc) - img.getPixelVal(left)) < epsilon)
			A.add(left);
		return A;
	}


	//	private boolean allcolored(Image img) {
	//		for (int r = 0; r < img.getHeight(); ++r) {
	//			for (int c = 0; c < img.getWidth(); ++c) {
	//				if (img.getColor(r, c) != ())
	//			}
	//		}
	//	}


	/**
	 * This function iterates over the image and colors output image using "color"
	 * array in circular fashion. i.e. if all colors have been used then pick the
	 * first color.
	 * 
	 * This is given to you as an example of how to use some of the available
	 * classes,
	 * 
	 * @param graph
	 *          - image graph.
	 * @param output
	 *          - output segmented image.
	 */
	public Image dummyIteration(Image input) {
		int colorIndx = 0;
		int numColors = colors.length;
		Image output = new Image(input.getHeight(), input.getWidth());
		for (int r = 0; r < input.getHeight(); ++r) {
			for (int c = 0; c < input.getWidth(); ++c) {
				// Get all possible neighbors of pixel at row r and column c for fun
				PixelCoordinate pc = new PixelCoordinate(r, c);


				//HINT: You need to iterate over all the some of neighbors of the current pixel here!


				// Get the next color in circular fashion
				Color nextColorToUse = colors[colorIndx % numColors];
				// Set the color of pixel at PixelCoordinate pc of segmentedImage to
				// nextColorToUse
				output.setColor(pc, nextColorToUse);
				// Increment color index
				++colorIndx;
			}
		}
		return output;
	}


	
	
	
	
	
	
	//	//insert vertices
	//	for (int r = 0; r < input.getHeight(); ++r) {
	//		for (int c = 0; c < input.getWidth(); ++c) {
	//			PixelCoordinate cp = new PixelCoordinate(r, c);
	//			graph.insertVertex(cp);
	//		}
	//	}
	//	
	//	//insert edges
	//	for (int r = 0; r < input.getHeight(); ++r) {
	//		for (int c = 0; c < input.getWidth(); ++c) {
	//			PixelCoordinate cp = new PixelCoordinate(r, c);
	//			if (r != 0) {
	//				PixelCoordinate upP = new PixelCoordinate(r - 1, c);
	//				if ((input.getPixelVal(cp) - input.getPixelVal(upP) < epsilon) 
	//						&& !graph.areAdjacent(cp, upP)) {
	//					graph.insertEdge(cp, upP);
	//				}
	//			}
	//			if (r != input.getHeight() - 1) {
	//				PixelCoordinate downP = new PixelCoordinate(r + 1, c);
	//				if ((input.getPixelVal(cp) - input.getPixelVal(downP) < epsilon) 
	//						&& !graph.areAdjacent(cp, downP)) {
	//					graph.insertEdge(cp, downP);
	//				}
	//			}
	//			if (c != 0) {
	//				PixelCoordinate leftP = new PixelCoordinate(r, c - 1);
	//				if ((input.getPixelVal(cp) - input.getPixelVal(leftP) < epsilon) 
	//						&& !graph.areAdjacent(cp, leftP)) {
	//					graph.insertEdge(cp, leftP);
	//				}
	//			}
	//			if (c != input.getWidth() - 1) {
	//				PixelCoordinate rightP = new PixelCoordinate(r + 1, c);
	//				if ((input.getPixelVal(cp) - input.getPixelVal(rightP) < epsilon) 
	//						&& !graph.areAdjacent(cp, rightP)) {
	//					graph.insertEdge(cp, rightP);
	//				}
	//			}
	//		}
	//	}
	
//	Color nextColorToUse = colors[colorIndx % numColors];
//	for (int r = 0; r < input.getHeight(); ++r) {
//		for (int c = 0; c < input.getWidth(); ++c) {
//			PixelCoordinate pc = new PixelCoordinate(r, c);
//			if (!visited.contains(pc)) {
//				List<PixelCoordinate> toColor = pixelDFS(graph, pc);
//				for (PixelCoordinate pc1 : toColor) {
//					visited.add(pc1);
//					output.setColor(pc, nextColorToUse);
//				}
//			}
//		}
//	}

	
//	q.addLast(new PixelCoordinate(0,0));
//	while (!q.isEmpty()) {
//		PixelCoordinate current = q.pop();
//		if (current.c == input.getWidth()-1 && current.r == input.getHeight()-1) {
//			return output;
//		} else if (!visited.contains(current)) {
//			visited.add(current);
//			PixelCoordinate upper = new PixelCoordinate(current.r-1, current.c);
//			PixelCoordinate below = new PixelCoordinate(current.r+1, current.c);
//			PixelCoordinate right = new PixelCoordinate(current.r, current.c+1);
//			PixelCoordinate left = new PixelCoordinate(current.r, current.c-1);
//			if (isInImage(below, input))
//				q.addLast(below);
//			if (isInImage(right, input))
//				q.addLast(right);
//			if (isInImage(upper, input) && isInImage(left, input) &&
//					input.getPixelVal(current) - input.getPixelVal(upper) > epsilon &&
//					input.getPixelVal(current) - input.getPixelVal(left) > epsilon)
//				colorIndx++;
//			Color nextColorToUse = colors[colorIndx % numColors];
//			output.setColor(current, nextColorToUse);	
//		}
//	}
	
}
