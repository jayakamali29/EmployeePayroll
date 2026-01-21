package com.wipro.eps.util;

public class InvalidEmployeeException extends Exception {

    @Override
    public String toString() {
        return "InvalidEmployeeException: Employee ID not found or invalid.";
    }
}
