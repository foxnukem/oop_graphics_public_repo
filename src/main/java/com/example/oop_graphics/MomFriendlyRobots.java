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
    private final double posX;
    private final double posY;
    private final double width;
    private final double height;
    private ImageView imageView;
    private Rectangle adjacentArea;
    private Text officeName;
    private Group momCorpArea;

    {
        this.posX = 3765;
        this.posY = 2335;
        this.width = 225;
        this.height = 409;
    }
    public MomFriendlyRobots() {
        this.robotSantas = new ArrayList<>();
        this.officeName = new Text("Mom Friendly Robots");
        this.officeName.setFont(new Font("Monako", 20));
        this.officeName.setFill(Color.WHITE);
        this.officeName.setX(this.posX + 10);
        this.officeName.setY(this.posY + 25);

        try {
            this.imageView = new ImageView(new File("src/images/momcorp.png").toURI().toString());
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.imageView.setFitWidth(this.width);
        this.imageView.setFitHeight(this.height - 110);
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY + 30);

        this.adjacentArea = new Rectangle(this.width, this.height);
        this.adjacentArea.setFill(Color.rgb(167, 181, 146));
        this.adjacentArea.setOpacity(0.4);
        this.adjacentArea.setX(this.posX);
        this.adjacentArea.setY(this.posY);
        this.adjacentArea.setArcHeight(40);
        this.adjacentArea.setArcWidth(40);

        this.momCorpArea = new Group(imageView, adjacentArea, officeName);
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
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
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
