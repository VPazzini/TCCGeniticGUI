
package main;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Random;

public class Generator {

    public void generate(int motifSize, int numSeqs, int seqSize, boolean noise, boolean lowConservation) {

        for (int k = 0; k < 1; k++) {
            Random r = new Random();
            ArrayList<String> seqs = new ArrayList<>();

            for (int i = 0; i < numSeqs; i++) {
                String s = "";
                for (int j = 0; j < seqSize - motifSize; j++) {
                    switch (r.nextInt(4)) {
                        case (0):
                            s += 'A';
                            break;
                        case (1):
                            s += 'C';
                            break;
                        case (2):
                            s += 'T';
                            break;
                        case (3):
                            s += 'G';
                            break;
                    }
                }
                seqs.add(s);
            }

            double t = 0.92;
            if (lowConservation) {
                t = 0.7;
            }
            int numMotifs = numSeqs;
            if (noise) {
                numMotifs = (int) (numSeqs * 0.9);
            }

            ArrayList<String> listMotifs = generateMotifs(numMotifs, motifSize, t);

            try {
                File newFile = new File("benchmark.fasta");
            File motifFile = new File("motif.fasta");

                FileWriter fw = new FileWriter(newFile.getAbsoluteFile());
                BufferedWriter out = new BufferedWriter(fw);
                FileWriter fw2 = new FileWriter(motifFile.getAbsoluteFile());
                BufferedWriter out2 = new BufferedWriter(fw2);

                int seqNum = 0;
                for (String s : seqs) {

                    int n = r.nextInt(seqSize - motifSize + 1);
                    if (noise) {
                        if (seqNum < (seqs.size() * 0.9)) {
                            out2.write("Sequence" + seqNum + "\t");
                            out2.write(listMotifs.get(seqNum) + "\t" + n + "\n");
                            out.write(">Sequence" + seqNum + "\n");
                            out.write(s.substring(0, n) + listMotifs.get(seqNum) + s.substring(n) + "\n");
                            seqNum++;
                        } else {
                            out.write(">Sequence" + seqNum + "\n");
                            out.write(s + "\n");
                            seqNum++;
                        }
                    } else {

                        out2.write("Sequence" + seqNum + "\t");
                        out2.write(listMotifs.get(seqNum) + "\t" + n + "\n");
                        out.write(">Sequence" + seqNum + "\n");
                        out.write(s.substring(0, n) + listMotifs.get(seqNum) + s.substring(n) + "\n");
                        seqNum++;
                    }

                }
                out.close();
                out2.close();
            } catch (Exception e) {

            }
        }
    }

    public ArrayList<String> generateMotifs(int numMotifs, int motifSize, double threshold) {
        Random r = new Random();
        char[] motif = new char[motifSize];
        for (int i = 0; i < motifSize; i++) {
            switch (r.nextInt(4)) {
                case (0):
                    motif[i] = 'A';
                    break;
                case (1):
                    motif[i] = 'C';
                    break;
                case (2):
                    motif[i] = 'T';
                    break;
                case (3):
                    motif[i] = 'G';
                    break;
            }
        }

        ArrayList<char[]> motifs = new ArrayList<>();
        for (int i = 0; i < numMotifs; i++) {
            motifs.add(motif.clone());
        }

        do {
            float[][] m = matrix(motifs);

            for (int j = 0; j < motif.length; j++) {
                if (getMostFreq(m, j) > threshold) {
                    motifs.get(r.nextInt(motifs.size()))[j] = selectLeastFreqNuc(m, j);
                }
            }

            boolean f = true;
            for (int j = 0; j < m[0].length; j++) {
                double biggest = 0;
                for (int i = 0; i < 4; i++) {
                    if (m[i][j] > biggest) {
                        biggest = m[i][j];
                    }
                }
                if (biggest > threshold) {
                    f = false;
                    break;
                }
            }
            if (f) {
                break;
            }
        } while (true);

        ArrayList<String> list = new ArrayList<>();
        for (char[] chars : motifs) {
            String s = "";
            for (char c : chars) {
                s += c;
            }
            list.add(s);
        }
        return list;
    }

    public char selectRandomNuc() {
        Random r = new Random();
        char c = 0;
        switch (r.nextInt(4)) {
            case (0):
                c = 'A';
                break;
            case (1):
                c = 'C';
                break;
            case (2):
                c = 'T';
                break;
            case (3):
                c = 'G';
                break;
        }
        return c;
    }

    public float getMostFreq(float[][] m, int j) {
        float most = 0;
        for (int i = 0; i < 4; i++) {
            if (m[i][j] > most) {
                most = m[i][j];
            }
        }
        return most;
    }

    public char selectLeastFreqNuc(float[][] m, int j) {
        Random r = new Random();

        double least = 1.1;
        int nuc = 0;
        for (int i = 0; i < 4; i++) {
            if (m[i][j] < least) {
                least = m[i][j];
                nuc = i;
            }
        }

        char c = 0;
        switch (nuc) {
            case (0):
                c = 'A';
                break;
            case (1):
                c = 'C';
                break;
            case (2):
                c = 'T';
                break;
            case (3):
                c = 'G';
                break;
        }
        return c;
    }

    public void printMatrix(float[][] m) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < m[0].length; j++) {

                System.out.print(m[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public float[][] matrix(ArrayList<char[]> seqs) {
        float[][] matrix = new float[4][seqs.get(0).length];
        for (int i = 0; i < seqs.get(0).length; i++) {
            for (int j = 0; j < seqs.size(); j++) {
                switch (seqs.get(j)[i]) {
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
        for (int i = 0; i < seqs.get(0).length; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[j][i] /= seqs.size();
            }
        }
        return matrix;
    }

    public float[][] matrix2(ArrayList<String> seqs) {
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

}
