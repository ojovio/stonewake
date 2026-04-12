//
// Created by j-otavio on 05/04/2026.
//

#include "../../include/tilemap/chunk.h"

#include <iostream>

Chunk::Chunk(const int16_t chunkX, const int16_t chunkY)
    : x(chunkX), y(chunkY), m_cells({})
{
    for (int layer = 0; layer < TileConfig::CHUNK_DEPTH; layer++) {
        for (uint8_t tx = 0; tx < TileConfig::CHUNK_WIDTH; tx++) {
            for (uint8_t ty = 0; ty < TileConfig::CHUNK_HEIGHT; ty++) {
                const int key = makeKey(static_cast<TileLayer>(layer), tx, ty);
                Cell& cell = m_cells[key];
                cell.localX = tx;
                cell.localY = ty;
                cell.chunkX = chunkX;
                cell.chunkY = chunkY;
                cell.layer = static_cast<TileLayer>(layer);
                cell.tile = -1;
            }
        }
    }
}

int Chunk::makeKey(const TileLayer layer, const uint8_t x, const uint8_t y) {
    return (static_cast<int>(layer) * TileConfig::CHUNK_WIDTH * TileConfig::CHUNK_HEIGHT) + (y * TileConfig::CHUNK_WIDTH) + x;
}

std::array<Cell, TileConfig::CHUNK_SIZE> &Chunk::getCells() {
    return m_cells;
}

Cell &Chunk::getCell(const TileLayer layer, const uint8_t localX, const uint8_t localY) {
    return m_cells[makeKey(layer, localX, localY)];
}

void Chunk::setCell(const TileLayer layer, const uint8_t localX, const uint8_t localY, const int16_t tile) {
    m_cells[makeKey(layer, localX, localY)].tile = tile;
}

void Chunk::clearCell(const TileLayer layer, const uint8_t localX, const uint8_t localY) {
    m_cells[makeKey(layer, localX, localY)].tile = -1;
}
