package edu.virginia.cs.hw3;

public class StateReaderFactory {
    private Configuration config;
    StateReader getStateReader(String filename){
        if (filename.toLowerCase().endsWith(".csv")) {
            return new CSVStateReader(filename);
        } else if (filename.toLowerCase().endsWith(".xlsx")) {
            return new ExcelStateReader(filename);
        } else {
            throw new IllegalArgumentException("Error: invalid file type. The system currently supports:\n" +
                    "\t.csv, .xlsx");
        }
    }
}
