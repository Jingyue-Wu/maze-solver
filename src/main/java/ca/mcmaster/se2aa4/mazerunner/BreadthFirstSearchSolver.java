package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearchSolver implements MazeSolver {

    // private MazeGraph graph;
    private Maze maze;
    private Position currentPosition;

    Queue<Position> queue = new LinkedList<Position>();
    private List<List<Boolean>> marked;

    private List<Direction> directionCheck = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT,
            Direction.RIGHT);

    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        currentPosition = maze.getStart();
        queue.add(currentPosition);

        // Initialized list of marked nodes
        initializeMarked();

        // Loop unless priority queue is empty
        while (!queue.isEmpty()) {
            currentPosition = queue.remove();

            int limitX = maze.getSizeX();
            int limitY = maze.getSizeY();

            for (int i = 0; i < directionCheck.size(); i++) {
                Position checkPosition = currentPosition.move(directionCheck.get(i));

                int checkX = checkPosition.getX();
                int checkY = checkPosition.getY();

                if (checkX < limitX && checkY < limitY && checkX >= 0 && checkY >= 0) {

                    // checks 4 sides and its not a wall and has not been visited yet, then add node
                    // to priority queue
                    // creating an implicit graph representation adjacency list
                    if (!maze.isWall(checkPosition) && !marked.get(checkX).get(checkY)) {
                        queue.add(checkPosition);
                        marked.get(checkX).set(checkY, true);
                    }
                }
            }
            System.out.println(queue);
            System.out.println(marked);
            System.out.println(", ");

        }

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

    private Path getPath() {

        return null;
    }

    @Override
    public Path accept(Visitor v) {
        return v.visit(this);
    }
}