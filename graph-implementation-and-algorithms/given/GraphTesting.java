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

import java.util.Collection;
import java.util.HashSet;

import autograder.Autograder;
import code.*;

public class GraphTesting {

  public static <T> void addToCollection(Collection<T> c, T... objs) {
    for (T obj : objs) {
      c.add(obj);
    }
  }

  public static <T> void iterableToCollection(Collection<T> c, Iterable<T> i) {
    for (T obj : i) {
      c.add(obj);
    }
  }

  public static <T> boolean isIterableEqualTo(Iterable<T> i, T... objs) {
    HashSet<T> hs1 = new HashSet<T>();
    HashSet<T> hs2 = new HashSet<T>();
    addToCollection(hs1, objs);
    iterableToCollection(hs2, i);
    if (hs1.equals(hs2))
      return true;
    return false;
  }

  public static <T> boolean isIterableEqualTo(Iterable<T> i1, Iterable<T> i2) {
    HashSet<T> hs1 = new HashSet<T>();
    HashSet<T> hs2 = new HashSet<T>();
    iterableToCollection(hs1, i1);
    iterableToCollection(hs2, i2);
    if (hs1.equals(hs2))
      return true;
    return false;
  }

  public static <T> boolean doesIterableHave(Iterable<T> i, T... objs) {
    HashSet<T> hs1 = new HashSet<T>();
    HashSet<T> hs2 = new HashSet<T>();
    addToCollection(hs1, objs);
    iterableToCollection(hs2, i);
    int count = 0;
    for (T itItem : hs2) {
      for (T obj : hs1) {
        if (itItem.equals(obj)) {
          count++;
        }
      }
    }
    return count == objs.length;
  }

  public static void testGenericGraph(BaseGraph<Character> G) {
    try {

      Autograder.Log("Starting graph test for " + G.toString());
      boolean directed = G.isDirected();
      boolean weighted = G.isWeighted();

      Character[] initList = { 'A', 'B', 'C' };
      int vertexNumCheckCount = 0;
      int edgeNumCheckCount = 0;
      // Regular insert

      for (Character c : initList) {
        G.insertVertex(c);
      }

      if (isIterableEqualTo(G.vertices(), initList))
        Autograder.addGrade(1f);
      else
        Autograder.Log("Simple insertVertex call is not working correctly (GraphTesting.java at testGenericGraph)");

      if (G.numVertices() == initList.length)
        vertexNumCheckCount++;
      else
        Autograder.Log("numVertices issue 1 (GraphTesting.java at testGenericGraph)");

      G.insertEdge('A', 'B');
      G.insertEdge('A', 'C');
      G.insertEdge('B', 'D');
      
      if (G.numEdges() == 3)
        edgeNumCheckCount++;
      else
        Autograder.Log("numEdges issue 1 (GraphTesting.java at testGenericGraph)");

      try {
        for (Character c : initList) {
          G.insertVertex(c);
        }
        if (G.numEdges() != 3)
          Autograder
              .Log("Identical insertVertex calls are overwriting vertices (GraphTesting.java at testGenericGraph)");
        else
          Autograder.addGrade(1);
      } catch (Exception e) {
        e.printStackTrace();
        Autograder.Log("Identical insertVertex call is not working correctly (GraphTesting.java at testGenericGraph)");
      }

      if (G.numVertices() == initList.length + 1)
        vertexNumCheckCount++;
      else
        Autograder.Log("numVertices issue 2 (GraphTesting.java at testGenericGraph)");

      boolean tmpB = doesIterableHave(G.vertices(), 'D');
      if (tmpB)
        Autograder.addGrade(0.25f);
      else
        Autograder.Log("insertEdge is not adding a new vertex (GraphTesting.java at testGenericGraph)");

      G.insertEdge('C', 'D');

      tmpB = G.areAdjacent('A', 'B') && G.areAdjacent('A', 'C') && G.areAdjacent('B', 'D') && G.areAdjacent('C', 'D');
      if (tmpB)
        Autograder.addGrade(0.5f);
      else
        Autograder.Log(
            "areAdjacent checks not correct for adjacent vertices. Either edges are not added or areAdjacent is not working correctly (GraphTesting.java at testGenericGraph)");
      if (!directed)
        tmpB = tmpB && G.areAdjacent('B', 'A') && G.areAdjacent('C', 'A') && G.areAdjacent('D', 'B')
            && G.areAdjacent('D', 'C');
      if (tmpB)
        Autograder.addGrade(0.25f);
      else
        Autograder.Log(
            "areAdjacent checks not correct for adjacent vertices for an undirected graph. Either edges are not added or areAdjacent is not working correctly (GraphTesting.java at testGenericGraph)");
      if (!G.areAdjacent('A', 'D'))
        Autograder.addGrade(0.25f);
      else
        Autograder.Log(
            "areAdjacent check is not correct for non-adjacent vertices.  (GraphTesting.java at testGenericGraph)");
      if (!G.areAdjacent('A', 'E'))
        Autograder.addGrade(0.25f);
      else
        Autograder.Log(
            "areAdjacent check is not correct with a single non-existing vertex.  (GraphTesting.java at testGenericGraph)");
      if (!G.areAdjacent('F', 'E'))
        Autograder.addGrade(0.25f);
      else
        Autograder.Log(
            "areAdjacent check is not correct with two non-existing vertices.  (GraphTesting.java at testGenericGraph)");

      if (G.removeEdge('A', 'B'))
        Autograder.addGrade(0.25f);
      else
        Autograder.Log("removeEdge did not return true.  (GraphTesting.java at testGenericGraph)");
      if (!G.removeEdge('A', 'D'))
        Autograder.addGrade(0.25f);
      else
        Autograder.Log("removeEdge did not return false.  (GraphTesting.java at testGenericGraph)");

      if (G.numEdges() == 3)
        edgeNumCheckCount++;
      else
        Autograder.Log("numEdges issue 2 (GraphTesting.java at testGenericGraph)");

      G.insertVertex('E');

      if (directed) {
        tmpB = G.outDegree('A') == 1 && G.inDegree('A') == 0 && G.outDegree('B') == 1 && G.inDegree('B') == 0
            && G.outDegree('C') == 1 && G.inDegree('C') == 1 && G.outDegree('D') == 0 && G.inDegree('D') == 2
            && G.outDegree('E') == 0 && G.inDegree('E') == 0 && G.outDegree('F') == -1 && G.inDegree('F') == -1;
      } else {
        tmpB = G.outDegree('A') == 1 && G.inDegree('A') == 1 && G.outDegree('B') == 1 && G.inDegree('B') == 1
            && G.outDegree('C') == 2 && G.inDegree('C') == 2 && G.outDegree('D') == 2 && G.inDegree('D') == 2
            && G.outDegree('E') == 0 && G.inDegree('E') == 0 && G.outDegree('F') == -1 && G.inDegree('F') == -1;
      }

      if (tmpB)
        Autograder.addGrade(1);
      else
        Autograder.Log("outDegree and/or inDegree results are not correct (GraphTesting.java at testGenericGraph)");

      G.insertEdge('F', 'G', 2f);
      G.insertEdge('E', 'G', 1.5f);
      G.insertEdge('E', 'F', -1f);

      G.insertEdge('A', 'E');
      tmpB = G.removeEdge('E', 'A');
      if (directed) {
        if (!tmpB)
          Autograder.addGrade(0.5f);
        else
          Autograder.Log("removeEdge removes in reverse for directed graphs (GraphTesting.java at testGenericGraph)");
        if (!G.removeEdge('A', 'E'))
          Autograder.Log("Something wrong with removeEdge, no grade impact (GraphTesting.java at testGenericGraph)");
      } else {
        if (tmpB)
          Autograder.addGrade(0.5f);
        else
          Autograder
              .Log("removeEdge does not work in reverse for undirected graphs (GraphTesting.java at testGenericGraph)");
      }

      tmpB = doesIterableHave(G.vertices(), 'F', 'G');
      if (tmpB)
        Autograder.addGrade(0.5f);
      else
        Autograder.Log("insertEdge is not adding two vertices (GraphTesting.java at testGenericGraph)");
      
      
      G.insertVertex('Z');
      G.insertVertex('X');
      G.insertEdge('X','Z');
      G.insertEdge('X','A');
      G.insertEdge('Z','E');

      if('X' == G.removeVertex('X') && 'Z' == G.removeVertex('Z') && null == G.removeVertex('Y'))
        Autograder.addGrade(1f);
      else
        Autograder.Log("removeVertex is not working correctly (GraphTesting.java at testGenericGraph)");

      if (G.numVertices() == 7)
        vertexNumCheckCount++;
      else
        Autograder.Log("vertexNumCheckCount issue 3 (GraphTesting.java at testGenericGraph)");
      
      if (G.numEdges() == 6)
        edgeNumCheckCount++;
      else
        Autograder.Log("numEdges issue 3 (GraphTesting.java at testGenericGraph)");

      G.insertEdge('A', 'B'); 

      if (directed) {
        tmpB = isIterableEqualTo(G.outgoingNeighbors('A'), 'C', 'B') && isIterableEqualTo(G.outgoingNeighbors('B'), 'D')
            && isIterableEqualTo(G.outgoingNeighbors('C'), 'D') && isIterableEqualTo(G.outgoingNeighbors('E'), 'F', 'G')
            && isIterableEqualTo(G.outgoingNeighbors('F'), 'G') && !G.outgoingNeighbors('D').iterator().hasNext()
            && !G.outgoingNeighbors('G').iterator().hasNext();
        if (tmpB) {
          Autograder.addGrade(1f);
        } else {
          Autograder.Log("outgoingNeighbors are not correct for a directed graph (GraphTesting.java at testGenericGraph)");
        }
        tmpB = isIterableEqualTo(G.incomingNeighbors('B'), 'A') && isIterableEqualTo(G.incomingNeighbors('C'), 'A')
            && isIterableEqualTo(G.incomingNeighbors('D'), 'B', 'C') && isIterableEqualTo(G.incomingNeighbors('F'), 'E')
            && isIterableEqualTo(G.incomingNeighbors('G'), 'E', 'F') && !G.incomingNeighbors('A').iterator().hasNext()
            && !G.incomingNeighbors('E').iterator().hasNext();
        if (tmpB) {
          Autograder.addGrade(1f);
        } else {
          Autograder.Log("incomingNeighbors are not correct for a directed graph (GraphTesting.java at testGenericGraph)");
        }
      } else {
        tmpB = isIterableEqualTo(G.outgoingNeighbors('A'), 'C', 'B') && isIterableEqualTo(G.outgoingNeighbors('B'), 'A', 'D')
            && isIterableEqualTo(G.outgoingNeighbors('C'), 'A', 'D') && isIterableEqualTo(G.outgoingNeighbors('D'), 'B', 'C')
            && isIterableEqualTo(G.outgoingNeighbors('E'), 'F', 'G') && isIterableEqualTo(G.outgoingNeighbors('F'), 'E', 'G')
            && isIterableEqualTo(G.outgoingNeighbors('G'), 'E', 'F');
        
        
        if (tmpB) {
          Autograder.addGrade(1f);
        } else {
          Autograder.Log("outgoingNeighbors are not correct for an undirected graph (GraphTesting.java at testGenericGraph)");
        }
        tmpB = isIterableEqualTo(G.incomingNeighbors('A'), 'C', 'B') && isIterableEqualTo(G.incomingNeighbors('B'), 'A', 'D')
            && isIterableEqualTo(G.incomingNeighbors('C'), 'A', 'D') && isIterableEqualTo(G.incomingNeighbors('D'), 'B', 'C')
            && isIterableEqualTo(G.incomingNeighbors('E'), 'F', 'G') && isIterableEqualTo(G.incomingNeighbors('F'), 'E', 'G')
            && isIterableEqualTo(G.incomingNeighbors('G'), 'E', 'F');
        if (tmpB) {
          Autograder.addGrade(1f);
        } else {
          Autograder.Log("incomingNeighbors are not correct for an undirected graph (GraphTesting.java at testGenericGraph)");
        }
      }      

      if(weighted) {
        tmpB = G.getEdgeWeight('F','G') == 2f && G.getEdgeWeight('E','G') == 1.5f && G.getEdgeWeight('E','F') == -1f;
        if (tmpB) {
          Autograder.addGrade(1.0f);
        } else {
          Autograder.Log("getEdgeWeight not working for given weights in a weighted graph (GraphTesting.java at testGenericGraph)");
        }
        
        G.insertEdge('F', 'G', 0.5f);
        if (G.getEdgeWeight('F','G') == 0.5f) {
          Autograder.addGrade(0.25f);
        } else {
          Autograder.Log("getEdgeWeight not changing the weight in a weighted graph (GraphTesting.java at testGenericGraph)");
        }
        if (G.getEdgeWeight('A','E') == Float.MAX_VALUE) {
          Autograder.addGrade(0.25f);
        } else {
          Autograder.Log("getEdgeWeight not returning the desired default for non-existent edges (GraphTesting.java at testGenericGraph)");
        }
        
        
      } else {
        tmpB = G.getEdgeWeight('F','G') == 1f && G.getEdgeWeight('E','G') == 1f && G.getEdgeWeight('E','F') == 1f;
        if (tmpB) {
          Autograder.addGrade(0.25f);
        } else {
          Autograder.Log("getEdgeWeight not returning 1 in an unweighted graph (GraphTesting.java at testGenericGraph)");
        }
        
        if (G.getEdgeWeight('A','E') == 0) {
          Autograder.addGrade(0.25f);
        } else {
          Autograder.Log("getEdgeWeight not returning the desired default for non-existent edges (GraphTesting.java at testGenericGraph)");
        }
      }
             
      
      Autograder.addGrade((vertexNumCheckCount + edgeNumCheckCount) / 6);

      Autograder.Log("Finished graph test for " + G.toString() + System.lineSeparator());
    } catch (Exception e) {
      e.printStackTrace();
      Autograder.Log("Exception caught while testing " + G.toString());
    }
  }

  public static void main(String args[]) {
    boolean iDidIt = false;
    if (!Autograder.initializedOnce) {
      Autograder.init();
      iDidIt = true;
    }
    UndirectedUnweightedGraph<Character> uuG = new UndirectedUnweightedGraph<Character>();
    if (!uuG.isDirected())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Undirected graph returning directed");
    if (!uuG.isWeighted())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Unweighted graph returning weighted");
    testGenericGraph(uuG);

    DirectedUnweightedGraph<Character> duG = new DirectedUnweightedGraph<Character>();
    if (duG.isDirected())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Directed graph returning undirected");
    if (!duG.isWeighted())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Unweighted graph returning weighted");
    testGenericGraph(duG);

    UndirectedWeightedGraph<Character> uwG = new UndirectedWeightedGraph<Character>();
    if (!uwG.isDirected())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Undirected graph returning directed");
    if (uwG.isWeighted())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Weighted graph returning unweighted");
    testGenericGraph(uwG);

    DirectedWeightedGraph<Character> dwG = new DirectedWeightedGraph<Character>();
    if (dwG.isDirected())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Directed graph returning undirected");
    if (dwG.isWeighted())
      Autograder.addGrade(0.25f);
    else
      Autograder.Log("Weighted graph returning unweighted");
    testGenericGraph(dwG);

    if (iDidIt) {
      Autograder.printLog();
      Autograder.printGrade(54);
    }
  }
}
