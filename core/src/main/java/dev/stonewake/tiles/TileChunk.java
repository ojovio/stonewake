package dev.stonewake.tiles;

import dev.stonewake.tiles.events.TileChangeEvent;
import dev.stonewake.tiles.events.TileChangeEventType;
import dev.stonewake.tiles.listeners.TileChangeListener;
import dev.stonewake.utils.TileUtils;

public class TileChunk {
    private final Tile[] tiles;
    private final int chunkStartX;
    private final int chunkStartY;
    private final int chunkEndX;
    private final int chunkEndY;
    private final short chunkX;
    private final short chunkY;

    public TileChunk(TileMap tileMap, short chunkX, short chunkY) {
        int chunkWidth = tileMap.getTileMapChunkWidth();
        int chunkHeight = tileMap.getTileMapChunkHeight();

        chunkStartX = chunkX * chunkWidth;
        chunkStartY = chunkY * chunkHeight;
        chunkEndX = chunkStartX + chunkWidth - 1;
        chunkEndY = chunkStartY + chunkHeight - 1;
        this.chunkX = chunkX;
        this.chunkY = chunkY;

        tiles = new Tile[tileMap.getTileMapLayersCount() * chunkWidth * chunkHeight];

        for (int layer = 0; layer < tileMap.getTileMapLayersCount(); layer++) {
            for (int x = 0; x < chunkWidth; x++) {
                for (int y = 0; y < chunkHeight; y++) {
                    tiles[TileUtils.codifyTilePosition(tileMap, layer, x, y)] = new Tile(layer, x, y, chunkX, chunkY);
                }
            }
        }
    }

    public boolean isTileOnChunkBounds(TileMap tileMap, int tileLayer, int tileChunkX, int tileChunkY) {
        return (tileLayer >= 0 && tileLayer < tileMap.getTileMapLayersCount()) &&
                (tileChunkX >= 0 && tileChunkX < tileMap.getTileMapChunkWidth()) &&
                (tileChunkY >= 0 && tileChunkY < tileMap.getTileMapChunkHeight());
    }

    public Tile getTile(TileMap tileMap, int tileLayer, int tileChunkX, int tileChunkY) {
        if (!isTileOnChunkBounds(tileMap, tileLayer, tileChunkX, tileChunkY))
            throw new RuntimeException(String.format("Tile(%d, %d, %d) is not on chunk (%d, %d) boundaries!", tileLayer, tileChunkX, tileChunkY, chunkX, chunkY));

        return tiles[TileUtils.codifyTilePosition(tileMap, tileLayer, tileChunkX, tileChunkY)];
    }

    public void setTile(TileMap tileMap, int tileLayer, int tileChunkX, int tileChunkY, short tileId) {
        Tile tile = tiles[TileUtils.codifyTilePosition(tileMap, tileLayer, tileChunkX, tileChunkY)];
        TileType lastTileType = (tile.isTileAir()) ? null : tileMap.getTileRegistry().getRegisteredTileType(tile.tileType);
        TileType currentTileType = tileMap.getTileRegistry().getRegisteredTileType(tileId);

        TileChangeEventType tileChangeEventType;
        if (lastTileType == null)
            tileChangeEventType = TileChangeEventType.ADD_TILE;
        else {
            tileChangeEventType = TileChangeEventType.CHANGE_TILE;

            for (TileChangeListener tileChangeListener : lastTileType.getTileChangeListeners()) {
                tileChangeListener.tileChange(new TileChangeEvent(tile, tileChangeEventType, lastTileType, currentTileType, true));
            }
        }

        for (TileChangeListener tileChangeListener : currentTileType.getTileChangeListeners()) {
            tileChangeListener.tileChange(new TileChangeEvent(tile, tileChangeEventType, lastTileType, currentTileType, false));
        }

        tile.tileType = tileId;

        updateTile(tileMap, tile);
    }

    public void clearTile(TileMap tileMap, int tileLayer, int tileChunkX, int tileChunkY) {
        Tile tile = tiles[TileUtils.codifyTilePosition(tileMap, tileLayer, tileChunkX, tileChunkY)];
        TileType lastTileType = (tile.isTileAir()) ? null : tileMap.getTileRegistry().getRegisteredTileType(tile.tileType);

        if (lastTileType != null) {
            for (TileChangeListener tileChangeListener : lastTileType.getTileChangeListeners()) {
                tileChangeListener.tileChange(new TileChangeEvent(tile, TileChangeEventType.CLEAR_TILE, lastTileType, null, false));
            }
        }

        tile.tileType = -1;
    }

    public Tile getTileFromWorld(TileMap tileMap, int tileLayer, int tileX, int tileY) {
        int chunkWidth = tileMap.getTileMapChunkWidth();
        int chunkHeight = tileMap.getTileMapChunkHeight();
        tileX = Math.floorMod(tileX, chunkWidth);
        tileY = Math.floorMod(tileY, chunkHeight);

        return getTile(tileMap, tileLayer, tileX, tileY);
    }

    public void updateTile(TileMap tileMap, Tile tile) {
        tile.markTileSpriteIndexDirty();
        tile.markTilePhysicsDirty();

        for (int layer = 0; layer < tileMap.getTileMapLayersCount(); layer++) {
            for (int dX = -1; dX <= 1; dX++) {
                for (int dY = -1; dY <= 1; dY++) {
                    if (dX == 0 && dY == 0 && layer == tile.getTileLayer()) continue;

                    int x =  + dX;
                    int y = tile.getTileChunkY() + dY;

                    int chunkdX = 0, chunkdY = 0;

                    if (!isTileOnChunkBounds(tileMap, layer, x, y)) {
                        if (x < 0) {
                            chunkdX = -1;
                            x = tileMap.getTileMapChunkWidth() - 1;
                        } else if (x >= tileMap.getTileMapChunkWidth()) {
                            chunkdX = 1;
                            x = 0;
                        }

                        if (y < 0) {
                            chunkdY = -1;
                            y = tileMap.getTileMapChunkHeight() - 1;
                        } else if (y >= tileMap.getTileMapChunkHeight()) {
                            chunkdY = 1;
                            y = 0;
                        }

                        if (!tileMap.isChunkOnBounds(chunkX + chunkdX, chunkY + chunkdY)) continue;

                        tileMap.getChunk(chunkX + chunkdX, chunkY + chunkdY).getTile(tileMap, layer, x, y).markTileSpriteIndexDirty();
                        tileMap.getChunk(chunkX + chunkdX, chunkY + chunkdY).getTile(tileMap, layer, x, y).markTilePhysicsDirty();

                        continue;
                    }

                    Tile neighbor = tiles[TileUtils.codifyTilePosition(tileMap, layer, x, y)];
                    neighbor.markTileSpriteIndexDirty();
                    neighbor.markTilePhysicsDirty();
                }
            }
        }
    }

    public short getChunkX() {
        return chunkX;
    }

    public short getChunkY() {
        return chunkY;
    }

    public int getChunkStartX() {
        return chunkStartX;
    }

    public int getChunkStartY() {
        return chunkStartY;
    }

    public int getChunkEndX() {
        return chunkEndX;
    }

    public int getChunkEndY() {
        return chunkEndY;
    }
}