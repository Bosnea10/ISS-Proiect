package com.example.gui;

import com.example.domain.Bug;
import com.example.repository.bug_repo.BugDbRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.Map;

public class ProgrammerController extends Observer {
    Observable observable;
    private BugDbRepo bugDbRepo;
    public void setRepos(BugDbRepo bugDbRepo, Observable observable){
        this.bugDbRepo = bugDbRepo;
        this.observable = observable;
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
    private TableView<Bug> assignedBugsTableView;
    @FXML
    private TableColumn<Bug, String> assignedNameColumn;
    @FXML
    private TableColumn<Bug, String> assignedDescriptionColumn;
    @FXML
    private TableColumn<Bug, String> assignedProjectColumn;

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

    public void assignBug() {
        Bug bug = bugsTableView.getSelectionModel().getSelectedItem();
        bug.setProgrammerName(bugDbRepo.getCrtProgrammer().getName());
        bugDbRepo.updateProgrammer(bug);
        loadBugsTable();
        loadAssignedBugsTable();
        observable.updateAll();
    }

    public void loadAssignedBugsTable() {
        assignedBugsTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        assignedBugsTableView.getItems().clear();
        assignedNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        assignedDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        assignedProjectColumn.setCellValueFactory(new PropertyValueFactory<>("projectName"));
        ObservableList<Bug> items = FXCollections.observableArrayList();
        items.setAll(bugDbRepo.findAllAssigned(bugDbRepo.getCrtProgrammer()));
        assignedBugsTableView.setItems(items);
    }

    public void unresolvedBug() {
        Bug bug = assignedBugsTableView.getSelectionModel().getSelectedItem();
        bugDbRepo.updateUnresolved(bug);
        loadBugsTable();
        loadAssignedBugsTable();
        observable.updateAll();
    }

    public void deleteBug() {
        Bug bug = assignedBugsTableView.getSelectionModel().getSelectedItem();
        bugDbRepo.delete(bug);
        loadBugsTable();
        loadAssignedBugsTable();
        observable.updateAll();
    }

    @Override
    public void update() {
        loadBugsTable();
        loadAssignedBugsTable();
    }
}
