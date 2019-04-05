/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;
import dialogs.special;
import query.Query;
import data_classes.Leave;
import data_classes.Employee;
import data_classes.Session;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import date_class.MyDate;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.VBox;
import org.mindrot.jbcrypt.BCrypt;
import static query.Query.execute_select;
/**
 * FXML Controller class
 *
 * @author Tarun Bisht
 */
public class FXMLDashboardController implements Initializable 
{
    @FXML
    TextField fempid,fempname,ffname,ftl,fl6,fl12;//Add Employee
    @FXML
    TextField scheduleid,checkid;//Dashboard
    @FXML
    TextField updateid;//Update Details
    @FXML
    TextField did ,dname;//for delete panel
    @FXML
    TextField newuser,confirmuser;//for setting panel
    @FXML
    PasswordField newpass,confirmpass;//dashboard setting
    @FXML
    Label totalRegister;//DashBoard
    @FXML
    DatePicker fdob,fdoj;
    @FXML
    CheckBox previouscheck,tl6check,tl12check;
    @FXML
    VBox leavepanel;//Add Employee
    @FXML
    VBox findpanel;//Update Employee
    
    @FXML
    private void Add_Employee(ActionEvent event) throws SQLException
    {
        Employee emp = GetEmployeeDataFromText();
        fempid.clear();fempname.clear();ffname.clear();ftl.clear();fl6.clear();fl12.clear();
        fdob.setValue(LocalDate.of(1990,1,1));
        fdoj.setValue(LocalDate.now());
        if(emp==null)
        {
            special.ErrorAlert("Error", "Fill all details", "Please fill all the compulsory details");
            return;
        }
        if(!special.PasswordAlertBox())
            return;
        Object[] employee={emp.Employee_Id,emp.Employee_Name,emp.Employee_Father,emp.DOB,emp.DOJ};
        Object[] leave={emp.Employee_Id,emp.leave.Total_Leave,emp.leave.Leave_6,emp.leave.Leave_12};
        if(!Query.Insert_Query("insert into employee values(?,?,?,?,?)","insert into employee_leave values(?,?,?,?)",employee,leave))
        {
            special.ErrorAlert("Error", "Cannot Insert Record", "Employee cannot be Inserted to database");
        }
        else
        {
            special.InformationAlert("Successful", "New Record Inserted", "New Employee is inserted to record");
        }
    }
    @FXML
    private void Update_Employee_Panel(ActionEvent event) throws SQLException, IOException
    {
        if(updateid.getText().isEmpty())
        {
            special.ErrorAlert("Error", "Fields Empty error", "Plese fill all necessary details");
            return;
        }
        if(!special.PasswordAlertBox())
            return;
        int id=Integer.parseInt(updateid.getText());
        updateid.clear();
        Object[] parameters={id,id};
        Employee emp=GetEmployeeDataFromDatabase("select * from employee e ,employee_leave l where e.id= ? and l.Emp_Id =?;",parameters);
        if(emp==null)
        {
            special.ErrorAlert("Account", "No User Found!", "Check Employee Id entered");
        }
        else
        {
            special s=new special();
            s.NewWindowUpdate(event, "/fxml_docs/updateFXML.fxml",emp);
        }
    }
    @FXML
    private void Delete_Employee() throws SQLException
    {
        String id=did.getText();
        String name=dname.getText();
        if(id.isEmpty()||name.isEmpty())
        {
            special.ErrorAlert("Error", "Fields Empty error", "Plese fill all necessary details");
            return;
        }
        if(!special.PasswordAlertBox())
            return;
        Object[] parameter={id,name.toUpperCase()};
        did.clear();
        dname.clear();
        if(!Query.remove("employee", "id=? and Employee_Name=?", parameter))
        {
            special.ErrorAlert("Error", "Cannot Remove Record", "Please Check Credentials No Employee found with entered details");
        }
        else
        {
            special.InformationAlert("Successful", "Record Deleted", "Employee is Removed Successfully");
        }
    }
    @FXML 
    private void CheckLeave(ActionEvent event) throws SQLException, IOException
    {
        if(checkid.getText().isEmpty())
        {
            special.ErrorAlert("Error", "Fields Empty error", "Plese fill all necessary details");
            return;
        }
        int id=Integer.parseInt(checkid.getText());
        checkid.clear();
        Object[] parameters={id,id};
        Employee emp=GetEmployeeDataFromDatabase("select * from employee e ,employee_leave l where e.id= ? and l.Emp_Id =?;",parameters);
        if(emp==null)
        {
            special.ErrorAlert("Account", "No User Found!", "Check Employee Id entered");
        }
        else
        {
            special s=new special();
            s.NewWindowCheck(event, "/fxml_docs/checkleave.fxml",emp);
        }
    }
    @FXML
    private void ScheduleLeave(ActionEvent event) throws SQLException, IOException
    {
        if(scheduleid.getText().isEmpty())
        {
            special.ErrorAlert("Error", "Fields Empty error", "Plese fill all necessary details");
            return;
        }
        int id=Integer.parseInt(scheduleid.getText());
        scheduleid.clear();
        if(!special.PasswordAlertBox())
            return;
        Object[] parameters={id,id};
        Employee emp=GetEmployeeDataFromDatabase("select * from employee e ,employee_leave l where e.id= ? and l.Emp_Id =?;",parameters);
        if(emp==null)
        {
            special.ErrorAlert("Account", "No User Found!", "Check Employee Id entered");
        }
        else
        {
            special s=new special();
            s.NewWindowSchedule(event, "/fxml_docs/schedule.fxml",emp);
        }
    }
    @FXML
    private void GetTotalEmployeeRegister() throws SQLException
    {
        totalRegister.setText(String.valueOf(Query.sql_num_rows("select * from employee")));
    }
    @FXML
    private void NewUsername() throws SQLException
    {
        String username=newuser.getText();
        String confirmusername=confirmuser.getText();
        if(username.isEmpty() || username.isEmpty())
        {
            special.ErrorAlert("Error", "Empty Field", "Please Fill all compulsory fields");
            return;
        }
        if(username.compareTo(confirmusername)!=0)
        {
            special.ErrorAlert("Error", "Fields not Match", "Username and Confirm Username field are not same");
            return;
        }
        if(!special.PasswordAlertBox())
            return;
        Object[] data={username,Session.GetId()};
        String sql="update el_admin set username=? where id=?";
        if(!Query.Insert_Query(sql,data))
        {
            special.ErrorAlert("Error", "Cannot Update Username", "Username cannot be Update");
        }
        else
        {
            special.InformationAlert("Successful", "Updated", "Username has Updated");
            Platform.exit();
            System.exit(0);
        }
    }
    @FXML
    private void NewPassword() throws SQLException
    {
        String password=newpass.getText();
        String confirmpassword=confirmpass.getText();
        if(password.isEmpty() || confirmpassword.isEmpty())
        {
            special.ErrorAlert("Error", "Empty Field", "Please Fill all compulsory fields");
            return;
        }
        if(password.compareTo(confirmpassword)!=0)
        {
            special.ErrorAlert("Error", "Fields not Match", "Username and Confirm Password field are not same");
            return;
        }
        if(!special.PasswordAlertBox())
            return;
        String hash=BCrypt.hashpw(password,BCrypt.gensalt());
        Object[] data={hash,Session.GetId()};
        String sql="update el_admin set password=? where id=?";
        if(!Query.Insert_Query(sql,data))
        {
            special.ErrorAlert("Error", "Cannot Update Password", "Password cannot be Update");
        }
        else
        {
            special.InformationAlert("Successful", "Updated", "Password has Updated");
            Platform.exit();
            System.exit(0);
        }
    }
    private Employee GetEmployeeDataFromText()
    {
        String xid=fempid.getText();
        String xname=fempname.getText();
        String xfname=ffname.getText();
        String xdob= fdob.getValue()==null?"":fdob.getValue().toString();
        String xdoj= fdoj.getValue()==null?"":fdoj.getValue().toString();
        Boolean isPrevious=previouscheck.isSelected();
        if(xid.isEmpty()|| xname.isEmpty() || xfname.isEmpty() || xdob.isEmpty() || xdoj.isEmpty())
        {
            return null;
        }
        else 
        {
            if(isPrevious)
            {
                String tleave=ftl.getText();
                if(tleave.isEmpty())
                {
                    return null;
                }
                else
                {
                    String tleave6=fl6.getText().isEmpty()?"16":fl6.getText();
                    String tleave12=fl12.getText().isEmpty()?"15":fl12.getText();
                    Leave leave=new Leave(Integer.parseInt(tleave),Integer.parseInt(tleave6),Integer.parseInt(tleave12));
                    Employee emp=new Employee(Integer.parseInt(xid),xname.toUpperCase(),xfname.toUpperCase(),xdob,xdoj,leave);
                    return emp;
                }
            }
            else if(!isPrevious)
            {
                Leave leave = CalculateHolidayDays(xdoj);
                Employee emp=new Employee(Integer.parseInt(xid),xname.toUpperCase(),xfname.toUpperCase(),xdob,xdoj,leave);
                return emp;
            }
        }
        return null;
    }
    private Employee GetEmployeeDataFromDatabase(String sql,Object[] parameters) throws SQLException
    {
        ArrayList data=execute_select(sql,parameters);
        if(data.size()<1)
        {
            return null;
        }
        HashMap map=(HashMap) data.get(0);
        int tl=(int)map.get("Total_Leave");
        int l6=(int)map.get("Leave_1_6");
        int l12=(int)map.get("Leave_7_12");
        Leave leave=new Leave(tl,l6,l12);
        int emp_id=(int)map.get("id");
        String name=(String)map.get("Employee_Name");
        String fname=(String)map.get("Employee_Father");
        String dob=(String)map.get("DOB").toString();
        String doj=(String)map.get("DOJ").toString();
        Employee emp=new Employee(emp_id,name,fname,dob,doj,leave);
        return emp;
    }
    private Leave CalculateHolidayDays(String DateofJoin)
    {
        MyDate doj=new MyDate(DateofJoin,"-");
        MyDate extreme;
        if(doj.isGreaterMonth(6))
        {
            //Joining between July-December
            extreme=new MyDate(31,12,doj.Year());
            double temp=(15.0/183.0)*doj.DaysBetweenDate(extreme);
            int tl12= roundoff(temp);
            int tl6=0;
            int tl=0;
            return new Leave(tl,tl6,tl12);
        }
        //Joining between January-June
        extreme=new MyDate(30,06,doj.Year());
        double temp=(16.0/182.0)*doj.DaysBetweenDate(extreme);
        int tl6= roundoff(temp);
        int tl12=15;
        int tl=0;
        return new Leave(tl,tl6,tl12);
    }
    private int roundoff(double number)
    {
        double diff= number-Math.floor(number);
        if(diff<0.5)
            return (int) Math.floor(number);
        return (int) Math.ceil(number);
    }  
//INITIALIZE METHOD
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        fdob.setValue(LocalDate.of(1990,1,1));
        fdoj.setValue(LocalDate.now());
        ftl.setDisable(true);
        fl6.setDisable(true);
        fl12.setDisable(true);
        tl6check.setDisable(true);
        tl12check.setDisable(true);
        leavepanel.setVisible(false);
        //add employee
        Validate_String_for_Numbers(fempid,11);
        Validate_String_for_Numbers(ftl,3,300);
        Validate_String_for_Numbers(fl6,2,16);
        Validate_String_for_Numbers(fl12,2,15);
        Validate_String_Words(fempname,32);
        Validate_String_Words(ffname,32);
        //delete employee
        Validate_String_for_Numbers(did,11);
        Validate_String_Words(dname,32);
        //update employee
        Validate_String_for_Numbers(updateid,11);
        //dashboard
        Validate_String_for_Numbers(checkid,11);
        Validate_String_for_Numbers(scheduleid,11);
    } 
    
//SETTING THE INTERFACE 
    public void CheckBoxEvent(ActionEvent ev)
    {
        if(previouscheck.isSelected())
        {
            ftl.setDisable(false);
            tl6check.setDisable(false);
            tl12check.setDisable(false);
            leavepanel.setVisible(true);
        }
        if(!previouscheck.isSelected())
        {
            leavepanel.setVisible(false);
            ftl.setDisable(true);
            tl6check.setDisable(true);
            tl12check.setDisable(true);
            tl6check.setSelected(false);
            tl12check.setSelected(false);
            ftl.clear();
            fl6.clear();
            fl12.clear();
        }
        if(tl6check.isSelected())
        {
            fl6.setDisable(false);
        }
        else if(!tl6check.isSelected())
        {
            fl6.setDisable(true);
            fl6.clear();
        }
        if(tl12check.isSelected())
        {
            fl12.setDisable(false);
        }
        else if(!tl12check.isSelected())
        {
            fl12.setDisable(true);
            fl12.clear();
        }
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
    private void Validate_String_for_Numbers(TextField txt,int length)
    {
        txt.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> 
        {
            if (!newValue.matches("\\d*")) 
            {
                txt.setText(newValue.replaceAll("[^\\d]",""));
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
