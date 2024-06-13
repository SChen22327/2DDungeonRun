import javax.swing.*;
import java.awt.*;

public class Main implements Runnable {

    private GraphicsPanel panel;

    public Main() {
        JFrame frame = new JFrame("Dungeon Run");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(768, 576 + 40); // 540 height of image + 40 for window menu bar
        frame.setLocationRelativeTo(null); // auto-centers frame in screen

        // create and add panel
        panel = new GraphicsPanel();
        frame.add(panel);
        panel.setBackground(new Color(24,19,37));

        // display the frame
        frame.setVisible(true);

        // start thread, required for animation
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        double drawInterval = 1000000000/60;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (true) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                panel.repaint();  // we don't ever call "paintComponent" directly, but call this to refresh the panel
                delta--;
            }
        }
    }

    public static void main(String[] args) {
        Main m = new Main();
    }
}
