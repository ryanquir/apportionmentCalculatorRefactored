package edu.virginia.cs.hw3;

import java.util.*;
import java.io.*;

public class ArgumentsHandler {

    public static final int FILENAME_INDEX = 0;
//    public static final int REPRESENTATIVES_INDEX = 1;
    private final List<String> arguments;
    private Configuration config;

    public ArgumentsHandler(List<String> arguments) {
        if (arguments.size() < 1) {
            throw new IllegalArgumentException("Error: No arguments were included at runtime. Arguments expected\n" +
                    "statePopulationFilename [number of representatives] [--hamilton]");
        }
        this.arguments = arguments;
    }

    public ArgumentsHandler(String[] args) {
        this(Arrays.asList(args));
    }

    public Configuration getConfiguration() {
        String filename = arguments.get(FILENAME_INDEX);
            try {
                if (filenameExists(filename)) {
                    setDefaultConfiguration();
                    configureStateReader(filename); //good
                    configureApportionmentStrategy(); //good
                    configureApportionmentFormat(); //good
                    checkForRepresentativeCount(); //good
                } else {
                    throw new FileNotFoundException();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("File not found. Please check for typos.");
            }

        return config;
    }

    private void setDefaultConfiguration() {
        config = new Configuration();
        config.setApportionmentStrategy(new HamiltonApportionmentStrategy());
        config.setRepresentatives(435);
        config.setApportionmentFormat(new AlphabeticalApportionmentFormat());
    }

    private void configureStateReader(String filename) {
        setStateReaderFromFilename(filename);
    }
    private void configureApportionmentStrategy() {
        if (arguments.contains("--algorithm")) {
            int index = arguments.indexOf("--algorithm");
            setApportionmentStrategyFromArgs(arguments.get(index+1));
        } else if (checkShortFlags("a") != -1) {
            //if there is a valid index corresponding to a short flag, set apportionment strategy from it.
            int index = checkShortFlags("a");
            setApportionmentStrategyFromArgs(arguments.get(index));
        } else {
            System.out.println("No \"-a\" or \"--algorithm\" flag found. Using Default Strategy of Hamilton.");
        }
        //if no strategies are found, system notifies user that default value is given.
    }
    private void configureApportionmentFormat() {
        if (arguments.contains("--format")) {
            int index = arguments.indexOf("--format");
            setApportionmentFormatFromArgs(arguments.get(index+1));
        } else if (checkShortFlags("f") != -1) {
            //if there is a valid index corresponding to a short flag, set apportionment format from it.
            int index = checkShortFlags("f");
            setApportionmentFormatFromArgs(arguments.get(index));
        } else {
            System.out.println("No \"-f\" or \"--format\" flag found. Using Default Format of Alphabetical.");
        }
        //if no formats are found, system notifies user that default value is given.
    }
    private void setStateReaderFromFilename(String filename) {
        StateReaderFactory factory = new StateReaderFactory();
        config.setStateReader(factory.getStateReader(filename));
    }
    private void setApportionmentStrategyFromArgs(String filename) {
        ApportionmentStrategyFactory factory = new ApportionmentStrategyFactory();
        config.setApportionmentStrategy(factory.getStrategy(filename));
    }
    private void setApportionmentFormatFromArgs(String filename) {
        ApportionmentFormatFactory factory = new ApportionmentFormatFactory();
        config.setApportionmentFormat(factory.getFormat(filename));
    }
    private void checkForRepresentativeCount() {
        if (arguments.contains("--reps")) {
            int index = arguments.indexOf("--reps");
            parseReps(index+1);
        } else if (checkShortFlags("r") != -1) {
            //if there is a valid index corresponding to a short flag, parse reps.
            parseReps(checkShortFlags("r"));
        } else {
            System.out.println("No \"-r\" or \"--reps\" flag found. Using Default number of 435 reps.");
        }
        //if no reps are found, system notifies user that default value is given.
    }

    private int checkShortFlags(String flag) {
        for (int i=1;i<arguments.size();i++) {
            //if there is an argument that has only ONE "-", and it has the flag in it...
            if (arguments.get(i).contains(flag) && arguments.get(i).contains("-") && !arguments.get(i).contains("--")) {
                int index = i;
                //we can treat it as a combined short flag, taking the index of where the flag is in the string
                //And adding it to original argument index to parse
                int stringIndex = arguments.get(i).indexOf(flag);
                parseReps(index+stringIndex);
                return (index+stringIndex);
            }
        }
        return -1;
    }

    private void parseReps (int index) {
        try {
            int representativeCount = Integer.parseInt(arguments.get(index));
            if (representativeCount <= 0) {
                throw new IllegalArgumentException("Error: Invalid representative count : " + representativeCount + " - number must be positive");
            }
            config.setRepresentatives(representativeCount);
        } catch (NumberFormatException ignored) {
        }
    }


    private boolean filenameExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

}
