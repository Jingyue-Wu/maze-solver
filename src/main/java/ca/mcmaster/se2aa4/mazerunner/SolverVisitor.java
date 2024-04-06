package ca.mcmaster.se2aa4.mazerunner;

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
