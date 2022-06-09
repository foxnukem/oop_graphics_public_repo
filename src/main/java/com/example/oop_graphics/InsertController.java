package com.example.oop_graphics;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;


public class InsertController implements Initializable {
    @FXML
    private TextField xPosition;
    @FXML
    private TextField yPosition;
    @FXML
    private ChoiceBox<String> classType;
    private final String[] classTypes = {"Nibblonian", "Fry", "RobotBender", "RobotSanta"};
    @FXML
    private CheckBox isActive;
    @FXML
    private Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classType.getItems().addAll(classTypes);
    }
}
