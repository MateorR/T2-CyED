import com.t2.cyed.util.Color;
import com.t2.cyed.util.GraphAdjacentList;
import com.t2.cyed.util.GraphType;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GraphAdjacentListTest {
  private GraphAdjacentList<Integer, Integer> graph;

  public void setUpStageSimpleGraph() {
    graph = new GraphAdjacentList<>(GraphType.SIMPLE);
    graph.addVertex(1, 1);
    graph.addVertex(2, 2);
    graph.addVertex(3, 3);
    graph.addVertex(4, 4);
    graph.addVertex(5, 5);
    graph.addVertex(6, 6);
    graph.addEdge(1, 2, 1);
    graph.addEdge(1, 3, 2);
    graph.addEdge(2, 4, 3);
    graph.addEdge(2, 5, 1);
    graph.addEdge(3, 5, 5);
    graph.addEdge(4, 6, 7);
    graph.addEdge(5, 6, 5);
  }

  public void setUpStageDirected() {
    graph = new GraphAdjacentList<>(GraphType.DIRECTED);

    graph.addVertex(1, 1);
    graph.addVertex(2, 2);
    graph.addVertex(3, 3);
    graph.addVertex(4, 4);
    graph.addVertex(5, 5);
    graph.addVertex(6, 6);
    graph.addVertex(7, 7);
    graph.addVertex(8, 8);
    graph.addVertex(9, 9);
    graph.addVertex(10, 10);
    graph.addVertex(11, 11);
    graph.addEdge(1, 2, 1);
    graph.addEdge(2, 1, 2);
    graph.addEdge(1, 3, 1);
    graph.addEdge(3, 1, 6);
    graph.addEdge(2, 4, 1);
    graph.addEdge(9, 7, 7);
    graph.addEdge(9, 11, 8);
    graph.addEdge(6, 10, 3);
    graph.addEdge(11, 7, 4);
    graph.addEdge(8, 6, 5);
    graph.addEdge(10, 11, 1);
    graph.addEdge(5, 1, 2);
    graph.addEdge(5, 6, 7);
    graph.addEdge(5, 3, 1);
    graph.addEdge(9, 4, 6);
    graph.addEdge(5, 7, 9);
    graph.addEdge(3, 6, 10);
    graph.addEdge(4, 7, 10);
    graph.addEdge(6, 7, 4);
    graph.addEdge(10, 8, 7);
    graph.addEdge(8, 5, 9);
    graph.addEdge(5, 2, 1);
    graph.addEdge(5, 4, 1);
    graph.addEdge(2, 9, 9);
    graph.addEdge(8, 7, 1);
  }

  public void setUpGraphWithoutConnected() {
    graph = new GraphAdjacentList<>(GraphType.SIMPLE);
    graph.addVertex(1, 1);
    graph.addVertex(2, 2);
    graph.addVertex(3, 3);
    graph.addVertex(4, 4);
    graph.addVertex(5, 5);
    graph.addVertex(6, 6);
  }

  @Test
  public void addVertexToGraph() {
    setUpStageSimpleGraph();
    boolean added = graph.addVertex(7, 7);
    assertTrue(added);
    assertEquals(7, graph.size());
  }

  @Test
  public void addVertexToGraphDirected() {
    setUpStageDirected();
    boolean added = graph.addVertex(12, 12);
    assertTrue(added);
    assertEquals(12, graph.size());
  }

  @Test
  public void addVertexToGraphAlreadyExist() {
    setUpStageSimpleGraph();
    boolean added = graph.addVertex(1, 1);
    assertFalse(added);
    assertEquals(6, graph.size());
  }


  @Test
  public void addEdgeToVertexThatNotExist() {
    setUpStageSimpleGraph();
    boolean added = graph.addEdge(1, 7, 1);
    assertFalse(added);
  }

  @Test
  public void addEdgeToSameVertexInGraphSimple() {
    setUpStageSimpleGraph();

  }

  @Test
  public void removeVertexOfGraphSimple() {
    setUpStageSimpleGraph();
    boolean result = graph.removeVertex(1);
    assertTrue(result);
    result = graph.removeVertex(2);
    assertTrue(result);
    result = graph.removeVertex(3);
    assertTrue(result);
    result = graph.removeVertex(4);
    assertTrue(result);
    result = graph.removeVertex(5);
    assertTrue(result);
    result = graph.removeVertex(6);
    assertTrue(result);
    assertEquals(0, graph.size());
  }

  @Test
  public void removeVertexOfGraphDirected() {
    setUpStageDirected();
    assertEquals(11, graph.size());
    boolean result = graph.removeVertex(1);
    assertTrue(result);
    assertEquals(10, graph.size());
    result = graph.removeVertex(2);
    assertTrue(result);
    result = graph.removeVertex(3);
    assertTrue(result);
    result = graph.removeVertex(4);
    assertTrue(result);
    result = graph.removeVertex(5);
    assertTrue(result);
    result = graph.removeVertex(6);
    assertTrue(result);
    result = graph.removeVertex(7);
    assertTrue(result);
    result = graph.removeVertex(8);
    assertTrue(result);
    result = graph.removeVertex(9);
    assertTrue(result);
    result = graph.removeVertex(10);
    assertTrue(result);
    result = graph.removeVertex(11);
    assertTrue(result);
    assertEquals(0, graph.size());
  }

  @Test
  public void removeVertexOfGraphSimpleNotExist() {
    setUpStageSimpleGraph();
    boolean result = graph.removeVertex(7);
    assertFalse(result);
  }

  @Test
  public void removeVertexOfGraphDirectedNotExist() {
    setUpStageDirected();
    boolean result = graph.removeVertex(12);
    assertFalse(result);
  }

  @Test
  public void removeEdgeConnectedWithOtherEdgeList() {
    setUpStageSimpleGraph();
    boolean result;
    result = graph.removeEdge(1, 2);
    assertTrue(result);
    result = graph.removeEdge(1, 3);
    assertTrue(result);
    result = graph.removeEdge(2, 4);
    assertTrue(result);
    result = graph.removeEdge(2, 5);
    assertTrue(result);
    result = graph.removeEdge(3, 5);
    assertTrue(result);
    result = graph.removeEdge(4, 6);
    assertTrue(result);
    result = graph.removeEdge(5, 6);
    assertTrue(result);
  }

  @Test
  public void removeEdgeConnectedWithOtherEdgeListDirected() {
    setUpStageDirected();
    boolean result;
    result = graph.removeEdge(1, 2);
    assertTrue(result);
    result = graph.removeEdge(2, 1);
    assertTrue(result);
    result = graph.removeEdge(1, 3);
    assertTrue(result);
    result = graph.removeEdge(3, 1);
    assertTrue(result);
    result = graph.removeEdge(2, 4);
    assertTrue(result);
    result = graph.removeEdge(9, 7);
    assertTrue(result);
    result = graph.removeEdge(9, 11);
    assertTrue(result);
    result = graph.removeEdge(6, 10);
    assertTrue(result);
    result = graph.removeEdge(11, 7);
    assertTrue(result);
    result = graph.removeEdge(8, 6);
    assertTrue(result);
    result = graph.removeEdge(10, 11);
    assertTrue(result);
    result = graph.removeEdge(5, 1);
    assertTrue(result);
    result = graph.removeEdge(5, 6);
    assertTrue(result);
    result = graph.removeEdge(5, 3);
    assertTrue(result);
    result = graph.removeEdge(9, 4);
    assertTrue(result);
    result = graph.removeEdge(5, 7);
    assertTrue(result);
    result = graph.removeEdge(3, 6);
    assertTrue(result);
    result = graph.removeEdge(4, 7);
    assertTrue(result);
    result = graph.removeEdge(6, 7);
    assertTrue(result);
    result = graph.removeEdge(10, 8);
    assertTrue(result);
    result = graph.removeEdge(8, 5);
    assertTrue(result);
    result = graph.removeEdge(5, 2);
    assertTrue(result);
    result = graph.removeEdge(5, 4);
    assertTrue(result);
    result = graph.removeEdge(2, 9);
    assertTrue(result);
    result = graph.removeEdge(8, 7);
    assertTrue(result);
  }

  @Test
  public void removeEdgeConnectedWithOtherEdgeListNotExist() {
    setUpStageSimpleGraph();
    boolean result;
    result = graph.removeEdge(1, 7);
    assertFalse(result);
  }

  @Test
  public void removeEdgeConnectedWithOtherEdgeListDirectedNotExist() {
    setUpStageDirected();
    boolean result;
    result = graph.removeEdge(1, 12);
    assertFalse(result);
  }

  @Test
  public void BFSWithAllVertexConnected() {
    setUpStageDirected();
    graph.bfs(1);

    assertEquals(0, graph.getVertex(1).getDistance());
    assertEquals(1, graph.getVertex(2).getDistance());
    assertEquals(1, graph.getVertex(3).getDistance());
    assertEquals(2, graph.getVertex(4).getDistance());
    assertEquals(5, graph.getVertex(5).getDistance());
    assertEquals(2, graph.getVertex(6).getDistance());
    assertEquals(3, graph.getVertex(7).getDistance());
    assertEquals(4, graph.getVertex(8).getDistance());
    assertEquals(2, graph.getVertex(9).getDistance());
    assertEquals(3, graph.getVertex(10).getDistance());
    assertEquals(3, graph.getVertex(11).getDistance());
  }

  @Test
  public void BFSWithAllVertexesConnectedSimple() {
    setUpStageSimpleGraph();
    graph.bfs(1);

    assertEquals(0, graph.getVertex(1).getDistance());
    assertEquals(1, graph.getVertex(2).getDistance());
    assertEquals(1, graph.getVertex(3).getDistance());
    assertEquals(2, graph.getVertex(4).getDistance());
    assertEquals(2, graph.getVertex(5).getDistance());
    assertEquals(3, graph.getVertex(6).getDistance());
  }

  @Test
  public void BFSOfGraphWithoutConnection() {
    setUpGraphWithoutConnected();
    graph.bfs(1);

    assertEquals(0, graph.getVertex(1).getDistance());
    assertEquals(Color.BLACK, graph.getVertex(1).getColor());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(2).getDistance());
    assertEquals(Color.WHITE, graph.getVertex(2).getColor());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(3).getDistance());
    assertEquals(Color.WHITE, graph.getVertex(3).getColor());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(4).getDistance());
    assertEquals(Color.WHITE, graph.getVertex(4).getColor());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(5).getDistance());
    assertEquals(Color.WHITE, graph.getVertex(5).getColor());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(6).getDistance());
    assertEquals(Color.WHITE, graph.getVertex(6).getColor());
  }


  @Test
  public void testBFSAndThereIsNotStartVertex() {
    setUpStageSimpleGraph();
    graph.bfs(6);
    assertEquals(0, graph.getVertex(6).getDistance());
  }

  @Test
  public void testDijkstraWithAllConnectedDrive() {
    setUpStageDirected();
    ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0, 1, 1, 2, 30, 11, 12, 21, 10, 14, 15));
    ArrayList<Integer> path;
    path = graph.dijkstra(1);

    assertEquals(result, path);
    assertEquals(0, graph.getVertex(1).getDistance());
    assertEquals(1, graph.getVertex(2).getDistance());
    assertEquals(1, graph.getVertex(3).getDistance());
    assertEquals(2, graph.getVertex(4).getDistance());
    assertEquals(30, graph.getVertex(5).getDistance());
    assertEquals(11, graph.getVertex(6).getDistance());
    assertEquals(21, graph.getVertex(8).getDistance());
    assertEquals(10, graph.getVertex(9).getDistance());
    assertEquals(14, graph.getVertex(10).getDistance());
    assertEquals(15, graph.getVertex(11).getDistance());
  }

  @Test
  public void testDijkstraWithGraphSimple() {
    setUpStageSimpleGraph();
    ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0, 1, 2, 4, 2, 7));
    ArrayList<Integer> path;
    path = graph.dijkstra(1);
    assertEquals(result, path);
    assertEquals(0, graph.getVertex(1).getDistance());
    assertEquals(1, graph.getVertex(2).getDistance());
    assertEquals(2, graph.getVertex(3).getDistance());
    assertEquals(4, graph.getVertex(4).getDistance());
    assertEquals(2, graph.getVertex(5).getDistance());
    assertEquals(7, graph.getVertex(6).getDistance());

  }

  public void setUpGraphWithoutConnected2() {
    graph = new GraphAdjacentList<>(GraphType.SIMPLE);
    graph.addVertex(1, 1);
    graph.addVertex(2, 2);
    graph.addVertex(3, 3);
    graph.addVertex(4, 4);
    graph.addVertex(5, 5);
    graph.addVertex(6, 6);
  }

  @Test
  public void testDijkstraWithVertexThatNotExist() {
    setUpStageSimpleGraph();
    ArrayList<Integer> path;
    path = graph.dijkstra(7);
    assertNull(path);
  }

  @Test
  public void testDijkstraGraphWithOutConnections() {
    setUpGraphWithoutConnected2();
    ArrayList<Integer> result = new ArrayList<>(Arrays.asList(0, Integer.MAX_VALUE - 100, Integer.MAX_VALUE - 100, Integer.MAX_VALUE - 100, Integer.MAX_VALUE - 100, Integer.MAX_VALUE - 100));
    ArrayList<Integer> path;

    path = graph.dijkstra(1);

    assertEquals(result, path);
    assertEquals(0, graph.getVertex(1).getDistance());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(2).getDistance());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(3).getDistance());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(4).getDistance());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(5).getDistance());
    assertEquals(Integer.MAX_VALUE - 100, graph.getVertex(6).getDistance());
  }
}