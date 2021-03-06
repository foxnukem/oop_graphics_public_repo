package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Nibblonian implements Cloneable, Comparable<Nibblonian> {
    private static final int initialHealthValue = 100;
    private static final int hurtRate = 5;
    private static final int regenerateRate = 10;
    private static final double step = 7.0;

    private String name;
    private final long id;
    protected boolean isBad;
    private boolean isActive;
    private int healthValue;
    private double distanceTravelled;
    private ArrayList<Device> devices;
    private double posX;
    private double posY;
    private double width;
    private double height;
    private ImageView image;
    private Line health;
    private Rectangle border;
    private final Text transformedDevices;
    private final Text objectId;
    private Group microGroup;

    public Nibblonian(String name, double initialPosX, double initialPosY) {
        this.name = name;
        id = Main.startId++;
        healthValue = getInitialHealthValue();
        distanceTravelled = 0.0;
        devices = new ArrayList<>();
        isBad = false;
        transformedDevices = new Text("0");
        objectId = new Text(Long.toString(id));

        posX = initialPosX;
        posY = initialPosY;
        width = 54;
        height = 70;
        try {
            image = new ImageView(new Image(new File("src/images/nibblonian.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        border = new Rectangle(0, 0, this.width, this.height);
        border.setStrokeWidth(6.5);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.PURPLE);
        border.setOpacity(0);

        health = new Line();
        health.setStroke(Color.GREEN);
        health.setStrokeWidth(5);
        health.setStartX(5);
        health.setStartY(10);
        health.setEndX(health.getStartX() + width - 10);
        health.setEndY(health.getStartY());

        transformedDevices.setX(5);
        transformedDevices.setY(10);

        objectId.setX(30);
        objectId.setY(5);
        objectId.setFill(Color.AZURE);
        objectId.setOpacity(0);

        microGroup = new Group(image, health, transformedDevices, border, objectId);
        microGroup.setLayoutX(posX);
        microGroup.setLayoutY(posY);
        microGroup.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> activateDeactivateBorder());
    }
    public Nibblonian(double initialPosX, double initialPosY, boolean isActive) {
        this("??????????", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public Nibblonian() {
        this("??????????", Main.getWorld().getPlanetExpressOffice().getPosX() + Main.getWorld().getPlanetExpressOffice().getWidth() - (Main.startId % 10) * 20, Main.getWorld().getPlanetExpressOffice().getPosY() + (Main.startId % 100) * 20);
    }

    public void moveUp() {
        this.posY -= this.getStep() + Main.speed;
        if (this.posY <= 0.0) {
            this.posY = 0.0;
        } else {
            distanceTravelled += getStep() + Main.speed;
        }
        this.setPosY(posY);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutY(this.posY * MiniMap.getScale().getY());
    }
    public void moveDown() {
        this.posY += this.getStep() + Main.speed;
        if (this.posY >= NewNewYork.getRootHeight() - this.height) {
            this.posY = NewNewYork.getRootHeight() - this.height;
        } else {
            distanceTravelled += getStep() + Main.speed;
        }
        this.setPosY(this.posY);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutY(this.posY * MiniMap.getScale().getY());
    }
    public void moveLeft() {
        this.posX -= this.getStep() + Main.speed;
        if (this.posX <= 0.0) {
            this.posX = 0.0;
        } else {
            distanceTravelled += getStep() + Main.speed;
        }
        this.setPosX(this.posX);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutX(this.posX * MiniMap.getScale().getX());
    }
    public void moveRight() {
        this.posX += this.getStep() + Main.speed;
        if (this.posX >= NewNewYork.getRootWidth() - this.width) {
            this.posX = NewNewYork.getRootWidth() - this.width;
        } else {
            distanceTravelled += getStep() + Main.speed;
        }
        this.setPosX(this.posX);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutX(this.posX * MiniMap.getScale().getX());
    }
    private Device markThisDeviceAsSafe(Device device) {
        if (!devices.contains(device) && device.changeStatusBecauseOf(this)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
            return device;
        }
        return null;
    }
    public void getHurt() {
        if ((this.healthValue - getHurtRate()) > 0) {
            this.healthValue -= getHurtRate();
            this.health.setEndX((healthValue * (width - 10)) / getInitialHealthValue() + health.getStartX());
        } else {
            this.healthValue = 0;
            Main.getWorld().removeCitizen(this);
        }
    }
    public void regenerate() {
        if (this.getMicroGroup().getBoundsInParent().intersects(Main.getWorld().getPlanetExpressOffice().getPlanetExpressArea().getBoundsInParent())) {
            if ((this.healthValue + getRegenerateRate()) >= getInitialHealthValue()) {
                this.healthValue = getInitialHealthValue();
                this.health.setEndX((healthValue * (width - 10)) / getInitialHealthValue() + health.getStartX());
            } else {
                this.healthValue += getRegenerateRate();
                this.health.setEndX((healthValue * (width - 10)) / getInitialHealthValue() + health.getStartX());
            }
        }
    }
    public void damageOtherSide() {
        for (Nibblonian citizen : Main.getWorld().getCitizens()) {
            if (!this.equals(citizen) && this.getMicroGroup().getBoundsInParent().intersects(citizen.getMicroGroup().getBoundsInParent()) && citizen instanceof RobotSanta) {
                citizen.getHurt();
                this.getHurt();
            }
        }
    }
    public Device interactWithMacro(Device device) {
        return markThisDeviceAsSafe(device);
    }
    public void setActive(boolean active) {
        isActive = active;
        if (isActive) {
            activate();
        }
    }
    public void cancelActivation() {
        isActive = false;
        border.setOpacity(0);
        objectId.setOpacity(0);
    }
    public void activate() {
        isActive = true;
        border.setOpacity(1);
        objectId.setOpacity(1);
    }
    private void activateDeactivateBorder() {
        isActive = !isActive;
        if (isActive) {
            activate();
        }
        if (!isActive) {
            cancelActivation();
        }
    }
    public void interactionWithWorld(int time) {
        for (Device device : Main.getWorld().getDevices()) {
            if (this.getMicroGroup().getBoundsInParent().intersects(device.getMacroGroup().getBoundsInParent())) {
                if (this instanceof RobotSanta) {
                    Main.getWorld().getMomFriendlyRobots().addTransformedDeviceBySantas(interactWithMacro(device));
                } else {
                    Main.getWorld().getPlanetExpressOffice().addTransformedDeviceByTeam(interactWithMacro(device));
                }
            }
        }
        if (time % 5 == 0) {
            regenerate();
            damageOtherSide();
        }
    }
    public void moveTo(Device targetDevice) {
        if (isActive || getDevices().contains(targetDevice) || targetDevice == null) {
            return;
        }
        if (posY <= targetDevice.getPosY() + getStep()) {
            moveDown();
        } else if (posY >= targetDevice.getPosY() + targetDevice.getHeight() / 2 - getStep()) {
            moveUp();
        } else if (posY >= targetDevice.getPosY() && posY < targetDevice.getPosY() + targetDevice.getHeight()) {
            if (posX <= targetDevice.getPosX() + getStep()) {
                moveRight();
            } else if (posX >= targetDevice.getPosX() + targetDevice.getHeight() / 2 - getStep()) {
                moveLeft();
            }
        }
    }
    public void moveToBase() {
        if (isActive()) {
            return;
        }
        if (getPosY() <= Main.getWorld().getPlanetExpressOffice().getPosY() + getStep()) {
            moveDown();
        } else if (getPosY() >= Main.getWorld().getPlanetExpressOffice().getPosY() + Main.getWorld().getPlanetExpressOffice().getHeight() / 2 - getStep()) {
            moveUp();
        } else if (getPosY() >= Main.getWorld().getPlanetExpressOffice().getPosY() && getPosY() < Main.getWorld().getPlanetExpressOffice().getPosY() + Main.getWorld().getPlanetExpressOffice().getHeight()) {
            if (getPosX() <= Main.getWorld().getPlanetExpressOffice().getPosX() + getStep()) {
                moveRight();
            } else if (getPosX() >= Main.getWorld().getPlanetExpressOffice().getPosX() + Main.getWorld().getPlanetExpressOffice().getHeight() / 2 - getStep()) {
                moveLeft();
            }
        }
    }
    public boolean isOnBase() {
        return this.getMicroGroup().getBoundsInParent().intersects(Main.getWorld().getPlanetExpressOffice().getPlanetExpressArea().getBoundsInParent());
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getId() {
        return id;
    }
    public boolean isBad() {
        return isBad;
    }
    public void setBad(boolean bad) {
        isBad = bad;
    }
    public boolean isActive() {
        return isActive;
    }
    public double getPosX() {
        return posX;
    }
    public void setPosX(double posX) {
        this.posX = posX;
        this.microGroup.setLayoutX(posX);
    }
    public double getPosY() {
        return posY;
    }
    public void setPosY(double posY) {
        this.posY = posY;
        this.microGroup.setLayoutY(posY);
    }
    public double getWidth() {
        return width;
    }
    public void setWidth(double width) {
        this.width = width;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public int getHealthValue() {
        return healthValue;
    }
    public void setHealthValue(int healthValue) {
        this.healthValue = healthValue;
    }
    public double getDistanceTravelled() {
        return distanceTravelled;
    }
    public void setDistanceTravelled(double distanceTravelled) {
        this.distanceTravelled = distanceTravelled;
    }
    public ArrayList<Device> getDevices() {
        return devices;
    }
    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
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
    public ImageView getImageView() {
        return image;
    }
    public void setImage(ImageView image) {
        this.image = image;
        microGroup.getChildren().removeAll(microGroup.getChildren());
        microGroup.getChildren().addAll(this.image, health, transformedDevices, border, objectId);
    }
    public Line getHealth() {
        return health;
    }
    public void setHealth(Line health) {
        this.health = health;
    }
    public Text getTransformedDevices() {
        return transformedDevices;
    }
    public Rectangle getBorder() {
        return border;
    }
    public void setBorder(Rectangle border) {
        this.border = border;
    }
    public Group getMicroGroup() {
        return microGroup;
    }
    public void setMicroGroup(Group microGroup) {
        this.microGroup = microGroup;
    }
    public String getInfo() {
        StringBuilder result = new StringBuilder();
        String[] info = new String[7];
        AtomicInteger indexThroughArray = new AtomicInteger(0);
        Long[] idOfDevices = new Long[devices.size()];
        devices.forEach(device -> idOfDevices[indexThroughArray.getAndIncrement()] = device.getId());
        Arrays.sort(idOfDevices);
        info[0] = "??????????????????????????: " + getId() + "\n";
        info[1] = "????'??: " + getName() + "\n";
        info[2] = "X: " + getPosX() + "\n";
        info[3] = "Y: " + getPosY() + "\n";
        info[4] = "????????????'??: " + getHealthValue() + "\n";
        info[5] = "???????????????? ????????????????: " + getDistanceTravelled() + "\n";
        info[6] = "???????????????????????? ????????????????: " + Arrays.toString(idOfDevices) + "\n\n";
        for (String i : info) {
            result.append(i);
        }
        return result.toString();
    }
    @Override
    public int compareTo(Nibblonian n) {
        if (this.devices.size() < n.devices.size()) {
            return -1;
        } else if (this.devices.size() > n.devices.size()) {
            return 1;
        }
        return 0;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Nibblonian that)) return false;
        return id == that.id && isBad == that.isBad && isActive == that.isActive && healthValue == that.healthValue && Double.compare(that.distanceTravelled, distanceTravelled) == 0 && Double.compare(that.posX, posX) == 0 && Double.compare(that.posY, posY) == 0 && Double.compare(that.width, width) == 0 && Double.compare(that.height, height) == 0 && Objects.equals(name, that.name) && Objects.equals(devices, that.devices) && Objects.equals(image, that.image) && Objects.equals(health, that.health) && Objects.equals(border, that.border) && Objects.equals(transformedDevices, that.transformedDevices) && Objects.equals(microGroup, that.microGroup);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, id, isBad, image);
    }
    @SuppressWarnings("unchecked")
    @Override
    public Nibblonian clone() throws CloneNotSupportedException {
        Nibblonian clone = (Nibblonian) super.clone();
        clone.devices = (ArrayList<Device>) this.devices.clone();
        clone.isActive = this.isActive;
        clone.microGroup = this.microGroup;
        return clone;
    }
    @Override
    public String toString() {
        return "Nibblonian{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", isBad=" + isBad +
                ", isActive=" + isActive +
                ", healthValue=" + healthValue +
                ", distanceTravelled=" + distanceTravelled +
                ", posX=" + posX +
                ", posY=" + posY +
                ", devices=" + devices +
                '}';
    }
}
