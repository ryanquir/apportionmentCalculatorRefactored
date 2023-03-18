package edu.virginia.cs.hw3;

public class ApportionmentFormatFactory {
    ApportionmentFormat getFormat(String formatName) {
        if (formatName.equalsIgnoreCase("alpha")) {
            return new AlphabeticalApportionmentFormat();
        } else if (formatName.equalsIgnoreCase("benefit")) {
            return new RelativeBenefitFormat();
        } else {
            throw new IllegalArgumentException("Error: invalid format type. The system currently supports:\n" +
                    "\talpha, benefit");
        }
    }
}
