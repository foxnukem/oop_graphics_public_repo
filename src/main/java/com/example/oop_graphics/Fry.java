package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Fry extends Nibblonian {
    private static final int initialHealthValue = 150;
    private static final int hurtRate = 3;
    private static final int regenerateRate = 8;
    private static final double step = 10.0;
    private static final double speed = 0.0;

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

    public Fry(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        try {
            this.image = new ImageView(new Image(new File("src/images/fry.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.microGroup.getChildren().removeAll(this.microGroup.getChildren());
        this.microGroup.getChildren().addAll(this.image, this.health, this.transformedDevices, this.border, this.objectId);
    }
    public Fry(double initialPosX, double initialPoxY, boolean isActive) {
        this("Фрай", initialPosX, initialPoxY);
        this.setActive(isActive);
    }
    public Fry() {
        this("Фрай", 1100, 1000);
    }
    protected void stopTimer(Device device) {
        if (!this.devices.contains(device) && device.setStatus(this, Device.DeviceStatus.STOPPEDTIMER)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
        }
    }
    @Override
    public void getHurt() {
        super.getHurt();
    }
    @Override
    public void interactWithMacro(Device device) {
        this.stopTimer(device);
    }

    @Override
    public String toString() {
        return "Fry{" +
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
