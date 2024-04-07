package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
            Maze maze = new Maze(filePath);

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

                String method = cmd.getOptionValue("method", "righthand");
                Path path = solveMaze(method, maze);

                if (cmd.getOptionValue("baseline") != null) {
                    String baselineMethod = cmd.getOptionValue("baseline");
                    Path baselinePath = solveMaze(baselineMethod, maze);

                    float speedup = calculateSpeedup(baselinePath, path);
                    System.out.printf("Speedup: %.2f \n", speedup);
                }

                else {
                    System.out.println(path.getFactorizedForm());
                }
            }

        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }

    private static float calculateSpeedup(Path baseline, Path method) {
        String baseLineString = baseline.getCanonicalForm();
        String methodString = method.getCanonicalForm();

        float speedup = (float) baseLineString.length() / methodString.length();
        return speedup;
    }

    /**
     * Solve provided maze with specified method.
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
