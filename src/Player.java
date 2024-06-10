import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private final double MOVE_AMT = .2;
    private int health;
    private int[] currentTile;
    private double xCoord;
    private double yCoord;
    private ArrayList<ArrayList<Tile>> walkable;
    private String state;
    private ArrayList<AnimationInfo> animations;
    private Animation currentAnimation;
    private BufferedImage staticIMG;
    public Player() {
        try {
            staticIMG = ImageIO.read(new File("assets/Wolf animations/wolf_Static.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        health = 3;
        state = "right;idle";
        reset();
        createAnimations();
    }

    private void createAnimations() {
        animations = new ArrayList<AnimationInfo>();
        Animation newAnimation;
        //idle
        ArrayList<BufferedImage> idle = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String filename = "assets/Wolf animations/wolf_idle/tile00" + i + ".png";
            try {
                idle.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(idle, 20);
        animations.add(new AnimationInfo("idle", newAnimation));
        currentAnimation = newAnimation;
        // walk
        ArrayList<BufferedImage> walk = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String filename = "assets/Wolf animations/wolf_walk/tile00" + i + ".png";
            try {
                walk.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(walk,20);
        animations.add(new AnimationInfo("walk", newAnimation));
        //attack
        ArrayList<BufferedImage> attack = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String filename = "assets/Wolf animations/wolf_attack/tile00" + i + ".png";
            try {
                attack.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(attack,20);
        animations.add(new AnimationInfo("attack", newAnimation));
        //hurt
        ArrayList<BufferedImage> hurt = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            String filename = "assets/Wolf animations/wolf_damage/tile00" + i + ".png";
            try {
                hurt.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(hurt, 20);
        animations.add(new AnimationInfo("hurt", newAnimation));
        //death
        ArrayList<BufferedImage> death = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String filename = "assets/Wolf animations/wolf_death/tile00" + i + ".png";
            try {
                death.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(death, 20);
        animations.add(new AnimationInfo("death", newAnimation));
    }
    private Animation getAnimation(String name) {
        for (AnimationInfo a : animations) {
            if (a.getName().equals(name)) {
                return a.getAnimation();
            }
        }
        return currentAnimation;
    }
    public void newMap(ArrayList<ArrayList<Tile>> t) {
        walkable = t;
    }

    public void takeDMG() {
        health -= 1;
        if (health == 0) {
            sendState(getDir() + ";death");
        }
    }
    public int getxCoord() {
        return (int) xCoord;
    }

    public int getyCoord() {
        return (int) yCoord;
    }
    public void checkState() {
        if (getState().equals("idle")) {
            currentAnimation = getAnimation("idle");
            currentAnimation.play();
        }
        if (getState().equals("walk")) {
            currentAnimation = getAnimation("walk");
            currentAnimation.play();
        }
        if (getState().equals("attack")) {
            currentAnimation = getAnimation("attack");
            currentAnimation.play();
        }
        if (getState().equals("hurt")) {
            currentAnimation = getAnimation("hurt");
            currentAnimation.play();
        }
        if (getState().equals("death")) {
            currentAnimation = getAnimation("death");
            currentAnimation.play();
        }
        currentTile = new int[]{(int) xCoord / 48, (int) yCoord / 48};
    }
    public void sendState(String s) {
        state = s;
    }
    public String getState() {
        return state.substring(state.indexOf(";") + 1);
    }
    public String getDir() {
        return state.substring(0, state.indexOf(";"));
    }
    public void reset() {
        currentTile = new int[]{2,2};
        xCoord = 96;
        yCoord = 96;
    }
    public void moveRight() {
        if (xCoord < (walkable.get(0).size() - 1) * 48 && canMove(1)) {
            xCoord += MOVE_AMT;
        } else {
            xCoord--;
        }
    }

    public void moveLeft() {
        if (xCoord > 48 && canMove(2)) {
            xCoord -= MOVE_AMT;
        } else {
            xCoord++;
        }
    }

    public void moveUp() {
        if (yCoord > 48 && canMove(3)) {
            yCoord -= MOVE_AMT;
        } else {
            yCoord++;
        }
    }

    public void moveDown() {
        if (yCoord < (walkable.size() - 1) * 48 && canMove(4)) {
            yCoord += MOVE_AMT;
        } else {
            yCoord--;
        }
    }

    private boolean canMove(int i) {
        switch (i) {
            //right
            case 1:
                return !walkable.get(currentTile[1]).get(currentTile[0] + 1).collided(playerRect(), 1);
            //left
            case 2:
                return !walkable.get(currentTile[1]).get(currentTile[0] - 1).collided(playerRect(), 2);
            //up
            case 3:
                return !walkable.get(currentTile[1] - 1).get(currentTile[0]).collided(playerRect(), 3);
            //down
            case 4:
                return !walkable.get(currentTile[1] + 1).get(currentTile[0]).collided(playerRect(), 4);
        }
        return false;
    }
    public BufferedImage getPlayerImage() {
        return currentAnimation.getActiveFrame();
    }
    public int getHeight() {
        return currentAnimation.getActiveFrame().getHeight();
    }

    public int getWidth() {
        return currentAnimation.getActiveFrame().getWidth();
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        int imageHeight = getHeight();
        int imageWidth = getWidth();
        int addX = (imageWidth - 13) / 4;
        int addY = (imageHeight - 26);
        Rectangle rect = new Rectangle((int) xCoord + addX - imageWidth/2, (int) yCoord + addY - imageHeight/2, 17, 26);
        return rect;
    }
}
