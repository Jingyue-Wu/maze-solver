package ca.mcmaster.se2aa4.mazerunner.solver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ca.mcmaster.se2aa4.mazerunner.Direction;
import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.Path;
import ca.mcmaster.se2aa4.mazerunner.Position;
import ca.mcmaster.se2aa4.mazerunner.graph.AdjacencyList;
import ca.mcmaster.se2aa4.visitor.Visitor;

public class BreadthFirstSearchSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
    private Maze maze;
    private AdjacencyList graph;
    private Position currentPosition;
    private Queue<Position> queue = new LinkedList<>();
    private List<Position> marked = new ArrayList<>();

    @Override
    public Path accept(Visitor visitor) {
        return visitor.visit(this);
    }

    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        logger.debug("Initalizing graph...");
        graph = new AdjacencyList(maze);
        logger.debug("Traversing maze...");
        return traverseMaze();
    }

    private Path traverseMaze() {
        int endOfMaze = maze.getSizeX() - 1;
        Path path = new Path();

        currentPosition = maze.getStart();
        queue.add(currentPosition);

        ArrayList<Position> start = new ArrayList<>();
        start.add(currentPosition);
        marked.add(currentPosition);

        graph.updateNodePath(start);

        while (!queue.isEmpty()) {
            ArrayList<Position> currentPath = graph.getLastNodePath();
            currentPosition = queue.remove();

            // Update adjacency list and queue
            graph.updateNodes(currentPosition, marked, currentPath);

            List<Position> adjacentNodes = graph.getAdjacentNodes(currentPosition);

            if (adjacentNodes != null) {
                for (int i = 0; i < adjacentNodes.size(); i++) {
                    queue.add(adjacentNodes.get(i));
                }
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
     * Gets the new travel direction based on the change in nodes
     * (Relative to x, y coordinates).
     *
     * @param current  Position of current node in the graph
     * @param previous Position of the previously checked node
     * @return New Direction
     */
    private Direction getNewDirection(Position current, Position previous) {
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