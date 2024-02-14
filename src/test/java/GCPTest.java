import de.antonkiessling.GCP;
import de.antonkiessling.Parser;
import de.antonkiessling.Solution;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GCPTest {

    @Test
    void isFeasible() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 2, 2, 3, 1, 3, 1, 1, 3};
        assertTrue(gcp.isFeasible(colors));
    }

    @Test
    void isFeasible_false() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 2, 2, 3, 1, 2, 1, 1, 3};
        assertFalse(gcp.isFeasible(colors));
    }
    @Test
    void isFeasible_true_someWithoutColor() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {1, 0, 2, 0, 3, 1, 3, 0, 0, 0};
        assertTrue(gcp.isFeasible(colors));
    }

    @Test
    void isFeasible_false_someWithoutColor() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {0, 2, 0, 2, 3, 1, 2, 0, 0, 0};
        assertFalse(gcp.isFeasible(colors));
    }

    @Test
    void isFeasible_queen5_5() throws IOException {
        Parser parser = new Parser();
        int[][] graph = parser.parse(new File("instances/queen5_5.col"));

        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 3, 4, 5, 3,  4,  1,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
        assertTrue(gcp.isFeasible(colors));
    }

    @Test
    void isFeasible_queen5_5_differentSuccessor() throws IOException {
        Parser parser = new Parser();
        int[][] graph = parser.parse(new File("instances/queen5_5.col"));

        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 3, 0, 0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
        assertTrue(gcp.isFeasible(colors));
    }

    @Test
    void isSolution() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 2, 2, 3, 1, 3, 1, 1, 3};
        assertTrue(gcp.isSolution(colors));
    }

    @Test
    void isSolution_false() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 2, 2, 3, 1, 2, 1, 1, 3};
        assertFalse(gcp.isSolution(colors));
    }
    @Test
    void isSolution_true_someWithoutColor() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {1, 0, 2, 0, 3, 1, 3, 0, 0, 0};
        assertFalse(gcp.isSolution(colors));
    }

    @Test
    void isSolution_false_someWithoutColor() {
        int[][] graph = {
                {0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0},
                {1, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 0, 0},
                {0, 0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0, 1},
                {0, 1, 0, 0, 0, 0, 1, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 0, 1, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {0, 2, 0, 2, 3, 1, 2, 0, 0, 0};
        assertFalse(gcp.isSolution(colors));
    }

    @Test
    void isSolution_queen5_5() throws IOException {
        Parser parser = new Parser();
        int[][] graph = parser.parse(new File("instances/queen5_5.col"));

        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 1, 0, 0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
        assertFalse(gcp.isSolution(colors));
    }

    @Test
    void isSolution_queen5_5_differentSuccessor() throws IOException {
        Parser parser = new Parser();
        int[][] graph = parser.parse(new File("instances/queen5_5.col"));

        GCP gcp = new GCP(graph);

        int[] colors = {1, 2, 3, 0, 0, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
        assertFalse(gcp.isSolution(colors));
    }

    @Test
    void solve() throws IOException {
        Parser parser = new Parser();
        int[][] graph = parser.parse(new File("instances/queen5_5.col"));

        GCP gcp = new GCP(graph);

        Solution solution = gcp.solve(IntStream.range(0, graph.length).toArray());

        assertEquals(5, solution.getChromaticNumber());
    }


}