package com.example.oop_graphics;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Main extends Application {
    public static long startId = 1000;
    public static long deviceStartId = 100;
    public static double speedCoefficient = 0.5;
    private final static int viewPortWidth = 1920;
    private final static int viewPortHeight = 1080;
    private static double scrollX;
    private static double scrollY;
    private final static Pane infoPane = new Pane();
    private static boolean infoEnabled = false;
    private final static ScrollPane infoScrollPane = new ScrollPane();
    private final static ArrayList<String> activatedObjectsInfo = new ArrayList<>();
    private final static Text infoInText = new Text();
    private final static NewNewYork newNewYork = new NewNewYork(5);
    public static StackPane group = new StackPane();
    private final static ScrollPane scrollPane = new ScrollPane(newNewYork.getRoot());
    private static Scene scene;

    @Override
    public void start(Stage stage) {
        newNewYork.addDevice(new Device(200, 200));
        newNewYork.addDevice(new Device(10, 10));
        newNewYork.addCitizen(new Nibblonian());
        newNewYork.addCitizen(new Fry());
        newNewYork.addCitizen(new RobotBender());
        newNewYork.addCitizen(new RobotSanta());

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
            for (Nibblonian n : newNewYork.getCitizens()) {
                if (n.isActive() && !keyEvent.isControlDown()) {
                    // Move left
                    if (keyEvent.getCode() == KeyCode.H) {
                        n.moveLeft();
                        activatedObjectsInfo.clear();
                    }
                    // Move down
                    if (keyEvent.getCode() == KeyCode.J) {
                        n.moveDown();
                        activatedObjectsInfo.clear();
                    }
                    // Move up
                    if (keyEvent.getCode() == KeyCode.K) {
                        n.moveUp();
                        activatedObjectsInfo.clear();
                    }
                    // Move right
                    if (keyEvent.getCode() == KeyCode.L) {
                        n.moveRight();
                        activatedObjectsInfo.clear();
                    }
                    // Delete activated
                    if (keyEvent.getCode() == KeyCode.DELETE) {
                        newNewYork.removeCitizen(n);
                        activatedObjectsInfo.clear();
                    }
                    // Cancel activation
                    if (keyEvent.getCode() == KeyCode.ESCAPE) {
                        n.cancelActivation();
                        infoEnabled = false;
                        updateInfo();
                        activatedObjectsInfo.clear();
                    } else {
                        activatedObjectsInfo.clear(); // Prevents repeating in microobjects info
                    }
                    // Activated info open
                    if (keyEvent.getCode() == KeyCode.I) {
                        infoEnabled = true;
                        if (!activatedObjectsInfo.contains(n.getInfo())) {
                            activatedObjectsInfo.add(n.getInfo());
                            activatedObjectsInfo.add("");
                        }
                        updateInfo();
                    }
                    // Activated info close
                    if (keyEvent.getCode() == KeyCode.O) {
                        infoEnabled = false;
                        activatedObjectsInfo.clear();
                        updateInfo();
                    }
                }
            }
            // Move quicker
            if (keyEvent.getCode() == KeyCode.Y) {
                increaseSpeed();
            }
            // Move slower
            if (keyEvent.getCode() == KeyCode.U) {
                decreaseSpeed();
            }
            // Dialog for adding microobjects
            if (keyEvent.getCode() == KeyCode.INSERT) {
                try {
                    insertNewMicro(stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                activatedObjectsInfo.clear();
            }
            if (keyEvent.isControlDown()) {
                // Deserialization
                if (keyEvent.getCode() == KeyCode.O) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Відкрити (десеріалізувати)");
                }
                // Serialization
                else if (keyEvent.getCode() == KeyCode.S) {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Зберегти (серіалізувати)");
                }
            }
        });
        scrollPane.viewportBoundsProperty().addListener((observableValue, bounds, t1) -> {
            scrollX = -1 * t1.getMinX();
            scrollY = -1 * t1.getMinY();
            newNewYork.getMiniMap().moveMapArea(scrollX * MiniMap.getScale().getX() + 0.5 * newNewYork.getMiniMap().getMapArea().getWidth(), scrollY * MiniMap.getScale().getY() + 0.5 * newNewYork.getMiniMap().getMapArea().getHeight());
        });
        AnimationTimer timer = new AnimationTimer() {
            private int frame;
            @Override
            public void start() {
                frame = 0;
                super.start();
            }
            @Override
            public void handle(long l) {
                if (frame < 60) {
                    frame++;
                } else {
                    frame = 0;
                }
                for (Nibblonian citizen : getWorld().getCitizens()) {
                    citizen.interactionWithWorld(frame);
                }
            }
        };
        stage.setTitle("Futurama. The Game");
        stage.setMaximized(true);
        stage.setResizable(false);
        stage.setScene(scene);
        timer.start();
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
        infoPane.setMinSize(MiniMap.getMiniMapWidth(), MiniMap.getMiniMapHeight());
        infoPane.setPrefSize(MiniMap.getMiniMapWidth() + 70, MiniMap.getMiniMapHeight() + 70);
        infoPane.setMaxSize(MiniMap.getMiniMapWidth() + 70, MiniMap.getMiniMapHeight() + 70);
        infoScrollPane.setMinSize(MiniMap.getMiniMapWidth(), MiniMap.getMiniMapHeight());
        infoScrollPane.setPrefSize(MiniMap.getMiniMapWidth() + 70, MiniMap.getMiniMapHeight() + 70);
        infoScrollPane.setMaxSize(MiniMap.getMiniMapWidth() + 70, MiniMap.getMiniMapHeight() + 70);
        infoScrollPane.setFitToWidth(true);
        infoScrollPane.setContent(infoInText);
        infoInText.setFill(Color.BLACK);
        infoInText.setFont(new Font("Arial", 14));
        infoPane.getChildren().addAll(infoScrollPane);
        infoPane.setOpacity(0);
    }
    public void updateInfo() {
        if (infoEnabled) {
            infoPane.setOpacity(0.65);
            infoPane.toFront();
            infoInText.setText("");
            activatedObjectsInfo.forEach(str -> infoInText.setText(infoInText.getText() + str));
            infoScrollPane.setContent(infoInText);
        } else {
            infoPane.setOpacity(0);
            infoPane.toBack();
        }
    }
    public void increaseSpeed() {
        if (speedCoefficient + 0.1 > 1) {
            speedCoefficient = 1;
            return;
        }
        speedCoefficient += 0.1;
        speedCoefficient = Math.round(speedCoefficient * 100) / 100.0;
    }
    public void decreaseSpeed() {
        if (speedCoefficient - 0.1 < 0.1) {
            speedCoefficient = 0.1;
            return;
        }
        speedCoefficient -= 0.1;
        speedCoefficient = Math.round(speedCoefficient * 100) / 100.0;
    }
    public static NewNewYork getWorld() {
        return Main.newNewYork;
    }
    public static int getViewportWidth() {
        return viewPortWidth;
    }
    public static int getViewPortHeight() {
        return viewPortHeight;
    }
    public static ScrollPane getScrollPane() {
        return scrollPane;
    }
    public static void main(String[] args) {
        launch();
    }
}