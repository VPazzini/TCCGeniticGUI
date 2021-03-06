package main;

public class Sequence {

    private String sequence;
    private String name;
    private Integer lenght;
    private float[] count = {0, 0, 0, 0};

    public Sequence(String name, String seq) {
        this.setName(name);
        this.setSequence(seq);
        this.lenght = sequence.length();
        for (char c : seq.toCharArray()) {
            switch (c) {
                case ('A'):
                    count[0]++;
                    break;
                case ('C'):
                    count[1]++;
                    break;
                case ('T'):
                    count[2]++;
                    break;
                case ('G'):
                    count[3]++;
                    break;
            }
        }
    }
    
    Population pop = null;
    public double findInSequence(Individual ind, boolean verbose) {
        double match1 = 0, match2 = 0, temp = -9999, find1, find2;
        if(pop ==null){
            pop = Population.getInstance();
        }
        double threshold = pop.getThresholdComparison();
        String subSeq = "";
        //String motif = ind.getSequence();
        String motif = ind.consensus();
        //String motif = ind.getSequence();
        int initSeq = 0;
        for (int i = 0; i < this.sequence.length() - motif.length(); i++) {
            subSeq = this.sequence.substring(i, i + motif.length());

            //match1 = similarity(motif, subSeq);
            match1 = similarity(ind, subSeq);

            if (pop.isSearchOnReverse()) {
                //match2 = similarity(Util.reverse(motif), subSeq);
                match2 = similarity(ind, Util.reverse(subSeq));
            }
            //find1 = find(motif, subSeq);
            //find2 = find(ind.getRevSequence(), subSeq);

            find1 = -9999;
            find2 = -9999;

            if (match1 >= threshold) {
                //ind.addMatch(this, i + 1);
                find1 = ind.pwm(subSeq);
                //ind.removeMatch(this, i + 1);
            }
            if (match2 >= threshold) {
                //ind.addMatch(this, -(i + 1));
                find2 = ind.pwm(Util.reverse(subSeq));
                //ind.removeMatch(this, -(i + 1));
            }

            if (match1 >= threshold) {
                if (find1 > temp && find1 >= find2) {
                    temp = find1;
                    initSeq = i + 1;
                }
                if (!pop.isOneOccurence() && find1 >= find2 && find1 >= threshold * motif.length()) {
                    //ind.setPresence(ind.getPresence() + 1);
                    ind.addMatch(this, (i + 1));
                }
            }
            if (match2 >= threshold) {
                if (find2 > temp && find2 > find1) {
                    temp = find2;
                    initSeq = -(i + 1);
                }
                if (!pop.isOneOccurence() && find2 > find1 && find2 >= threshold * motif.length()) {
                    //ind.setPresence(ind.getPresence() + 1);
                    ind.addMatch(this, -(i + 1));
                }
            }
        }
        //System.out.println(temp);
        if (pop.isOneOccurence() && temp > -9999) {
            //if (temp > 0) {
            // ind.setPresence(ind.getPresence() + 1);
            ind.addMatch(this, initSeq);
        }
        return temp;
    }

    public float similarity(String motif, String seq) {
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

    public double similarity(Individual ind, String seq) {
        if (ind.getSequence().length() != seq.length()) {
            return 0;
        }
        float match = 0;
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

    public String getSubSequence(int init, int size) {
        int absInit = Math.abs(init) - 1;
        String seq = this.sequence.substring(absInit, absInit + size);
        return init >= 0 ? seq : Util.reverse(seq);
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int lenght() {
        return this.lenght;
    }

    public float[] getFreq() {
        return this.count.clone();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((lenght == null) ? 0 : lenght.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
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
        Sequence other = (Sequence) obj;
        if (lenght == null) {
            if (other.lenght != null) {
                return false;
            }
        } else if (!lenght.equals(other.lenght)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (sequence == null) {
            if (other.sequence != null) {
                return false;
            }
        } else if (!sequence.equals(other.sequence)) {
            return false;
        }
        return true;
    }

}
