//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_CELL_H
#define STONEWAKE_CELL_H
#include <cstdint>
#include <string>

#include "tile_layer.h"
#pragma pack(push, 1)

struct Cell {
    int16_t tile = -1;
    uint8_t localX = 0;
    uint8_t localY = 0;
    int16_t chunkX = 0;
    int16_t chunkY = 0;
    TileLayer layer = TileLayer::GROUND;

    Cell() = default;

    [[nodiscard]] bool isOccupied() const;
    [[nodiscard]] bool isEmpty() const;
    [[nodiscard]] int getGlobalX() const;
    [[nodiscard]] int getGlobalY() const;
};



#endif //STONEWAKE_CELL_H
