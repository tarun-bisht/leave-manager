/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_classes;
/**
 *
 * @author Tarun Bisht
 */
public class Employee 
{
    public int Employee_Id;
    public String Employee_Name;
    public String Employee_Father;
    public String DOB;
    public String DOJ;
    public Leave leave;
    public Employee(int id,String name,String fname, String dob, String doj,Leave leave)
    {
        this.Employee_Id=id;
        this.Employee_Name=name;
        this.Employee_Father=fname;
        this.DOB=dob;
        this.DOJ=doj;
        this.leave=leave;
    }
}
