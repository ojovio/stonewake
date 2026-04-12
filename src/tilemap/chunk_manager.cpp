//
// Created by j-otavio on 05/04/2026.
//

#include "../../include/tilemap/chunk_manager.h"

int64_t ChunkManager::makeKey(const int16_t x, const int16_t y) {
    return (static_cast<int64_t>(x) << 32) | static_cast<uint32_t>(y);
}

Chunk &ChunkManager::getOrCreateChunk(const int16_t x, const int16_t y) {
    const int64_t chunkKey = makeKey(x, y);

    if (const auto it = m_chunks.find(chunkKey); it != m_chunks.end()) {
        return it->second;
    }

    auto [newIt, inserted] = m_chunks.try_emplace(chunkKey, x, y);
    return newIt->second;
}

Chunk& ChunkManager::getChunk(const int64_t key) {
    return m_chunks.at(key);
}

void ChunkManager::disposeChunk(const int16_t x, const int16_t y) {
    const int64_t key = makeKey(x, y);

    if (const auto it = m_chunks.find(key); it != m_chunks.end()) {
        m_chunks.erase(it);
    }
}

std::unordered_map<int64_t, Chunk> &ChunkManager::getChunks() {
    return m_chunks;
}

bool ChunkManager::hasChunk(const int16_t x, const int16_t y) {
    return m_chunks.contains(makeKey(x, y));
}
