package ca.mcmaster.se2aa4.mazerunner;

public class SolverVisitor implements Visitor {
    Maze maze;

    public SolverVisitor(Maze maze) {
        this.maze = maze;
    }

    @Override
    public Path visit(RightHandSolver rh) {
        // Implement logic to get the path for RightHandSolver
        return rh.solve(maze);
    }

    @Override
    public Path visit(TremauxSolver t) {
        return t.solve(maze);
    }

    // @Override
    // public void visit(BFS bfs){
    // return bfs.getPath();
    // }

}
