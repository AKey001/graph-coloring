package de.antonkiessling;

public class Solution {
    private int chromaticNumber;
    private int[] colors;

    private int millis;

    public Solution(int chromaticNumber, int[] colors, int millis) {
        this.chromaticNumber = chromaticNumber;
        this.colors = colors;
        this.millis = millis;
    }

    public int getChromaticNumber() {
        return chromaticNumber;
    }

    public int[] getColors() {
        return colors;
    }

    public int getMillis() {
        return millis;
    }
}
