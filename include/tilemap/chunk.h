//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_CHUNK_H
#define STONEWAKE_CHUNK_H
#include <array>

#include "cell.h"
#include "tile_config.h"


struct Chunk {
    const int16_t x;
    const int16_t y;

    Chunk(int16_t x, int16_t y);

    std::array<Cell, TileConfig::CHUNK_SIZE>& getCells();
    Cell& getCell(TileLayer layer, uint8_t localX, uint8_t localY);
    void setCell(TileLayer layer, uint8_t localX, uint8_t localY, int16_t tile);
    void clearCell(TileLayer layer, uint8_t localX, uint8_t localY);

    static int makeKey(TileLayer layer, uint8_t x, uint8_t y);
private:
    std::array<Cell, TileConfig::CHUNK_SIZE> m_cells{};
};



#endif //STONEWAKE_CHUNK_H
