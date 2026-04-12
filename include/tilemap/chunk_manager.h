//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_CHUNK_MANAGER_H
#define STONEWAKE_CHUNK_MANAGER_H
#include <cstdint>
#include <unordered_map>

#include "chunk.h"


class ChunkManager {
    std::unordered_map<int64_t, Chunk> m_chunks = std::unordered_map<int64_t, Chunk>();

    std::unordered_map<int64_t, Chunk>& getChunks();
public:
    Chunk& getOrCreateChunk(int16_t x, int16_t y);
    Chunk& getChunk(int64_t key);
    void disposeChunk(int16_t x, int16_t y);
    bool hasChunk(int16_t x, int16_t y);

    static int64_t makeKey(int16_t x, int16_t y);
};



#endif //STONEWAKE_CHUNK_MANAGER_H
