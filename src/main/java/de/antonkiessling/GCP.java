package de.antonkiessling;

import de.antonkiessling.model.Graph;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class GCP {

    int n; // Anzahl Knoten
    int[] colors; // Farben: 1-n ; 0 = noch keine Farbe
    private int chromaticNumber;
    int[][] graph;
//    {{0, 1, 0, 0, 1, 1},      Knoten A
//    {1, 0, 1, 1, 0, 1},       Knoten B
//    {0, 1, 0, 1, 0, 0},       Knoten C
//    {0, 1, 1, 0, 0, 1},       Knoten D
//    {1, 0, 0, 0, 0, 0},       Knoten E
//    {1, 1, 0, 1, 0, 0}};      Knoten F

//    {Kante zu A, zu B, zu C, ...}

    private int[] best;

    int[] variablenHeuristik;

    public GCP(int[][] graph) {
        this.n = graph.length;
        this.graph = graph;
        this.colors = new int[n];
        this.best = IntStream.range(1, n + 1).toArray();
        this.chromaticNumber = n;
    }

    public void branchAndBound(int[] colors, int posIndex) {
        if (isSolution(colors)) {
            if (inBound(colors)) {
                best = ArrayUtils.clone(colors);
                chromaticNumber = (int) Arrays.stream(best).distinct().count();

                System.out.println("better solution: " +
                        chromaticNumber + "   =>   " + Arrays.toString(best)
                );
            }
        }
        if (posIndex == n) {
            return;
        }

        int pos = variablenHeuristik[posIndex];

        List<Integer> succ = succ(colors, pos);
        for (int color : succ) {
            if (inBound(colors)) {
                colors[pos] = color;
                branchAndBound(colors, posIndex + 1);
            }
        }
        colors[pos] = 0;
    }

    public List<Integer> succ(int[] colors, int pos) {
        List<Integer> x = new ArrayList<>();
        if (pos == n) return x;

        int max = Arrays.stream(colors).max().getAsInt() + 1;

        for (int i = 1; i < max + 1; i++) {
            colors[pos] = i;

            if (isFeasible(colors)) {
                x.add(i);
            }
        }

        colors[pos] = 0;
        return x;
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

//        for (int i = 0; i < colors.length; i++) {
//            if (colors[i] != 0) {
//
//                int[] neighbors = graph[i];
//                for (int j = i + 1; j < neighbors.length; j++) {
//
//                    if (neighbors[j] == 1) {
//                        if (colors[i] == colors[j]) {
//                            return false;
//                        }
//                    }
//                }
//
//            }
//        }

//        for (int knotenIndex = 0; knotenIndex < n; knotenIndex++) {
//            int color = colors[knotenIndex];
//            if (color != 0) {
//                for (int neighborsIndex = 0; neighborsIndex < n; neighborsIndex++) {
//                    int isNeighbor = graph[knotenIndex][neighborsIndex];
//
//                    if (isNeighbor == 1) {
//                        if (colors[neighborsIndex] == color) {
//                            return false;
//                        }
//                    }
//                }
//            }
//        }

        for (int knotenIndex = 0; knotenIndex < n; knotenIndex++) {
            int color = colors[knotenIndex];
            if (color != 0) {
                for (int neighborsIndex = knotenIndex + 1; neighborsIndex < n; neighborsIndex++) {
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

    private boolean isSolution(int[] colors) {
        if (ArrayUtils.contains(colors, 0)) {
            return false;
        }

        return isFeasible(colors);
    }


    public void solve(int[] variablenHeuristik) {

        branchAndBound(new int[graph.length], 0);
        System.out.println(chromaticNumber + "  =>  " + Arrays.toString(best));
    }

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        Graph graph1 = parser.parse(new File("queen5_5.col"));

        GCP gcp = new GCP(graph1.getGraph());
        gcp.solve(IntStream.range(0, graph1.getVertices()).toArray());


    }

}
