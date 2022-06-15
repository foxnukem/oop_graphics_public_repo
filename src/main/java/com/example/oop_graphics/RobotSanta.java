package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class RobotSanta extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 7;
    private static final int regenerateRate = 10;
    private static final double step = 15.0;
    private static final double speed = 0.0;

    public RobotSanta(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        isBad = true;
        try {
            setImage(new ImageView(new Image(new File("src/images/santa.png").toURI().toString())));
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    public RobotSanta(double initialPosX, double initialPosY, boolean isActive) {
        this("Робот Санта", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public RobotSanta() {
        this("Робот Санта", Main.getWorld().getMomFriendlyRobots().getPosX() + (Main.startId % 10) * 10, Main.getWorld().getMomFriendlyRobots().getPosY() + (Main.startId % 10) * 35);
    }
    private void makeBombFromThisDevice(Device device) {
        if (!devices.contains(device) && device.changeStatusBecauseOf(this)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
        }
    }
    @Override
    public void interactWithMacro(Device device) {
        makeBombFromThisDevice(device);
    }

    // RobotSanta can damage other side objects
    @Override
    public void damageOtherSide() {
        for (Nibblonian citizen : Main.getWorld().getCitizens()) {
            if (!this.equals(citizen) && this.getMicroGroup().getBoundsInParent().intersects(citizen.getMicroGroup().getBoundsInParent()) && !(citizen instanceof RobotSanta)) {
                citizen.getHurt();
                this.getHurt();
            }
        }
    }
    // The difference is in the place where the object can regenerate
    @Override
    public void regenerate() {
        if (this.getMicroGroup().getBoundsInParent().intersects(Main.getWorld().getMomFriendlyRobots().getAdjacentArea().getBoundsInParent())) {
            if ((this.getHealthValue() + getRegenerateRate()) >= getInitialHealthValue()) {
                this.setHealthValue(getInitialHealthValue());
                this.health.setEndX((getHealthValue() * (getWidth() - 10)) / getInitialHealthValue() + health.getStartX());
            } else {
                this.setHealthValue(getHealthValue() + getRegenerateRate());
                this.health.setEndX((getHealthValue() * (getWidth() - 10)) / getInitialHealthValue() + health.getStartX());
            }
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
