package ca.mcmaster.se2aa4.mazerunner;

public interface Visitor {

    /**
     * Visits the given solver method and gets the solved Path.
     * 
     * @param MazeSolver Selected solving method
     * @return Path obtained from visited solver
     */

    public Path visit(RightHandSolver solver);

    public Path visit(TremauxSolver solver);

    public Path visit(BreadthFirstSearchSolver solver);
}