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

public class NewNewYork {
    private ArrayList<Device> devices;
    private ArrayList<Nibblonian> citizens;
    private MomFriendlyRobots momFriendlyRobots;
    private PlanetExpressOffice planetExpressOffice;
    private final MiniMap miniMap;

    private final static int rootWidth = 4000;
    private final static int rootHeight = 3000;
    private final static Pane root;

    private Text undefinedDevices;
    private Text safeDevices;
    private Text stoppedTimerDevices;
    private Text activeDevices;
    private Text destroyedDevices;

    static {
        root = new Pane();
    }

    {
        planetExpressOffice = new PlanetExpressOffice();
        momFriendlyRobots = new MomFriendlyRobots();
        miniMap = new MiniMap();
        citizens = new ArrayList<>();
        devices = new ArrayList<>();
    }

    public NewNewYork() {
        root.setMinWidth(rootWidth);
        root.setMinHeight(rootHeight);
        Rectangle container = new Rectangle(rootWidth, rootHeight);
        Image image;
        try {
            image = new Image(new File("src/images/beach.png").toURI().toString());
            container.setFill(new ImagePattern(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        root.getChildren().add(container);

        root.getChildren().add(planetExpressOffice.getPlanetExpressArea());
        root.getChildren().add(momFriendlyRobots.getMomCorpArea());
        root.getChildren().addAll(miniMap.getPane());

        undefinedDevices = new Text("Undefined: 1");
        undefinedDevices.setFill(Color.GRAY);
        undefinedDevices.setFont(new Font("Monako", 20));
        undefinedDevices.setX(10);
        undefinedDevices.setY(55);
        root.getChildren().add(undefinedDevices);

        safeDevices = new Text("Safe: 0");
        safeDevices.setFill(Color.GREEN);
        safeDevices.setFont(new Font("Monako", 20));
        safeDevices.setX(10);
        safeDevices.setY(85);
        root.getChildren().add(safeDevices);

        stoppedTimerDevices = new Text("Stopped timer: 0");
        stoppedTimerDevices.setFill(Color.PURPLE);
        stoppedTimerDevices.setFont(new Font("Monako", 20));
        stoppedTimerDevices.setX(10);
        stoppedTimerDevices.setY(115);
        root.getChildren().add(stoppedTimerDevices);

        activeDevices = new Text("Active bombs: 0");
        activeDevices.setFill(Color.RED);
        activeDevices.setFont(new Font("Monako", 20));
        activeDevices.setX(10);
        activeDevices.setY(145);
        root.getChildren().add(activeDevices);

        destroyedDevices = new Text("Destroyed: 0");
        destroyedDevices.setFill(Color.BROWN);
        destroyedDevices.setFont(new Font("Monako", 20));
        destroyedDevices.setX(10);
        destroyedDevices.setY(175);
        root.getChildren().add(destroyedDevices);

    }

    public static Pane getRoot() {
        return root;
    }

    public ArrayList<Device> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<Device> devices) {
        this.devices = devices;
    }

    public ArrayList<Nibblonian> getCitizens() {
        return citizens;
    }

    public void setCitizens(ArrayList<Nibblonian> citizens) {
        this.citizens = citizens;
    }

    public MomFriendlyRobots getMomFriendlyRobots() {
        return momFriendlyRobots;
    }

    public void setMomFriendlyRobots(MomFriendlyRobots momFriendlyRobots) {
        this.momFriendlyRobots = momFriendlyRobots;
    }

    public PlanetExpressOffice getPlanetExpressOffice() {
        return planetExpressOffice;
    }

    public void setPlanetExpressOffice(PlanetExpressOffice planetExpressOffice) {
        this.planetExpressOffice = planetExpressOffice;
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
        if (citizens.contains(citizen) && (citizen instanceof RobotSanta)) {
            this.citizens.remove(citizen);
            this.momFriendlyRobots.removeRobotSanta((RobotSanta) citizen);
        } else if (citizens.contains(citizen) && !(citizen instanceof RobotSanta)) {
            this.citizens.remove(citizen);
            this.planetExpressOffice.removeTeamMember(citizen);
        }
    }

    public void addCitizen(Nibblonian citizen) {
        if (!(citizens.contains(citizen)) && (citizen instanceof RobotSanta)) {
            this.citizens.add(citizen);
            this.momFriendlyRobots.addRobotSanta((RobotSanta) citizen);
        } else if (!(citizens.contains(citizen)) && !(citizen instanceof RobotSanta)) {
            this.citizens.add(citizen);
            this.planetExpressOffice.addTeamMember(citizen);
        }
        root.getChildren().add(citizen.getMicroGroup());
    }

    public void addDevice(Device device) {
        this.devices.add(device);
        root.getChildren().add(device.getMacroGroup());
    }
    public static int getRootWidth() {
        return rootWidth;
    }

    public static int getRootHeight() {
        return rootHeight;
    }
}
