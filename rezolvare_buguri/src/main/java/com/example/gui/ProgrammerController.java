package com.example.gui;

import com.example.domain.Bug;
import com.example.repository.bug_repo.BugDbRepo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

import java.util.HashMap;
import java.util.Map;

public class ProgrammerController {
    private BugDbRepo bugDbRepo;
    public void setRepos(BugDbRepo bugDbRepo){
        this.bugDbRepo = bugDbRepo;
    }
    @FXML
    private TableView<Map<String, Object>> bugsTableView;
    @FXML
    private TableColumn<Map, String> nameColumn;
    @FXML
    private TableColumn<Map, String> descriptionColumn;
    @FXML
    private TableColumn<Map, String> projectColumn;
    @FXML
    private TableColumn<Map, String> programmerColumn;

    public void loadBugsTable() {
        bugsTableView.getItems().clear();
        nameColumn.setCellValueFactory(new MapValueFactory<>("name_column"));
        descriptionColumn.setCellValueFactory(new MapValueFactory<>("description_column"));
        projectColumn.setCellValueFactory(new MapValueFactory<>("project_column"));
        ObservableList<Map<String, Object>> items = FXCollections.observableArrayList();
        Iterable<Bug> bugs = bugDbRepo.findAll();
        for(Bug b : bugs) {
            Map<String, Object> item = new HashMap<>();
            item.put("name_column",b.getName());
            item.put("description_column", b.getDescription());
            item.put("project_column",b.getProjectName());
            items.add(item);
        }
        bugsTableView.getItems().addAll(items);
    }
}
