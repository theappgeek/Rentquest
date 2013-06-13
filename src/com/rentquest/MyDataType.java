/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rentquest;

import java.sql.Date;

/**
 *
 * @author drenpro
 */
public class MyDataType {
    
    //Instance Variable declaration
    private double totalExpense = 0.0;
    private double totalIncome =0.0;
    private Date fromDate = null;
    private Date toDate = null;
    private String profitLossStatus = null;
    
    public MyDataType(double totalExpense,double totalIncome,Date fromDate, Date toDate,String profitLossStatus)
    {
        this.totalExpense = totalExpense;
        this.totalIncome = totalIncome;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.profitLossStatus = profitLossStatus;
                
    }
    
    public double getTotalExpenses()
    {
        return totalExpense;
    }
    
    public double getTotalIncome()
    {
        return  totalIncome;
    }
    public String getProfitLossStatus()
    {
        return profitLossStatus;
    }
    public Date getFromDate()
    {
        return fromDate;
    }
    public Date getToDate()
    {
        return toDate;
    }
}
