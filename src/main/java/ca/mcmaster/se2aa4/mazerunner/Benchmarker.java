package ca.mcmaster.se2aa4.mazerunner;

public class Benchmarker {
    private long baselineTime = 0;
    private long selectedMethodTime = 0;
    private int baselineUpdate = 0;
    private int selectedUpdate = 0;

    /**
     * Prints the results of the benchmark.
     *
     * @param baselinePath Path obtained from the baseline method
     * @param selectedPath Path obtained from the selected method
     * @param mazeLoadTime Time taken to load the maze from .txt file
     * @return void
     */
    public void getResults(Path baselinePath, Path selectedPath, long mazeLoadTime) {
        System.out.printf("Time to load maze from file: %d milliseconds \n", mazeLoadTime);
        System.out.printf("Explore time for selected method: %d milliseconds \n", selectedMethodTime);
        System.out.printf("Explore time for Baseline: %d milliseconds \n", baselineTime);
        System.out.printf("Speedup: %.2f times faster \n", calculateSpeedup(baselinePath, selectedPath));
    }

    /**
     * Updates the time it takes to run the baseline method. Call twice to obtain
     * time difference.
     */
    public void updateBaselineTime() throws Exception {
        baselineTime = System.currentTimeMillis() - baselineTime;
        baselineUpdate++;

        if (baselineUpdate > 2) {
            throw new IllegalStateException("Baseline explore time has already been recorded");
        }
    }

    /**
     * Updates the time it takes to run the selected method. Call twice to obtain
     * time difference.
     */
    public void updateselectedTime() throws Exception {
        selectedMethodTime = System.currentTimeMillis() - selectedMethodTime;
        selectedUpdate++;

        if (selectedUpdate > 2) {
            throw new IllegalStateException("Selected method explore time has already been recorded");
        }
    }

    /**
     * Calculates the speedup in instructions between two paths.
     *
     * @param baselinePath Path obtained from the baseline method
     * @param selectedPath Path obtained from the selected method
     * @return Speedup of the new selected method compared to the baseline
     */
    private double calculateSpeedup(Path baselinePath, Path selectedPath) {
        String baseLineString = baselinePath.getCanonicalForm().replaceAll("\\s+", "");
        String methodString = selectedPath.getCanonicalForm().replaceAll("\\s+", "");

        double speedup = (double) baseLineString.length() / methodString.length();
        return speedup;
    }
}
