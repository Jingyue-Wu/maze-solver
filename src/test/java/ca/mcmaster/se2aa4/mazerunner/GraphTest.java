package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import ca.mcmaster.se2aa4.mazerunner.graph.AdjacencyList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

public class GraphTest {
    Maze maze;
    AdjacencyList graph;
    String mediumMazeFile = "./examples/medium.maz.txt";

    @BeforeEach
    public void setUpMaze() throws Exception {
        try {
            maze = new Maze(mediumMazeFile);
        } catch (Exception e) {
            throw new Exception("Invalid maze: ", e);
        }

        graph = new AdjacencyList(maze);
    }

    @Test
    void testUpdateAdjacentNodes() {
        Position currentNode = maze.getStart();
        List<Position> marked = new ArrayList<>();
        ArrayList<Position> currentPath = new ArrayList<>();

        currentPath.add(currentNode);

        graph.updateNodes(currentNode, marked, currentPath);

        // Starting node should be updated with 1 non-wall neighbour

        List<Position> adjacentNodes = graph.getAdjacentNodes(currentNode);
        assertEquals(1, adjacentNodes.size());

        for (Position node : adjacentNodes) {
            assertTrue(!maze.isWall(node));
        }
    }

    @Test
    void testUpdateGetNodePath() {
        ArrayList<Position> newPath = new ArrayList<>();
        newPath.add(new Position(0, 0));
        newPath.add(new Position(0, 1));
        newPath.add(new Position(1, 1));

        graph.updateNodePath(newPath);

        ArrayList<Position> lastNodePath = graph.getLastNodePath();

        assertEquals(newPath, lastNodePath);
    }
}
