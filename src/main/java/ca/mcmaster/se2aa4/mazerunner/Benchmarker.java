package ca.mcmaster.se2aa4.mazerunner;

public class Benchmarker {
    private long baselineTime = 0;
    private long selectedMethodTime = 0;
    private int baselineUpdate = 0;
    private int selectedUpdate = 0;

    public void getResults(Path baselinePath, Path selectedPath, long mazeLoadTime) {
        System.out.printf("Time to load maze from file: %d milliseconds \n", mazeLoadTime);
        System.out.printf("Explore time for selected method: %d milliseconds \n", selectedMethodTime);
        System.out.printf("Explore time for Baseline: %d milliseconds \n", baselineTime);
        System.out.printf("Speedup: %.2f times faster \n", calculateSpeedup(baselinePath, selectedPath));
    }

    public void updateBaselineTime() throws Exception {
        baselineTime = System.currentTimeMillis() - baselineTime;
        baselineUpdate++;

        if (baselineUpdate > 2) {
            throw new IllegalStateException("Baseline explore time has already been recorded");
        }
    }

    public void updateselectedTime() throws Exception {
        selectedMethodTime = System.currentTimeMillis() - selectedMethodTime;
        selectedUpdate++;

        if (selectedUpdate > 2) {
            throw new IllegalStateException("Selected method explore time has already been recorded");
        }
    }

    private double calculateSpeedup(Path baselinePath, Path selectedPath) {
        String baseLineString = baselinePath.getCanonicalForm();
        String methodString = selectedPath.getCanonicalForm();

        double speedup = (double) baseLineString.length() / methodString.length();
        return speedup;
    }
}
