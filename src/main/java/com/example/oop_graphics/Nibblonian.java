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
import java.util.Objects;

public class Nibblonian implements Cloneable, Comparable<Nibblonian> {
    private static final int initialHealthValue = 100;
    private static final int hurtRate = 2;
    private static final int regenerateRate = 10;
    private static final double step = 7.0;
    private static final double speed = 0.0;

    protected String name;
    protected long id;
    protected boolean isBad;
    protected boolean isActive;
    protected int healthValue;
    protected double distanceTravelled;
    protected ArrayList<Device> devices;

    protected double posX;
    protected double posY;
    protected double width;
    protected double height;
    protected ImageView image;
    protected Line health;
    protected Rectangle border;
    protected Text transformedDevices;
    protected Text objectId;
    protected Group microGroup;

    protected void moveTo(double x, double y) {

    }
    public void moveUp() {
        this.posY -= getStep();
        if (this.posY <= 0.0) {
            this.posY = 0.0;
        }
        this.setPosY(this.posY);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutY(this.posY * MiniMap.getScale().getY());
    }
    public void moveDown() {
        this.posY += getStep();
        if (this.posY >= NewNewYork.getRootHeight() - this.height) {
            this.posY = NewNewYork.getRootHeight() - this.height;
        }
        this.setPosY(this.posY);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutY(this.posY * MiniMap.getScale().getY());
    }
    public void moveLeft() {
        this.posX -= getStep();
        if (this.posX <= 0.0) {
            this.posX = 0.0;
        }
        this.setPosX(this.posX);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutX(this.posX * MiniMap.getScale().getX());
    }
    public void moveRight() {
        this.posX += getStep();
        if (this.posX >= NewNewYork.getRootWidth() - this.width) {
            this.posX = NewNewYork.getRootWidth() - this.width;
        }
        this.setPosX(this.posX);
        Main.getWorld().getMiniMap().getCitizensMap().get(this).setLayoutX(this.posX * MiniMap.getScale().getX());
    }
    protected void markDeviceAsSafe(Device device) {
        if (!this.devices.contains(device) && device.setStatus(this, Device.DeviceStatus.SAFE)) {
            devices.add(device);
            transformedDevices.setText(Integer.toString(devices.size()));
        }
    }
    public void getHurt() {
        if ((this.healthValue - getHurtRate()) > 0) {
            this.healthValue -= getHurtRate();
        } else {
            this.healthValue = 0;
        }
    }
    public void regenerate() {
        if ((this.healthValue + getRegenerateRate()) >= getInitialHealthValue()) {
            this.healthValue = getInitialHealthValue();
        } else {
            this.healthValue += getRegenerateRate();
        }
    }
    public void damageOther(RobotSanta robotSanta) {
        robotSanta.getHurt();
    }

    public void interactWithMacro(Device device) {
        markDeviceAsSafe(device);
    }
    public Nibblonian(String name, double initialPosX, double initialPosY) {
        this.name = name;
        this.id = Main.startId++;
        this.healthValue = getInitialHealthValue();
        this.distanceTravelled = 0.0;
        this.devices = new ArrayList<>();
        this.isBad = false;
        this.transformedDevices = new Text("0");
        this.objectId = new Text(Long.toString(id));

        this.posX = initialPosX;
        this.posY = initialPosY;
        this.width = 54;
        this.height = 70;
        try {
            this.image = new ImageView(new Image(new File("src/images/nibblonian.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.border = new Rectangle(0, 0, this.width, this.height);
        this.border.setStrokeWidth(6.5);
        this.border.setFill(Color.TRANSPARENT);
        this.border.setStroke(Color.PURPLE);
        this.border.setOpacity(0);

        this.health = new Line();
        this.health.setStroke(Color.GREEN);
        this.health.setStrokeWidth(5);
        this.health.setStartX(this.health.getStartX() + 5);
        this.health.setStartY(this.health.getStartY() + 10);
        this.health.setEndX(this.health.getStartX() + this.width - 10);
        this.health.setEndY(this.health.getStartY());

        this.transformedDevices.setX(5);
        this.transformedDevices.setY(10);

        this.objectId.setX(30);
        this.objectId.setY(5);
        this.objectId.setFill(Color.AZURE);
        this.objectId.setOpacity(0);

        this.microGroup = new Group(health, transformedDevices, border, objectId);
        this.microGroup.getChildren().add(image);
        this.microGroup.setLayoutX(this.posX);
        this.microGroup.setLayoutY(this.posY);
        this.microGroup.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> activateDeactivateBorder());
    }

    public Nibblonian(double initialPosX, double initialPosY, boolean isActive) {
        this("Жуйка", initialPosX, initialPosY);
        this.setActive(isActive);
    }
    public Nibblonian() {
        this("Жуйка", 1000, 1000);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setActive(boolean active) {
        isActive = active;
        if (isActive) {
            activate();
        }
    }

    private void activateDeactivateBorder() {
        this.isActive = !this.isActive;
        if (this.isActive) {
            activate();
        }
        if (!this.isActive) {
            cancelActivation();
        }
    }
    public void cancelActivation() {
        this.isActive = false;
        this.border.setOpacity(0);
        this.objectId.setOpacity(0);
    }
    public void activate() {
        this.isActive = true;
        this.border.setOpacity(1);
        this.objectId.setOpacity(1);
    }
    //TODO make to work this damn method (btw Cloning)
    public void respawn() {
        double posXOfOffice = Main.getWorld().getPlanetExpressOffice().getPosX() + Main.getWorld().getPlanetExpressOffice().getWidth();
        double posYOfOffice = Main.getWorld().getPlanetExpressOffice().getPosY();
        Nibblonian n;
        try {
            n = this.clone();
            n.setPosX(posXOfOffice);
            n.setPosY(posYOfOffice);
            while (n.getHealthValue() < getInitialHealthValue()) {
                n.regenerate();
            }
            n.setName("Клон " + n.getName());
            System.out.println(Main.getWorld().getCitizens());
            Main.getWorld().removeCitizen(this);
            Main.getWorld().addCitizen(n);
            System.out.println("\n" + Main.getWorld().getCitizens());
        } catch (CloneNotSupportedException e) {
            System.out.println("Клонування не відбулось");
        }
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
    public double getSpeed() {
        return speed;
    }

    @Override
    public int compareTo(Nibblonian n) {
        if (this.devices.size() == n.devices.size() && this.healthValue < n.healthValue) {
            return -1;
        } else if (this.devices.size() < n.devices.size() && this.healthValue == n.healthValue) {
            return -1;
        } else if (this.devices.size() == n.devices.size() && this.healthValue > n.healthValue) {
            return 1;
        } else if (this.devices.size() > n.devices.size() && this.healthValue == n.healthValue) {
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
                ", devices=" + devices +
                ", posX=" + posX +
                ", posY=" + posY +
                '}';
    }
    // print() method

    public void print() {
        System.out.println(this);
    }

    public ImageView getImageView() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Line getHealth() {
        return health;
    }

    public void setHealth(Line health) {
        this.health = health;
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
}
