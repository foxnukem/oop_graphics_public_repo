package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;

public class PlanetExpressOffice {
    private ArrayList<Nibblonian> teamMembers;
    private double posX;
    private double posY;
    private final double width;
    private final double height;
    private ImageView imageView;
    private Rectangle adjacentArea;
    private Text officeName;
    private final Group planetExpressArea;

    public PlanetExpressOffice() {
        teamMembers = new ArrayList<>();
        this.posX = 15;
        this.posY = 610;
        this.width = 445;
        this.height = 510;

        this.officeName = new Text("Planet Express");
        this.officeName.setFont(new Font("Monako", 20));
        this.officeName.setFill(Color.WHITE);
        this.officeName.setX(this.posX + 10);
        this.officeName.setY(this.posY + 25);
        try {
            this.imageView = new ImageView(new File("src/images/planetexpress.png").toURI().toString());
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.imageView.setFitWidth(this.width);
        this.imageView.setFitHeight(this.height - 110);
        this.imageView.setX(this.posX);
        this.imageView.setY(this.posY + 30);

        this.adjacentArea = new Rectangle(this.width, this.height);
        this.adjacentArea.setFill(Color.rgb(182, 216, 155));
        this.adjacentArea.setOpacity(0.4);
        this.adjacentArea.setX(this.posX);
        this.adjacentArea.setY(this.posY);
        this.adjacentArea.setArcHeight(40);
        this.adjacentArea.setArcWidth(40);

        this.planetExpressArea = new Group(imageView, adjacentArea, officeName);
    }
    public void addTeamMember(Nibblonian newTeamMember) {
        if (!newTeamMember.isBad()) {
            teamMembers.add(newTeamMember);
        }
    }
    public void removeTeamMember(Nibblonian teamMember) {
        teamMembers.remove(teamMember);
    }

    public ArrayList<Nibblonian> getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ArrayList<Nibblonian> teamMembers) {
        this.teamMembers = teamMembers;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
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

    public Group getPlanetExpressArea() {
        return planetExpressArea;
    }

}
