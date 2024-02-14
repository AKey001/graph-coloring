package de.antonkiessling;

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

    public boolean isSolution(int[] colors) {
        if (ArrayUtils.contains(colors, 0)) {
            return false;
        }

        return isFeasible(colors);
    }


    public Solution solve(int[] variablenHeuristik) {
        this.variablenHeuristik = variablenHeuristik;
        branchAndBound(new int[graph.length], 0);
        System.out.println(chromaticNumber + "  =>  " + Arrays.toString(best));
        return new Solution(chromaticNumber, best, 0);
    }

    public static void main(String[] args) throws IOException {
        List<String> files = List.of("1-FullIns_3", "2-FullIns_3", "2-Insertions_3", "3-Insertions_3", "anna", "ash331GPIA", "david", "DSJC125.1", "DSJR500.1", "fpsol2.i.1", "fpsol2.i.2", "fpsol2.i.3", "games120", "homer", "huck", "inithx.i.1", "inithx.i.2", "inithx.i.3", "jean", "le450_25a", "le450_25b", "le450_5c", "le450_5d", "miles1000", "miles1500", "miles250", "miles500", "miles750", "mug88_1", "mug88_25", "mulsol.i.1", "mulsol.i.2", "mulsol.i.3", "mulsol.i.4", "mulsol.i.5", "myciel3", "myciel4", "myciel5", "qg.order30", "queen5_5", "queen6_6", "queen7_7", "queen8_12", "queen8_8", "queen9_9", "r1000.1", "r125.1", "r125.1c", "r125.5", "r250.1", "r250.1c", "school1", "will199GPIA", "zeroin.i.1", "zeroin.i.2", "zeroin.i.3");
        List<Integer> chromaticNumber = List.of(4, 5, 4, 4, 11, 4, 11, 5, 12, 65, 30, 30, 9, 13, 11, 54, 31, 31, 10, 25, 25, 5, 5, 42, 73, 8, 20, 31, 4, 4, 49, 31, 31, 31, 31, 4, 5, 6, 30, 5, 7, 7, 12, 9, 10, 20, 5, 46, 36, 8, 64, 14, 7, 49, 30, 30);

        for (int i = 0; i < files.size(); i++) {
            String file = files.get(i);

            System.out.println("Start solving: " + file);

            Parser parser = new Parser();
            int[][] graph = parser.parse(new File("instances/" + file + ".col"));

            GCP gcp = new GCP(graph);
            Solution solution = gcp.solve(IntStream.range(0, graph.length).toArray());

            System.out.println(solution.getChromaticNumber() == chromaticNumber.get(i));
        }



    }

}
