package com.example.oop_graphics;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class NewNewYork {
    private final static Random random = new Random(System.currentTimeMillis());
    private static ArrayList<Device> devices;
    // Problem: https://stackoverflow.com/questions/37104215/error-exception-in-thread-javafx-application-thread
    // Solution:https://stackoverflow.com/questions/6916385/is-there-a-concurrent-list-in-javas-jdk
    private CopyOnWriteArrayList<Nibblonian> citizens;
    private static Text undefinedDevices;
    private static int numberOfUndefined;
    private final static String undefinedText;
    private static Text safeDevices;
    private static int numberOfSafe;
    private final static String safeText;
    private static Text stoppedTimerDevices;
    private static int numberOfStoppedTimer;
    private final static String stoppedTimerText;
    private static Text activeDevices;
    private static int numberOfActive;
    private final static String activeText;
    private static Text destroyedDevices;
    private static int numberOfDestroyed;
    private final static String destroyedText;
    private final static MomFriendlyRobots momFriendlyRobots;
    private final static PlanetExpressOffice planetExpressOffice;
    private final static MiniMap miniMap;
    private final static int rootWidth;
    private final static int rootHeight;
    private final Pane root;

    static {
        rootWidth = 4000;
        rootHeight = 3000;
        planetExpressOffice = new PlanetExpressOffice();
        momFriendlyRobots = new MomFriendlyRobots();
        miniMap = new MiniMap(planetExpressOffice, momFriendlyRobots);
        undefinedText = "Undefined: ";
        safeText = "Safe: ";
        stoppedTimerText = "Stopped timer: ";
        activeText = "Active bombs: ";
        destroyedText = "Destroyed: ";
    }

    {
        root = new Pane();
        citizens = new CopyOnWriteArrayList<>();
    }

    public NewNewYork(int numberOfDevices) {
        devices = new ArrayList<>();
        root.setMinWidth(rootWidth);
        root.setMinHeight(rootHeight);
        Rectangle container = new Rectangle(rootWidth, rootHeight);
        Image image;
        try {
            image = new Image(new File("src/images/beach.png").toURI().toString());
            container.setFill(new ImagePattern(image));
        } catch (Exception e) {
            System.out.println("Error");
        }
        root.getChildren().add(container);
        root.getChildren().add(planetExpressOffice.getPlanetExpressArea());
        root.getChildren().add(momFriendlyRobots.getMomCorpArea());

        undefinedDevices = new Text(undefinedText);
        undefinedDevices.setFill(Color.GRAY);
        undefinedDevices.setFont(new Font("Arial", 20));
        undefinedDevices.setX(10);
        undefinedDevices.setY(35);
        root.getChildren().add(undefinedDevices);

        safeDevices = new Text(safeText);
        safeDevices.setFill(Color.GREEN);
        safeDevices.setFont(new Font("Arial", 20));
        safeDevices.setX(10);
        safeDevices.setY(65);
        root.getChildren().add(safeDevices);

        stoppedTimerDevices = new Text(stoppedTimerText);
        stoppedTimerDevices.setFill(Color.PURPLE);
        stoppedTimerDevices.setFont(new Font("Arial", 20));
        stoppedTimerDevices.setX(10);
        stoppedTimerDevices.setY(95);
        root.getChildren().add(stoppedTimerDevices);

        activeDevices = new Text(activeText);
        activeDevices.setFill(Color.RED);
        activeDevices.setFont(new Font("Arial", 20));
        activeDevices.setX(10);
        activeDevices.setY(125);
        root.getChildren().add(activeDevices);

        destroyedDevices = new Text(destroyedText);
        destroyedDevices.setFill(Color.BROWN);
        destroyedDevices.setFont(new Font("Arial", 20));
        destroyedDevices.setX(10);
        destroyedDevices.setY(155);
        root.getChildren().add(destroyedDevices);

        update();
    }
    public void addCitizen(Nibblonian citizen) {
        if (citizen instanceof RobotSanta) {
            momFriendlyRobots.addRobotSanta((RobotSanta) citizen);
        } else {
            planetExpressOffice.addTeamMember(citizen);
        }
        citizens.add(citizen);
        root.getChildren().add(citizen.getMicroGroup());
        miniMap.addCitizenToMiniMap(citizen);
    }
    public void removeCitizen(Nibblonian citizen) {
        if (citizens.contains(citizen) && citizen instanceof RobotSanta) {
            root.getChildren().remove(citizen.getMicroGroup());
            miniMap.removeCitizenFromMiniMap(citizen);
            momFriendlyRobots.removeRobotSanta((RobotSanta) citizen);
            citizens.remove(citizen);
        } else if (citizens.contains(citizen) && !(citizen instanceof RobotSanta)) {
            root.getChildren().remove(citizen.getMicroGroup());
            miniMap.removeCitizenFromMiniMap(citizen);
            citizens.remove(citizen);
            planetExpressOffice.removeTeamMember(citizen);
        }
    }
    public void addDevice(Device device) {
        devices.add(device);
        device.getMacroGroup().setLayoutX(device.getPosX());
        device.getMacroGroup().setLayoutY(device.getPosY());
        root.getChildren().add(device.getMacroGroup());
        miniMap.addDeviceToMiniMap(device);
    }
    public static void update() {
        numberOfUndefined = 0;
        numberOfSafe = 0;
        numberOfStoppedTimer = 0;
        numberOfActive = 0;
        numberOfDestroyed = 0;
        devices.forEach(element -> {
            switch (element.getStatus()) {
                case UNDEFINED -> numberOfUndefined++;
                case SAFE -> numberOfSafe++;
                case STOPPEDTIMER -> numberOfStoppedTimer++;
                case ACTIVEBOMB -> numberOfActive++;
                case DESTROYEDBOMB -> numberOfDestroyed++;
            }
        });
        undefinedDevices.setText(undefinedText + numberOfUndefined);
        safeDevices.setText(safeText + numberOfSafe);
        stoppedTimerDevices.setText(stoppedTimerText + numberOfStoppedTimer);
        activeDevices.setText(activeText + numberOfActive);
        destroyedDevices.setText(destroyedText + numberOfDestroyed);
        miniMap.updateMiniMap();
    }
    public static int getRootWidth() {
        return rootWidth;
    }
    public static int getRootHeight() {
        return rootHeight;
    }
    public ArrayList<Device> getDevices() {
        return devices;
    }
    public Pane getRoot() {
        return root;
    }
    public CopyOnWriteArrayList<Nibblonian> getCitizens() {
        return citizens;
    }
    public void setCitizens(CopyOnWriteArrayList<Nibblonian> citizens) {
        this.citizens = citizens;
    }
    public MomFriendlyRobots getMomFriendlyRobots() {
        return momFriendlyRobots;
    }
    public PlanetExpressOffice getPlanetExpressOffice() {
        return planetExpressOffice;
    }
    public MiniMap getMiniMap() {
        return miniMap;
    }
}
