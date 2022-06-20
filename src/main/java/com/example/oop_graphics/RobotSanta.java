package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class RobotSanta extends Fry {
    private static final int initialHealthValue = 200;
    private static final int hurtRate = 7;
    private static final int regenerateRate = 10;
    private static final double step = 12.0;

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
    private Device makeBombFromThisDevice(Device device) {
        if (!getDevices().contains(device) && device.changeStatusBecauseOf(this)) {
            getDevices().add(device);
            getTransformedDevices().setText(Integer.toString(getDevices().size()));
            return device;
        }
        return null;
    }
    @Override
    public Device interactWithMacro(Device device) {
        return makeBombFromThisDevice(device);
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
                this.getHealth().setEndX((getHealthValue() * (getWidth() - 10)) / getInitialHealthValue() + getHealth().getStartX());
            } else {
                this.setHealthValue(getHealthValue() + getRegenerateRate());
                this.getHealth().setEndX((getHealthValue() * (getWidth() - 10)) / getInitialHealthValue() + getHealth().getStartX());
            }
        }
    }
    @Override
    public void moveToBase() {
        if (isActive()) {
            return;
        }
        if (getPosY() <= Main.getWorld().getMomFriendlyRobots().getPosY() + getStep()) {
            moveDown();
        } else if (getPosY() >= Main.getWorld().getMomFriendlyRobots().getPosY() + Main.getWorld().getMomFriendlyRobots().getHeight() / 2 - getStep()) {
            moveUp();
        } else if (getPosY() >= Main.getWorld().getMomFriendlyRobots().getPosY() && getPosY() < Main.getWorld().getMomFriendlyRobots().getPosY() + Main.getWorld().getMomFriendlyRobots().getHeight()) {
            if (getPosX() <= Main.getWorld().getMomFriendlyRobots().getPosX() + getStep()) {
                moveRight();
            } else if (getPosX() >= Main.getWorld().getMomFriendlyRobots().getPosX() + Main.getWorld().getMomFriendlyRobots().getHeight() / 2 - getStep()) {
                moveLeft();
            }
        }
    }
    @Override
    public boolean isOnBase() {
        return this.getMicroGroup().getBoundsInParent().intersects(Main.getWorld().getMomFriendlyRobots().getMomCorpArea().getBoundsInParent());
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
        return "RobotSanta{" +
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
