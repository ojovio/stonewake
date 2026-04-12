//
// Created by j-otavio on 05/04/2026.
//

#include "../../include/tilemap/cell.h"

#include "../../include/tilemap/tile_config.h"

bool Cell::isEmpty() const {
    return tile == -1;
}

bool Cell::isOccupied() const {
    return tile != -1;
}

int Cell::getGlobalX() const {
    return (chunkX * TileConfig::CHUNK_WIDTH) + localX;
}

int Cell::getGlobalY() const {
    return (chunkY * TileConfig::CHUNK_HEIGHT) + localY;
}
