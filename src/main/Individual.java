package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Individual {

    private float fitness = 1;

    private String sequence;
    private String revSequence;
    private int presence = 0;

    private boolean changed = true;
    private boolean fitnessReady = false;
    private float[][] pwmMatrix;

    private HashMap<Sequence, ArrayList<Integer>> matches = new HashMap<>();
    String[] nucleotides = {"A", "C", "T", "G"};

    public Individual(float fitness, String sequence) {
        this.fitness = fitness;
        this.sequence = sequence;
    }

    public Individual(String sequence) {
        this.sequence = sequence;
        revSequence = "";
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (sequence.charAt(i) == 'A') {
                revSequence += 'T';
            }
            if (sequence.charAt(i) == 'C') {
                revSequence += 'G';
            }
            if (sequence.charAt(i) == 'T') {
                revSequence += 'A';
            }
            if (sequence.charAt(i) == 'G') {
                revSequence += 'C';
            }
        }
    }

    public float getFitness() {
        if (!fitnessReady) {
            fitness = calculateFitness();
        }
        return this.fitness;
    }

    public boolean isFitnessReady() {
        return fitnessReady;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public String getSequence() {
        return sequence;
    }

    public String getRevSequence() {
        return revSequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public int getPresence() {
        int p = 0;
        for (ArrayList<Integer> a : matches.values()) {
            p += a.size();
        }
        return p;
    }

    public void setPresence(int presence) {
        this.presence = presence;
    }

    public HashMap<Sequence, ArrayList<Integer>> getMatches() {
        return matches;
    }

    public void addMatch(Sequence s, int init) {
        if (matches.containsKey(s)) {
            int motifLength = sequence.length();
            boolean b = false;
            for (Integer i : matches.get(s)) {
                if (init > i && init < i + motifLength) {
                    b = true;
                }
                if (init + motifLength > i && init + motifLength < i + motifLength) {
                    b = true;
                }
                if (b) {
                    if (pwm(s.getSubSequence(i, motifLength)) > pwm(s.getSubSequence(init, motifLength))) {
                        return;
                    } else {
                        matches.get(s).remove(i);
                        break;
                    }
                }
            }

            matches.get(s).add(init);
        } else {
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(init);
            matches.put(s, temp);
        }
        //matches.put(s, init);
        this.changed = true;
        this.fitnessReady = false;
    }

    public void removeMatch(Sequence s, int init) {
        matches.remove(s, init);
        this.changed = true;
    }

    public float pwm(String subSeq) {
        double[] count = {0.0, 0.0, 0.0, 0.0};
        if (matches.isEmpty()) {
            //return 0;
            count[0] = (float) 0.25;
            count[1] = (float) 0.25;
            count[2] = (float) 0.25;
            count[3] = (float) 0.25;
        }

        int maxSize = 0;
        for (Sequence s : this.matches.keySet()) {
            for (int i = 0; i < 4; i++) {
                count[i] += s.getFreq()[i];
            }
            maxSize += s.lenght();
        }

        for (Sequence s : this.matches.keySet()) {
            for (Integer init : matches.get(s)) {
                for (char c : s.getSubSequence(init, this.sequence.length()).toCharArray()) {
                    switch (c) {
                        case ('A'):
                            count[0]--;
                            break;
                        case ('C'):
                            count[1]--;
                            break;
                        case ('T'):
                            count[2]--;
                            break;
                        case ('G'):
                            count[3]--;
                            break;
                    }
                    maxSize--;
                }
            }
        }

        double[][] m = matrix();
        if (!matches.isEmpty()) {

            for (char c : subSeq.toCharArray()) {
                switch (c) {
                    case ('A'):
                        count[0]--;
                        break;
                    case ('C'):
                        count[1]--;
                        break;
                    case ('T'):
                        count[2]--;
                        break;
                    case ('G'):
                        count[3]--;
                        break;
                }
                maxSize--;
            }
            for (int i = 0; i < 4; i++) {
                count[i] /= (maxSize);
                /*if(count[i] == 0){
                 count[i] = 1;
                 }*/
            }
        }
        /*for (int i = 0; i < sequence.length(); i++) {
         for (int j = 0; j < 4; j++) {
         m[j][i] /= count[j];
         }
         }*/

        float score = 0;
        int index = 0;
        int nuc = 0;
        for (char c : subSeq.toCharArray()) {
            switch (c) {
                case ('A'):
                    nuc = 0;
                    break;
                case ('C'):
                    nuc = 1;
                    break;
                case ('T'):
                    nuc = 2;
                    break;
                case ('G'):
                    nuc = 3;
                    break;
            }
            score += m[nuc][index] * (Math.log(m[nuc][index] / count[nuc]));
            /*for (int j = 0; j < 4; j++) {
             score += m[j][index] * (Math.log(m[j][index] / count[j]));
             }*/
            index++;
        }

        // if (score > 0)
        // System.out.println(seq.getName() + " " + init + " "+
        // seq.getSubSequence(init, sequence.length()) + " "+ score);
        return score;

    }

    public double[][] matrix() {
        if (changed) {
            pwmMatrix = new float[4][sequence.length()];

            for (int i = 0; i < sequence.length(); i++) {
                for (int j = 0; j < 4; j++) {
                    pwmMatrix[j][i] = (float) 0.001;
                }
            }

            for (int i = 0; i < sequence.length(); i++) {
                // for (char c : sequence.toCharArray()) {
                switch (sequence.charAt(i)) {
                    case ('A'):
                        pwmMatrix[0][i]++;
                        break;
                    case ('C'):
                        pwmMatrix[1][i]++;
                        break;
                    case ('T'):
                        pwmMatrix[2][i]++;
                        break;
                    case ('G'):
                        pwmMatrix[3][i]++;
                        break;
                }
                // }
            }

            for (int i = 0; i < sequence.length(); i++) {
                for (Sequence key : matches.keySet()) {

                    //int init = matches.get(key);
                    for (Integer init : matches.get(key)) {
                        switch (key.getSubSequence(init, sequence.length()).charAt(
                                i)) {
                            case ('A'):
                                pwmMatrix[0][i]++;
                                break;
                            case ('C'):
                                pwmMatrix[1][i]++;
                                break;
                            case ('T'):
                                pwmMatrix[2][i]++;
                                break;
                            case ('G'):
                                pwmMatrix[3][i]++;
                                break;
                        }
                    }
                }
            }
            int size = 0;
            for (ArrayList<Integer> t : matches.values()) {
                size += t.size();
            }

            for (int i = 0; i < sequence.length(); i++) {
                for (int j = 0; j < 4; j++) {
                    //pwmMatrix[j][i] /= (matches.size() + 1.004);
                    pwmMatrix[j][i] /= (size + 1.004);
                }
            }
            changed = false;
        }
        double[][] temp = new double[4][sequence.length()];
        for (int i = 0; i < sequence.length(); i++) {
            for (int j = 0; j < 4; j++) {
                temp[j][i] = pwmMatrix[j][i];
            }
        }
        return temp;
    }

    public String consensus() {
        double[][] m = matrix();
        if (matches.isEmpty()) {
            return sequence;
        }
        String cons = "";
        double max;
        int pos = 0;
        for (int i = 0; i < sequence.length(); i++) {
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

    @Override
    public String toString() {
        return "[fitness=" + getFitness() + ", seq="
                + consensus() + ",rev=" + Util.reverse(consensus()) + ", ocor=" + getPresence() + "]";
        //+ sequence + ",rev=" + Util.reverse(consensus()) + ", ocor=" + presence + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(fitness);
        result = prime * result
                + ((sequence == null) ? 0 : sequence.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Individual other = (Individual) obj;
        if (sequence == null) {
            if (other.sequence != null) {
                return false;
            }
            // } else if (!sequence.equals(other.sequence))
        } else if (!consensus().equals(other.consensus())) {
            return false;
        }
        return true;
    }

    public String worstMatch() {
        String s = "";
        double f;
        int index = 0;
        double[][] m = matrix();
        for (int i = 0; i < sequence.length(); i++) {
            f = 2;
            for (int j = 0; j < 4; j++) {
                if (m[j][i] < f) {
                    f = m[j][i];
                    index = j;
                }
            }

            switch (index) {
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
        return s;
    }

    public String worstMatch(int num) {
        String s = "";
        double f;
        double n = num;
        int indexWorst = 0;
        int secondWorst = 0;
        int index;
        double[][] m = matrix();
        for (int i = 0; i < sequence.length(); i++) {
            f = 2;
            for (int j = 0; j < 4; j++) {
                if (m[j][i] < f) {
                    f = m[j][i];
                    indexWorst = j;
                }
            }
            f = 2;
            for (int j = 0; j < 4; j++) {
                if (j != indexWorst) {
                    if (m[j][i] < f) {
                        f = m[j][i];
                        secondWorst = j;
                    }
                }
            }

            Random r = new Random();
            index = indexWorst;
            float rand = r.nextFloat();
            double dist = (n / (n + 1));
            if (rand > dist) {
                index = secondWorst;
            }

            switch (index) {
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

        return s;
    }

    public float calculateFitness() {
        float temp = 0;
        if (matches.isEmpty()) {
            return 1;
        }
        //System.out.println("oi");
        temp = this.pwm(this.consensus());
        //temp = this.pwm(this.sequence);
        /*for (Sequence seq : matches.keySet()) {
         String m = seq.getSubSequence(matches.get(seq),
         this.sequence.length());

         temp += this.pwm(seq, m);

         }
         */

        /*String s = worstMatch(10);
         System.out.println(consensus() + " " + pwm(consensus())
         + " " + worstMatch() + " " + pwm(worstMatch())
         + " " + s + " " + pwm(s));*/
        /*double dec = 1, w = 0.2, re;
         double wm = pwm(worstMatch());
         for (int i = 1; i < matches.size() - 1; i++) {
         double swm = pwm(worstMatch(i));
         re = (wm - swm) / swm;
         dec *= (1 + (w * re));
         }*/
        //temp = ((float) temp - ((3 * this.sequence.length()) / (matches.size())));
        temp = ((float) temp - ((3 * Population.getInstance().getNumSequences()) / (matches.size())));
        //temp = (temp / matches.size()) - ((3 * this.sequence.length()) / (matches.size()));

        //System.out.println(dec);
        //temp *= dec;
        //System.out.println(temp);
        fitnessReady = true;
        if (temp < 1) {
            return 1;
        } else {
            return temp;
        }
    }

    public String individualOutput() {
        String s = "";
        s += ("Motif:\t" + sequence + "| Fitness: "
                + this.getFitness() + "\n");
        s += ("Consensus:\t" + this.consensus() + " | " + Util.reverse(this.consensus()) + "\n");
        s += (getPresence() + " Matches:\n");

        int seqN = 0;
        for (Sequence seq : matches.keySet()) {
            for (Integer init : matches.get(seq)) {
                //String m = seq.getSubSequence(matches.get(seq),this.sequence.length());
                String m = seq.getSubSequence(init, this.sequence.length());
                s += (seqN++ + ".\t");
                String con = this.consensus();
                for (int i = 0; i < m.length(); i++) {
                    // if (m.charAt(i) == this.sequence.charAt(i)) {

                    if (m.charAt(i) == con.charAt(i)) {
                        s += ((m.charAt(i) + "").toUpperCase());
                    } else {
                        s += ((m.charAt(i) + "").toLowerCase());
                    }
                }
                s += (" | "
                        // + Population.getInstance().find(this.sequence, m)
                        + this.pwm(m) + " | "
                        //+ Population.getInstance().similarity(con, m) * 100
                        + Util.similarity(this, m) * 100
                        + "% | " + seq.getName() + " (" + init
                        + ")\n");
            }
        }
        double[][] m = matrix();
        for (int j = 0; j < 4; j++) {
            s += (nucleotides[j] + " ");
            for (int i = 0; i < sequence.length(); i++) {
                s += (String.format("%.4f  ", m[j][i]));
            }
            s += ("\n");
        }
        s += ("--------------------------------------------------------\n");
        return s;
    }

    public void writeToFile(File f) {
        BufferedWriter output = null;
        try {
            File file;
            if (f == null) {
                file = new File("output.txt");
            } else {
                file = f;
            }
            output = new BufferedWriter(new FileWriter(file, true));
            // writer = new PrintWriter("output.txt", "UTF-8");

            output.write("Motif:\t" + sequence + "| Fitness: "
                    + this.getFitness() + "\n");
            output.write("Consensus:\t" + this.consensus() + " | " + Util.reverse(this.consensus()) + "\n");
            output.write(getPresence() + " Matches:\n");

            int seqN = 0;
            for (Sequence seq : matches.keySet()) {
                for (Integer init : matches.get(seq)) {
                    //String m = seq.getSubSequence(matches.get(seq),this.sequence.length());
                    String m = seq.getSubSequence(init, this.sequence.length());
                    output.write(seqN++ + ".\t");
                    String con = this.consensus();
                    for (int i = 0; i < m.length(); i++) {
                        // if (m.charAt(i) == this.sequence.charAt(i)) {

                        if (m.charAt(i) == con.charAt(i)) {
                            output.write((m.charAt(i) + "").toUpperCase());
                        } else {
                            output.write((m.charAt(i) + "").toLowerCase());
                        }
                    }
                    output.write(" | "
                            // + Population.getInstance().find(this.sequence, m)
                            + this.pwm(m) + " | "
                            //+ Population.getInstance().similarity(con, m) * 100
                            + Util.similarity(this, m) * 100
                            + "% | " + seq.getName() + " (" + init
                            + ")\n");
                }
            }
            double[][] m = matrix();
            for (int j = 0; j < 4; j++) {
                output.write(nucleotides[j] + " ");
                for (int i = 0; i < sequence.length(); i++) {
                    output.write(String.format("%.4f  ", m[j][i]));
                }
                output.write("\n");
            }
            output.write("--------------------------------------------------------\n");
            output.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
