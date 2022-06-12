package com.example.oop_graphics;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import java.io.File;
import java.util.HashMap;

public class MiniMap {
    public final static Scale scale;
    private static double miniMapHeight;
    private static double miniMapWidth;

    public HashMap<Nibblonian, ImageView> getCitizensMap() {
        return citizensMap;
    }

    private final HashMap<Nibblonian, ImageView> citizensMap;
    private final HashMap<Device, ImageView> deviceGroups;
    private final ImageView planetExpressOfficeThumbnail;
    private final ImageView momFriendlyRobotsThumbnail;
    private Pane pane;
    private Rectangle container;
    private Rectangle mapArea;
    private Rectangle border;
    private Image miniMapBackground;

    static {
        scale = new Scale(0.1, 0.1);
        miniMapHeight = NewNewYork.getRootHeight() * scale.getX();
        miniMapWidth = NewNewYork.getRootWidth() * scale.getY();
    }
    public MiniMap(PlanetExpressOffice planetExpressOffice, MomFriendlyRobots momFriendlyRobots) {
        citizensMap = new HashMap<>();
        deviceGroups = new HashMap<>();
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

    }
    public void removeDeviceFromMiniMap(Device device) {

    }
    public void moveMapArea(double posX, double posY) {

    }
    public void moveBigMapTo(double posX, double posY) {

    }
    public void updateMiniMap() {
        Pane temporaryPane = new Pane();
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
