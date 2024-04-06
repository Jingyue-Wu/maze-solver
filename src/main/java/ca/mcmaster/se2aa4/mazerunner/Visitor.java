package ca.mcmaster.se2aa4.mazerunner;

public interface Visitor {
    public Path visit(RightHandSolver solver);

    public Path visit(TremauxSolver solver);

    // public Path visit(BreadthFirstSearchSolver solver);
}