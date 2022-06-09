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
    private double xPosition;
    private double yPosition;
    private final double width;
    private final double height;
    private ImageView image;
    private Rectangle adjacentArea;
    private Text officeName;
    private Group planetExpressArea;

    public PlanetExpressOffice() {
        teamMembers = new ArrayList<>();
        this.xPosition = 15;
        this.yPosition = 610;
        this.width = 445;
        this.height = 510;

        this.officeName = new Text("Planet Express");
        this.officeName.setFont(new Font("Monako", 20));
        this.officeName.setFill(Color.WHITE);
        this.officeName.setX(this.xPosition + 10);
        this.officeName.setY(this.yPosition + 25);

        this.image = new ImageView(new File("src/images/planetexpress.png").toURI().toString());
        this.image.setFitWidth(this.width);
        this.image.setFitHeight(this.height - 110);
        this.image.setX(this.xPosition);
        this.image.setY(this.yPosition + 30);

        this.adjacentArea = new Rectangle(this.width, this.height);
        this.adjacentArea.setFill(Color.rgb(182, 216, 155));
        this.adjacentArea.setOpacity(0.4);
        this.adjacentArea.setX(this.xPosition);
        this.adjacentArea.setY(this.yPosition);
        this.adjacentArea.setArcHeight(40);
        this.adjacentArea.setArcWidth(40);

        this.planetExpressArea = new Group(image, adjacentArea, officeName);
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

    public Group getPlanetExpressArea() {
        return planetExpressArea;
    }

    public void setPlanetExpressArea(Group planetExpressArea) {
        this.planetExpressArea = planetExpressArea;
    }
}
