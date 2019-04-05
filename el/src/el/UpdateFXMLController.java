/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;

import data_classes.Employee;
import date_class.MyDate;
import dialogs.special;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import query.Query;

/**
 * FXML Controller class
 *
 * @author Tarun Bisht
 */
public class UpdateFXMLController implements Initializable 
{
    @FXML
    TextField updatename,updatefname,updatetl,updatel6,updatel12;
    @FXML
    DatePicker updatedob,updatedoj;
    Employee employee;
    @FXML
    private void UpdateDetails() throws SQLException
    {
        String xname=updatename.getText();
        String xfname=updatefname.getText();
        String xdob= updatedob.getValue()==null?"":updatedob.getValue().toString();
        String xdoj= updatedoj.getValue()==null?"":updatedoj.getValue().toString();
        String tl=updatetl.getText();
        String l6=updatel6.getText();
        String l12=updatel12.getText();
        if(xname.isEmpty() || xfname.isEmpty() || xdob.isEmpty() || xdoj.isEmpty() ||tl.isEmpty() ||l6.isEmpty() || l12.isEmpty())
        {
            special.ErrorAlert("Error", "Fill all details", "Please fill all the compulsory details");
            return;
        }
        Object[] emp={xname.toUpperCase(),xfname.toUpperCase(),xdob,xdoj,employee.Employee_Id};
        Object[] leave={tl,l6,l12,employee.Employee_Id};
        String sql_1="update employee set Employee_Name=?,Employee_Father=?,DOB=?,DOJ=? where id=?";
        String sql_2="update employee_leave set Total_Leave=?,Leave_1_6=?,Leave_7_12=? where Emp_Id=?";
        if(!Query.Insert_Query(sql_1,sql_2,emp,leave))
        {
            special.ErrorAlert("Error", "Cannot Update Record", "Employee Record cannot be Update");
        }
        else
        {
            special.InformationAlert("Successful", "Updated", "Employee Record is Updated");
        }
    }
    public void GetEmployeeData(Employee emp)
    {
        employee=emp;
        updatename.setText(employee.Employee_Name);
        updatefname.setText(employee.Employee_Father);
        updatetl.setText(String.valueOf(employee.leave.Total_Leave));
        updatel6.setText(String.valueOf(employee.leave.Leave_6));
        updatel12.setText(String.valueOf(employee.leave.Leave_12));
        MyDate date=new MyDate(employee.DOB,"-");
        LocalDate dob=LocalDate.of(date.Year(), date.Month(), date.Day());
        updatedob.setValue(dob);
        date=new MyDate(employee.DOJ,"-");
        LocalDate doj=LocalDate.of(date.Year(), date.Month(), date.Day());
        updatedoj.setValue(doj);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        Validate_String_Words(updatename,32);
        Validate_String_Words(updatefname,32);
        Validate_String_for_Numbers(updatetl,3,300);
        Validate_String_for_Numbers(updatel6,2,16);
        Validate_String_for_Numbers(updatel12,2,15);
    }
    private void Validate_String_for_Numbers(TextField txt,int length,int maxValue)
    {
        txt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
        {
            if (!newValue.matches("\\d*")) 
            {
                txt.setText(newValue.replaceAll("[^\\d]",""));
            }
            if(!txt.getText().isEmpty() && Integer.parseInt(txt.getText()) > maxValue)
            {
                special.ErrorAlert("Out of Range", "Value out of Range", "Check entered value is greater than maximum value");
                Platform.runLater(() -> {
                    txt.clear();
                });
            }
        });
        txt.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> 
        {
            if(newValue.intValue() > oldValue.intValue())
            {
                if (!txt.getText().isEmpty() && txt.getText().length() >= length)
                {
                    txt.setText(txt.getText().substring(0, length));
                }
            }
        });
    }
    private void Validate_String_Words(TextField txt,int length)
    {
        txt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
        {
            if (!newValue.matches("[a-zA-Z]\\s")) 
            {
                txt.setText(newValue.replaceAll("[^a-zA-Z\\s]",""));
            }
        });
        txt.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> 
        {
            if(newValue.intValue()>oldValue.intValue())
            {
                if (!txt.getText().isEmpty() && txt.getText().length() >= length)
                {
                    txt.setText(txt.getText().substring(0, length));
                }
            }
        });
    }
}
