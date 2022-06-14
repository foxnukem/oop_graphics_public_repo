package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class RobotBender extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 5;
    private static final int regenerateRate = 10;
    private static final double step = 15.0;
    private static final double speed = 0.0;

    public RobotBender(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        try {
            image = new ImageView(new Image(new File("src/images/bender.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        microGroup.getChildren().removeAll(microGroup.getChildren());
        microGroup.getChildren().addAll(image, health, transformedDevices, border, objectId);
    }

    public RobotBender(double initialPosX, double initialPosY, boolean isActive) {
        this("Бендер", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public RobotBender() {
        this("Бендер", 1200, 1000);
    }
    private void destroyThisBomb(Device device) {
        if (!devices.contains(device) && device.changeStatusBecauseOf(this)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
        }
    }
    @Override
    public void interactWithMacro(Device device) {
        destroyThisBomb(device);
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
    public double getStep() {
        return step;
    }
    public double getSpeed() {
        return speed * Main.speedCoefficient;
    }
    @Override
    public String toString() {
        return "RobotBender{" +
                "name='" + getName() + '\'' +
                ", id=" + getId() +
                ", isBad=" + isBad() +
                ", isActive=" + isActive() +
                ", healthValue=" + getHealthValue() +
                ", distanceTravelled=" + getDistanceTravelled() +
                ", posX=" + getPosX() +
                ", posY=" + getPosY() +
                ", devices=" + getDevices() +
                '}';
    }
}
