package ca.mcmaster.se2aa4.visitor;

import ca.mcmaster.se2aa4.mazerunner.Path;
import ca.mcmaster.se2aa4.mazerunner.solver.BreadthFirstSearchSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.RightHandSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.TremauxSolver;

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