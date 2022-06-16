package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MomFriendlyRobots {
    private final ArrayList<RobotSanta> robotSantas;
    private final ArrayList<Device> transformedDevicesByTeam;
    private boolean hasGottenDevicesInfo = false;
    private ArrayList<Device> allDevicesFromWorld;
    private final HashMap<RobotSanta, Device> processedDevices;
    private final double posX;
    private final double posY;
    private final double width;
    private final double height;
    private ImageView imageView;
    private final Rectangle adjacentArea;
    private final Text officeName;
    private final Group momCorpArea;

    {
        posX = 3765;
        posY = 2335;
        width = 225;
        height = 409;
    }
    public MomFriendlyRobots() {
        robotSantas = new ArrayList<>();
        transformedDevicesByTeam = new ArrayList<>();

        processedDevices = new HashMap<>();
        officeName = new Text("Mom Friendly Robots");
        officeName.setFont(new Font("Arial", 20));
        officeName.setFill(Color.WHITE);
        officeName.setX(this.posX + 10);
        officeName.setY(this.posY + 25);

        try {
            imageView = new ImageView(new File("src/images/momcorp.png").toURI().toString());
        } catch (Exception e) {
            System.out.println("Error");
        }
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height - 110);
        imageView.setX(this.posX);
        imageView.setY(this.posY + 30);

        adjacentArea = new Rectangle(this.width, this.height);
        adjacentArea.setFill(Color.rgb(167, 181, 146));
        adjacentArea.setOpacity(0.4);
        adjacentArea.setX(this.posX);
        adjacentArea.setY(this.posY);
        adjacentArea.setArcHeight(40);
        adjacentArea.setArcWidth(40);

        momCorpArea = new Group(imageView, adjacentArea, officeName);
    }
    public void addRobotSanta(RobotSanta newRobotSanta) {
        robotSantas.add(newRobotSanta);
        processedDevices.put(newRobotSanta, null);
    }
    public void removeRobotSanta(RobotSanta robotSanta) {
        robotSantas.remove(robotSanta);
        processedDevices.remove(robotSanta);
    }
    public void addTransformedDeviceBySantas(Device device) {
        if (!transformedDevicesByTeam.contains(device) && device != null) {
            transformedDevicesByTeam.add(device);
        }
    }
    public void lifeCycle() {
        int indexForDevicesCollection = 0;
        if (!hasGottenDevicesInfo) {
            allDevicesFromWorld = (ArrayList<Device>) Main.getWorld().getDevices().clone();
            allDevicesFromWorld.sort(Device::compareTo);
            allDevicesFromWorld.sort(Collections.reverseOrder());
            hasGottenDevicesInfo = true;
        }
        allDevicesFromWorld.removeIf(device -> device.getStatus() != Device.DeviceStatus.UNDEFINED);
        // Setting targets for each robot
        for (RobotSanta rs : robotSantas) {
            if (robotSantas.size() >= allDevicesFromWorld.size() && allDevicesFromWorld.size() > 0) {
                processedDevices.put(rs, allDevicesFromWorld.get(0));
            } else if (allDevicesFromWorld.size() == 0) {
                processedDevices.put(rs, null);
            } else {
                processedDevices.put(rs, allDevicesFromWorld.get(indexForDevicesCollection++));
            }
        }
        // Checking if one of the robots is injured
        for (RobotSanta rs : robotSantas) {
            if (rs.getHealthValue() <= 0.4 * rs.getInitialHealthValue() && !rs.isOnBase()) {
                rs.moveToBase();
                processedDevices.remove(rs);
            }
        }
        // Checking if processing units still UNDEFINED
        for (Device device : allDevicesFromWorld) {
            if (device.getStatus() != Device.DeviceStatus.UNDEFINED && processedDevices.containsValue(device)) {
                for (RobotSanta r : processedDevices.keySet()) {
                    if (processedDevices.get(r).equals(device)) {
                        processedDevices.put(r, null);
                    }
                }
            }
        }
        // Move to targets
        processedDevices.forEach((robotSanta, device) -> robotSanta.moveTo(device));
        // Checking if there are non-transformed units
        if (allDevicesFromWorld.isEmpty()) {
            robotSantas.forEach(robotSanta -> robotSanta.moveToBase());
        }
    }
    public ArrayList<RobotSanta> getRobotSantas() {
        return robotSantas;
    }
    public ArrayList<Device> getTransformedDevicesByTeam() {
        return transformedDevicesByTeam;
    }
    public double getPosX() {
        return posX;
    }
    public double getPosY() {
        return posY;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public ImageView getImageView() {
        return imageView;
    }
    public Rectangle getAdjacentArea() {
        return adjacentArea;
    }
    public Text getOfficeName() {
        return officeName;
    }
    public Group getMomCorpArea() {
        return momCorpArea;
    }
}
