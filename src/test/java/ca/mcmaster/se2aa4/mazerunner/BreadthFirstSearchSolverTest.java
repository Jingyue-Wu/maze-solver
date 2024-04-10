package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;

import ca.mcmaster.se2aa4.mazerunner.solver.BreadthFirstSearchSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.MazeSolver;
import ca.mcmaster.se2aa4.visitor.SolverVisitor;
import ca.mcmaster.se2aa4.visitor.Visitor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;

class BFSTest {
    Maze maze;
    String mediumMazeFile = "./examples/medium.maz.txt";
    Path mediumMazePath = new Path("F L 2F R 2F L 18F L 2F R 2F R 8F R 2F L 6F R 10F L 4F R 10F L 10F R 4F L F");

    @BeforeEach
    public void setUpMaze() throws Exception {
        try {
            maze = new Maze(mediumMazeFile);
        } catch (Exception e) {
            throw new Exception("Invalid maze: ", e);
        }
    }

    @Test
    void testBFSVisitor() throws Exception {
        Visitor solverVisitor = new SolverVisitor(maze);
        MazeSolver solver = new BreadthFirstSearchSolver();
        Path result = solver.accept(solverVisitor);

        assertEquals(result.getFactorizedForm(), mediumMazePath.getFactorizedForm());
    }

    @Test
    void testSolve() {
        MazeSolver solver = new BreadthFirstSearchSolver();
        Path result = solver.solve(maze);

        assertEquals(result.getFactorizedForm(), mediumMazePath.getFactorizedForm());
    }
}
