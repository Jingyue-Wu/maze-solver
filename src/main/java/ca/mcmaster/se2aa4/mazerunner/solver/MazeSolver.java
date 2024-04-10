package ca.mcmaster.se2aa4.mazerunner.solver;

import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.Path;
import ca.mcmaster.se2aa4.visitor.Visitor;

public interface MazeSolver {

    /**
     * Accept visitor to use MazeSolver methods.
     *
     * @param visitor Maze to solve
     * @return Final Path can be returned from visitor
     */
    public Path accept(Visitor visitor);

    /**
     * Solve maze and return path through maze.
     *
     * @param maze Maze to solve
     * @return Path that solves the provided maze
     */
    Path solve(Maze maze);
}
