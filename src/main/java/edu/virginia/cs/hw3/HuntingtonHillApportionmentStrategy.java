package edu.virginia.cs.hw3;

import java.util.*;

public class HuntingtonHillApportionmentStrategy extends ApportionmentStrategy {
    private List<State> stateList;
    Hashtable<State, Float> priorityList = new Hashtable<>();
    private int targetRepresentatives;
    private Apportionment apportionment;
    private float priority;

    public Apportionment getApportionment() {
        Apportionment apportionment = new Apportionment();
        return apportionment;
    }

    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        apportionment = getApportionment();
        executeFirstPassApportionment(stateList);
        executeSecondPassApportionment(stateList);
        return apportionment;
    }

    private void executeSecondPassApportionment(List<State> stateList) {
        while (getRepsLeftToAllocate() > 0) {
            for (State state : stateList) {
                if (Collections.max(priorityList.values()).equals(priorityList.get(state))) {
                    apportionment.addRepresentativesToState(state, 1);
                    priorityList.put(state, getPriority(state));
                    break;
                }
            }
        }
    }

    private void executeFirstPassApportionment(List<State> stateList) {
        for (State state : stateList) {
            this.apportionment.addRepresentativesToState(state, 1);
            priorityList.put(state, getPriority(state));
        }
    }

    private void initializeFields(List<State> stateList, int representatives) {
        this.stateList = stateList;
        targetRepresentatives = representatives;
    }

    private float getPriority(State state) {
        priority = (float) (state.getPopulation() / (Math.sqrt(apportionment.getRepresentativesForState(state))*(apportionment.getRepresentativesForState(state)+1)));
        return priority;
    }
    private int getRepsLeftToAllocate() {
        int allocatedRepresentatives = apportionment.getAllocatedRepresentatives();
        return targetRepresentatives - allocatedRepresentatives;
    }
}
