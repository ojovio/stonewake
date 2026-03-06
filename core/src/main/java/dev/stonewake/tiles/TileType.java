package dev.stonewake.tiles;

import com.badlogic.gdx.graphics.Texture;
import dev.stonewake.assets.TileAssetManager;
import dev.stonewake.tiles.tiling.AutoTiler;
import dev.stonewake.tiles.tiling.BitMask;

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

    public int getTileSpriteIndex(BitMask bitMask, Tile occupiedTile, int tileSize) {
        if (!occupiedTile.isSpriteIndexDirty()) {
            return occupiedTile.getCachedSpriteIndex();
        }
        int spriteIndex = autoTiler.getTileSpriteIndex(bitMask, occupiedTile, tileSize);
        occupiedTile.clearDirtySpriteIndex();
        occupiedTile.cacheSpriteIndex(spriteIndex);

        return spriteIndex;
    }

    public void loadTileTexture(Texture texture) {
        tileTexture = texture;
    }

    public Texture getTileTexture() {
        return tileTexture;
    }
}
