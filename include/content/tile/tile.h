//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_TILE_H
#define STONEWAKE_TILE_H
#include <cstdint>
#include <string>
#include <raylib.h>

#include "../../tilemap/bitmask.h"


struct Tile {
    std::string id;
    std::string name;
    std::string texturePath;

    bool isEmissive = false;
    Color lightColor = {255, 255, 255};
    float lightIntensity = 10.0f;

    bool isSolid = true;

    Tile() = default;

    Tile(const std::string &id, const std::string &name, const std::string &texturePath);

    virtual Vector2 getTextureRegion(ChunkManager& chunkManager, Cell& cell) const {
        int mask = Bitmask::calculate(chunkManager, cell, [](const Cell& cell) {
            return cell.isOccupied();
        });

        const bool T = mask & Bitmask::T;
        const bool TR = mask & Bitmask::TR;
        const bool R = mask & Bitmask::R;
        const bool BR = mask & Bitmask::BR;
        const bool B = mask & Bitmask::B;
        const bool BL = mask & Bitmask::BL;
        const bool L = mask & Bitmask::L;
        const bool TL = mask & Bitmask::TL;
        if (B && R && BR && !T && !L)
            return Vector2(1, 0);
        if (T && R && TR && !B && !L)
            return Vector2(1, 2);
        if (B && L && BL && !T && !R)
            return Vector2(3, 0);
        if (T && L && TL && !B && !R)
            return Vector2(3, 2);
        if (B && R && L && !T)
            return Vector2(2, 0);
        if (T && R && L && !B)
            return Vector2(2, 2);
        if (R && T && B && !L)
            return Vector2(1, 1);
        if (L && T && B && !R)
            return Vector2(3, 1);

        return Vector2(0,0);
    }

    friend class TileRegistry;
    friend class ChunkRenderer;
private:
    uint16_t internalId;
};

#endif //STONEWAKE_TILE_H
