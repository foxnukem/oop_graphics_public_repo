package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.File;

public class RobotSanta extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 10;
    private static final int regenerateRate = 10;

    public int getInitialHealthValue() {
        return initialHealthValue;
    }
    public int getHurtRate() {
        return hurtRate;
    }
    public int getRegenerateRate() {
        return regenerateRate;
    }
    public RobotSanta(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        this.isBad = true;
        try {
            this.image = new ImageView(new Image(new File("src/images/santa.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.microGroup.getChildren().removeAll(this.microGroup.getChildren());
        this.microGroup.getChildren().addAll(this.image, this.health, this.transformedDevices, this.border);
    }
    public RobotSanta(double initialPosX, double initialPosY, boolean isActive) {
        this("Санта", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public RobotSanta() {
        this("Робот Санта", 1300, 1000);
    }
    private void makeBombFromDevice(Device device) {
        if (!this.devices.contains(device) && device.setStatus(this, Device.DeviceStatus.ACTIVEBOMB)) {
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
        }
    }
    @Override
    public boolean equals(Object compared) {
        if (this == compared)
            return true;
        if (!(compared instanceof RobotSanta comparedRobotSanta))
            return false;
        return this.name.equals(comparedRobotSanta.name) &&
                this.isBad == comparedRobotSanta.isBad &&
                this.healthValue == comparedRobotSanta.healthValue &&
                this.distanceTravelled == comparedRobotSanta.distanceTravelled &&
                this.devices.equals(comparedRobotSanta.devices);
    }
    @Override
    public String toString() {
        return "RobotSanta{" +
                "name='" + name + '\'' +
                ", isBad=" + isBad +
                ", healthValue=" + healthValue +
                ", distanceTravelled=" + distanceTravelled +
                ", numberOfTransformedDevices=" + devices.size() +
                ", devices=" + devices +
                '}';
    }
}
