package ca.mcmaster.se2aa4.mazerunner.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import ca.mcmaster.se2aa4.mazerunner.Direction;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.Position;

public class AdjacencyList implements Graph {
    private Maze maze;
    private Map<Position, List<Position>> adjacencyList = new HashMap<>();
    private List<Direction> directionCheck = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT,
            Direction.RIGHT);

    /**
     * checkedNodePaths Queue holds all of visited paths so far.
     * (required for getting the correct path once BFS terminates)
     * 
     * All maze exploring algorithms that use Graph will require this feature.
     */
    private Queue<ArrayList<Position>> checkedNodePaths = new LinkedList<>();

    public AdjacencyList(Maze maze) {
        this.maze = maze;
    }

    @Override
    public void updateNodes(Position currentNode, List<Position> marked, ArrayList<Position> currentPath) {
        for (int i = 0; i < directionCheck.size(); i++) {
            Position checkNode = currentNode.move(directionCheck.get(i));

            int limitX = maze.getSizeX();
            int limitY = maze.getSizeY();
            int checkX = checkNode.getX();
            int checkY = checkNode.getY();

            /**
             * Checks 4 adjecent nodes.
             * If it's not a wall and has not been visited yet, then add the node to the
             * adjacency list.
             * Each adjacency list entry is only computed if needed, improving efficency.
             */

            if (checkX < limitX && checkY < limitY && checkX > 0 && checkY > 0) {
                if (!maze.isWall(checkNode) && !marked.contains(checkNode)) {
                    addNode(currentNode, checkNode);
                    marked.add(checkNode);

                    ArrayList<Position> newPosition = new ArrayList<>(currentPath);
                    newPosition.add(checkNode);
                    updateNodePath(newPosition);
                }
            }
        }
    }

    @Override
    public void updateNodePath(ArrayList<Position> newNodePath) {
        checkedNodePaths.add(newNodePath);
    }

    @Override
    public ArrayList<Position> getLastNodePath() {
        return checkedNodePaths.remove();
    }

    @Override
    public List<Position> getAdjacentNodes(Position node) {
        return adjacencyList.get(node);
    }

    /**
     * Gets a list of nodes adjacent to any given node.
     *
     * @param node Input node
     * @return List of Position nodes that are adjacent to given node
     */
    private void addNode(Position node, Position adjacentNodes) {
        List<Position> currentAdjacentNodes = new LinkedList<>();
        if (adjacencyList.get(node) != null) {
            currentAdjacentNodes = adjacencyList.get(node);
        }
        currentAdjacentNodes.add(adjacentNodes);
        adjacencyList.put(node, currentAdjacentNodes);
    }
}
