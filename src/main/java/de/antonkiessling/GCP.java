package de.antonkiessling;

import de.antonkiessling.model.Heuristic;
import de.antonkiessling.model.Solution;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;

public class GCP {
    private int[][] graph;
    private int[] best;
    private int chromaticNumber;
    private final Heuristic heuristic;


    public GCP(int[][] graph, Heuristic heuristic) {
        this.heuristic = heuristic;

        int n = graph.length;
        this.graph = graph;
        this.best = IntStream.range(1, n + 1).toArray();
        this.chromaticNumber = n;
    }

    public void branchAndBound(int[] colors, int posIndex) {
        if (isSolution(colors)) {
            if (inBound(colors)) {
                best = ArrayUtils.clone(colors);
                chromaticNumber = (int) Arrays.stream(best).distinct().count();
                System.out.println("temporary solution: " + chromaticNumber + "   =>   " + Arrays.toString(best));
            }
        }
        if (posIndex == graph.length) {
            return;
        }

        int pos = chooseVariable(colors, posIndex);

        int maxColor = Arrays.stream(colors).max().getAsInt() + 1;
        for (int color = 1; color < maxColor + 1; color++) {
            colors[pos] = color;
            if (isFeasible(colors) && inBound(colors)) {
                branchAndBound(colors, posIndex + 1);
            }
        }
        colors[pos] = 0;
    }

    private int chooseVariable(int[] colors, int posIndex) {
        if (heuristic == Heuristic.FEWEST_SUCCESSORS) {
            int bestCount = Integer.MAX_VALUE;
            int bestPos = 0;

            int maxColor = Arrays.stream(colors).max().getAsInt() + 1;
            for (int pos = 0; pos < colors.length; pos++) {
                if (colors[pos] == 0) {
                    int count = 0;
                    for (int color = 1; color < maxColor + 1; color++) {
                        colors[pos] = color;
                        if (isFeasible(colors) && inBound(colors)) {
                            count++;
                        }
                    }
                    colors[pos] = 0;


                    if (count < bestCount) {
                        bestCount = count;
                        bestPos = pos;
                    }

                    if (count == bestCount) {
                        long posNeighbors = Arrays.stream(graph[pos]).filter(value -> value == 1).count();
                        long bestPosNeighbors = Arrays.stream(graph[bestPos]).filter(value -> value == 1).count();

                        if (posNeighbors > bestPosNeighbors) {
                            bestCount = count;
                            bestPos = pos;
                        }

                    }

                }
            }

            return bestPos;
        } else {
            return posIndex;
        }

    }

    private boolean inBound(int[] colors) {
        long currentBest = Arrays
                .stream(colors)
                .distinct()
                .filter(value -> value != 0)
                .count();

        return currentBest < chromaticNumber;
    }

    public boolean isFeasible(int[] colors) {
        for (int knotenIndex = 0; knotenIndex < graph.length; knotenIndex++) {
            int color = colors[knotenIndex];
            if (color != 0) {
                for (int neighborsIndex = knotenIndex + 1; neighborsIndex < graph.length; neighborsIndex++) {
                    int isNeighbor = graph[knotenIndex][neighborsIndex];

                    if (isNeighbor == 1) {
                        if (colors[neighborsIndex] == color) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    public boolean isSolution(int[] colors) {
        if (ArrayUtils.contains(colors, 0)) {
            return false;
        }

        return isFeasible(colors);
    }


    public Solution solve() {
        long start =  System.currentTimeMillis();
        branchAndBound(new int[graph.length], 0);
        long end = System.currentTimeMillis();
        return new Solution(chromaticNumber, best, (int) (end - start));
    }

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        int[][] graph = parser.parse(new File("instances/" + "2-Insertions 3" + ".col"));

        // Ausführung mit naiver Heuristik
        GCP gcp = new GCP(graph, Heuristic.NAIVE);

        Solution solution = gcp.solve();

        System.out.println(solution.getChromaticNumber() + "  =>  " + Arrays.toString(solution.getColors()));
        System.out.println(solution.getMillis());


        // Ausführung mit der im Paper beschriebenen Heuristik
        GCP gcp2 = new GCP(graph, Heuristic.FEWEST_SUCCESSORS);

        Solution solution2 = gcp2.solve();

        System.out.println(solution2.getChromaticNumber() + "  =>  " + Arrays.toString(solution2.getColors()));
        System.out.println(solution2.getMillis());
    }

}
