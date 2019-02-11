package main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GOAP.GoapAgent;
import main.util.ImageCache;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;


public class Game {

    public GameObject food, boat, sword, anvil;

    public final static String texturesFolder = "resources";

    private GraphicsContext g;

    private ArrayList<GameObject> gameObjects;
    private ArrayList<GoapAgent> goapAgents;

    private int waterPadding;


    public Game(GraphicsContext g) {
        this.g = g;
        this.waterPadding = 100;

        this.gameObjects = new ArrayList<>();
        this.goapAgents = new ArrayList<>();
    }

    public void initialize()
    {
        this.preloadAllTextures();
        this.createGameObjects();
        this.createAI();
    }

    public void preloadAllTextures()
    {
        File currentDir = new File(texturesFolder);
        try {
            preloadTextures(currentDir.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void preloadTextures( Path path ) throws IOException
    {
        DirectoryStream<Path> dirStream = Files.newDirectoryStream( path );
        for ( Path p : dirStream )
        {
            if ( p.toFile().isDirectory() )
            {
                preloadTextures( p );
            } else {
                String relPath = p.toString().replaceFirst("^" + texturesFolder, "");
                ImageCache.getInstance().fetch(relPath);
                System.out.println(relPath);
            }
        }
    }

    public void destroyGameObject(GameObject object) {
        this.gameObjects.remove(object);
    }

    private void drawTerrain(GraphicsContext g) {

        Image waterImg = ImageCache.getInstance().fetch("textures/terrain/water.jpg");
        Image landImg = ImageCache.getInstance().fetch("textures/terrain/grass.jpg");

        g.drawImage(waterImg,0, 0, Engine.windowWidth, Engine.windowHeight);

        int landWidth = Engine.windowWidth - this.waterPadding * 2;
        int landHeight = Engine.windowHeight - this.waterPadding * 2;

        g.drawImage(landImg, this.waterPadding, this.waterPadding, landWidth, landHeight);
    }

    private void createAI() {
        Image manTexture = ImageCache.getInstance().fetch("textures/characters/man_standing.png");

        Man man = new Man("Man", Engine.windowWidth / 2, Engine.windowHeight - this.waterPadding - 30.0, 0, 128, manTexture);
        man.setSpeed(150);
        GoapAgent agent = new GoapAgent(man);


        this.goapAgents.add(agent);
    }

    private void createGameObjects() {

        Image boatTexture = ImageCache.getInstance().fetch("textures/assets/boat.png");
        Image foodTexture = ImageCache.getInstance().fetch("textures/assets/food.png");
        Image anvilTexture = ImageCache.getInstance().fetch("textures/assets/anvil.png");
        Image swordTexture = ImageCache.getInstance().fetch("textures/assets/sword.png");

        boat = new GameObject("Boat", Engine.windowWidth / 2, this.waterPadding - 20.0, 90, 128, boatTexture);
        food = new GameObject("Food", this.waterPadding + 50, Engine.windowHeight / 2, 0, 128, foodTexture);
        sword = new GameObject("Sword", Engine.windowWidth - this.waterPadding - 50, Engine.windowHeight / 2, 0, 128, swordTexture);
        anvil = new GameObject("Anvil", Engine.windowWidth / 2, Engine.windowHeight / 2, 0, 128, anvilTexture);

        Collections.addAll(this.gameObjects, boat, food, sword, anvil);
    }

    public void update() {

        for(GoapAgent entity: goapAgents) {
            entity.update();
        }

        for(GameObject gameObject : gameObjects) {
            gameObject.update();
        }
    }

    public void draw()
    {
        g.clearRect(0,0, Engine.windowWidth, Engine.windowHeight);

        this.drawTerrain(g);

        for(GameObject gameObject : gameObjects) {
            gameObject.draw(g);
        }

        for(GoapAgent entity: goapAgents) {
            entity.draw(g);
        }
    }
}
