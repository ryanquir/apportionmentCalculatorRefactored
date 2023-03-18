package edu.virginia.cs.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RelativeBenefitFormat extends ApportionmentFormat{

    Map<State, List<Integer>> relativeBenefitData = new HashMap<>();
    @Override
    public String getApportionmentString(Apportionment apportionment) {
        int totalPopulation = getTotalPopulation(apportionment);
        return null;
    }

    private int getTotalPopulation(Apportionment apportionment) {
        Set<State> stateSet = apportionment.apportionmentMap.keySet();
        int totalPopulation = 0;
        for (State state : stateSet) {
            totalPopulation += state.getPopulation();
        }
        return totalPopulation;
    }

    private double getDivisor(Apportionment apportionment) {
        int totalPopulation = getTotalPopulation(apportionment);
        return (double) totalPopulation / apportionment.getAllocatedRepresentatives();
    }

    private void storeData(Apportionment apportionment) {

    }

}
