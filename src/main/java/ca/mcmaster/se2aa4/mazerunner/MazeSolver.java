package ca.mcmaster.se2aa4.mazerunner;

public interface MazeSolver {

    public Path accept(Visitor v);

    /**
     * Solve maze and return path through maze.
     *
     * @param maze Maze to solve
     * @return Path that solves the provided maze
     */
    Path solve(Maze maze);
}
