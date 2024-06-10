import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player {
    private final double MOVE_AMT = .2;
    private int[] currentTile;
    private double xCoord;
    private double yCoord;
    private ArrayList<ArrayList<Tile>> walkable;
    private BufferedImage staticIMG;
    private String state;
    private ArrayList<AnimationInfo> animations;
    private Animation currentAnimation;
    public Player() {
        try {
            staticIMG = ImageIO.read(new File("assets/Wolf animations/wolf_Static.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
            default:
                return false;
        }
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
        int addX = (imageWidth - 13) / 2;
        int addY = (imageHeight - 26);
        Rectangle rect = new Rectangle((int) xCoord + addX - imageWidth/2, (int) yCoord + addY - imageHeight/2, 17, 26);
        return rect;
    }
}
