package de.antonkiessling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void parse() throws IOException {
        int[][] expectedGraph = {
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

        int[][] graph = parser.parse(new File("src/test/java/test.col"));

        assertArrayEquals(expectedGraph, graph);
    }
}