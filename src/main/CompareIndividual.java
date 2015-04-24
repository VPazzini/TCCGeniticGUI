package main;

import java.util.Comparator;

public class CompareIndividual implements Comparator<Individual> {

    /*@Override
     public int compare(Individual arg0, Individual arg1) {
     if(arg0.getFitness()+arg0.getPresence() > arg1.getFitness()+arg1.getPresence()){
     return -1;
     }
     if(arg0.getFitness()+arg0.getPresence() < arg1.getFitness()+arg1.getPresence()){
     return 1;
     }
     return 0;
     }*/
    /*@Override
     public int compare(Individual arg0, Individual arg1) {
     if(arg0.getPresence() > arg1.getPresence()){
     return -1;
     }
     if(arg0.getPresence() < arg1.getPresence()){
     return 1;
     }
     return 0;
     }*/
    @Override
    public int compare(Individual arg0, Individual arg1) {
        float fitArg0, fitArg1;
        fitArg0 = arg0.getFitness();
        fitArg1 = arg1.getFitness();
        if (fitArg0 > fitArg1) {
            return -1;
        }
        if (fitArg0 < fitArg1) {
            return 1;
        }
        return 0;
    }

}
