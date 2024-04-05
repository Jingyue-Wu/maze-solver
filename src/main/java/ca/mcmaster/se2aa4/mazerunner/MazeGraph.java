package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.List;

public class MazeGraph {
    private List<List<Boolean>> AdjacencyMatrix = new ArrayList<List<Boolean>>();

    MazeGraph(Maze maze) {
        AdjacencyMatrix = convertToMatrix(maze);
    }

    private List<List<Boolean>> convertToMatrix(Maze maze) {
        return maze.getMazeList();
    }

    public void getNext(int row, int column) {

    }

    public void checkExit(int row, int column) {

    }
}
