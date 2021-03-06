package main;

import java.util.ArrayList;

public class Util {

    public static String[] nucleotides = {"A", "C", "T", "G"};

    public static String reverse(String sequence) {
        String rev = "";
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (sequence.charAt(i) == 'A') {
                rev += 'T';
            }
            if (sequence.charAt(i) == 'C') {
                rev += 'G';
            }
            if (sequence.charAt(i) == 'T') {
                rev += 'A';
            }
            if (sequence.charAt(i) == 'G') {
                rev += 'C';
            }
        }
        return rev;
    }

    public static float[][] matrix(ArrayList<String> seqs) {
        float[][] matrix = new float[4][seqs.get(0).length()];
        for (int i = 0; i < seqs.get(0).length(); i++) {
            for (int j = 0; j < seqs.size(); j++) {
                switch (seqs.get(j).charAt(i)) {
                    case ('A'):
                        matrix[0][i]++;
                        break;
                    case ('C'):
                        matrix[1][i]++;
                        break;
                    case ('T'):
                        matrix[2][i]++;
                        break;
                    case ('G'):
                        matrix[3][i]++;
                        break;
                }
            }
        }
        for (int i = 0; i < seqs.get(0).length(); i++) {
            for (int j = 0; j < 4; j++) {
                matrix[j][i] /= seqs.size();
            }
        }
        return matrix;
    }

    public static String consensus(ArrayList<String> seqs) {
        float[][] m = Util.matrix(seqs);
        if (seqs.isEmpty()) {
            return null;
        }
        String cons = "";
        float max = 0;
        int pos = 0;
        for (int i = 0; i < seqs.get(0).length(); i++) {
            max = 0;
            for (int j = 0; j < 4; j++) {
                if (m[j][i] > max) {
                    max = m[j][i];
                    pos = j;
                }
            }
            cons += nucleotides[pos];
        }

        return cons;
    }

    public static float similarity(String motif, String seq) {
        if (motif.length() != seq.length()) {
            return 0;
        }
        float match = 0;
        for (int i = 0; i < motif.length(); i++) {
            if (motif.charAt(i) == seq.charAt(i)) {
                match++;
            }
        }
        return match / motif.length();
    }
    
    public static double similarity(Individual ind, String seq) {
        if (ind.getSequence().length() != seq.length()) {
            return 0;
        }
        double match = 0;
        double[][] m = ind.matrix();
        for (int i = 0; i < seq.length(); i++) {
            switch (seq.charAt(i)) {
                case ('A'):
                    match += m[0][i];
                    break;
                case ('C'):
                    match += m[1][i];
                    break;
                case ('T'):
                    match += m[2][i];
                    break;
                case ('G'):
                    match += m[3][i];
                    break;
            }
        }
        return match / seq.length();
    }
    
}
