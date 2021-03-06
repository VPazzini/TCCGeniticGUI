package main;

import java.util.Stack;

//Harry Hull
//Implements the following Smith-Waterman algorithm http://en.wikipedia.org/wiki/Smith_waterman
//	Affine Gap algorithm taken from:
//  http://en.wikipedia.org/wiki/Gap_penalty#Affine_gap_penalty
//	gap = o + (l-1)*e;
//	o:	gap opening penalty  (o < 0)
//	l:	length of the gap
//	e:	gap extension penalty (o < e < 0)
public class SWaterman {

    private final String one, two;
    private final int matrix[][];
    private int gap;
    private final int match;
    private final int o;
    private int l;
    private final int e;

    public SWaterman(String one, String two) {
        this.one = "-" + one.toLowerCase();
        this.two = "-" + two.toLowerCase();
        this.match = 2;

        // Define affine gap starting values
        o = -2;
        l = 0;
        e = -1;

        // initialize matrix to 0
        matrix = new int[one.length() + 1][two.length() + 1];
        for (int i = 0; i < one.length(); i++) {
            for (int j = 0; j < two.length(); j++) {
                matrix[i][j] = 0;
            }
        }

    }

    // returns the alignment score
    public int computeSmithWaterman() {
        for (int i = 0; i < one.length(); i++) {
            for (int j = 0; j < two.length(); j++) {
                gap = o + (l - 1) * e;
                if (i != 0 && j != 0) {
                    if (one.charAt(i) == two.charAt(j)) {
						// match
                        // reset l
                        l = 0;
                        matrix[i][j] = Math.max(0, Math.max(
                                matrix[i - 1][j - 1] + match, Math.max(
                                        matrix[i - 1][j] + gap,
                                        matrix[i][j - 1] + gap)));
                    } else {
                        // gap
                        l++;
                        matrix[i][j] = Math.max(0, Math.max(
                                matrix[i - 1][j - 1] + gap, Math.max(
                                        matrix[i - 1][j] + gap,
                                        matrix[i][j - 1] + gap)));
                    }
                }
            }
        }

        // find the highest value
        int longest = 0;
        int iL = 0, jL = 0;
        for (int i = 0; i < one.length(); i++) {
            for (int j = 0; j < two.length(); j++) {
                if (matrix[i][j] > longest) {
                    longest = matrix[i][j];
                    iL = i;
                    jL = j;
                }
            }
        }

        return longest;
    }

    public void printMatrix() {
        for (int i = 0; i < one.length(); i++) {
            if (i == 0) {
                for (int z = 0; z < two.length(); z++) {
                    if (z == 0) {
                        System.out.print("   ");
                    }
                    System.out.print(two.charAt(z) + "  ");

                    if (z == two.length() - 1) {
                        System.out.println();
                    }
                }
            }

            for (int j = 0; j < two.length(); j++) {
                if (j == 0) {
                    System.out.print(one.charAt(i) + "  ");
                }
                System.out.print(matrix[i][j] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
