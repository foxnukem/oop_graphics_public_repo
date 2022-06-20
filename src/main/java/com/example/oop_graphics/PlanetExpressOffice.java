package com.example.oop_graphics;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PlanetExpressOffice {
    private final ArrayList<Nibblonian> teamMembers;
    private final ArrayList<Device> transformedDevicesByTeam;
    private boolean hasGottenDevicesInfo = false;
    private ArrayList<Device> allDevicesFromWorld;
    private final HashMap<Nibblonian, Device> processedDevices;
    private final double posX;
    private final double posY;
    private final double width;
    private final double height;
    private ImageView imageView;
    private final Rectangle adjacentArea;
    private final Text officeName;
    private final Group planetExpressArea;

    {
        processedDevices = new HashMap<>();
    }

    public PlanetExpressOffice() {
        teamMembers = new ArrayList<>();
        transformedDevicesByTeam = new ArrayList<>();
        posX = 15;
        posY = 610;
        width = 445;
        height = 510;

        officeName = new Text("Planet Express");
        officeName.setFont(new Font("Arial", 20));
        officeName.setFill(Color.WHITE);
        officeName.setX(this.posX + 10);
        officeName.setY(this.posY + 25);
        try {
            imageView = new ImageView(new File("src/images/planetexpress.png").toURI().toString());
        } catch (Exception e) {
            System.out.println("Error");
        }
        imageView.setFitWidth(this.width);
        imageView.setFitHeight(this.height - 110);
        imageView.setX(this.posX);
        imageView.setY(this.posY + 30);

        adjacentArea = new Rectangle(this.width, this.height);
        adjacentArea.setFill(Color.rgb(182, 216, 155));
        adjacentArea.setOpacity(0.4);
        adjacentArea.setX(this.posX);
        adjacentArea.setY(this.posY);
        adjacentArea.setArcHeight(40);
        adjacentArea.setArcWidth(40);

        planetExpressArea = new Group(imageView, adjacentArea, officeName);
    }
    public void addTeamMember(Nibblonian newTeamMember) {
        if (!newTeamMember.isBad()) {
            teamMembers.add(newTeamMember);
        }
    }
    public void removeTeamMember(Nibblonian teamMember) {
        teamMembers.remove(teamMember);
        processedDevices.remove(teamMember);
    }
    public void addTransformedDeviceByTeam(Device device) {
        if (!transformedDevicesByTeam.contains(device) && device != null) {
            transformedDevicesByTeam.add(device);
        }
    }
    public void lifeCycle(int frame) {
        int indexForDevicesCollection = 0;
        if (!hasGottenDevicesInfo) {
            allDevicesFromWorld = (ArrayList<Device>) Main.getWorld().getDevices().clone();
            allDevicesFromWorld.sort(Device::compareTo);
            hasGottenDevicesInfo = true;
        }
        allDevicesFromWorld.removeIf(device -> device.getStatus() == Device.DeviceStatus.SAFE || device.getStatus() == Device.DeviceStatus.STOPPEDTIMER || device.getStatus() == Device.DeviceStatus.DESTROYEDBOMB);
        // Setting targets for each team member
        for (Nibblonian n : teamMembers) {
            if (allDevicesFromWorld.size() == 0) {
                processedDevices.put(n, null);
            } else if (allDevicesFromWorld.get(0).getStatus() == Device.DeviceStatus.ACTIVEBOMB && n instanceof Fry || n instanceof RobotBender && teamMembers.size() >= allDevicesFromWorld.size() && allDevicesFromWorld.size() > 0) {
                processedDevices.put(n, allDevicesFromWorld.get(0));
            } else if (allDevicesFromWorld.get(0).getStatus() == Device.DeviceStatus.UNDEFINED && n instanceof Nibblonian && teamMembers.size() >= allDevicesFromWorld.size() && allDevicesFromWorld.size() > 0) {
                processedDevices.put(n, allDevicesFromWorld.get(0));
            } else {
                if (allDevicesFromWorld.get(indexForDevicesCollection).getStatus() == Device.DeviceStatus.UNDEFINED && n instanceof Nibblonian) {
                    processedDevices.put(n, allDevicesFromWorld.get(indexForDevicesCollection++));
                } else if (allDevicesFromWorld.get(indexForDevicesCollection).getStatus() == Device.DeviceStatus.ACTIVEBOMB && n instanceof Fry || n instanceof RobotBender) {
                    processedDevices.put(n, allDevicesFromWorld.get(indexForDevicesCollection++));
                }
            }
        }
        // Checking if one of the team members is injured
        for (Nibblonian n : teamMembers) {
            if (n.getHealthValue() <= 0.4 * n.getInitialHealthValue() && !n.isOnBase()) {
                n.moveToBase();
                processedDevices.remove(n);
            }
        }
        // Checking current status of each unit
        for (Device device : allDevicesFromWorld) {
            for (Nibblonian n : teamMembers) {
                if (!processedDevices.containsValue(device) && device.getStatus() == Device.DeviceStatus.UNDEFINED) {
                    processedDevices.put(n, device);
                } else if (n instanceof Fry && !processedDevices.containsValue(device) && device.getStatus() == Device.DeviceStatus.ACTIVEBOMB) {
                    processedDevices.put(n, device);
                }
            }
        }
        // Move to targets
        processedDevices.forEach((teamMember, device) -> teamMember.moveTo(device));
        // Checking if there are non-transformed units
        AtomicInteger indexOfTheBooleanArray = new AtomicInteger();
        Boolean[] isAllTeamMembersOnTheBase = new Boolean[teamMembers.size()];
        if (allDevicesFromWorld.isEmpty()) {
            teamMembers.forEach(teamMember -> teamMember.moveToBase());
            teamMembers.forEach(teamMember -> isAllTeamMembersOnTheBase[indexOfTheBooleanArray.getAndIncrement()] = teamMember.isOnBase());
            // Solution: https://stackoverflow.com/questions/8260881/what-is-the-most-elegant-way-to-check-if-all-values-in-a-boolean-array-are-true
            Main.setIsAutoMoveEnabledForPlanetExpressTeam(!Arrays.asList(isAllTeamMembersOnTheBase).stream().allMatch(Boolean::booleanValue));
        }
    }
    public ArrayList<Nibblonian> getTeamMembers() {
        return teamMembers;
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
    public Group getPlanetExpressArea() {
        return planetExpressArea;
    }
}
