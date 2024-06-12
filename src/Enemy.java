import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy extends Entity {
    private ArrayList<AnimationInfo> animations;
    public Enemy(int xCoord, int yCoord, BufferedImage img) {
        super(xCoord, yCoord, "assets/Dino/0/idle/tile000.png", 3, 4);
        state = "right;idle";
        createAnimations();
    }

    public void createAnimations() {
        health = 3;
        animations = new ArrayList<AnimationInfo>();
        Animation newAnimation;
        //idle
        ArrayList<BufferedImage> idle = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            String filename = "assets/Dino/0/idle/tile00" + i + ".png";
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
        for (int i = 0; i < 6; i++) {
            String filename = "assets/Dino/0/walk/tile00" + i + ".png";
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
        for (int i = 0; i < 4; i++) {
            String filename = "assets/Dino/0/kick/tile00" + i + ".png";
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
            String filename = "assets/Dino/0/hurt/tile00" + i + ".png";
            try {
                hurt.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(hurt,20);
        animations.add(new AnimationInfo("hurt", newAnimation));
        //death
        ArrayList<BufferedImage> death = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String filename = "assets/Dino/0/hurt/tile00" + i + ".png";
            try {
                death.add(ImageIO.read(new File(filename)));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        newAnimation = new Animation(death,20);
        animations.add(new AnimationInfo("death", newAnimation));
    }
    public void takeDMG(int dmg) {
        health -= dmg;
    }

    public void move() {
        int i = (int) (Math.random() * 4);
        switch (i) {
            case 0:
                sendState(getDir() + ";walk");
                moveUp();
            case 1:
                sendState(getDir() + ";walk");
                moveDown();
            case 2:
                sendState("left;walk");
                moveLeft();
            case 3:
                sendState("right;walk");
                moveRight();
        }
    }
    @Override
    public Rectangle rect() {
        return new Rectangle((int) xCoord,(int) yCoord, 24, 24);
    }
}
