//
// Created by j-otavio on 12/04/2026.
//

#ifndef STONEWAKE_CHUNK_COLLISION_H
#define STONEWAKE_CHUNK_COLLISION_H
#include <cstdint>
#include <unordered_map>
#include <vector>

#include "raylib.h"
#include "../content/tile/tile.h"
#include "../tilemap/chunk.h"
#include "box2d/types.h"

class ChunkCollision {
    std::unordered_map<uint64_t, std::vector<b2BodyId>> bodies;

    struct ChunkMesh {
        Vector2 pos;
        Vector2 size;
    };

    bool isTileCompatible(Tile& a, Tile& b);
    ChunkMesh buildMesh(Chunk& chunk, Vector2 startingCell, std::vector<Vector2>& visitedCells);
public:
    void build(Chunk& chunk);
};



#endif //STONEWAKE_CHUNK_COLLISION_H
