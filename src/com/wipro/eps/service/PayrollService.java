package com.wipro.eps.service;

import java.util.ArrayList;

import com.wipro.eps.entity.Employee;
import com.wipro.eps.entity.PayrollRule;
import com.wipro.eps.entity.Payslip;
import com.wipro.eps.util.InvalidEmployeeException;
import com.wipro.eps.util.SalaryComputationException;

public class PayrollService {

    private ArrayList<Employee> employees;
    private PayrollRule rule;
    private ArrayList<Payslip> payslips = new ArrayList<>();

    public PayrollService(ArrayList<Employee> employees, PayrollRule rule) {
        this.employees = employees;
        this.rule = rule;
    }

    public boolean validateEmployee(String employeeId) throws InvalidEmployeeException {
        for (Employee emp : employees) {
            if (emp.getEmployeeId().equals(employeeId)) {
                return true;
            }
        }
        throw new InvalidEmployeeException();
    }

    public Employee findEmployee(String employeeId) throws InvalidEmployeeException {
        validateEmployee(employeeId);
        for (Employee emp : employees) {
            if (emp.getEmployeeId().equals(employeeId)) {
                return emp;
            }
        }
        throw new InvalidEmployeeException();
    }

    public double calculateGrossSalary(Employee emp) throws SalaryComputationException {
        if (emp.getBasicSalary() < 0 || emp.getHra() < 0 || emp.getOtherAllowance() < 0) {
            throw new SalaryComputationException();
        }
        return emp.getBasicSalary() + emp.getHra() + emp.getOtherAllowance();
    }

    public double calculateTotalDeductions(double gross) throws SalaryComputationException {

        if (rule.getTaxPercentage() < 0 || rule.getPfPercentage() < 0 || rule.getOtherDeductionsPercentage() < 0) {
            throw new SalaryComputationException();
        }

        double tax = gross * rule.getTaxPercentage() / 100;
        double pf = gross * rule.getPfPercentage() / 100;
        double other = gross * rule.getOtherDeductionsPercentage() / 100;

        double total = tax + pf + other;

        if (total > gross) {
            throw new SalaryComputationException();
        }

        return total;
    }

    public Payslip generatePayslip(String employeeId, String month) throws Exception {

        for (Payslip p : payslips) {
            if (p.getEmployeeId().equals(employeeId) && p.getMonth().equals(month)) {
                throw new Exception("Payslip already generated for this month.");
            }
        }

        Employee emp = findEmployee(employeeId);
        double gross = calculateGrossSalary(emp);
        double deductions = calculateTotalDeductions(gross);
        double net = gross - deductions;

        Payslip payslip = new Payslip();
        payslip.setPayslipId("PSL-" + month + "-" + employeeId);
        payslip.setEmployeeId(employeeId);
        payslip.setMonth(month);
        payslip.setGrossSalary(gross);
        payslip.setTotalDeductions(deductions);
        payslip.setNetSalary(net);

        payslips.add(payslip);
        return payslip;
    }
}
