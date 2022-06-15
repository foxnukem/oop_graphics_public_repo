package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class RobotBender extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 5;
    private static final int regenerateRate = 10;
    private static final double step = 12.0;

    public RobotBender(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        try {
            setImage(new ImageView(new Image(new File("src/images/bender.png").toURI().toString())));
        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public RobotBender(double initialPosX, double initialPosY, boolean isActive) {
        this("Бендер", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public RobotBender() {
        this("Бендер", Main.getWorld().getPlanetExpressOffice().getPosX() + Main.getWorld().getPlanetExpressOffice().getWidth() - (Main.startId % 10) * 21, Main.getWorld().getPlanetExpressOffice().getPosY() + (Main.startId % 100) * 22);
    }
    private Device destroyThisBomb(Device device) {
        if (!devices.contains(device) && device.changeStatusBecauseOf(this)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
            return device;
        }
        return null;
    }
    @Override
    public Device interactWithMacro(Device device) {
        return destroyThisBomb(device);
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
