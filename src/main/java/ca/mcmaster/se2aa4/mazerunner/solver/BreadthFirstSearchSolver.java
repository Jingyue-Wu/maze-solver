package ca.mcmaster.se2aa4.mazerunner.solver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ca.mcmaster.se2aa4.mazerunner.Direction;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.Path;
import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.visitor.Visitor;

public class BreadthFirstSearchSolver implements MazeSolver {

    @Override
    public Path accept(Visitor visitor) {
        return visitor.visit(this);
    }

    private Maze maze;
    private Position currentPosition;
    private Queue<Position> queue = new LinkedList<>();
    private List<Position> marked = new ArrayList<>();
    private List<Direction> directionCheck = Arrays.asList(Direction.UP, Direction.DOWN, Direction.LEFT,
            Direction.RIGHT);

    /**
     * Holds all of visited paths so far (required for getting the correct path once
     * BFS terminates)
     */
    private Queue<ArrayList<Position>> checkedPaths = new LinkedList<>();

    @Override
    public Path solve(Maze maze) {
        Path path = new Path();

        this.maze = maze;
        int endOfMaze = maze.getSizeX() - 1;

        currentPosition = maze.getStart();
        queue.add(currentPosition);

        ArrayList<Position> start = new ArrayList<>();
        start.add(currentPosition);
        checkedPaths.add(start);
        marked.add(currentPosition);

        while (!queue.isEmpty()) {
            ArrayList<Position> currentPath = checkedPaths.remove();
            currentPosition = queue.remove();

            for (int i = 0; i < directionCheck.size(); i++) {
                Position checkPosition = currentPosition.move(directionCheck.get(i));

                updatePaths(checkPosition, currentPath);
            }

            // Check if end is reached
            if (currentPosition.getX() == endOfMaze) {
                path = getPath(currentPath);
                return path;
            }
        }
        return path;
    }

    /**
     * Updates the priority queue and queue of possible paths.
     *
     * @param checkPosition Position of current node in the graph
     * @param currentPath   Position of the previously checked node
     * @return void
     */

    // -----------------------MOVE TO GRAPH CLASS!-------------------------
    private void updatePaths(Position checkPosition, ArrayList<Position> currentPath) {
        int limitX = maze.getSizeX();
        int limitY = maze.getSizeY();
        int checkX = checkPosition.getX();
        int checkY = checkPosition.getY();

        if (checkX < limitX && checkY < limitY && checkX > 0 && checkY > 0) {

            /**
             * Checks 4 adjecent nodes.
             * If it's not a wall and has not been visited yet, then add the node
             * to priority queue creating an implicit adjacency list graph representation.
             * Each adjacency list entry is only computed if needed, improving efficency.
             */

            if (!maze.isWall(checkPosition) && !marked.contains(checkPosition)) {
                queue.add(checkPosition);
                marked.add(checkPosition);

                // Update path queue
                ArrayList<Position> newPosition = new ArrayList<>(currentPath);
                newPosition.add(checkPosition);
                checkedPaths.add(newPosition);
            }

            // if (!maze.isWall(checkPosition) && !marked.get(checkX).get(checkY)) {
            // queue.add(checkPosition);
            // marked.get(checkX).set(checkY, true);

            // // Update path queue
            // ArrayList<Position> newPosition = new ArrayList<>(currentPath);
            // newPosition.add(checkPosition);
            // checkedPaths.add(newPosition);
            // }
        }
    }

    /**
     * Get the shortest path to exit the maze based on the order of nodes travelled.
     *
     * @param minimumNodes List of minimal node positions in traversal order
     * @return Shortest Path
     */
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
                        default -> throw new IllegalStateException("Can not turn around in one spot");
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
                        default -> throw new IllegalStateException("Can not turn around in one spot");
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
                        default -> throw new IllegalStateException("Can not turn around in one spot");
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
                        default -> throw new IllegalStateException("Can not turn around in one spot");
                    }
                }
            }
        }
        return finalPath;
    }

    /**
     * Gets the new travel direction based on the change in nodes.
     *
     * @param current  Position of current node in the graph
     * @param previous Position of the previously checked node
     * @return New Direction
     */
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
}