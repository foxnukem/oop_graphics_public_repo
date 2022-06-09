package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.File;

public class RobotBender extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 5;
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
    public RobotBender(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        this.width = 54;
        this.height = 70;
        try {
            this.image = new ImageView(new Image(new File("src/images/bender.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.microGroup = new Group(image, health, transformedDevices, border);
        this.microGroup.setLayoutX(this.posX);
        this.microGroup.setLayoutY(this.posY);
        this.microGroup.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            activateDeactivateBorder();
        });
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
    @Override
    public boolean equals(Object compared) {
        if (this == compared)
            return true;
        if (!(compared instanceof RobotBender comparedRobotBender))
            return false;
        return this.name.equals(comparedRobotBender.name) &&
                this.isBad == comparedRobotBender.isBad &&
                this.healthValue == comparedRobotBender.healthValue &&
                this.distanceTravelled == comparedRobotBender.distanceTravelled &&
                this.devices.equals(comparedRobotBender.devices);
    }
    @Override
    public String toString() {
        return "RobotBender{" +
                "name='" + name + '\'' +
                ", isBad=" + isBad +
                ", healthValue=" + healthValue +
                ", distanceTravelled=" + distanceTravelled +
                ", numberOfTransformedDevices=" + devices.size() +
                ", devices=" + devices +
                '}';
    }
}
