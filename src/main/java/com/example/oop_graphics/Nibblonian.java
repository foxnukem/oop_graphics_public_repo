package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Objects;

public class Nibblonian implements Cloneable, Comparable<Nibblonian> {
    private static final int initialHealthValue = 100;
    private static final int hurtRate = 2;
    private static final int regenerateRate = 10;

    protected String name;
    protected boolean isBad;
    protected boolean isActive;
    protected int healthValue;
    protected double distanceTravelled;
    protected ArrayList<Device> devices;

    protected double posX;
    protected double posY;
    protected double width;
    protected double height;
    protected ImageView image;
    protected Line health;
    protected Rectangle border;
    protected Text transformedDevices;
    protected Group microGroup;

    public void move(double x, double y) {

    }
    protected void markDeviceAsSafe(Device device) {
        if (!this.devices.contains(device) && device.setStatus(this, Device.DeviceStatus.SAFE)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
        }
    }
    public void getHurt() {
        if ((this.healthValue - getHurtRate()) > 0) {
            this.healthValue -= getHurtRate();
        } else {
            this.healthValue = 0;
        }
    }
    public void regenerate() {
        if ((this.healthValue + getRegenerateRate()) >= getInitialHealthValue()) {
            this.healthValue = getInitialHealthValue();
        } else {
            this.healthValue += getRegenerateRate();
        }
    }
    public void damageOther(RobotSanta robotSanta) {
        robotSanta.getHurt();
    }

    public void interactWithMacro(Device device) {
        markDeviceAsSafe(device);
    }
    public Nibblonian(String name, double initialPosX, double initialPosY) {
        this.name = name;
        this.healthValue = Nibblonian.initialHealthValue;
        this.distanceTravelled = 0.0;
        this.devices = new ArrayList<>();
        this.isBad = false;
        this.transformedDevices = new Text("0");

        this.posX = initialPosX;
        this.posY = initialPosY;
        this.width = 54;
        this.height = 70;
        try {
            this.image = new ImageView(new Image(new File("src/images/nibblonian.png").toURI().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.border = new Rectangle(0, 0, this.width, this.height);
        this.border.setStrokeWidth(3);
        this.border.setFill(Color.TRANSPARENT);
        this.border.setStroke(Color.PURPLE);

        this.health = new Line();
        this.health.setStroke(Color.GREEN);
        this.health.setStrokeWidth(5);
        this.health.setStartX(this.health.getStartX() + 5);
        this.health.setStartY(this.health.getStartY() + 10);
        this.health.setEndX(this.health.getStartX() + this.width - 10);
        this.health.setEndY(this.health.getStartY());

        this.transformedDevices.setX(5);
        this.transformedDevices.setY(10);

        this.microGroup = new Group(image, health, transformedDevices);
        this.microGroup.setLayoutX(this.posX);
        this.microGroup.setLayoutY(this.posY);
    }

    public Nibblonian() {
        this("Жуйка", 1000, 1000);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBad() {
        return isBad;
    }

    public void setBad(boolean bad) {
        isBad = bad;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getHealthValue() {
        return healthValue;
    }

    public void setHealthValue(int healthValue) {
        this.healthValue = healthValue;
    }

    public double getDistanceTravelled() {
        return distanceTravelled;
    }

    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public int getInitialHealthValue() {
        return initialHealthValue;
    }
    public int getHurtRate() {
        return hurtRate;
    }
    public int getRegenerateRate() {
        return regenerateRate;
    }

    @Override
    public int compareTo(Nibblonian n) {
        if (this.devices.size() == n.devices.size() && this.healthValue < n.healthValue) {
            return -1;
        } else if (this.devices.size() < n.devices.size() && this.healthValue == n.healthValue) {
            return -1;
        } else if (this.devices.size() == n.devices.size() && this.healthValue > n.healthValue) {
            return 1;
        } else if (this.devices.size() > n.devices.size() && this.healthValue == n.healthValue) {
            return 1;
        }
        return 0;
    }
    @Override
    public boolean equals(Object compared) {
        if (this == compared)
            return true;
        if (!(compared instanceof Nibblonian comparedNibblonian))
            return false;
        return this.name.equals(comparedNibblonian.name) &&
                this.isBad == comparedNibblonian.isBad &&
                this.healthValue == comparedNibblonian.healthValue &&
                this.distanceTravelled == comparedNibblonian.distanceTravelled &&
                this.devices.equals(comparedNibblonian.devices);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, healthValue, distanceTravelled, devices);
    }
    @SuppressWarnings("unchecked")
    @Override
    public Nibblonian clone() throws CloneNotSupportedException {
        Nibblonian clone = (Nibblonian) super.clone();
        clone.devices = (ArrayList<Device>) this.devices.clone();
        return clone;
    }
    @Override
    public String toString() {
        return "Nibblonian{" +
                "name='" + name + '\'' +
                ", isBad=" + isBad +
                ", healthValue=" + healthValue +
                ", distanceTravelled=" + distanceTravelled +
                ", numberOfTransformedDevices=" + devices.size() +
                ", devices=" + devices +
                '}';
    }
    // print() method

    public void print() {
        System.out.println(this);
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Line getHealth() {
        return health;
    }

    public void setHealth(Line health) {
        this.health = health;
    }

    public Rectangle getBorder() {
        return border;
    }

    public void setBorder(Rectangle border) {
        this.border = border;
    }



    public Group getMicroGroup() {
        return microGroup;
    }

    public void setMicroGroup(Group microGroup) {
        this.microGroup = microGroup;
    }
}
