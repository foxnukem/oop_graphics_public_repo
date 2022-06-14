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
            this.image = new ImageView(new Image(new File("src/images/bender.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.microGroup.getChildren().removeAll(this.microGroup.getChildren());
        this.microGroup.getChildren().addAll(this.image, this.health, this.transformedDevices, this.border, this.objectId);
    }

    public RobotBender(double initialPosX, double initialPosY, boolean isActive) {
        this("Бендер", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public RobotBender() {
        this("Робот Бендер", 1200, 1000);
    }
    private void destroyBomb(Device device) {
        if (!this.devices.contains(device) && device.setStatus(this, Device.DeviceStatus.DESTROYEDBOMB)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
        }
    }
    @Override
    public void interactWithMacro(Device device) {
        this.destroyBomb(device);
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
        return speed;
    }
    @Override
    public String toString() {
        return "RobotBender{" +
                "name='" + getName() + '\'' +
                ", bad=" + isBad() +
                ", posX=" + getPosX() +
                ", posY=" + getPosY() +
                ", width=" + getWidth() +
                ", height=" + getHeight() +
                ", healthValue=" + getHealthValue() +
                ", distanceTravelled=" + getDistanceTravelled() +
                ", devices=" + getDevices() +
                '}';
    }
}
