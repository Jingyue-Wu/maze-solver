package ca.mcmaster.se2aa4.visitor;

import ca.mcmaster.se2aa4.mazerunner.Maze;
import ca.mcmaster.se2aa4.mazerunner.Path;
import ca.mcmaster.se2aa4.mazerunner.solver.BreadthFirstSearchSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.RightHandSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.TremauxSolver;

public class SolverVisitor implements Visitor {
    Maze maze;

    public SolverVisitor(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Path visit(RightHandSolver rh) {
        return rh.solve(maze);
    }

    @Override
    public Path visit(TremauxSolver t) {
        return t.solve(maze);
    }

    @Override
    public Path visit(BreadthFirstSearchSolver bfs) {
        return bfs.solve(maze);
    }
}
