package com.wipro.eps.util;

public class PayrollProcessingException extends Exception {

    @Override
    public String toString() {
        return "PayrollProcessingException: Payroll processing failed due to invalid operation.";
    }
}
