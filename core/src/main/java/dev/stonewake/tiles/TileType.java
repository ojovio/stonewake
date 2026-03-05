package dev.stonewake.tiles;

import com.badlogic.gdx.graphics.Texture;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.tiles.tiling.AutoTiler;

public abstract class TileType {
    private int tileId;
    private Texture tileTexture;
    protected AutoTiler autoTiler;
    protected String tileSprite;

    public TileType(int tileId) {
        this.tileId = tileId;

        setDefaults();
    }

    public abstract void setDefaults();

    public int getTileId() {
        return tileId;
    }

    public String getTileSprite() {
        return tileSprite;
    }

    public int getTileSpriteIndex(Tile occupiedTile, int tileSize) {
        return autoTiler.getTileSpriteIndex(occupiedTile, tileSize);
    }

    public void loadTileTexture(Texture texture) {
        tileTexture = texture;
    }

    public Texture getTileTexture() {
        return tileTexture;
    }
}
