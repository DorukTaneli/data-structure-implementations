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

import java.util.HashMap;
import java.util.List;

import autograder.Autograder;
import code.*;

/*
 * NOTE: This testing is not comprehensive at all.
 * We will probably use other graphs to test your algorithms so do not trust this by itself!
 */

public class AlgTesting {
  
  public static <T> boolean compareListAndArray(List<T> l, T objs[]) {
    if(l.size() != objs.length)
      return false;
    
    for(int i = 0; i < objs.length; i++) {
      if(objs[i] != l.get(i))
        return false;
    }
    return true;
  }
  
  public static void testDFS(GraphAlgorithms<Character> algs, BaseGraph<Character> G) {
    G.insertVertex('A');
    G.insertVertex('B');
    G.insertVertex('C');
    G.insertVertex('D');
    G.insertVertex('E');
    G.insertVertex('F');
    G.insertVertex('G');
    G.insertVertex('H');
    
    G.insertEdge('A', 'B', 6.9f);
    G.insertEdge('A', 'F', 4.8f);
    G.insertEdge('A', 'H', 1.0f);
    G.insertEdge('B', 'F', 8.4f);
    G.insertEdge('B', 'E', 3.6f);
    G.insertEdge('B', 'G', 2.4f);
    G.insertEdge('C', 'G', 9.7f); 
    G.insertEdge('C', 'F', 0.2f);
    G.insertEdge('D', 'G', 5.8f);
    G.insertEdge('E', 'F', 5.0f);
    
    List<Character> dfsSequence =  algs.DFS(G, 'A');
    Character[] desiredRecursiveSequence = {'A','B','E','F','G','H'};
    Character[] desiredIterativeSequence = {'A','H','F','B','G','E'};
    
    if(compareListAndArray(dfsSequence, desiredRecursiveSequence) || compareListAndArray(dfsSequence, desiredIterativeSequence)) {
      Autograder.addGrade(5f);
    }
    else {
      Autograder.Log("DFS failed for " + G);
    }
  }
  
  public static void testBFS(GraphAlgorithms<Character> algs, BaseGraph<Character> G) {
    G.insertVertex('A');
    G.insertVertex('B');
    G.insertVertex('C');
    G.insertVertex('D');
    G.insertVertex('E');
    G.insertVertex('F');
    G.insertVertex('G');
    G.insertVertex('H');
    
    G.insertEdge('A', 'B', 6.9f);
    G.insertEdge('A', 'F', 4.8f);
    G.insertEdge('A', 'H', 1.0f);
    G.insertEdge('B', 'F', 8.4f);
    G.insertEdge('B', 'E', 3.6f);
    G.insertEdge('B', 'G', 2.4f);
    G.insertEdge('C', 'G', 9.7f); 
    G.insertEdge('C', 'F', 0.2f);
    G.insertEdge('D', 'G', 5.8f);
    G.insertEdge('E', 'F', 5.0f);
    
    List<Character> dfsSequence =  algs.BFS(G, 'A');
    Character[] desiredSequence = {'A','B','F','H','E','G','C','D'};
    
    if(compareListAndArray(dfsSequence, desiredSequence)) {
      Autograder.addGrade(5f);
    }
    else {
      Autograder.Log("BFS failed for " + G);
    }
  }
  
  public static void testDijk(GraphAlgorithms<Character> algs, BaseGraph<Character> G) {
    G.insertVertex('A');
    G.insertVertex('B');
    G.insertVertex('C');
    G.insertVertex('D');
    G.insertVertex('E');
    G.insertVertex('F');
    G.insertVertex('G');
    G.insertVertex('H');
    
    G.insertEdge('A', 'B', 6.9f);
    G.insertEdge('A', 'F', 4.8f);
    G.insertEdge('A', 'H', 1.0f);
    G.insertEdge('B', 'F', 8.4f);
    G.insertEdge('B', 'E', 3.6f);
    G.insertEdge('B', 'G', 2.4f);
    G.insertEdge('C', 'G', 9.7f); 
    G.insertEdge('C', 'F', 0.2f);
    G.insertEdge('D', 'G', 5.8f);
    G.insertEdge('E', 'F', 5.0f);
    
    G.insertEdge('A', 'E', 10.6f);
    if(G.isDirected())
      G.insertEdge('F', 'H', 0.1f);
    
    HashMap<Character,Float> costs = algs.Dijkstras(G, 'A');
    HashMap<Character,Float> desiredCosts = new HashMap<Character,Float>();
    if(G.isDirected()) {
      desiredCosts.put('A', 0f);
      desiredCosts.put('B', 6.9f);
      desiredCosts.put('E', 10.5f);
      desiredCosts.put('F', 4.8f);
      desiredCosts.put('G', 9.3f);
      desiredCosts.put('H', 1.0f);
    }
    else {
      desiredCosts.put('A', 0f);
      desiredCosts.put('B', 6.9f);
      desiredCosts.put('C', 5.0f);
      desiredCosts.put('D', 15.1f);
      desiredCosts.put('E', 9.8f);
      desiredCosts.put('F', 4.8f);
      desiredCosts.put('G', 9.3f);
      desiredCosts.put('H', 1.0f);
    }
    
    if(costs.equals(desiredCosts)) {
      Autograder.addGrade(8f);
    }
    else {
      for(Character c : costs.keySet())
        System.out.println(c + " " + costs.get(c) );
      Autograder.Log("Dijkstra failed for " + G);
    }
  }
  
  public static void testCyclic(GraphAlgorithms<Character> algs, BaseGraph<Character> G) {
    G.insertVertex('A');
    G.insertVertex('B');
    G.insertVertex('C');
    G.insertVertex('D');
    
    G.insertVertex('X');
    G.insertVertex('Y');
    G.insertVertex('Z');
    
    if(!algs.isCyclic(G))
      Autograder.addGrade(0.5f);
    else
      Autograder.Log("Graph with no vertices cannot be cyclic (AlgTesting.java)");
      
    G.insertEdge('A', 'B');
    G.insertEdge('A', 'C');
    
    if(!algs.isCyclic(G))
      Autograder.addGrade(0.5f);
    else {
      Autograder.Log("A tree structure is not cyclic (AlgTesting.java)");
      if(!G.isDirected())
        Autograder.Log("Graph is undirected, you maybe re-checking the immediate parent");
    }
    
    G.insertEdge('B', 'D');
    G.insertEdge('C', 'B');
    
    boolean  tmpB =algs.isCyclic(G);
    
    if(G.isDirected()) {
      if(!tmpB)
        Autograder.addGrade(1f);
      else 
        Autograder.Log("This directed graph is not cyclic (AlgTesting.java)");
    } else {
      if(tmpB)
        Autograder.addGrade(2.0f);
      else 
        Autograder.Log("This undirected graph is cyclic (AlgTesting.java)");
    }
    
    G.removeEdge('A', 'B');
    tmpB =algs.isCyclic(G);
    if(!tmpB)
      Autograder.addGrade(0.5f);
    else 
      Autograder.Log("This graph is not cyclic (AlgTesting.java)");
    
    G.insertEdge('X','Y');
    G.insertEdge('Y','Z');
    G.insertEdge('Z','X');
    
    tmpB =algs.isCyclic(G);
    if(tmpB)
      Autograder.addGrade(2.5f);
    else 
      Autograder.Log("This graph is cyclic (AlgTesting.java)");
  }
  
  public static void main(String args[]) {
    boolean iDidIt = false;
    if (!Autograder.initializedOnce) {
      Autograder.init();
      iDidIt = true;
    }
    
    Autograder.Log("Starting algorithm testing");
    
    GraphAlgorithms<Character> algs = new GraphAlgorithms<Character>();
    
    //Test DFS with directed unweighted
    DirectedUnweightedGraph<Character> duG = new DirectedUnweightedGraph<Character>();
    testDFS(algs, duG);
    
    //Test BFS with undirected unweighted
    UndirectedUnweightedGraph<Character> uuG = new UndirectedUnweightedGraph<Character>();
    testBFS(algs, uuG);
    
    //Test Dijktra with both weighted
    UndirectedWeightedGraph<Character> uwG = new UndirectedWeightedGraph<Character>();
    testDijk(algs, uwG);
    DirectedWeightedGraph<Character> dwG = new DirectedWeightedGraph<Character>();
    testDijk(algs, dwG);
    
    //Test cyclic 
    duG = new DirectedUnweightedGraph<Character>();
    testCyclic(algs, duG);
    uuG = new UndirectedUnweightedGraph<Character>();
    testCyclic(algs, uuG);
    
    Autograder.Log("Finished algorithm testing");
    
    if (iDidIt) {
      Autograder.printLog();
      Autograder.printGrade(37);
    }
  }

}
