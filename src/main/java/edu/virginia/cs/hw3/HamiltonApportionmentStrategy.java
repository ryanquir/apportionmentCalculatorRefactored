package edu.virginia.cs.hw3;

import java.util.*;

public class HamiltonApportionmentStrategy extends ApportionmentStrategy{

    private List<State> stateList;
    private int targetRepresentatives;
    private double divisor;
    private DecimalApportionment decimalApportionment;
    private Apportionment apportionment;

    public Apportionment getApportionment() {
        Apportionment apportionment = new Apportionment();
        return apportionment;
    }
    @Override
    public Apportionment getApportionment(List<State> stateList, int representatives) {
        initializeFields(stateList, representatives);
        apportionment = getApportionment();
        int repsLeft = getRepsLeftToAllocate();
        if (repsLeft < stateList.size()) {
            throw new InsufficientRepAmount(repsLeft,stateList.size());
        }
        divisor = getDivisor();
        decimalApportionment = getDecimalApportionment();
        return getIntegerApportionment();
    }

    private Apportionment getIntegerApportionment() {
        apportionment = getFirstPassApportionment();
        executeSecondPassApportionment();
        return apportionment;
    }

    private void initializeFields(List<State> stateList, int representatives) {
        this.stateList = stateList;
        targetRepresentatives = representatives;
    }

    private double getDivisor() {
        int totalPopulation = getTotalPopulation();
        return (double) totalPopulation / targetRepresentatives;
    }

    private DecimalApportionment getDecimalApportionment() {
        DecimalApportionment decimalApportionment = new DecimalApportionment();
        for (State state : stateList) {
            double decimalRepresentatives = state.getPopulation() / divisor;
            decimalApportionment.setDecimalApportionmentForState(state, decimalRepresentatives);
        }
        return decimalApportionment;
    }

    private Apportionment getFirstPassApportionment() {
        return decimalApportionment.getRoundedDownApportionment();
    }

    private void executeSecondPassApportionment() {
        int repsLeftToAllocate = getRepsLeftToAllocate();
        Map<State, Double> remainderMap = decimalApportionment.getRemainderMap();
        remainderMap.entrySet().stream()
                .sorted((e1, e2) -> (Double.compare(e2.getValue(), e1.getValue())))
                .limit(repsLeftToAllocate)
                .map(Map.Entry::getKey)
                .forEach(state -> apportionment.addRepresentativesToState(state, 1));
    }

    private int getTotalPopulation() {
        int totalPopulation = 0;
        for (State state : stateList) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }

    private int getRepsLeftToAllocate() {
        int allocatedRepresentatives = apportionment.getAllocatedRepresentatives();
        return targetRepresentatives - allocatedRepresentatives;
    }
}
