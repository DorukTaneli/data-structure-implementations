package code;

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
 * ASSIGNMENT 2
 * AUTHORS: Baris Akgun, Buket Yuksel
 *
 * DO NOT MODIFY 
 * 
 * */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import code.Maze.Coordinate;
import given.Util;
import given.iDeque;
import given.iSimpleContainer;

/* * 
 * 
 * ASSIGNMENT 2-Part2
 * STUDENT AUTHOR:  Doruk Taneli
 *
 * MODIFY 
 * 
 * */

public class Maze {

	// Characters that define the maze
	char O = 'O'; // allowable cells
	char I = 'I'; // walls
	char S = 'S'; // start point of the Maze
	char E = 'E'; // exit cell
	char visited = '*'; // visited cells

	char[][] currentMaze;
	int rows;
	int cols;

	/*
	 * ADD FIELDS IF NEEDED
	 */

	public Maze() {
		/*
		 * 
		 * Implement a constructor if needed
		 * 
		 */
	}

	public static class Coordinate {
		int x;
		int y;

		public Coordinate(int r, int c) {
			x = r;
			y = c;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (!Coordinate.class.isAssignableFrom(obj.getClass())) {
				return false;
			}
			final Coordinate other = (Coordinate) obj;
			if (this.x == other.x && this.y == other.y)
				return true;
			else
				return false;
		}

		/*
		 * ADD CODE IF NEEDED
		 * 
		 */
	}

	/*
	 * This method loads a maze given in a file. It is given for you. It reads the
	 * given maze file and load it into a 2-D char array
	 * 
	 * Feel free to modify it if needed.
	 * 
	 */
	public void loadMaze(String fileName) throws IOException {

		// BARIS: ASK THEM TO MODIFY?

		BufferedReader br1 = new BufferedReader(new FileReader(fileName));
		String line1;

		line1 = br1.readLine();
		rows = 0;

		while (line1 != null) { // gets the size of the maze

			line1 = br1.readLine();
			rows++;
		}
		br1.close();

		BufferedReader br2 = new BufferedReader(new FileReader(fileName));
		currentMaze = new char[rows][]; // creates a char array with maze size

		String line2;
		int i = 0;

		while ((line2 = br2.readLine()) != null) // loads maze elements to 2-D char array
		{
			String newStr = line2.replaceAll(", ", "");
			currentMaze[i++] = newStr.toCharArray();
		}

		cols = currentMaze[0].length;

		br2.close();
	}

	// Prints the maze if you want to debug
	public String toString() {
		int i = 0, j = 0;
		StringBuilder sb = new StringBuilder(1000);
		sb.append("Maze: " + rows + " x " + cols);

		for (; i < rows; i++) {
			for (; j < cols - 1; j++) {
				sb.append(currentMaze[i][j] + ", ");
			}
			sb.append(currentMaze[i][j] + System.lineSeparator());
		}

		return sb.toString();
	}

	/*
	 * ADD METHODS IF NEEDED
	 */

	/*
	 * Implement the algorithm given in the document in pseudocode form to solve the
	 * maze: i.e. find a path from the start coordinate to the end coordinate.
	 * 
	 * The algorithm takes in a container which changes its behavior, a deque to be
	 * filled from the back (we could have used something from Java but going with
	 * custom made DSes)
	 * 
	 * You are required to fill in the input iDeque with the visited nodes in the
	 * given order in a first-in first-out manner.
	 * 
	 * The neighbors of a coordinate follow the 4-neighborhood rule, i.e., they can
	 * be UP, DOWN, LEFT, RIGHT. Although it does not matter which order you push
	 * the neighbors to the container, we are going to impose an order for the
	 * autograder: 1) Decrease the y coordinate 2) Increase the x coordinate 3)
	 * Increase the y coordinate 4) Decrease the x coordinate
	 * 
	 * It returns true is an exit is fond, false otherwise.
	 * 
	 */
	public boolean solveMaze(iSimpleContainer<Coordinate> sc, iDeque<Coordinate> visitedNodes, String mazeName)
			throws IOException {
		loadMaze(mazeName);

		sc.push(getStartState(currentMaze));
		while(!sc.isEmpty()){
			Coordinate currentState = sc.pop();
			if(isExit(currentState.x, currentState.y)){
				return true;
			}else if(!isVisited(currentState)){
				markVisited(currentState, visited);
				visitedNodes.addBehind(currentState);
				Coordinate upper = new Coordinate(currentState.x,currentState.y-1);
				Coordinate right = new Coordinate(currentState.x+1,currentState.y);
				Coordinate bottom = new Coordinate(currentState.x,currentState.y+1);
				Coordinate left = new Coordinate(currentState.x-1,currentState.y);
				if(isInMaze(upper))
					sc.push(upper);
				if(isInMaze(right))
					sc.push(right);
				if(isInMaze(bottom))
					sc.push(bottom);
				if(isInMaze(left))
					sc.push(left);

			}
		}

		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * 
	 * The below functions are given to you as suggestions. You can use them in the
	 * solveMaze function. However, we will not check these explicitly
	 * 
	 */

	// Helper method which marks a coordinate as visited
	private void markVisited(Coordinate c, char val) {
		currentMaze[c.x][c.y] = val;
	}

	// Helper method which checks if the coordinate has been visited before
	private boolean isVisited(Coordinate c) {
		return currentMaze[c.x][c.y] == visited;

	}

	// Helper method which checks if the coordinate is within the maze or not
	private boolean isInMaze(Coordinate c) {
		return	c.x<currentMaze[0].length &&
				c.x>=0 &&
				c.y<currentMaze.length &&
				c.y>=0 &&
				(currentMaze[c.x][c.y] == O || currentMaze[c.x][c.y] == E);
	}

	// Helper method which checks if the coordinate is an exit or not
	private boolean isExit(int row, int col) {
		return currentMaze[row][col]==E;
	}

	// Returns the start state from the maze
	private Coordinate getStartState(char[][] maze) {
		Coordinate startState = null;
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if(maze[i][j] == S)
					startState = new Coordinate(i,j);
			}
		}
		return startState;
	}
};
