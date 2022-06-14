package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class RobotSanta extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 10;
    private static final int regenerateRate = 10;
    private static final double step = 15.0;
    private static final double speed = 0.0;

    public RobotSanta(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        isBad = true;
        try {
            image = new ImageView(new Image(new File("src/images/santa.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        microGroup.getChildren().removeAll(microGroup.getChildren());
        microGroup.getChildren().addAll(image, health, transformedDevices, border, objectId);
    }
    public RobotSanta(double initialPosX, double initialPosY, boolean isActive) {
        this("Робот Санта", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public RobotSanta() {
        this("Робот Санта", 1300, 1000);
    }
    private void makeBombFromDevice(Device device) {
        if (!devices.contains(device) && device.setStatus(this, Device.DeviceStatus.ACTIVEBOMB)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
        }
    }
    @Override
    public void interactWithMacro(Device device) {
        this.makeBombFromDevice(device);
    }
    public void damageOther(Nibblonian nibblonian) {
        if (!(nibblonian instanceof RobotSanta)) {
            nibblonian.getHurt();
            this.getHurt();
        }
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
