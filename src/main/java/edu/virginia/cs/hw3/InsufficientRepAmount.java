package edu.virginia.cs.hw3;

public class InsufficientRepAmount extends RuntimeException {
    public InsufficientRepAmount(int repAmount,int stateCount) {
        super("Error: " + repAmount + " reps are not enough to allocate to " + stateCount + " states");
    }
}