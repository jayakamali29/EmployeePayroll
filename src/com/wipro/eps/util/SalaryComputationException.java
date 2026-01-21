package com.wipro.eps.util;

public class SalaryComputationException extends Exception {

    @Override
    public String toString() {
        return "SalaryComputationException: Invalid salary values or deduction percentages.";
    }
}
