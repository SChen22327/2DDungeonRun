import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Entity {
    public final double MOVE_AMT;
    public int health;
    public String state;
    public double xCoord;
    public double yCoord;
    public ArrayList<ArrayList<Tile>> walkable;
    public ArrayList<AnimationInfo> animations;
    public Animation currentAnimation;
    public BufferedImage staticIMG;
    public Entity(double xCoord, double yCoord, String filepath, int health, double MOVE_AMT) {
        this.MOVE_AMT = MOVE_AMT;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        try {
            staticIMG = ImageIO.read(new File(filepath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.health = health;
    }

    public String getState() {
        return state.substring(state.indexOf(";") + 1);
    }

    public String getDir() {
        return state.substring(0, state.indexOf(";"));
    }

    public BufferedImage getImg() {
        return staticIMG;
    }

    public void sendState(String s) {
        state = s;
        if (getState().equals("death")) {
            currentAnimation = getAnimation("death");
            currentAnimation.play();
        } else if (getState().equals("hurt")) {
            currentAnimation = getAnimation("hurt");
            currentAnimation.play();
        } else if (getState().equals("walk")) {
            currentAnimation = getAnimation("walk");
            currentAnimation.play();
        } else if (getState().equals("idle")) {
            currentAnimation = getAnimation("idle");
            currentAnimation.play();
        }
    }
    public Animation getAnimation(String name) {
        for (AnimationInfo a : animations) {
            if (a.getName().equals(name)) {
                return a.getAnimation();
            }
        }
        return currentAnimation;
    }
    public void takeDMG() {
        sendState(getDir() + ";hurt");
        health--;
        if (health == 0) {
            sendState(getDir() + ";death");
        }
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

    public void createAnimations() {}
    public String currentAnimationName() {
        for (AnimationInfo a : animations) {
            if (a.getAnimation() == currentAnimation) {
                return a.getName();
            }
        }
        return null;
    }

    public int getHeight() {
        return staticIMG.getHeight();
    }

    public int getWidth() {
        return staticIMG.getWidth();
    }

    public Rectangle rect() {
        return new Rectangle();
    }
}
