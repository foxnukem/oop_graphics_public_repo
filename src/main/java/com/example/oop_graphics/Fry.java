package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.File;

public class Fry extends Nibblonian {
    private static final int initialHealthValue = 150;
    private static final int hurtRate = 3;
    private static final int regenerateRate = 8;

    public int getInitialHealthValue() {
        return initialHealthValue;
    }
    public int getHurtRate() {
        return hurtRate;
    }
    public int getRegenerateRate() {
        return regenerateRate;
    }

    public Fry(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        this.width = 54;
        this.height = 70;
        try {
            this.image = new ImageView(new Image(new File("src/images/fry.png").toURI().toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.microGroup = new Group(image, health, transformedDevices);
        this.microGroup.setLayoutX(this.posX);
        this.microGroup.setLayoutY(this.posY);
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
    public boolean equals(Object compared) {
        if (this == compared)
            return true;
        if (!(compared instanceof Fry comparedFry))
            return false;
        return this.name.equals(comparedFry.name) &&
                this.isBad == comparedFry.isBad &&
                this.healthValue == comparedFry.healthValue &&
                this.distanceTravelled == comparedFry.distanceTravelled &&
                this.devices.equals(comparedFry.devices);
    }
    @Override
    public String toString() {
        return "Fry{" +
                "name='" + name + '\'' +
                ", isBad=" + isBad +
                ", healthValue=" + healthValue +
                ", distanceTravelled=" + distanceTravelled +
                ", numberOfTransformedDevices=" + devices.size() +
                ", devices=" + devices +
                '}';
    }
}
