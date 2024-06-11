import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Enemy extends Entity {
    private ArrayList<AnimationInfo> animations;
    private int health;
    private Animation currentAnimation;
    public Enemy(double xCoord, double yCoord, BufferedImage img) {
        super(xCoord, yCoord, img);
    }

    private void createAnimations() {
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

    @Override
    public Rectangle rect() {
        return new Rectangle((int) getxCoord(),(int) getyCoord(), 24, 24);
    }
}
