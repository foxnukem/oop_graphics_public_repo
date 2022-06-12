package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.File;
import java.util.Objects;

public class RobotSanta extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 10;
    private static final int regenerateRate = 10;
    private static final double step = 15.0;
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
    public RobotSanta(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        this.isBad = true;
        try {
            this.image = new ImageView(new Image(new File("src/images/santa.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.microGroup.getChildren().removeAll(this.microGroup.getChildren());
        this.microGroup.getChildren().addAll(this.image, this.health, this.transformedDevices, this.border, this.objectId);
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
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof RobotSanta)) return false;
//        RobotSanta that = (RobotSanta) o;
//        return id == that.id && isBad == that.isBad && isActive == that.isActive && healthValue == that.healthValue && Double.compare(that.distanceTravelled, distanceTravelled) == 0 && Double.compare(that.posX, posX) == 0 && Double.compare(that.posY, posY) == 0 && Double.compare(that.width, width) == 0 && Double.compare(that.height, height) == 0 && Objects.equals(name, that.name) && Objects.equals(devices, that.devices) && Objects.equals(image, that.image) && Objects.equals(health, that.health) && Objects.equals(border, that.border) && Objects.equals(transformedDevices, that.transformedDevices) && Objects.equals(microGroup, that.microGroup);
//    }
    @Override
    public String toString() {
        return "RobotSanta{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isBad=" + isBad +
                ", isActive=" + isActive +
                ", healthValue=" + healthValue +
                ", distanceTravelled=" + distanceTravelled +
                ", devices=" + devices +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }
}
