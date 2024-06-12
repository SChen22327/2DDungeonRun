import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation implements ActionListener {
    private ArrayList<BufferedImage> frames;
    private Timer timer;
    private int currentFrame;

    public Animation(ArrayList<BufferedImage> frames, int delay) {
        this.frames = frames;
        currentFrame = 0;
        timer = new Timer(delay * 10, this);
    }

    public void play() {
        timer.start();
    }
    public boolean playing() {
        return currentFrame != frames.size() - 1;
    }
    public boolean finished() {
        return currentFrame == frames.size() - 1;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public ArrayList<BufferedImage> getFrames() {
        return frames;
    }

    public BufferedImage getActiveFrame() {
        return frames.get(currentFrame);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            if (currentFrame == frames.size() - 1) {
                timer.stop();
                currentFrame = 0;
            } else {
                currentFrame = (currentFrame + 1) % frames.size();
            }
        }

    }
}
