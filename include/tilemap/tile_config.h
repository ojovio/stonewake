//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_TILE_CONFIG_H
#define STONEWAKE_TILE_CONFIG_H
#include <cstdint>

namespace TileConfig {
    constexpr uint8_t TILE_SIZE = 8;
    constexpr uint8_t CHUNK_DEPTH = 3;
    constexpr uint8_t CHUNK_WIDTH = 16;
    constexpr uint8_t CHUNK_HEIGHT = 32;
    constexpr int CHUNK_SIZE = CHUNK_DEPTH * CHUNK_WIDTH * CHUNK_HEIGHT;
}

#endif //STONEWAKE_TILE_CONFIG_H
