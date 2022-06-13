package com.example.oop_graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {
    public static long startId = 1000;
    public static long deviceStartId = 100;
    //TODO set viewport to fullHD and add setMaximized(true) to stage
    private final static int viewPortWidth = 1600;
    private final static int viewPortHeight = 900;
    public static int getViewportWidth() {
        return viewPortWidth;
    }
    public static int getViewPortHeight() {
        return viewPortHeight;
    }
    private static double scrollX;
    private static double scrollY;
    private final static Pane infoPane = new Pane();
    private static boolean infoEnabled = false;

    private static ArrayList<Nibblonian> microObjectsForInfoPane;
    private final static NewNewYork newNewYork = new NewNewYork(5);
    public static StackPane group = new StackPane();
    private final static ScrollPane scrollPane = new ScrollPane(newNewYork.getRoot());
    private static Scene scene;
    @Override
    public void start(Stage stage) {
        newNewYork.addDevice(new Device(800, 800));
        newNewYork.addDevice(new Device(10, 10));
        newNewYork.addCitizen(new Nibblonian());
        newNewYork.addCitizen(new Fry());
        newNewYork.addCitizen(new RobotBender());
        newNewYork.addCitizen(new RobotSanta());

        newNewYork.getCitizens().get(3).interactWithMacro(newNewYork.getDevices().get(0));
        newNewYork.getCitizens().get(2).interactWithMacro(newNewYork.getDevices().get(0));

        initInfoPane();

        scrollPane.setPannable(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        group.getChildren().addAll(scrollPane, newNewYork.getMiniMap().getPane(), infoPane);
        StackPane.setAlignment(scrollPane, Pos.TOP_LEFT);
        StackPane.setAlignment(newNewYork.getMiniMap().getPane(), Pos.TOP_RIGHT);
        StackPane.setAlignment(infoPane, Pos.BOTTOM_RIGHT);
        infoPane.toBack();
        scene = new Scene(group, viewPortWidth, viewPortHeight);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            //TODO create working methods for move, with different step
            for (Nibblonian n : newNewYork.getCitizens()) {
                if (n.isActive() && !keyEvent.isControlDown()) {
                    // Move left
                    if (keyEvent.getCode() == KeyCode.H) {
                        n.moveLeft();
                    }
                    // Move down
                    if (keyEvent.getCode() == KeyCode.J) {
                        n.moveDown();
                    }
                    // Move up
                    if (keyEvent.getCode() == KeyCode.K) {
                        n.moveUp();
                    }
                    // Move right
                    if (keyEvent.getCode() == KeyCode.L) {
                        n.moveRight();
                    }
                    // Delete activated
                    if (keyEvent.getCode() == KeyCode.DELETE) {
                        newNewYork.removeCitizen(n);
                    }
                    // Cancel activation
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        n.cancelActivation();
                    }
                }
            }
            // Move quicker
//            if (keyEvent.getCode() == KeyCode.Y) {
//
//            }
            // Move slower
//            if (keyEvent.getCode() == KeyCode.U) {
//
//            }
            // Dialog for adding microobjects
            if (keyEvent.getCode() == KeyCode.INSERT) {
                try {
                    insertNewMicro(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            // Last activated info
            if (keyEvent.getCode() == KeyCode.I) {
                infoEnabled = !infoEnabled;
                updateInfo();
            }
        });
        scrollPane.viewportBoundsProperty().addListener((observableValue, bounds, t1) -> {
            scrollX = -1 * (int) t1.getMinX();
            scrollY = -1 * (int) t1.getMinY();

            System.out.println("X: " +scrollX + " Y: "  + scrollY);
        });
        stage.setTitle("Futurama. The Game");
        stage.setScene(scene);
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

    public void initInfoPane() {
        Rectangle container = new Rectangle(0, 0, newNewYork.getMiniMap().getMiniMapWidth(), newNewYork.getMiniMap().getMiniMapHeight());
        infoPane.setMinSize(newNewYork.getMiniMap().getMiniMapWidth(), newNewYork.getMiniMap().getMiniMapHeight());
        infoPane.setPrefSize(newNewYork.getMiniMap().getMiniMapWidth(), newNewYork.getMiniMap().getMiniMapHeight());
        infoPane.setMaxSize(newNewYork.getMiniMap().getMiniMapWidth(), newNewYork.getMiniMap().getMiniMapHeight());
        Label name = new Label("Ім'я: \n");
        Label level = new Label("Рівень: \n");
        Label health = new Label("Здоров'я: \n");
        Label travelledDistance = new Label("Пройдена відстань: \n");
        Label devices = new Label("Опрацьовані: ");
        container.setFill(Color.GRAY);
        infoPane.getChildren().addAll(container, name, level, health, travelledDistance, devices);
        infoPane.setOpacity(0);
    }
    public void updateInfo() {
        if (infoEnabled) {
            infoPane.setOpacity(1);
            infoPane.toFront();
        } else {
            infoPane.setOpacity(0);
            infoPane.toBack();
        }
    }
    public static void main(String[] args) {
        launch();
    }
    public static NewNewYork getWorld() {
        return Main.newNewYork;
    }
}