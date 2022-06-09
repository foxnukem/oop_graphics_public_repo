package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

import java.io.File;

public class MiniMap {
    private final Scale scale;
    private double miniMapHeight;
    private double miniMapWidth;
    private Pane pane;
    private Rectangle container;
    private Rectangle mapArea;
    private Rectangle border;
    private Image miniMapBackground;


    public MiniMap() {
        this.scale = new Scale(0.1, 0.1);
        this.miniMapHeight = NewNewYork.getRootHeight() * scale.getX();
        this.miniMapWidth = NewNewYork.getRootWidth() * scale.getY();
        this.pane = new Pane();
        this.pane.setMinSize(miniMapWidth, miniMapHeight);

        try {
            miniMapBackground = new Image(new File("src/images/beach.png").toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.container = new Rectangle(0, 0, pane.getMinWidth(), pane.getMinHeight());
        container.setFill(new ImagePattern(miniMapBackground));

        this.pane.getChildren().addAll(container);
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
