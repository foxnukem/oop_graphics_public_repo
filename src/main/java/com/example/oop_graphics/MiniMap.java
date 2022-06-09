package com.example.oop_graphics;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import java.io.File;

public class MiniMap {
    public final static Scale scale;
    private static double miniMapHeight;
    private static double miniMapWidth;
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
    public MiniMap() {

        this.pane = new Pane();
        this.pane.setMinSize(miniMapWidth, miniMapHeight);
        this.pane.setPrefSize(miniMapWidth, miniMapHeight);
        this.pane.setMaxSize(miniMapWidth, miniMapHeight);

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
        this.pane.getChildren().addAll(container, mapArea);
    }

    public double getMiniMapHeight() {
        return miniMapHeight;
    }

    public void setMiniMapHeight(double miniMapHeight) {
        this.miniMapHeight = miniMapHeight;
    }

    public double getMiniMapWidth() {
        return miniMapWidth;
    }

    public void setMiniMapWidth(double miniMapWidth) {
        this.miniMapWidth = miniMapWidth;
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
}
