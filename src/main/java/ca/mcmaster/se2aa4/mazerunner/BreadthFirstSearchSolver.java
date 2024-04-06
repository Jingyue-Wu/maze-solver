package ca.mcmaster.se2aa4.mazerunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearchSolver implements MazeSolver {

    @Override
    public Path accept(Visitor v) {
        return v.visit(this);
    }

    // private MazeGraph graph;
    private Maze maze;
    private Position currentPosition;

    // Priority queue
    Queue<Position> queue = new LinkedList<Position>();

    // Queue for storing the current longest path discovered for the path
    // instruction
    Queue<ArrayList<Position>> checkedPaths = new LinkedList<>();

    private List<List<Boolean>> marked;

    private List<Direction> directionCheck = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT,
            Direction.RIGHT);

    @Override
    public Path solve(Maze maze) {
        Path path = new Path();
        this.maze = maze;
        currentPosition = maze.getStart();
        Direction dir = Direction.RIGHT;
        queue.add(currentPosition);

        // Initialize starting node in pathD
        ArrayList<Position> start = new ArrayList<Position>();
        start.add(currentPosition);
        checkedPaths.add(start);

        // Initialized list of marked nodes
        initializeMarked();

        while (!queue.isEmpty()) {
            ArrayList<Position> currentPath = checkedPaths.remove();
            currentPosition = queue.remove();

            int limitX = maze.getSizeX();
            int limitY = maze.getSizeY();

            // Check if end is reached
            if (currentPosition.getX() == maze.getSizeX() - 1) {
                path = getPath(currentPath); // Generate path
                return path;
            }

            for (int i = 0; i < directionCheck.size(); i++) {
                Position checkPosition = currentPosition.move(directionCheck.get(i));

                int checkX = checkPosition.getX();
                int checkY = checkPosition.getY();

                if (checkX < limitX && checkY < limitY && checkX > 0 && checkY > 0) {

                    // checks 4 sides and its not a wall and has not been visited yet, then add node
                    // to priority queue
                    // creating an implicit graph representation adjacency list
                    if (!maze.isWall(checkPosition) && !marked.get(checkX).get(checkY)) {
                        queue.add(checkPosition);
                        marked.get(checkX).set(checkY, true);

                        // Update path queue
                        currentPath.add(checkPosition);
                        checkedPaths.add(currentPath);
                    }
                }
            }
        }
        return path;
    }

    private Path getPath(ArrayList<Position> minimumNodes) {

        System.out.println(minimumNodes);
        System.out.println(" ");


        // Path result = getPath();

        // return result;
        Path tempPath = new Path("FRFF");

        return tempPath;
    }

    private void initializeMarked() {
        marked = new LinkedList<List<Boolean>>();
        for (int i = 0; i < maze.getSizeX(); i++) {
            List<Boolean> row = new ArrayList<Boolean>();

            for (int j = 0; j < maze.getSizeY(); j++) {
                row.add(false);
            }
            marked.add(row);
        }
    }
}