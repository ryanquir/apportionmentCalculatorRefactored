# HW3-Apportionment-Refactored

## Authors

* Christopher Joseph
* Pranav Ramkumar
* Ryan Quiram

## Description on how to use your code
To use our code, you must run the program with the following inputs:
Required: filename (.xlsx or .csv)
Optional: representatives (--reps or -r [number]), algorithm (--algorithm or -a [hamilton, jefferson, huntington], 
format (--format or -f [alpha, benefit]))

Examples of valid inputs are as follows:
- census2022.xlsx --reps 500 -af hamilton alpha
- census2022.csv 
- census2022.xlsx --reps 1000 -algorithm jefferson

It is important that each specification of a flag is listed AFTER the flag in its respective order. For long flags, 
you must put your specification directly after (--reps 500), while with short flags, you must put them in the order they
appear (-raf 500 hamilton benefit).

If you follow these input rules, the code should function to apportion representatives based on your input specifications.
