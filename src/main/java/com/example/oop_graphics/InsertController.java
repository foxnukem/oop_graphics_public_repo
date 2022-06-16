package com.example.oop_graphics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class InsertController implements Initializable {
    @FXML
    private TextField posX;
    @FXML
    private TextField posY;
    @FXML
    private ChoiceBox<String> classType;
    private final String[] classTypes = {"Nibblonian", "Fry", "RobotBender", "RobotSanta"};
    @FXML
    private CheckBox isActive;
    @FXML
    private CheckBox isMember;
    @FXML
    private Button addButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        classType.getItems().addAll(classTypes);
    }
    public void add(ActionEvent event) {
        Random random = new Random(System.currentTimeMillis());
        double objectPosX, objectPosY;
        try {
            objectPosX = Double.parseDouble(posX.getText());
        } catch (NumberFormatException e) {
            objectPosX = Math.abs(random.nextDouble(Main.getViewportWidth()));
        }
        try {
            objectPosY = Double.parseDouble(posY.getText());
        } catch (NumberFormatException e) {
            objectPosY = Math.abs(random.nextDouble(Main.getViewPortHeight()));
        }
        boolean isObjectActive = isActive.isSelected();
        if (classType.getValue() == null) {
            Alert noChosenClass = new Alert(Alert.AlertType.INFORMATION);
            noChosenClass.setTitle("Помилка");
            noChosenClass.setHeaderText(null);
            noChosenClass.setContentText("Оберіть у випадному меню клас нового мікрооб'єкта");
            noChosenClass.showAndWait();
        }
        if (classType.getValue() != null && classType.getValue().equals("Nibblonian")) {
            Main.getWorld().addCitizen(new Nibblonian(objectPosX, objectPosY, isObjectActive), isMember.isSelected());
        } else if (classType.getValue() != null && classType.getValue().equals("Fry")) {
            Main.getWorld().addCitizen(new Fry(objectPosX, objectPosY, isObjectActive), isMember.isSelected());
        } else if (classType.getValue() != null && classType.getValue().equals("RobotBender")) {
            Main.getWorld().addCitizen(new RobotBender(objectPosX, objectPosY, isObjectActive), isMember.isSelected());
        } else if (classType.getValue() != null && classType.getValue().equals("RobotSanta")) {
            Main.getWorld().addCitizen(new RobotSanta(objectPosX, objectPosY, isObjectActive), isMember.isSelected());
        }
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
