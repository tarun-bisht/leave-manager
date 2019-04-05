/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dialogs;

import data_classes.Employee;
import el.CheckleaveController;
import el.ScheduleController;
import el.UpdateFXMLController;
import query.Query;
import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Tarun Bisht
 */
public class special 
{
    private static boolean session=false;
    public void ChangeScene(ActionEvent event,String FXMLPath,boolean stageStyle) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource(FXMLPath));
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/dashboard.css").toExternalForm());
        Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        if(stageStyle)
        {
            Stage newStage=new Stage();
            newStage.initStyle(StageStyle.UNDECORATED);
            newStage.setScene(scene);
            newStage.show();
        }
        else
        {
            Stage newStage = new Stage();
            newStage.setScene(scene);
            newStage.show();
        }
    }
    public void NewWindowUpdate(ActionEvent event,String FXMLPath,Employee data) throws IOException
    {
        Stage primarystage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(getClass().getResource(FXMLPath));
        Parent root = loader.load();
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/dashboard.css").toExternalForm());
        Stage stage=new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primarystage);
        stage.setScene(scene);
        UpdateFXMLController update=(UpdateFXMLController)loader.getController();
        update.GetEmployeeData(data);
        stage.show();
    }
    public void NewWindowCheck(ActionEvent event,String FXMLPath,Employee data) throws IOException
    {
        Stage primarystage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(getClass().getResource(FXMLPath));
        Parent root = loader.load();
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/dashboard.css").toExternalForm());
        Stage stage=new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primarystage);
        stage.setScene(scene);
        CheckleaveController check=(CheckleaveController)loader.getController();
        check.SetLabels(data);
        stage.show();
    }
    public void NewWindowSchedule(ActionEvent event,String FXMLPath,Employee data) throws IOException
    {
        Stage primarystage=(Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader loader=new FXMLLoader(getClass().getResource(FXMLPath));
        Parent root = loader.load();
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/dashboard.css").toExternalForm());
        Stage stage=new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primarystage);
        stage.setScene(scene);
        ScheduleController schedule=(ScheduleController)loader.getController();
        schedule.GetEmployeeData(data);
        stage.show();
    }
    public void NewWindow(ActionEvent event,String FXMLPath) throws IOException
    {
        Stage primarystage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(FXMLPath));
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/dashboard.css").toExternalForm());
        Stage stage=new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(primarystage);
        stage.setScene(scene);
        stage.show();
    }
    public static void ErrorAlert(String title,String header,String content)
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void InformationAlert(String title,String header,String content)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static boolean PasswordAlertBox()
    {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Verify");
        dialog.setHeaderText("Verify Yourself enter Password");
        ButtonType loginButtonType = new ButtonType("Done", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);
        dialog.getDialogPane().setContent(grid);
        Platform.runLater(() -> password.requestFocus());
        dialog.setResultConverter((ButtonType dialogButton) -> 
        {
            if (dialogButton == loginButtonType) {
                return password.getText();
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(Password -> 
        {
            if(Query.Validate_Session(Password))
            {
                session=true;
            }
            else
            {
                ErrorAlert("Verify","Failed","Verification Failed");
                session=false;
            }
        });
        return session;
    }
}
