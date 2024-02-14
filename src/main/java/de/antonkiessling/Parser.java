package de.antonkiessling;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {

    public int[][] parse(File file) throws IOException {
        int vertices = 0;

        LineIterator iterator = FileUtils.lineIterator(file);
        String line;
        while (iterator.hasNext()) {
            line = iterator.nextLine();

            if (line.contains("p edge")) {
                String[] counts = line.split(" ");
                vertices = Integer.parseInt(counts[2]);
                break;
            }
        }

        Map<Integer, int[]> verticesEdges = new HashMap<>();
        for (int i = 0; i < vertices; i++) {
            verticesEdges.put(i, new int[vertices]);
        }

        while (iterator.hasNext()) {
            line = iterator.nextLine();
            String[] edge = line.split(" ");

            int start = Integer.parseInt(edge[1]) - 1;
            int end = Integer.parseInt(edge[2]) - 1;

            verticesEdges.get(start)[end] = 1;
            verticesEdges.get(end)[start] = 1;
        }

        int[][] graph = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            int[] neighbors = verticesEdges.get(i);
            graph[i] = neighbors;
        }

        return graph;
    }

}


