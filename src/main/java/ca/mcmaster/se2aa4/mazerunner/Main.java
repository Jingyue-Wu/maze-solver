package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.solver.BreadthFirstSearchSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.MazeSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.RightHandSolver;
import ca.mcmaster.se2aa4.mazerunner.solver.TremauxSolver;
import ca.mcmaster.se2aa4.visitor.SolverVisitor;
import ca.mcmaster.se2aa4.visitor.Visitor;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    private static Visitor solverVisitor;

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');

            long mazeLoadTime = System.currentTimeMillis();
            Maze maze = new Maze(filePath);
            mazeLoadTime = System.currentTimeMillis() - mazeLoadTime;

            solverVisitor = new SolverVisitor(maze);

            if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            }

            else if (cmd.getOptionValue("method") != null) {
                String selectedMethod = cmd.getOptionValue("method", "righthand");

                if (cmd.getOptionValue("baseline") != null) {
                    String baselineMethod = cmd.getOptionValue("baseline");
                    Benchmarker benchmark = new Benchmarker();

                    benchmark.updateselectedTime();
                    Path selectedPath = solveMaze(selectedMethod, maze);
                    benchmark.updateselectedTime();

                    benchmark.updateBaselineTime();
                    Path baselinePath = solveMaze(baselineMethod, maze);
                    benchmark.updateBaselineTime();

                    benchmark.getResults(baselinePath, selectedPath, mazeLoadTime);
                }

                else {
                    Path selectedPath = solveMaze(selectedMethod, maze);
                    System.out.println(selectedPath.getFactorizedForm());
                }
            }

        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }
        logger.info("End of MazeRunner");
    }

    /**
     * Solve provided maze with specified method by creating a solver visitor.
     *
     * @param method Method to solve maze with
     * @param maze   Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze) throws Exception {
        MazeSolver solver = null;

        Path result = null;

        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                solver = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux algorithm chosen.");
                solver = new TremauxSolver();
            }
            case "bfs" -> {
                logger.debug("Breadth First Search algorithm chosen.");
                solver = new BreadthFirstSearchSolver();
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        logger.info("Computing path");

        result = solver.accept(solverVisitor);
        return result;
    }

    /**
     * Get options for CLI parser.
     *
     * @return CLI parser options
     */
    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        options.addOption(new Option("baseline", true, "Specify the baseline method used  for benchmarking"));

        return options;
    }
}
