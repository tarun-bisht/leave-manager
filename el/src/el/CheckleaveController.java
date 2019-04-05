/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;

import data_classes.Employee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Tarun Bisht
 */
public class CheckleaveController implements Initializable 
{
    @FXML
    Label id,name,fname,dob,doj,tl,l6,l12;
    /**
     * Initializes the controller class.
     * @param emp
     */
    public void SetLabels(Employee emp)
    {
        id.setText("Employee Id: "+String.valueOf(emp.Employee_Id));
        name.setText("Name: "+emp.Employee_Name);
        fname.setText("Father's Name: "+emp.Employee_Father);
        dob.setText("Date of Birth: "+emp.DOB);
        doj.setText("Date of Joining: "+emp.DOJ);
        tl.setText(String.valueOf(emp.leave.Total_Leave));
        l6.setText(String.valueOf(emp.leave.Leave_6));
        l12.setText(String.valueOf(emp.leave.Leave_12));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
