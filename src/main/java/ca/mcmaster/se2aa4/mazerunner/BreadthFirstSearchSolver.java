package ca.mcmaster.se2aa4.mazerunner;

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

    private Maze maze;
    private Position currentPosition;

    // Priority queue
    Queue<Position> queue = new LinkedList<>();

    // Queue for storing current longest path discovered for the path instruction
    Queue<ArrayList<Position>> checkedPaths = new LinkedList<>();

    private List<List<Boolean>> marked;

    private List<Direction> directionCheck = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT,
            Direction.RIGHT);

    @Override
    public Path solve(Maze maze) {
        Path path = new Path();
        this.maze = maze;
        currentPosition = maze.getStart();
        queue.add(currentPosition);

        // Initialize starting node in path
        ArrayList<Position> start = new ArrayList<>();
        start.add(currentPosition);
        checkedPaths.add(start);

        // Initialized list of marked nodes
        initializeMarked();

        while (!queue.isEmpty()) {
            ArrayList<Position> currentPath = checkedPaths.remove();
            currentPosition = queue.remove();

            for (int i = 0; i < directionCheck.size(); i++) {
                Position checkPosition = currentPosition.move(directionCheck.get(i));

                updatePaths(checkPosition, currentPath);
            }

            // Check if end is reached
            if (currentPosition.getX() == maze.getSizeX() - 1) {
                path = getPath(currentPath); // Generate path
                return path;
            }

        }
        return path;
    }

    private Path getPath(ArrayList<Position> minimumNodes) {
        Path finalPath = new Path();
        Direction dir = Direction.RIGHT;

        for (int i = 1; i < minimumNodes.size(); i++) {
            Position current = minimumNodes.get(i);
            Position previous = minimumNodes.get(i - 1);
            Direction newDir = getNewDirection(current, previous);

            switch (dir) {
                case UP -> {
                    switch (newDir) {
                        case UP -> {
                            finalPath.addStep('F');
                        }
                        case LEFT -> {
                            finalPath.addStep('L');
                            finalPath.addStep('F');
                            dir = dir.turnLeft();

                        }
                        case RIGHT -> {
                            finalPath.addStep('R');
                            finalPath.addStep('F');
                            dir = dir.turnRight();
                        }
                        default -> throw new IllegalStateException("Can not turn around in one spot: " + this);
                    }
                }
                case DOWN -> {
                    switch (newDir) {
                        case DOWN -> {
                            finalPath.addStep('F');

                        }
                        case LEFT -> {
                            finalPath.addStep('R');
                            finalPath.addStep('F');
                            dir = dir.turnRight();
                        }
                        case RIGHT -> {
                            finalPath.addStep('L');
                            finalPath.addStep('F');
                            dir = dir.turnLeft();
                        }
                        default -> throw new IllegalStateException("Can not turn around in one spot: " + this);
                    }
                }
                case LEFT -> {
                    switch (newDir) {
                        case UP -> {
                            finalPath.addStep('R');
                            finalPath.addStep('F');
                            dir = dir.turnRight();
                        }
                        case DOWN -> {
                            finalPath.addStep('L');
                            finalPath.addStep('F');
                            dir = dir.turnLeft();
                        }
                        case LEFT -> {
                            finalPath.addStep('F');
                        }
                        default -> throw new IllegalStateException("Can not turn around in one spot: " + this);
                    }
                }
                case RIGHT -> {
                    switch (newDir) {
                        case UP -> {
                            finalPath.addStep('L');
                            finalPath.addStep('F');
                            dir = dir.turnLeft();
                        }
                        case DOWN -> {
                            finalPath.addStep('R');
                            finalPath.addStep('F');
                            dir = dir.turnRight();
                        }
                        case RIGHT -> {
                            finalPath.addStep('F');
                        }
                        default -> throw new IllegalStateException("Can not turn around in one spot: " + this);
                    }
                }
            }
        }
        return finalPath;
    }

    private Direction getNewDirection(Position current, Position previous) {
        // Find absolute direction (Relative to x, y coordinates)
        int currentX = current.getX();
        int currentY = current.getY();
        int prevX = previous.getX();
        int prevY = previous.getY();

        if (currentY < prevY && currentX == prevX) {
            return Direction.UP;
        }

        else if (currentY > prevY && currentX == prevX) {
            return Direction.DOWN;
        }

        else if (currentX < prevX && currentY == prevY) {
            return Direction.LEFT;
        }

        else if (currentX > prevX && currentY == prevY) {
            return Direction.RIGHT;
        }
        throw new IllegalStateException("New position must be different from existing position: " + this);
    }

    private void updatePaths(Position checkPosition, ArrayList<Position> currentPath) {
        int limitX = maze.getSizeX();
        int limitY = maze.getSizeY();
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
                ArrayList<Position> newPosition = new ArrayList<>(currentPath);
                newPosition.add(checkPosition);
                checkedPaths.add(newPosition);
            }
        }
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