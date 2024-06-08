import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private ArrayList<Map> maps;
    private int map;
    private BufferedImage walkable;
    private BufferedImage background;
    private Player player;
    private boolean[] pressedKeys;
    private final int MAXCOL = 16;
    private final int MAXROW = 12;
    private final int TILESIZE = 48;

    public GraphicsPanel() {
        maps = new ArrayList<>();
        for (int i = 0; i < new File("assets/Maps").list().length; i++) {
            try {
                maps.add(new Map(ImageIO.read(new File("assets/Maps/map00" + i + "/map.png")), ImageIO.read(new File("assets/Maps/map00" + i + "/walkable.png"))));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        map = 0;
        //https://www.youtube.com/@RyiSnow
        //https://stackoverflow.com/questions/15940328/jpanel-animated-background
        background = maps.get(map).getBG();
        walkable = maps.get(map).getWalkable();
        player = new Player();
        player.newMap(walkable);
        pressedKeys = new boolean[128];
        addKeyListener(this);
        addMouseListener(this);
        setFocusable(true); // this line of code + one below makes this panel active for keylistener events
        requestFocusInWindow(); // see comment above
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        checkLevel();

        g.drawImage(walkable,  -player.getxCoord() + 322, -player.getyCoord() + 240, null);
        g.drawImage(background,  -player.getxCoord() + 322, -player.getyCoord() + 240, null);

        if (player.getDir().equals("left")) {
            g.drawImage(player.getPlayerImage(), 384 + player.getWidth() / 2, 288 - player.getHeight() / 2, -player.getPlayerImage().getWidth(), player.getPlayerImage().getHeight(), null);
        } else {
            g.drawImage(player.getPlayerImage(), 384 - player.getWidth() / 2, 288 - player.getHeight() / 2, null);
        }


        // player moves left (A)
        if (pressedKeys[65]) {
            player.sendState("left;walk");
            player.moveLeft();
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            player.sendState("right;walk");
            player.moveRight();
        }

        // player moves up (W)
        if (pressedKeys[87]) {
            player.sendState(player.getDir() + ";walk");
            player.moveUp();
        }

        // player moves down (S)
        if (pressedKeys[83]) {
            player.sendState(player.getDir() + ";walk");
            player.moveDown();
        }
        if (!(pressedKeys[65] || pressedKeys[68] || pressedKeys[87] || pressedKeys[83])) {
            player.sendState(player.getDir() + ";idle");
        }
        player.checkState();
    }

    public void checkLevel() {
//        if (true) {
//            map++;
//            player.reset();
//        }
        background = maps.get(map).getBG();
        walkable = maps.get(map).getWalkable();
    }

    // ----- KeyListener interface methods -----
    public void keyTyped(KeyEvent e) { } // unimplemented

    public void keyPressed(KeyEvent e) {
        // see this for all keycodes: https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list
        // A = 65, D = 68, S = 83, W = 87, left = 37, up = 38, right = 39, down = 40, space = 32, enter = 10
        int key = e.getKeyCode();
        pressedKeys[key] = true;
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        pressedKeys[key] = false;
    }

    // ----- MouseListener interface methods -----
    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click

        } else {

        }
    }

    public void mouseEntered(MouseEvent e) { } // unimplemented

    public void mouseExited(MouseEvent e) { } // unimplemented

    public void actionPerformed(ActionEvent e) {

    }
}
