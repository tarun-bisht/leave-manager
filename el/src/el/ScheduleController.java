/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;

import data_classes.Employee;
import data_classes.Leave;
import date_class.MyDate;
import dialogs.special;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import query.Query;

/**
 * FXML Controller class
 *
 * @author Tarun Bisht
 */
public class ScheduleController implements Initializable {

    @FXML
    DatePicker from,till;
    @FXML
    Label tl,l6,l12,numberofdays;
    Employee emp;
    public void GetEmployeeData(Employee data)
    {
        emp=data;
        tl.setText(String.valueOf(emp.leave.Total_Leave));
        l6.setText(String.valueOf(emp.leave.Leave_6));
        l12.setText(String.valueOf(emp.leave.Leave_12));
    }
    @FXML
    private void ScheduleLeave(ActionEvent event) throws IOException, SQLException
    {
        String xfrom= from.getValue()==null?"":from.getValue().toString();
        String xtill= till.getValue()==null?"":till.getValue().toString();
        if(xfrom.isEmpty() || xtill.isEmpty())
        {
            special.ErrorAlert("Error", "Fields Empty error", "Plese fill all necessary details");
        }
        Employee employee=CutLeaveDays(xfrom,xtill);
        if(employee==null)
        {
            special.ErrorAlert("Error", "Cannot Schedule Leave", "Employee do not have sufficient leave to schedule");
        }
        Object[] leave={employee.leave.Total_Leave,employee.leave.Leave_6,employee.leave.Leave_12,employee.Employee_Id};
        String sql="update employee_leave set Total_Leave=?,Leave_1_6=?,Leave_7_12=? where Emp_Id=?";
        if(!Query.Insert_Query(sql,leave))
        {
            special.ErrorAlert("Error", "Cannot Schedule Leave", "Leave cannot be scheduled.");
        }
        else
        {
            special.InformationAlert("Successful", "Leave Scheduled", "Leave is Scheduled successfully. Enjoy!");
        }
        special s=new special();
        s.NewWindowCheck(event, "/fxml_docs/checkleave.fxml",employee);
    }
    @FXML
    private void Preview(ActionEvent event) throws IOException
    {
        String xfrom= from.getValue()==null?"":from.getValue().toString();
        String xtill= till.getValue()==null?"":till.getValue().toString();
        if(xfrom.isEmpty() || xtill.isEmpty())
        {
            special.ErrorAlert("Error", "Fields Empty error", "Plese fill all necessary details");
            return;
        }
        Employee employee=CutLeaveDays(xfrom,xtill);
        if(employee==null)
        {
            special.ErrorAlert("Error", "Cannot Schedule Leave", "Employee do not have sufficient leave to schedule");
        }
        else
        {
            special s=new special();
            s.NewWindowCheck(event, "/fxml_docs/checkleave.fxml",employee);
        }
    }
    @FXML 
    private void setLabel()
    {
        String xfrom= from.getValue()==null?"":from.getValue().toString();
        String xtill= till.getValue()==null?"":till.getValue().toString();
        if(xfrom.isEmpty() || xtill.isEmpty())
            return;
        MyDate from_date=new MyDate(xfrom,"-");
        MyDate till_date=new MyDate(xtill,"-");
        if(from_date.isGreaterDateThan(till_date))
        {
            special.ErrorAlert("Error", "Value Error", "From date is greater than till date. Cannot Take Leave from past.");
            from.setValue(LocalDate.now());
            till.setValue(LocalDate.now());
            numberofdays.setText("Number of Leave Days- 1");
            return;
        }
        int days=from_date.DaysBetweenDate(till_date, true);
        numberofdays.setText("Number of Leave Days- "+days);
    }
    private Employee CutLeaveDays(String from,String till)
    {
        Leave l=new Leave(emp.leave.Total_Leave,emp.leave.Leave_6,emp.leave.Leave_12);
        Employee updated=new Employee(emp.Employee_Id,emp.Employee_Name,emp.Employee_Father,emp.DOB,emp.DOJ,l);
        MyDate from_date=new MyDate(from,"-");
        MyDate till_date=new MyDate(till,"-");
        int leave=from_date.DaysBetweenDate(till_date, true);
        if((leave>updated.leave.Leave_12 || leave>updated.leave.Leave_6) && leave>updated.leave.Total_Leave)
        {
            return null;
        }
        if(from_date.isGreaterMonth(6))
        {
            if(leave<=updated.leave.Leave_12)
            {
                updated.leave.Leave_12-=leave;
            }
            else
            {
                int diff=leave-updated.leave.Leave_12;
                updated.leave.Total_Leave-=diff;
                leave-=diff;
                updated.leave.Leave_12-=leave;
            }
        }
        else
        {
            if(leave<=updated.leave.Leave_6)
            {
                updated.leave.Leave_6-=leave;
            }
            else
            {
                int diff=leave-updated.leave.Leave_6;
                updated.leave.Total_Leave-=diff;
                leave-=diff;
                updated.leave.Leave_6-=leave;
            }
        }
        return updated;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        from.setValue(LocalDate.now());
        till.setValue(LocalDate.now());
        setLabel();
    } 
}
