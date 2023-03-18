package edu.virginia.cs.hw3;

import java.util.*;
import java.util.stream.Collectors;

public class RelativeBenefitFormat extends ApportionmentFormat{

    Apportionment apportionment;

    double divisor;
    @Override
    public String getApportionmentString(Apportionment apportionment) {
        setApportionment(apportionment);
        divisor = getDivisor();
        return getRelativeBenefitApportionmentString();
    }

    private void setApportionment(Apportionment apportionment) {
        this.apportionment = apportionment;
    }

    private int getTotalPopulation() {
        Set<State> stateSet = apportionment.apportionmentMap.keySet();
        int totalPopulation = 0;
        for (State state : stateSet) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }

    private double getDivisor() {
        int totalPopulation = getTotalPopulation();
        return (double) totalPopulation / apportionment.getAllocatedRepresentatives();
    }

//    private void storeData() {
//        Set<State> stateSet = apportionment.apportionmentMap.keySet();
//        ArrayList<Double> tempList;
//
//        for(State state: stateSet) {
//            tempList = new ArrayList<>(2);
//            tempList.add((double)apportionment.apportionmentMap.get(state));
//            tempList.add(tempList.get(0) - state.getPopulation()/divisor);
//            relativeBenefitData.put(state, tempList);
//        }
//        return apportionment.getStateSet().stream()
//
//    }

    private String getRelativeBenefitApportionmentString() {
        return apportionment.getStateSet().stream()
                .sorted(Comparator.comparing((state) -> this.getStateRelativeBenefit(apportionment.getRepresentativesForState(state), state.getPopulation())))
                .map(this::getApportionmentStringForState)
                .collect(Collectors.joining("\n"));
    }

    private String getApportionmentStringForState(State state) {
        String stateName = state.getName();
        int apportionedRepresentatives = apportionment.getRepresentativesForState(state);
        return stateName + " - " + apportionedRepresentatives + " - " + getStateRelativeBenefit(apportionedRepresentatives, state.getPopulation());
    }

    private double getStateRelativeBenefit(int apportionedRepresentatives, int statePopulation) {
        return apportionedRepresentatives - statePopulation/divisor;
    }

}
