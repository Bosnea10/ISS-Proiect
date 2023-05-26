package com.example.gui;

import com.example.domain.Programmer;
import com.example.domain.Tester;
import com.example.repository.bug_repo.BugDbRepo;
import com.example.repository.programmer_repo.ProgrammerDbRepo;
import com.example.repository.tester_repo.TesterDbRepo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    private TesterDbRepo testerDbRepo;
    private ProgrammerDbRepo programmerDbRepo;
    private BugDbRepo bugDbRepo;
    public void setRepos(TesterDbRepo testerDbRepo, ProgrammerDbRepo programmerDbRepo, BugDbRepo bugDbRepo) {
        this.testerDbRepo = testerDbRepo;
        this.programmerDbRepo = programmerDbRepo;
        this.bugDbRepo = bugDbRepo;
    }

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    public void login() throws IOException {
        if(Objects.equals(usernameTextField.getText(), "") || Objects.equals(passwordField.getText(), "")) {
            errorLabel.setText("Username and password can't be empty!");
            return;
        }
        if(Objects.equals(comboBox.getValue(), null)){
            errorLabel.setText("Combobox is empty!");
        }
        if(Objects.equals(comboBox.getValue(), "Programmer")){
            Programmer programmer = programmerDbRepo.findProgrammer(usernameTextField.getText(), passwordField.getText());
            if(programmer == null){
                errorLabel.setText("Wrong username or passoword!");
                return;
            }
        }
        if(Objects.equals(comboBox.getValue(), "Tester")){
            Tester tester = testerDbRepo.findTester(usernameTextField.getText(), passwordField.getText());
            if(tester == null){
                errorLabel.setText("Wrong username or passoword!");
                return;
            }
        }

        if(Objects.equals(comboBox.getValue(), "Programmer")){
            FXMLLoader programmerAppLoader = new FXMLLoader(getClass().getResource("programmerView.fxml"));
            Parent root = programmerAppLoader.load();
            Scene scene = new Scene(root);

            ProgrammerController programmerController = programmerAppLoader.getController();
            programmerController.setRepos(bugDbRepo);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
        if(Objects.equals(comboBox.getValue(), "Tester")){
            FXMLLoader testerAppLoader = new FXMLLoader(getClass().getResource("testerView.fxml"));
            Parent root = testerAppLoader.load();
            Scene scene = new Scene(root);

            TesterController testerController = testerAppLoader.getController();
            testerController.setRepos(bugDbRepo);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private TextField nameRegisterTextField;
    @FXML
    private TextField companyRegisterTextField;
    @FXML
    private TextField usernameRegisterTextField;
    @FXML
    private PasswordField passwordRegisterPasswordField;
    @FXML
    private ComboBox<String> registerComboBox;
    @FXML
    private Label registerLabel;

    @FXML
    public void register() {
        if(Objects.equals(nameRegisterTextField.getText(), "") || Objects.equals(usernameRegisterTextField.getText(), "") || Objects.equals(passwordRegisterPasswordField.getText(), "") || Objects.equals(companyRegisterTextField.getText(), "")) {
            registerLabel.setText("Name, Company, Username or Password can not be empty!");
            return;
        }
        if(Objects.equals(registerComboBox.getValue(), null)){
            registerLabel.setText("Combobox is empty!");
            return;
        }
        if(Objects.equals(registerComboBox.getValue(), "Programmer")){
            if(programmerDbRepo.findProgrammer(usernameRegisterTextField.getText(),passwordRegisterPasswordField.getText()) != null){
                registerLabel.setTextFill(Color.RED);
                registerLabel.setText("Programmer already exists!");
            }
            else {
                registerLabel.setTextFill(Color.GREEN);
                registerLabel.setText("Programmer registered successfully");
                programmerDbRepo.add(new Programmer(nameRegisterTextField.getText(),usernameRegisterTextField.getText(),passwordRegisterPasswordField.getText(), companyRegisterTextField.getText()));
            }
        }
        if(Objects.equals(registerComboBox.getValue(), "Tester")){
            if(testerDbRepo.findTester(usernameRegisterTextField.getText(),passwordRegisterPasswordField.getText()) != null){
                registerLabel.setTextFill(Color.RED);
                registerLabel.setText("Tester already exists!");
            }
            else {
                registerLabel.setTextFill(Color.GREEN);
                registerLabel.setText("Tester registered successfully");
                testerDbRepo.add(new Tester(nameRegisterTextField.getText(),usernameRegisterTextField.getText(),passwordRegisterPasswordField.getText(), companyRegisterTextField.getText()));
            }
        }
    }

    public void loadScene(){
        comboBox.getItems().addAll("Programmer","Tester");
        registerComboBox.getItems().addAll("Programmer","Tester");
    }
}
