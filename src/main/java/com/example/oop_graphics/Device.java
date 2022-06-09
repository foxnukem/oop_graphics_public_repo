package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.File;
import java.security.spec.ECField;
import java.util.Objects;

public class Device {
    public enum DeviceStatus {
        UNDEFINED,
        SAFE,
        ACTIVEBOMB,
        DESTROYEDBOMB,
        STOPPEDTIMER
    }
    private final Text statusInText;
    private DeviceStatus status;
    protected double xPosition;
    protected double yPosition;
    protected double width;
    protected double height;
    protected ImageView image;
    protected Circle border;

    public Group getMacroGroup() {
        return macroGroup;
    }

    public void setMacroGroup(Group macroGroup) {
        this.macroGroup = macroGroup;
    }

    protected Group macroGroup;
    public Device() {
        this.status = DeviceStatus.UNDEFINED;

        this.xPosition = 1500;
        this.yPosition = 1000;
        this.width = 200;
        this.height = 200;
        try {
            this.image = new ImageView(new Image(new File("images/device.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }
        this.image.setFitWidth(this.width - 50);
        this.image.setFitHeight(this.height - 50);

        this.border = new Circle(this.xPosition + this.width / 2, this.yPosition + this.height / 2, this.width / 2, Color.GRAY);
        this.border.setOpacity(0.3);

        this.statusInText = new Text(this.status.toString());
        this.macroGroup = new Group(image, border, statusInText);
        this.macroGroup.setLayoutX(this.xPosition);
        this.macroGroup.setLayoutY(this.yPosition);

    }
    public boolean setStatus(Nibblonian nibblonian, DeviceStatus status) {
        if (status.equals(DeviceStatus.SAFE)
            && !this.status.equals(DeviceStatus.ACTIVEBOMB)) {
            this.status = status;
            return true;
        } else if (this.status.equals(DeviceStatus.UNDEFINED)) {
            return false;
        } else {
            System.out.println("Не дозволено для об'єктів класу Nibblonian");
        }
        return false;
    }
    public boolean setStatus(Fry fry, DeviceStatus status) {
        if (status.equals(DeviceStatus.STOPPEDTIMER)
            && this.status.equals(DeviceStatus.ACTIVEBOMB)) {
            this.status = status;
            return true;
        } else if (status.equals(DeviceStatus.DESTROYEDBOMB)
                || this.status.equals(DeviceStatus.ACTIVEBOMB)) {
            System.out.println("Не дозволено для об'єктів класу Fry");
        }
        return setStatus((Nibblonian) fry, status);
    }
    public boolean setStatus(RobotBender robotBender, DeviceStatus status) {
        if (status.equals(DeviceStatus.DESTROYEDBOMB)
            && this.status.equals(DeviceStatus.ACTIVEBOMB)) {
            this.status = status;
            return true;
        }
        return setStatus((Fry)robotBender, status);
    }
    public boolean setStatus(RobotSanta robotSanta, DeviceStatus status) {
        if (status.equals(DeviceStatus.ACTIVEBOMB)
            && this.status.equals(DeviceStatus.UNDEFINED)) {
            this.status = status;
            return true;
        } else if (!status.equals(DeviceStatus.UNDEFINED)) {
            System.out.println("Не дозволено для об'єктів класу RobotSanta");
        }
        return false;
    }
    public DeviceStatus getStatus() {
        return status;
    }
    @Override
    public int hashCode() {
        return Objects.hash(statusInText, status, xPosition, yPosition, width, height, image, border, macroGroup);
    }

    @Override
    public String toString() {
        return Integer.toString(this.hashCode());
    }
}
