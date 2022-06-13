package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.Objects;

public class Device {
    public enum DeviceStatus {
        UNDEFINED,
        SAFE,
        ACTIVEBOMB,
        DESTROYEDBOMB,
        STOPPEDTIMER
    }
    private DeviceStatus status;
    private final static Color undefinedColor;
    private final static Color safeColor;
    private final static Color stoppedTimerColor;
    private final static Color activeColor;
    private final static Color destroyedColor;
    private final long id;

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    private double posX;
    private double posY;
    private final double width;
    private final double height;
    private final Text statusInText;
    private final Text deviceId;

    public ImageView getImage() {
        return image;
    }

    public Circle getBorder() {
        return border;
    }

    private ImageView image;
    private final Circle border;
    private final Group macroGroup;


    static {
       undefinedColor = Color.GRAY;
       safeColor = Color.GREEN;
       stoppedTimerColor = Color.PURPLE;
       activeColor = Color.RED;
       destroyedColor = Color.BROWN;

    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    {
        width = 200;
        height = 200;
    }
    public Group getMacroGroup() {
        return macroGroup;
    }

    public Device(double initialPosX, double initialPosY) {
        status = DeviceStatus.UNDEFINED;
        id = Main.deviceStartId++;

        posX = initialPosX;
        posY = initialPosY;

        try {
            image = new ImageView(new Image(new File("src/images/device.png").toURI().toString()));
        } catch (Exception e) {
            System.out.println("Error");
        }

        image.setFitWidth(width - 50);
        image.setFitHeight(height - 50);
        image.setX(posX + 25);
        image.setY(posY + 30);

        border = new Circle(posX + width / 2, posY + height / 2, width / 2, undefinedColor);
        border.setOpacity(0.3);

        statusInText = new Text(this.status.toString());
        statusInText.setFont(new Font("Arial", 12));
        statusInText.setFill(Color.ANTIQUEWHITE);
        statusInText.setX(this.posX + 45);
        statusInText.setY(this.posY + 35);

        deviceId = new Text(Long.toString(this.id));
        deviceId.setFont(new Font("Arial", 12));
        deviceId.setFill(Color.ANTIQUEWHITE);
        deviceId.setX(this.posX + this.width / 2 - 10);
        deviceId.setY(this.posY + this.height - 30);
        macroGroup = new Group(image, border, statusInText, deviceId);
        macroGroup.setLayoutX(this.posX);
        macroGroup.setLayoutY(this.posY);
    }
    public boolean setStatus(Nibblonian nibblonian, DeviceStatus status) {
        if (status.equals(DeviceStatus.SAFE)
            && !this.status.equals(DeviceStatus.ACTIVEBOMB)) {
            this.status = status;
            this.statusInText.setText(this.status.toString());
            this.border.setFill(safeColor);
            NewNewYork.update();
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
            this.statusInText.setText(this.status.toString());
            this.border.setFill(stoppedTimerColor);
            NewNewYork.update();
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
            this.statusInText.setText(this.status.toString());
            this.border.setFill(destroyedColor);
            NewNewYork.update();
            return true;
        }
        return setStatus((Fry)robotBender, status);
    }
    public boolean setStatus(RobotSanta robotSanta, DeviceStatus status) {
        if (status.equals(DeviceStatus.ACTIVEBOMB)
            && this.status.equals(DeviceStatus.UNDEFINED)) {
            this.status = status;
            this.statusInText.setText(this.status.toString());
            this.border.setFill(activeColor);
            NewNewYork.update();
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
        return Objects.hash(id, deviceId);
    }

    @Override
    public String toString() {
        return Integer.toString(this.hashCode());
    }

    public long getId() {
        return id;
    }
}
