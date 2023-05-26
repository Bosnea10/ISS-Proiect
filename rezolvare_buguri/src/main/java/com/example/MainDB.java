package com.example;

import com.example.domain.Tester;
import com.example.gui.LoginController;
import com.example.repository.bug_repo.BugDbRepo;
import com.example.repository.programmer_repo.ProgrammerDbRepo;
import com.example.repository.tester_repo.TesterDbRepo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainDB extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loginLoader = new FXMLLoader(MainDB.class.getResource("gui/loginView.fxml"));
        Parent root = loginLoader.load();
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
        }
        ProgrammerDbRepo programmerDbRepo = new ProgrammerDbRepo(props);
        TesterDbRepo testerDbRepo = new TesterDbRepo(props);
        BugDbRepo bugDbRepo = new BugDbRepo(props);

        LoginController loginController = loginLoader.getController();
        loginController.loadScene();
        loginController.setRepos(testerDbRepo, programmerDbRepo, bugDbRepo);

        Scene loginScene = new Scene(root);
        primaryStage.setTitle("Solve bugs");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

