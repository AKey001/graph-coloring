import de.antonkiessling.GCP;
import de.antonkiessling.Parser;
import de.antonkiessling.model.Graph;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GCPTest {

    @Test
    void isFeasible() {
        int[][] graph = {{0, 1, 0}, {1, 0, 1}, {0, 1, 0}};
        GCP gcp = new GCP(graph);

        int[] colors = {2, 1, 3};
        assertTrue(gcp.isFeasible(colors));

    }

    @Test
    void isFeasible_2() throws IOException {
        Parser parser = new Parser();
        Graph graph1 = parser.parse(new File("queen5_5.col"));

        System.out.println(Arrays.deepToString(graph1.getGraph()));

        GCP gcp = new GCP(graph1.getGraph());

        int[] colors = {1, 2, 1, 2, 1, 0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0};
        assertTrue(gcp.isFeasible(colors));
    }
}