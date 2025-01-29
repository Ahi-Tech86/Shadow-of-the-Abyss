package objects;

import java.io.IOException;

public class CrystalObject extends SuperObject {

    public CrystalObject(int worldX, int worldY, boolean isSolid) {
        name = "Crystal";
        image = getSpriteImage("/objects/Icon29.png");
        this.worldX = worldX;
        this.worldY = worldY;
        this.collision = isSolid;
    }
}
