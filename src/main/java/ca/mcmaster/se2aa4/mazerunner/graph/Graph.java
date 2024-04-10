package ca.mcmaster.se2aa4.mazerunner.graph;

import java.util.ArrayList;
import java.util.List;

import ca.mcmaster.se2aa4.mazerunner.Position;

public interface Graph {

    /**
     * Updates the neighbours of nodes in adjacency list for newly discovered nodes.
     *
     * @param currentNode Node being updated
     * @param marked List of nodes that have been visited
     * @param currentPath The current path being taken in the order of nodes travelled
     * @return void
     */
    public void updateNodes(Position currentNode, List<Position> marked, ArrayList<Position> currentPath);

    /**
     * Updates/Initalizies the paths based on newly discovered nodes
     *
     * @param newNodePath List of new paths taken in the order of nodes travelled
     * @return void
     */
    public void updateNodePath(ArrayList<Position> newNodePath);

    /**
     * Removes and returns the next path taken in the order of nodes travelled.
     *
     * @return The next possible path in the graph that needs to be checked
     */
    public ArrayList<Position> getLastNodePath();

    /**
     * Gets a list of nodes adjacent to any given node.
     *
     * @param node Input node
     * @return List of nodes that are adjacent to the given node
     */
    public List<Position> getAdjacentNodes(Position node);
}
