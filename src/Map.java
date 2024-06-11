import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {
    private BufferedImage img;
    private File walkable;
    private ArrayList<ArrayList<Tile>> tiles;
    public Map(BufferedImage img, File walkable) {
        this.img = img;
        this.walkable = walkable;
    }

    public BufferedImage getBG() {
        return img;
    }

    public File getWalkable() {
        return walkable;
    }

    public ArrayList<ArrayList<Tile>> loadMap() {
        if (tiles == null){
            ArrayList<String> ints = new ArrayList<>();
            try {
                Scanner s = new Scanner(walkable);
                while (s.hasNextLine()) {
                    ints.add(s.nextLine());
                }
                s.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            tiles = new ArrayList<ArrayList<Tile>>();
            double x = 0;
            double y = 0;
            for (int i = 0; i < ints.size(); i++) {
                tiles.add(new ArrayList<>());
                for (int j = 0; j < ints.get(i).length(); j++) {
                    if (ints.get(i).charAt(j) != ' ') {
                        int z = Integer.parseInt(ints.get(i).charAt(j) + "");
                        if (z != 2) {
                            tiles.get(i).add(new Tile(x, y, z));
                        } else {
                            tiles.get(i).add(new Door(x, y));
                            y += 48;
                            tiles.get(i).add(new Tile(x, y, 2));
                            j += 2;
                        }
                        y += 48;
                    }
                }
                x += 48;
                if (y / 48 == ints.get(0).length() / 2 + 1) {
                    y = 0;
                }
            }
        }

        return tiles;
    }
}
