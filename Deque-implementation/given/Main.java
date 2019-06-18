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
 * AUTHORS: Buket Yuksel, Baris Akgun
 *
 * DO NOT MODIFY 
 * 
 * */

import code.*;

public class Main {

  // Main method to test if methods implementations are correct
  
  
  private static void testDeque(iDeque<Integer> deque, String note) {
    System.out.println(note);
    
    deque.addFront(2);
    deque.addFront(12);
    deque.addFront(17);
    deque.addBehind(9);
    deque.addFront(4);
    deque.addBehind(6);
    deque.removeFront();
    deque.removeBehind();

    System.out.println("Deque items are: " + deque);
    System.out.println("Is deque empty?: " + deque.isEmpty());
    System.out.println("The element at the front of deque is: " + deque.front());
    System.out.println("The element at the behind of deque is: " + deque.behind());
    System.out.println();
  }
  
  private static void testContainer(iSimpleContainer<Integer> sc, String note) {
    System.out.println(note);

    sc.push(5);
    sc.push(7);
    sc.push(8);
    sc.push(11);
    sc.push(14);
    sc.pop();

    System.out.println("Items are: " + sc);
    System.out.println("The next item is " + sc.peek() + "\n");
  }
  
  public static void main(String[] args) throws RuntimeException, Throwable {

    System.out.println("Asignment Part I: Implementing the data structures");
    iDeque<Integer> arrayDeque = new ArrayDeque<Integer>();
    iDeque<Integer> llDeque = new LLDeque<Integer>();
    iSimpleContainer<Integer> stackAD = new Stack<ArrayDeque<Integer>, Integer>(new ArrayDeque<Integer>());
    iSimpleContainer<Integer> stackLL = new Stack<LLDeque<Integer>, Integer>( new LLDeque<Integer>());
    iSimpleContainer<Integer> queueAD = new Queue<ArrayDeque<Integer>, Integer>( new ArrayDeque<Integer>());
    iSimpleContainer<Integer> queueLL = new Queue<LLDeque<Integer>, Integer>( new LLDeque<Integer>());

    testDeque(arrayDeque, "Testing ArrayDeque");
    testDeque(llDeque, "Testing Linked list deque");
    
    testContainer(stackAD, "Stack ArrayDeque");
    testContainer(stackLL, "Stack Linked list deque");
    testContainer(queueAD, "Queue ArrayDeque");
    testContainer(queueLL, "Queue Linked list deque");
    
    //completely BS
    System.out.println("Asignment Part II: Solving a maze with the implemented data structures");
    Maze maze = new Maze();
    
    ArrayDeque<Maze.Coordinate> visitedDeque = new ArrayDeque<Maze.Coordinate>();
    
    System.out.println("Maze Test 1");
    iSimpleContainer<Maze.Coordinate> stackAD2 = new Stack<ArrayDeque<Maze.Coordinate>, Maze.Coordinate>(new ArrayDeque<Maze.Coordinate>());
    maze.solveMaze(stackAD2,visitedDeque,"maze1.txt");
    System.out.println(visitedDeque);
    
    System.out.println("Maze Test 2");
    visitedDeque.clear();
    iSimpleContainer<Maze.Coordinate> queueLL2 = new Queue<LLDeque<Maze.Coordinate>, Maze.Coordinate>( new LLDeque<Maze.Coordinate>());
    maze.solveMaze(queueLL2,visitedDeque,"maze1.txt");
    System.out.println(visitedDeque);
    
    System.out.println("Maze Test 3");
    visitedDeque.clear();
    iSimpleContainer<Maze.Coordinate> stackLL2 = new Stack<LLDeque<Maze.Coordinate>, Maze.Coordinate>( new LLDeque<Maze.Coordinate>());
    maze.solveMaze(stackLL2,visitedDeque,"maze2.txt");
    System.out.println(visitedDeque);
    
    System.out.println("Maze Test 4");
    visitedDeque.clear();
    iSimpleContainer<Maze.Coordinate> queueAD2 = new Queue<ArrayDeque<Maze.Coordinate>, Maze.Coordinate>( new ArrayDeque<Maze.Coordinate>());
    maze.solveMaze(queueAD2,visitedDeque,"maze2.txt");
    System.out.println(visitedDeque);
    
    
    System.out.println("Maze Test 5a");
    visitedDeque.clear();
    stackAD2.clear();
    System.out.println(maze.solveMaze(stackAD2,visitedDeque,"maze3.txt"));
    System.out.println(visitedDeque);
    
    System.out.println("Maze Test 5b");
    visitedDeque.clear();
    queueLL2.clear();
    System.out.println(maze.solveMaze(queueLL2,visitedDeque,"maze3.txt"));
    System.out.println(visitedDeque);
    
    System.out.println("Maze Test 6a");
    visitedDeque.clear();
    stackAD2.clear();
    System.out.println(maze.solveMaze(stackAD2,visitedDeque,"maze4.txt"));
    System.out.println(visitedDeque);
    
    System.out.println("Maze Test 6b");
    visitedDeque.clear();
    queueAD2.clear();
    System.out.println(maze.solveMaze(queueAD2,visitedDeque,"maze4.txt"));
    System.out.println(visitedDeque);  
  }

}
