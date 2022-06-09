package com.example.oop_graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    private final static int viewPortWidth = 1920;
    private final static int viewPortHeight = 1080;
    private static double scrollX;
    private static double scrollY;
    private final static NewNewYork universalObject = new NewNewYork();
    private static ScrollPane scrollPane = new ScrollPane(NewNewYork.getRoot());
    private static Scene mainScene = new Scene(scrollPane, viewPortWidth, viewPortHeight);
    @Override
    public void start(Stage stage) throws IOException {
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        universalObject.addCitizen(new Nibblonian());
        universalObject.addCitizen(new Fry());
        universalObject.addCitizen(new RobotBender());
        universalObject.addCitizen(new RobotSanta());

        Parent parent = FXMLLoader.load(getClass().getResource("main.fxml"));
        NewNewYork.getRoot().getChildren().add(parent);

        scrollPane.viewportBoundsProperty().addListener((observableValue, o, t1) -> {

            Main.scrollX = (-1) * (int) t1.getMinX();
            Main.scrollY = (-1) * (int) t1.getMinY();

            parent.setLayoutX(Main.scrollX);
            parent.setLayoutY(Main.scrollY);

            universalObject.getMiniMap().getPane().setLayoutX(Main.scrollX + 1430);
            universalObject.getMiniMap().getPane().setLayoutY(Main.scrollY + 31);

        });
        stage.setTitle("Futurama. The Game");
        stage.setMaximized(true);
        stage.setScene(mainScene);
        stage.show();
    }

    public void insertNewMicro(Stage stage) throws IOException {
        Scene scene = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("insert.fxml"))));
        Stage window = new Stage();
        window.setTitle("Додавання мікрооб'єктів");
        window.initOwner(stage);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setScene(scene);
        window.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}