package com.example.gui;

import com.example.domain.Bug;
import com.example.domain.Project;
import com.example.repository.bug_repo.BugDbRepo;
import com.example.repository.project_repo.ProjectDbRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TesterController {
    private BugDbRepo bugDbRepo;
    private ProjectDbRepo projectDbRepo;
    public void setRepos(BugDbRepo bugDbRepo, ProjectDbRepo projectDbRepo) {
        this.bugDbRepo = bugDbRepo;
        this.projectDbRepo = projectDbRepo;
    }

    @FXML
    private TableView<Bug> bugsTableView;
    @FXML
    private TableColumn<Bug, String> nameColumn;
    @FXML
    private TableColumn<Bug, String> descriptionColumn;
    @FXML
    private TableColumn<Bug, String> projectColumn;

    @FXML
    private TextField addNameTextField;
    @FXML
    private TextField addDescriptionTextField;
    @FXML
    private TextField addProjectNameTextField;
    @FXML
    private TextField editNameTextField;
    @FXML
    private TextField editDescriptionTextField;
    @FXML
    private TextField editProjectNameTextField;
    @FXML
    private Label addErrorLabel;
    @FXML
    private Label editErrorLabel;

    public void loadBugsTable() {
        bugsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        bugsTableView.getItems().clear();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        ObservableList<Bug> items = FXCollections.observableArrayList();
        items.setAll(bugDbRepo.findAll());
        bugsTableView.setItems(items);
    }

    public void addBug() {
        String name = addNameTextField.getText();
        String description = addDescriptionTextField.getText();
        String projectName = addProjectNameTextField.getText();
        if(Objects.equals(addNameTextField.getText(), "") || Objects.equals(addDescriptionTextField.getText(), "") || Objects.equals(addProjectNameTextField.getText(), "")){
            addErrorLabel.setTextFill(Color.RED);
            addErrorLabel.setText("Name, Description or Project can not be empty!");
            return;
        }
        if(projectDbRepo.findOne(projectName) == null){
            projectDbRepo.add(new Project(projectName, null));
        }
        bugDbRepo.add(new Bug(name, description, projectName, null));
        addErrorLabel.setTextFill(Color.GREEN);
        addErrorLabel.setText("Bug added successfully!");
        loadBugsTable();
    }

    public void editBug() {
        String name = editNameTextField.getText();
        String description = editDescriptionTextField.getText();
        String projectName = editProjectNameTextField.getText();
        if(Objects.equals(name, "") || Objects.equals(description, "") || Objects.equals(projectName, "")){
            editErrorLabel.setTextFill(Color.RED);
            editErrorLabel.setText("Name, Description or Project can not be empty!");
            return;
        }
        Bug bug = bugsTableView.getSelectionModel().getSelectedItem();
        if(bug == null){
            editErrorLabel.setTextFill(Color.RED);
            editErrorLabel.setText("You must select a bug to edit!");
            return;
        }
        if(projectDbRepo.findOne(projectName) == null){
            projectDbRepo.add(new Project(projectName, null));
        }
        bug.setName(name);
        bug.setDescription(description);
        bug.setProjectName(projectName);
        bugDbRepo.update(bug);
        editErrorLabel.setTextFill(Color.GREEN);
        editErrorLabel.setText("Bug edited successfully!");
        loadBugsTable();
    }
}