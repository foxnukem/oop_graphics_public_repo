package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import java.io.File;
import java.text.CharacterIterator;
import java.util.HashMap;

public class MiniMap {
    private final static SnapshotParameters snapshotParameters;
    private final static Scale scale;
    private static double miniMapHeight;
    private static double miniMapWidth;
    private static boolean isTransparent;

    public HashMap<Nibblonian, ImageView> getCitizensMap() {
        return citizensMap;
    }

    private final HashMap<Nibblonian, ImageView> citizensMap;
    private final HashMap<Device, Group> deviceGroups;
    private final HashMap<Device, Circle> devicesCircles;
    private final ImageView planetExpressOfficeThumbnail;
    private final ImageView momFriendlyRobotsThumbnail;
    private Pane pane;
    private Rectangle container;
    private Rectangle mapArea;
    private Rectangle border;
    private Image miniMapBackground;

    static {
        snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        scale = new Scale(0.1, 0.1);
        miniMapHeight = NewNewYork.getRootHeight() * scale.getX();
        miniMapWidth = NewNewYork.getRootWidth() * scale.getY();
        isTransparent = false;
    }
    public MiniMap(PlanetExpressOffice planetExpressOffice, MomFriendlyRobots momFriendlyRobots) {
        citizensMap = new HashMap<>();
        deviceGroups = new HashMap<>();
        devicesCircles = new HashMap<>();
        this.pane = new Pane();
        this.pane.setMinSize(miniMapWidth, miniMapHeight);
        this.pane.setPrefSize(miniMapWidth, miniMapHeight);
        this.pane.setMaxSize(miniMapWidth, miniMapHeight);

        planetExpressOfficeThumbnail = new ImageView(planetExpressOffice.getImageView().getImage());
        planetExpressOfficeThumbnail.setLayoutX(planetExpressOffice.getPosX() * scale.getX());
        planetExpressOfficeThumbnail.setLayoutY(planetExpressOffice.getPosY() * scale.getY());
        planetExpressOfficeThumbnail.setFitWidth(planetExpressOffice.getWidth() * scale.getX());
        planetExpressOfficeThumbnail.setFitHeight(planetExpressOffice.getHeight() * scale.getY());

        momFriendlyRobotsThumbnail = new ImageView(momFriendlyRobots.getImageView().getImage());
        momFriendlyRobotsThumbnail.setLayoutX(momFriendlyRobots.getPosX() * scale.getX());
        momFriendlyRobotsThumbnail.setLayoutY((momFriendlyRobots.getPosY() + 30) * scale.getY()); // 30 - offset in container
        momFriendlyRobotsThumbnail.setFitWidth(momFriendlyRobots.getWidth() * scale.getX());
        momFriendlyRobotsThumbnail.setFitHeight((momFriendlyRobots.getHeight() - 110) * scale.getY()); // 110px - margin of photo in container

        try {
            miniMapBackground = new Image(new File("src/images/beach.png").toURI().toString());
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.container = new Rectangle(0, 0, pane.getMinWidth(), pane.getMinHeight());
        container.setFill(new ImagePattern(miniMapBackground));

        this.mapArea = new Rectangle(0, 0, Main.getViewportWidth() * scale.getX(), Main.getViewPortHeight() * scale.getY());
        this.mapArea.setFill(Color.TRANSPARENT);
        this.mapArea.setStroke(Color.GREEN);
        this.mapArea.setStrokeWidth(2);
        this.pane.getChildren().addAll(container, planetExpressOfficeThumbnail, momFriendlyRobotsThumbnail, mapArea);

        pane.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                if (!isTransparent) {
                    moveMapArea(mouseEvent.getX(), mouseEvent.getY());
                }
            }
            if (mouseEvent.getButton() == MouseButton.MIDDLE) {
                isTransparent = !isTransparent;
                if (isTransparent) {
                    this.pane.setOpacity(0);
                } else {
                    this.pane.setOpacity(1);
                }
            }
        });
    }

    public void addCitizenToMiniMap(Nibblonian citizen) {
        ImageView citizenThumbnail = new ImageView(citizen.getImageView().getImage());
        citizensMap.put(citizen, citizenThumbnail);
        citizensMap.get(citizen).setLayoutX(citizen.getPosX() * scale.getX());
        citizensMap.get(citizen).setLayoutY(citizen.getPosY() * scale.getY());
        citizensMap.get(citizen).setPreserveRatio(true);
        citizensMap.get(citizen).setFitHeight(citizen.getHeight() * scale.getY());
        pane.getChildren().add(citizensMap.get(citizen));
        updateMiniMap();
    }
    public void removeCitizenFromMiniMap(Nibblonian citizen) {
        pane.getChildren().remove(citizensMap.get(citizen));
        citizensMap.remove(citizen);
    }
    public void addDeviceToMiniMap(Device device) {
        Circle miniBorder = new Circle((device.getPosX() + device.getWidth() / 2) * scale.getX(), (device.getPosY() + device.getHeight() / 2)* scale.getY(), (device.getWidth() / 2) * scale.getX(), device.getBorder().getFill());
        miniBorder.setOpacity(0.5);
        devicesCircles.put(device, miniBorder);
        ImageView imageView = new ImageView(device.getImage().getImage());
        imageView.setFitWidth((device.getWidth() - 50) * scale.getX());
        imageView.setFitHeight((device.getHeight() - 50) * scale.getY());
        imageView.setX((device.getPosX() + 25) * scale.getX());
        imageView.setY((device.getPosY() + 30) * scale.getY());
        Group deviceMiniGroup = new Group(devicesCircles.get(device), imageView);
        deviceMiniGroup.setLayoutX(device.getPosX() * scale.getX());
        deviceMiniGroup.setLayoutY(device.getPosY() * scale.getY());
        deviceGroups.put(device, deviceMiniGroup);
        pane.getChildren().add(deviceGroups.get(device));
    }
    public void moveMapArea(double posX, double posY) {
        if (posX < 0.5 * mapArea.getWidth()) {
            mapArea.setX(0);
            Main.getScrollPane().setHvalue(0);
        } else if (posX > miniMapWidth - mapArea.getWidth() / 2) {
            mapArea.setX(miniMapWidth - mapArea.getWidth());
            Main.getScrollPane().setHvalue(1);
        } else {
            mapArea.setX(posX - mapArea.getWidth() / 2);
            Main.getScrollPane().setHvalue(posX / pane.getWidth());
        }

        if (posY < mapArea.getHeight() / 2) {
            mapArea.setY(0);
            Main.getScrollPane().setVvalue(0);
        } else if (posY > miniMapHeight - mapArea.getHeight() / 2) {
            mapArea.setY(miniMapHeight - mapArea.getHeight());
            Main.getScrollPane().setVvalue(1);
        } else {
            mapArea.setY(posY - mapArea.getHeight() / 2);
            Main.getScrollPane().setVvalue(posY / pane.getHeight());
        }
    }
    public void updateMiniMap() {
        Pane temporaryPane = new Pane();
        devicesCircles.forEach(((device, circle) -> circle.setFill(device.getBorder().getFill())));
        temporaryPane.getChildren().addAll(pane.getChildren());
        pane.getChildren().removeAll(pane.getChildren());
        pane.getChildren().addAll(temporaryPane.getChildren());
        temporaryPane.getChildren().removeAll(temporaryPane.getChildren());
    }
    public double getMiniMapHeight() {
        return miniMapHeight;
    }

    public void setMiniMapHeight(double miniMapHeight) {
        MiniMap.miniMapHeight = miniMapHeight;
    }

    public double getMiniMapWidth() {
        return miniMapWidth;
    }

    public void setMiniMapWidth(double miniMapWidth) {
        MiniMap.miniMapWidth = miniMapWidth;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Rectangle getContainer() {
        return container;
    }

    public void setContainer(Rectangle container) {
        this.container = container;
    }

    public Rectangle getMapArea() {
        return mapArea;
    }

    public void setMapArea(Rectangle mapArea) {
        this.mapArea = mapArea;
    }

    public Rectangle getBorder() {
        return border;
    }

    public void setBorder(Rectangle border) {
        this.border = border;
    }

    public Image getMiniMapBackground() {
        return miniMapBackground;
    }

    public void setMiniMapBackground(Image miniMapBackground) {
        this.miniMapBackground = miniMapBackground;
    }
    public static Scale getScale() {
        return scale;
    }
}
