import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private final double MOVE_AMT = .4;
    private int health;
    private Weapon weapon;
    private double xCoord;
    private double yCoord;
    private ArrayList<ArrayList<Tile>> walkable;
    private String state;
    private ArrayList<AnimationInfo> animations;
    private Animation currentAnimation;
    private BufferedImage staticIMG;
    public Player() {
        weapon = new Weapon();
        try {
            staticIMG = ImageIO.read(new File("assets/Wolf animations/wolf_Static.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        health = 6;
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
        reset();
    }

    public Rectangle attack() {
        sendState(getDir() + ";attack");
        return weapon.getRect((int) xCoord,(int) yCoord);
    }
    public int getHealth() {
        return health;
    }
    public void takeDMG() {
        sendState(getDir() + ";hurt");
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
        if (getState().equals("death")) {
            currentAnimation = getAnimation("death");
            currentAnimation.play();
        } else if (getState().equals("hurt")) {
            currentAnimation = getAnimation("hurt");
            currentAnimation.play();
        } else if (getState().equals("attack")) {
            currentAnimation = getAnimation("attack");
            currentAnimation.play();
        } else if (getState().equals("walk")) {
            currentAnimation = getAnimation("walk");
            currentAnimation.play();
        } else if (getState().equals("idle")) {
            currentAnimation = getAnimation("idle");
            currentAnimation.play();
        }

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
        xCoord = 288;
        yCoord = 288;
    }
    public void moveRight() {
        if (canMove(1)) {
            xCoord += MOVE_AMT;
        }
    }

    public void moveLeft() {
        if (canMove(2)) {
            xCoord -= MOVE_AMT;
        }
    }

    public void moveUp() {
        if (canMove(3)) {
            yCoord -= MOVE_AMT;
        }
    }

    public void moveDown() {
        if (canMove(4)) {
            yCoord += MOVE_AMT;
        }
    }

    private boolean canMove(int i) {
        switch (i) {
            //right
            case 1:
                int col = (int) (xCoord + 2) / 48;
                return !walkable.get((int) yCoord / 48).get(col).collided();
            //left
            case 2:
                col = (int) (xCoord - 2) / 48;
                return !walkable.get((int) yCoord / 48).get(col).collided();
            //up
            case 3:
                int row = (int) (yCoord - 2) / 48;
                return !walkable.get(row).get((int) xCoord / 48).collided();
            //down
            case 4:
                row = (int) (yCoord + 2) / 48;
                return !walkable.get(row).get((int) xCoord / 48).collided();
        }
        return false;
    }
    public BufferedImage getPlayerImage() {
        return currentAnimation.getActiveFrame();
    }
    public int getHeight() {
        return staticIMG.getHeight();
    }

    public int getWidth() {
        return staticIMG.getWidth();
    }

    // we use a "bounding Rectangle" for detecting collision
    public Rectangle playerRect() {
        Rectangle rect = new Rectangle((int) xCoord - getWidth() / 2, (int) yCoord - getHeight() / 4, 17, 26);
        return rect;
    }
}
