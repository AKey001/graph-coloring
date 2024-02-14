package de.antonkiessling;

import de.antonkiessling.model.Graph;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {

    public Graph parse(File file) throws IOException {
        Graph graph = new Graph();

        LineIterator iterator = FileUtils.lineIterator(file);
        String line = null;
        while (iterator.hasNext()) {
            line = iterator.nextLine();

            if (line.contains("p edge")) {
                String[] counts = line.split(" ");
                graph.setVertices(Integer.parseInt(counts[2]));
                graph.setEdges(Integer.parseInt(counts[3]));
                break;
            }
        }

        Map<Integer, int[]> verticesEdges = new HashMap<>();
        for (int i = 0; i < graph.getVertices(); i++) {
            verticesEdges.put(i, new int[graph.getVertices()]);
        }

        while (iterator.hasNext()) {
            line = iterator.nextLine();
            String[] edge = line.split(" ");

            int start = Integer.parseInt(edge[1]) - 1;
            int end = Integer.parseInt(edge[2]) - 1;

            verticesEdges.get(start)[end] = 1;
            verticesEdges.get(end)[start] = 1;
        }

        int[][] graphEdges = new int[graph.getVertices()][graph.getVertices()];
        for (int i = 0; i < graph.getVertices(); i++) {
            int[] neighbors = verticesEdges.get(i);
            graphEdges[i] = neighbors;
        }
        graph.setGraph(graphEdges);

        return graph;
    }

}


