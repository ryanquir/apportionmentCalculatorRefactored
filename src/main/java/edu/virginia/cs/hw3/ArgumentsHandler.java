package edu.virginia.cs.hw3;

import java.util.*;
import java.io.*;

public class ArgumentsHandler {

    public static final int FILENAME_INDEX = 0;
    public static final int REPRESENTATIVES_INDEX = 1;
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
        setDefaultConfiguration();
        configureStateReader();
        checkForRepresentativeCount();
        return config;
    }

    private void setDefaultConfiguration() {
        config = new Configuration();
        config.setApportionmentStrategy(new HamiltonApportionmentStrategy());
        config.setRepresentatives(435);
        config.setApportionmentFormat(new AlphabeticalApportionmentFormat());
    }

    private void configureStateReader() {
        String filename = arguments.get(FILENAME_INDEX);
        setStateReaderFromFilename(filename);
    }


    private void checkForRepresentativeCount() {
        // if (arguments.contains("--reps")) {
           //int index = arguments.indexOf("--reps");
           //parseReps(index+1);
        //} else {
            //checkShortFlags("r") //will check for short flag of "r", return nothing if none.
        //}

        if (arguments.size() < 2) {
            return;
        }
        try {
            int representativeCount = Integer.parseInt(arguments.get(REPRESENTATIVES_INDEX));
            if (representativeCount <= 0) {
                throw new IllegalArgumentException("Error: Invalid representative count : " + representativeCount + " - number must be positive");
            }
            config.setRepresentatives(representativeCount);
        } catch (NumberFormatException ignored) {
        }
    }

    private void checkShortFlags(String flag) {
        for (int i=1;i<arguments.size();i++) {
            //if there is an argument that has only ONE "-", and it has the flag in it...
            if (arguments.get(i).contains(flag) && arguments.get(i).contains("-") && !arguments.get(i).contains("--")) {
                int index = i;
                //we can treat it as a combined short flag, taking the index of where the flag is in the string
                //And adding it to original argument index to parse
                int stringIndex = arguments.get(i).indexOf(flag);
                parseReps(index+stringIndex);
                break;
            }
        }
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

    private void setStateReaderFromFilename(String filename) {
        if (filename.toLowerCase().endsWith(".csv")) {
            setConfigurationToCSVReader(filename);
        } else if (filename.toLowerCase().endsWith(".xlsx")) {
            setConfigurationToXLSXReader(filename);
        } else {
            throw new IllegalArgumentException("Error: invalid file type. The system currently supports:\n" +
                    "\t.csv, .xlsx");
        }
    }

    private boolean filenameExists(String filename) {
        File file = new File(filename);
        return file.exists();
    }

    private void setConfigurationToCSVReader(String filename) {
        config.setStateReader(new CSVStateReader(filename));
    }

    private void setConfigurationToXLSXReader(String filename) {
        config.setStateReader(new ExcelStateReader(filename));
    }
}
