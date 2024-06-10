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
            double r = 0;
            double c = 0;
            for (int i = 0; i < ints.size(); i++) {
                tiles.add(new ArrayList<>());
                for (int j = 0; j < ints.get(i).length(); j++) {
                    if (ints.get(i).charAt(j) != ' ') {
                        int z = Integer.parseInt(ints.get(i).charAt(j) + "");
                        tiles.get(i).add(new Tile(r, c, z));
                        c += 48;
                    }
                }
                r += 48;
                if ((r / 48) - 1 == ints.size() / 2 + 1) {
                    r = 0;
                }
            }
        }
        return tiles;
    }
}
