import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener {
    private ArrayList<BufferedImage> hearts;
    private ArrayList<Map> maps;
    public static int map = 0;
    public static ArrayList<ArrayList<Tile>> walkable;
    private BufferedImage background;
    private Animation gameover;
    private Player player;
    private ArrayList<Enemy> enemies;
    private Door door;
    private boolean[] pressedKeys;
    public GraphicsPanel() {
        maps = new ArrayList<>();
        for (int i = 0; i < new File("assets/Maps").list().length; i++) {
            try {
                maps.add(new Map(ImageIO.read(new File("assets/Maps/map00" + i + "/map.png")), new File("assets/Maps/map00" + i + "/walkable.txt")));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        ArrayList<BufferedImage> go = new ArrayList<>();
        for (File f : new File("assets/GameOver").listFiles()) {
            try {
                go.add(ImageIO.read(f));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        gameover = new Animation(go,10);
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

        if (!player.dead() && (map != 2 || !enemies.isEmpty())){
            checkLevel();

            g.drawImage(background, -(int) player.xCoord + 384, -(int) player.yCoord + 288, null);
            if (map != 2) {
                g.drawImage(door.getImg(), door.getxCoord() - (int) player.xCoord + 384, door.getyCoord() - (int) player.yCoord + 288, null);
            }

            if (player.getDir().equals("left")) {
                g.drawImage(player.getPlayerImage(), 384 + player.getWidth() / 2, 288 - player.getHeight() / 2, -player.getPlayerImage().getWidth(), player.getPlayerImage().getHeight(), null);
            } else {
                g.drawImage(player.getPlayerImage(), 384 - player.getWidth() / 2, 288 - player.getHeight() / 2, null);
            }

            if (player.health != 0) {
                for (int i = 0; i < enemies.size(); i++) {
                    Enemy e = enemies.get(i);
                    if (e.dead()) {
                        enemies.remove(i);
                        i--;
                    } else {
                        if (e.getDir().equals("left")) {
                            g.drawImage(e.getEnemyImage(), (int) e.xCoord - (int) player.xCoord + 384 + e.getWidth() / 2, (int) e.yCoord - (int) player.yCoord + 288 - e.getHeight() / 2, -e.getWidth(), e.getHeight(), null);
                        } else {
                            g.drawImage(e.getEnemyImage(), (int) e.xCoord - (int) player.xCoord + 384 - e.getWidth() / 2, (int) e.yCoord - (int) player.yCoord + 288 - e.getHeight() / 2, null);
                        }
                        e.move();
                    }
                }

                int x = 0;
                for (int i = 0; i < player.health; i++) {
                    g.drawImage(hearts.get(1), x, 0, null);
                    x += 20;
                }
                if (player.health < 5) {
                    for (int i = 0; i < 5 - player.health; i++) {
                        g.drawImage(hearts.get(0), x, 0, null);
                        x += 20;
                    }
                }
                if (!player.hurt()) {// player moves left (A)
                    if (pressedKeys[65]) {
                        if (!(player.attacking() || player.hurt())) {
                            player.sendState("left;walk");
                        }
                        player.moveLeft();
                    }

                    // player moves right (D)
                    if (pressedKeys[68]) {
                        if (!(player.attacking() || player.hurt())) {
                            player.sendState("right;walk");
                        }
                        player.moveRight();
                    }

                    // player moves up (W)
                    if (pressedKeys[87]) {
                        if (!(player.attacking() || player.hurt())) {
                            player.sendState(player.getDir() + ";walk");
                        }
                        player.moveUp();
                    }

                    // player moves down (S)
                    if (pressedKeys[83]) {
                        if (!(player.attacking() || player.hurt())) {
                            player.sendState(player.getDir() + ";walk");
                        }
                        player.moveDown();
                    }

                    if (!(pressedKeys[65] || pressedKeys[68] || pressedKeys[87] || pressedKeys[83] || player.attacking() || player.hurt())) {
                        player.sendState(player.getDir() + ";idle");
                    }
                }
            } else {
                enemies.clear();
            }

            for (Enemy e : enemies) {
                if (player.playerRect().intersects(e.rect())) {
                    if (player.invincible) {
                        player.counter++;
                        if (player.counter > 100) {
                            player.invincible = false;
                            player.counter = 0;
                        }
                    }
                    if (!(player.invincible || e.health == 0)) {
                        player.takeDMG();
                    }
                    if (player.hurt()) {
                        double diffX = e.xCoord - player.xCoord;
                        double diffY = e.yCoord - player.yCoord;
                        float angle = (float) Math.atan2(diffY, diffX);
                        player.xCoord -= player.KB_AMT * Math.cos(angle);
                        player.yCoord -= player.KB_AMT * Math.sin(angle);
                    }
                }
            }
        } else if (player.dead()) {
            gameover.play();
            g.drawImage(gameover.getActiveFrame(), -gameover.getActiveFrame().getWidth() / 4 + 72, 0, null);
        } else {
            try {
                BufferedImage win = ImageIO.read(new File("assets/win.png"));
                g.drawImage(win, -win.getWidth() / 4 + 72, 0, null);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void loadMap() {
        background = maps.get(map).getBG();
        walkable = maps.get(map).loadMap();
        player.newMap();
        if (map != 2) {
            for (ArrayList<Tile> a : walkable) {
                for (Tile t : a) {
                    if (t instanceof Door) {
                        door = (Door) t;
                    }
                }
            }
        } else {
            door = null;
        }
        enemies = new ArrayList<>();
        switch (map) {
            case 1:
                enemies.add(new Enemy(288, 192, player));
                enemies.add(new Enemy(background.getWidth() - 288, 192, player));
                enemies.add(new Enemy(288, background.getHeight() - 240, player));
                enemies.add(new Enemy(background.getWidth() - 288, background.getHeight() - 240, player));
                break;
            case 2:
                enemies.add(new Enemy(144, 144, player));
                enemies.add(new Enemy(288, 288, player));
                enemies.add(new Enemy(384, 288, player));
                enemies.add(new Enemy(1392, 288, player, true));
                enemies.add(new Enemy(1392, 576, player, true));
                enemies.add(new Enemy(1680, 288, player, true));
                enemies.add(new Enemy(1680, 576, player, true));
                break;
            default:
                break;
        }
    }
    public void checkLevel() {
        if (map != 2) {
            if (enemies.isEmpty()) {
                door.open();
            }
            if (door.doorRect() != null && player.playerRect().intersects(door.doorRect())) {
                map++;
                loadMap();
            }
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
        if (!player.dead()) {
            if (e.getButton() == MouseEvent.BUTTON1) {  // left mouse click
                Rectangle r = player.attack();
                for (Enemy enemy : enemies) {
                    if (r.intersects(enemy.rect())) {
                        enemy.takeDMG();
                    }
                }
            }
        }
    }

    public void mouseEntered(MouseEvent e) { } // unimplemented

    public void mouseExited(MouseEvent e) { } // unimplemented

    public void actionPerformed(ActionEvent e) {

    }
}
