//
// Created by j-otavio on 07/04/2026.
//

#ifndef STONEWAKE_BITMASK_H
#define STONEWAKE_BITMASK_H
#include <cstdint>

#include "cell.h"
#include "chunk_manager.h"


class Bitmask {
public:
    static constexpr uint8_t TL = 1 << 0;
    static constexpr uint8_t T = 1 << 1;
    static constexpr uint8_t TR = 1 << 2;
    static constexpr uint8_t R = 1 << 3;
    static constexpr uint8_t BR = 1 << 4;
    static constexpr uint8_t B = 1 << 5;
    static constexpr uint8_t BL = 1 << 6;
    static constexpr uint8_t L = 1 << 7;

    template<typename Func>
    static uint8_t calculate(ChunkManager& chunkManager, Cell& cell, Func&& rule)  {
        uint8_t mask = 0;

        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;

                int displacedX = cell.localX + dx;
                int displacedY = cell.localY + dy;
                int16_t chunkX = cell.chunkX;
                int16_t chunkY = cell.chunkY;

                if (displacedX < 0) {
                    displacedX = TileConfig::CHUNK_WIDTH - 1;
                    chunkX -= 1;
                }
                else if (displacedX >= TileConfig::CHUNK_WIDTH) {
                    displacedX = 0;
                    chunkX += 1;
                }

                if (displacedY < 0) {
                    displacedY = TileConfig::CHUNK_HEIGHT - 1;
                    chunkY -= 1;
                }
                else if (displacedY >= TileConfig::CHUNK_HEIGHT) {
                    displacedY = 0;
                    chunkY += 1;
                }

                if (chunkManager.hasChunk(chunkX, chunkY)) {
                    Chunk& chunk = chunkManager.getOrCreateChunk(chunkX, chunkY);

                    if (Cell& neighbor = chunk.getCell(cell.layer, displacedX, displacedY); rule(neighbor)) {
                        mask |= get(dx, dy);
                    }

                    continue;
                }

                mask |= get(dx, dy);
            }
        }

        return mask;
    }

    static uint8_t get(int dx, int dy);
};



#endif //STONEWAKE_BITMASK_H
