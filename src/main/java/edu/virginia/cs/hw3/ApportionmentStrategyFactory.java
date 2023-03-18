package edu.virginia.cs.hw3;

public class ApportionmentStrategyFactory {
    ApportionmentStrategy getStrategy(String algorithmName) {
        if (algorithmName.equalsIgnoreCase("hamilton")) {
            return new HamiltonApportionmentStrategy();
        } else if (algorithmName.equalsIgnoreCase("huntington")) {
            return new HuntingtonHillApportionmentStrategy();
        } else if (algorithmName.equalsIgnoreCase("jefferson")) {
            return new JeffersonApportionmentStrategy();
        } else {
            throw new IllegalArgumentException("Error: invalid algorithm type. The system currently supports:\n" +
                    "\thamilton, huntington, jefferson");
        }
    }
}
