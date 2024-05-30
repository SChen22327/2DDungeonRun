import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    private ArrayList<BufferedImage> frames;
    private Timer timer;
    private int current;
    public Animation(ArrayList<BufferedImage> frames, int delay) {
        this.frames = frames;
        current = 0;
        timer = new Timer(delay, (ActionListener) this);
        timer.start();
    }
    public BufferedImage getFrame() {
        return frames.get(current);
    }

}
