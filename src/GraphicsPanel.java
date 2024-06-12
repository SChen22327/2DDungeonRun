import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private ArrayList<BufferedImage> hearts;
    private ArrayList<Map> maps;
    public static int map = 0;
    private ArrayList<ArrayList<Tile>> walkable;
    private BufferedImage background;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Door door;
    private boolean[] pressedKeys;
    private Timer timer;
    public GraphicsPanel() {
        timer = new Timer(1500,this);
        maps = new ArrayList<>();
        for (int i = 0; i < new File("assets/Maps").list().length; i++) {
            try {
                maps.add(new Map(ImageIO.read(new File("assets/Maps/map00" + i + "/map.png")), new File("assets/Maps/map00" + i + "/walkable.txt")));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        hearts = new ArrayList<>();
        try {
            hearts.add(ImageIO.read(new File("assets/heart_blank.png")));
            hearts.add(ImageIO.read(new File("assets/heart_full.png")));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //https://www.youtube.com/@RyiSnow
        //https://stackoverflow.com/questions/15940328/jpanel-animated-background
        player = new Player();
        loadMap();
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

        g.drawImage(background,   - (int) player.xCoord + 384, - (int) player.yCoord + 288, null);
        g.drawImage(door.getImg(),door.getxCoord() - (int) player.xCoord + 384,door.getyCoord() - (int) player.yCoord + 288,null);

        if (player.getDir().equals("left")) {
            g.drawImage(player.getPlayerImage(), 384 + player.getWidth() / 2, 288 - player.getHeight() / 2, -player.getPlayerImage().getWidth(), player.getPlayerImage().getHeight(), null);
        } else {
            g.drawImage(player.getPlayerImage(), 384 - player.getWidth() / 2, 288 - player.getHeight() / 2, null);
        }

        for (Enemy e : enemies) {
            if (e.getDir().equals("left")) {
                g.drawImage(e.getImg(), (int) e.xCoord - (int) player.xCoord + e.getWidth() / 2, (int) e.yCoord - (int) player.yCoord, -e.getWidth(), e.getHeight(), null);
            } else {
                g.drawImage(e.getImg(), (int) e.xCoord - (int) player.xCoord + 384 - e.getWidth() / 2, (int) e.yCoord - (int) player.yCoord + 288 - e.getHeight() / 2, null);
            }
        }

        int x = 0;
        for (int i = 0; i < player.health; i++) {
            g.drawImage(hearts.get(1), x, 0, null);
            x += 20;
        }
        if (player.health < 6) {
            for (int i = 0; i < 6 - player.health; i++) {
                g.drawImage(hearts.get(0),x,0,null);
                x += 20;
            }
        }
        // player moves left (A)
        if (pressedKeys[65]) {
            if (!player.attacking()) {
                player.sendState("left;walk");
            }
            player.moveLeft();
        }

        // player moves right (D)
        if (pressedKeys[68]) {
            if (!player.attacking()) {
                player.sendState("right;walk");
            }
            player.moveRight();
        }

        // player moves up (W)
        if (pressedKeys[87]) {
            if (!player.attacking()) {
                player.sendState(player.getDir() + ";walk");
            }
            player.moveUp();
        }

        // player moves down (S)
        if (pressedKeys[83]) {
            if (!player.attacking()) {
                player.sendState(player.getDir() + ";walk");
            }
            player.moveDown();
        }

        if (!(pressedKeys[65] || pressedKeys[68] || pressedKeys[87] || pressedKeys[83] || player.attacking())) {
            player.sendState(player.getDir() + ";idle");
        }
    }

    public void loadMap() {
        enemies = new ArrayList<>();
        switch (map) {
            case 1:
                try {
                    enemies.add(new Enemy(288, 192, ImageIO.read(new File("assets/Dino/0/idle/tile000.png"))));
                    enemies.add(new Enemy(background.getWidth() - 288, 192, ImageIO.read(new File("assets/Dino/0/idle/tile000.png"))));
                    enemies.add(new Enemy(288, background.getHeight() - 240, ImageIO.read(new File("assets/Dino/0/idle/tile000.png"))));
                    enemies.add(new Enemy(background.getWidth() - 288, background.getHeight() - 240, ImageIO.read(new File("assets/Dino/0/idle/tile000.png"))));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
        }
        background = maps.get(map).getBG();
        walkable = maps.get(map).loadMap();
        player.newMap(walkable);
        for (ArrayList<Tile> a : walkable) {
            for (Tile t : a) {
                if (t instanceof Door) {
                    door = (Door) t;
                }
            }
        }
        if (enemies.isEmpty()) {
            door.open();
        }
    }
    public void checkLevel() {
        if (door.doorRect() != null && player.playerRect().intersects(door.doorRect())) {
            map++;
            loadMap();
        }
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
    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) { } // unimplemented

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
            player.attack();
        }
    }

    public void mouseEntered(MouseEvent e) { } // unimplemented

    public void mouseExited(MouseEvent e) { } // unimplemented

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            if (!enemies.isEmpty()) {
                for (Enemy enemy : enemies) {
                    enemy.move();
                }
            }
        }
    }
}
