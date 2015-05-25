package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ValidateResult {

    public void compareAll(ArrayList<Individual> list, File f) {
        BufferedWriter output = null;

        float[] max = {0, 0, 0};
        float[] r;
        for (Individual ind : list) {
            if (ind.getFitness() > 1) {
                r = compare(ind, f);
                if (r[2] > max[2]) {
                    max[0] = r[0];
                    max[1] = r[1];
                    max[2] = r[2];
                }
            } else {
                break;
            }
        }

        try {
            File file = new File("results.txt");
            output = new BufferedWriter(new FileWriter(file, true));
            output.write(max[0] + "\t" + max[1] + "\t" + max[2] + "\n");
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(ValidateResult.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public float[] compare(Individual ind, File f) {
        FileReader fw;
        float precision = 0;
        float recall = 0;
        float score = 0;
        try {
            fw = new FileReader(f.getAbsoluteFile());

            BufferedReader out = new BufferedReader(fw);
            String line;

            String seqName;
            int init;
            float correct = 0;

            float total = 0;
            while ((line = out.readLine()) != null) {
                seqName = line.split("\t")[0];
                init = Integer.parseInt(line.split("\t")[2]);

                for (Sequence s : ind.getMatches().keySet()) {
                    if (s.getName().equals(seqName.toUpperCase())) {
                        for (Integer i : ind.getMatches().get(s)) {
                            if (Math.abs(init - i) < ind.getSequence().length()) {
                                correct++;
                                break;
                            }
                        }
                    }
                }

                total++;
            }
            float guesses = 0;
            for (ArrayList<Integer> ai : ind.getMatches().values()) {
                guesses += ai.size();
            }

            if (correct != 0) {
                precision = (correct / guesses);
                recall = (correct / total);
                score = 2 * precision * (recall / (precision + recall));
            }

            /*if (precision < 0.5) {
             System.out.println(ind.individualOutput());
             }*/
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidateResult.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidateResult.class.getName()).log(Level.SEVERE, null, ex);
        }
        float[] r = {precision, recall, score};
        return r;

    }

    public float[] compareOne(Individual ind, File f) {
        FileReader fw;
        float precision = 0;
        float recall = 0;
        float score = 0;
        try {
            fw = new FileReader(f.getAbsoluteFile());

            BufferedReader out = new BufferedReader(fw);
            String line;

            String seqName;
            int init;
            float correct = 0;

            float total = 0;
            while ((line = out.readLine()) != null) {
                seqName = line.split("\t")[0];
                init = Integer.parseInt(line.split("\t")[2]);

                for (Sequence s : ind.getMatches().keySet()) {
                    if (s.getName().equals(seqName.toUpperCase())) {
                        for (Integer i : ind.getMatches().get(s)) {
                            if (Math.abs(init - i) < ind.getSequence().length()) {
                                correct++;
                                break;
                            }
                        }
                    }
                }

                total++;
            }
            float guesses = 0;
            for (ArrayList<Integer> ai : ind.getMatches().values()) {
                guesses += ai.size();
            }

            if (correct != 0) {
                precision = (correct / guesses);
                recall = (correct / total);
                score = 2 * precision * (recall / (precision + recall));
            }

            /*if (precision < 0.5) {
             System.out.println(ind.individualOutput());
             }*/
            try {
                File file = new File("results.txt");
                BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
                output.write(precision + "\t" + recall + "\t" + score + "\n");
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(ValidateResult.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ValidateResult.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ValidateResult.class.getName()).log(Level.SEVERE, null, ex);
        }
        float[] r = {precision, recall, score};
        return r;

    }
}
