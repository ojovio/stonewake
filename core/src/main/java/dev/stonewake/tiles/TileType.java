package dev.stonewake.tiles;

import com.badlogic.gdx.graphics.Texture;
import dev.stonewake.assets.TileAssetManager;

public abstract class TileType {
    private int tileId;
    private Texture tileTexture;
    protected String tileSprite;

    public TileType(int tileId) {

        this.tileId = tileId;

        setDefaults();
    }

    public void setDefaults() {

    }

    public int getTileId() {
        return tileId;
    }

    public String getTileSprite() {
        return tileSprite;
    }

    public int getTileSpriteIndex(TileAssetManager tileAssetManager, Tile occupiedTile) {
        return 0;
    }

    public void loadTileTexture(Texture texture) {
        tileTexture = texture;
    }

    public Texture getTileTexture() {
        return tileTexture;
    }
}
