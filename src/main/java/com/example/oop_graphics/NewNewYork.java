package com.example.oop_graphics;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class NewNewYork {
    private Device[] devices;
    // Problem: https://stackoverflow.com/questions/37104215/error-exception-in-thread-javafx-application-thread
    // Solution:https://stackoverflow.com/questions/6916385/is-there-a-concurrent-list-in-javas-jdk
    private CopyOnWriteArrayList<Nibblonian> citizens;
    private final MomFriendlyRobots momFriendlyRobots;
    private final PlanetExpressOffice planetExpressOffice;
    private final MiniMap miniMap;

    private final static int rootWidth;
    private final static int rootHeight;
    private final Pane root;

    private Text undefinedDevices;
    private Text safeDevices;
    private Text stoppedTimerDevices;
    private Text activeDevices;
    private Text destroyedDevices;

    // Статичний блок ініціалізації
    static {
        rootWidth = 4000;
        rootHeight = 3000;
    }
    // Нестатичний блок ініціалізації
    {
        root = new Pane();
        planetExpressOffice = new PlanetExpressOffice();
        momFriendlyRobots = new MomFriendlyRobots();
        miniMap = new MiniMap(planetExpressOffice, momFriendlyRobots);
        citizens = new CopyOnWriteArrayList<>();
    }

    public NewNewYork(int numberOfDevices) {
        devices = new Device[numberOfDevices];
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

        undefinedDevices = new Text("Undefined:");
        undefinedDevices.setFill(Color.GRAY);
        undefinedDevices.setFont(new Font("Monako", 20));
        undefinedDevices.setX(10);
        undefinedDevices.setY(35);
        root.getChildren().add(undefinedDevices);

        safeDevices = new Text("Safe:");
        safeDevices.setFill(Color.GREEN);
        safeDevices.setFont(new Font("Monako", 20));
        safeDevices.setX(10);
        safeDevices.setY(65);
        root.getChildren().add(safeDevices);

        stoppedTimerDevices = new Text("Stopped timer:");
        stoppedTimerDevices.setFill(Color.PURPLE);
        stoppedTimerDevices.setFont(new Font("Monako", 20));
        stoppedTimerDevices.setX(10);
        stoppedTimerDevices.setY(95);
        root.getChildren().add(stoppedTimerDevices);

        activeDevices = new Text("Active bombs:");
        activeDevices.setFill(Color.RED);
        activeDevices.setFont(new Font("Monako", 20));
        activeDevices.setX(10);
        activeDevices.setY(125);
        root.getChildren().add(activeDevices);

        destroyedDevices = new Text("Destroyed:");
        destroyedDevices.setFill(Color.BROWN);
        destroyedDevices.setFont(new Font("Monako", 20));
        destroyedDevices.setX(10);
        destroyedDevices.setY(155);
        root.getChildren().add(destroyedDevices);
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

    public Text getUndefinedDevices() {
        return undefinedDevices;
    }

    public void setUndefinedDevices(Text undefinedDevices) {
        this.undefinedDevices = undefinedDevices;
    }

    public Text getSafeDevices() {
        return safeDevices;
    }

    public void setSafeDevices(Text safeDevices) {
        this.safeDevices = safeDevices;
    }

    public Text getStoppedTimerDevices() {
        return stoppedTimerDevices;
    }

    public void setStoppedTimerDevices(Text stoppedTimerDevices) {
        this.stoppedTimerDevices = stoppedTimerDevices;
    }

    public Text getActiveDevices() {
        return activeDevices;
    }

    public void setActiveDevices(Text activeDevices) {
        this.activeDevices = activeDevices;
    }

    public Text getDestroyedDevices() {
        return destroyedDevices;
    }

    public void setDestroyedDevices(Text destroyedDevices) {
        this.destroyedDevices = destroyedDevices;
    }

    public void removeCitizen(Nibblonian citizen) {
        if (citizens.contains(citizen) && citizen instanceof RobotSanta) {
            root.getChildren().remove(citizen.getMicroGroup());
            this.miniMap.removeCitizenFromMiniMap(citizen);
            this.momFriendlyRobots.removeRobotSanta((RobotSanta) citizen);
            this.citizens.remove(citizen);
        } else if (citizens.contains(citizen) && !(citizen instanceof RobotSanta)) {
            root.getChildren().remove(citizen.getMicroGroup());
            this.miniMap.removeCitizenFromMiniMap(citizen);
            this.citizens.remove(citizen);
            this.planetExpressOffice.removeTeamMember(citizen);
        }
    }

    public void addCitizen(Nibblonian citizen) {
        if (citizen instanceof RobotSanta) {
            this.momFriendlyRobots.addRobotSanta((RobotSanta) citizen);
        } else {
            this.planetExpressOffice.addTeamMember(citizen);
        }
        this.citizens.add(citizen);
        root.getChildren().add(citizen.getMicroGroup());
        this.miniMap.addCitizenToMiniMap(citizen);
    }

    public static int getRootWidth() {
        return rootWidth;
    }

    public static int getRootHeight() {
        return rootHeight;
    }
}
