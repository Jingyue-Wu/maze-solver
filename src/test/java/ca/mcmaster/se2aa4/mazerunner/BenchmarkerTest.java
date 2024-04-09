package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.concurrent.TimeUnit;

class BenchmarkerTest {
    Benchmarker benchmark = new Benchmarker();

    @Test
    void testUpdateBaselineTime() throws Exception {
        try {
            benchmark.updateBaselineTime();
            TimeUnit.MILLISECONDS.sleep(100);
            benchmark.updateBaselineTime();
        } catch (Exception e) {
            throw new IllegalStateException("Incorrect number of updateBaselineTime updates");
        }

        try {
            benchmark.updateBaselineTime();
        } catch (Exception e) {
            String expected = "Baseline explore time has already been recorded";
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    void testUpdateselectedTime() {
        try {
            benchmark.updateselectedTime();
            TimeUnit.MILLISECONDS.sleep(100);
            benchmark.updateselectedTime();
        } catch (Exception e) {
            throw new IllegalStateException("Incorrect number of updateSelectedTime updates");
        }

        try {
            benchmark.updateselectedTime();
        } catch (Exception e) {
            String expected = "Selected method explore time has already been recorded";
            assertEquals(expected, e.getMessage());
        }
    }
}
