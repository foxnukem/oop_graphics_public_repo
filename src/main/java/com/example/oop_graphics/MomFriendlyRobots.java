package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

public class MomFriendlyRobots {
    private ArrayList<RobotSanta> robotSantas;
    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    private ImageView image;
    private Rectangle adjacentArea;
    private Text officeName;
    private Group momCorpArea;

    public MomFriendlyRobots() {
        this.robotSantas = new ArrayList<>();

        this.xPosition = 3765;
        this.yPosition = 2335;
        this.width = 225;
        this.height = 409;

        this.officeName = new Text("Mom Friendly Robots");
        this.officeName.setFont(new Font("Monako", 20));
        this.officeName.setFill(Color.WHITE);
        this.officeName.setX(this.xPosition + 10);
        this.officeName.setY(this.yPosition + 25);

        try {
            this.image = new ImageView(new File("src/images/momcorp.png").toURI().toString());
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.image.setFitWidth(this.width);
        this.image.setFitHeight(this.height - 110);
        this.image.setX(this.xPosition);
        this.image.setY(this.yPosition + 30);

        this.adjacentArea = new Rectangle(this.width, this.height);
        this.adjacentArea.setFill(Color.rgb(167, 181, 146));
        this.adjacentArea.setOpacity(0.4);
        this.adjacentArea.setX(this.xPosition);
        this.adjacentArea.setY(this.yPosition);
        this.adjacentArea.setArcHeight(40);
        this.adjacentArea.setArcWidth(40);

        this.momCorpArea = new Group(image, adjacentArea, officeName);
    }
    public void addRobotSanta(RobotSanta newRobotSanta) {
        robotSantas.add(newRobotSanta);
    }
    public void removeRobotSanta(RobotSanta robotSanta) {
        robotSantas.remove(robotSanta);
    }
    public ArrayList<RobotSanta> getRobotSantas() {
        return robotSantas;
    }
    public void setRobotSantas(ArrayList<RobotSanta> robotSantas) {
        this.robotSantas = robotSantas;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Rectangle getAdjacentArea() {
        return adjacentArea;
    }

    public void setAdjacentArea(Rectangle adjacentArea) {
        this.adjacentArea = adjacentArea;
    }

    public Text getOfficeName() {
        return officeName;
    }

    public void setOfficeName(Text officeName) {
        this.officeName = officeName;
    }

    public Group getMomCorpArea() {
        return momCorpArea;
    }

    public void setMomCorpArea(Group momCorpArea) {
        this.momCorpArea = momCorpArea;
    }
}
