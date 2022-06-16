package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Fry extends Nibblonian {
    private static final int initialHealthValue = 150;
    private static final int hurtRate = 5;
    private static final int regenerateRate = 8;
    private static final double step = 8.0;
    public Fry(String name, double initialPosX, double initialPosY) {
        super(name, initialPosX, initialPosY);
        try {
            setImage(new ImageView(new Image(new File("src/images/fry.png").toURI().toString())));
        } catch (Exception e) {
            System.out.println("Error");
        }
    }
    public Fry(double initialPosX, double initialPoxY, boolean isActive) {
        this("Фрай", initialPosX, initialPoxY);
        this.setActive(isActive);
    }
    public Fry() {
        this("Фрай", Main.getWorld().getPlanetExpressOffice().getPosX() + Main.getWorld().getPlanetExpressOffice().getWidth() - (Main.startId % 10) * 20, Main.getWorld().getPlanetExpressOffice().getPosY() + (Main.startId % 100) * 21);
    }
    private Device stopTimerOnThisDevice(Device device) {
        if (!getDevices().contains(device) && device.changeStatusBecauseOf(this)) {
            getDevices().add(device);
            getTransformedDevices().setText(Integer.toString(getDevices().size()));
            return device;
        }
        return null;
    }
    @Override
    public void getHurt() {
        super.getHurt();
    }
    @Override
    public Device interactWithMacro(Device device) {
        return stopTimerOnThisDevice(device);
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
        return "Fry{" +
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
