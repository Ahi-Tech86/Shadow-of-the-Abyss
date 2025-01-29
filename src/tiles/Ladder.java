package tiles;

import java.awt.*;

public class Ladder extends Tile {
    public Ladder() {
        solidArea = new Rectangle();
        solidArea.x = 18;
        solidArea.y = 0;
        solidArea.width = 28;
        solidArea.height = 64;
    }
}
