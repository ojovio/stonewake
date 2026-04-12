#ifndef STONEWAKE_CHUNK_LIGHTING_H
#define STONEWAKE_CHUNK_LIGHTING_H

#include <array>
#include <queue>
#include <unordered_set>
#include <unordered_map>
#include <vector>
#include <tuple>
#include "raylib.h"
#include "../tilemap/chunk.h"
#include "../tilemap/tile_config.h"

class ChunkLighting {
public:
    struct LightNode {
        float r, g, b;
        float intensity;
    };

    struct ChunkLightmap {
        std::array<LightNode, TileConfig::CHUNK_SIZE> m_lights{};

        ChunkLightmap();
        void addLight(TileLayer layer, uint8_t localX, uint8_t localY, const Color& color, float intensity);
        void propagateAll(ChunkLighting& chunkLighting, Vector2 currentChunk, TileLayer layer, int maxDistance = 15) const;
        [[nodiscard]] Color getLight(TileLayer layer, uint8_t localX, uint8_t localY) const;

        static Color getAmbientLight();
        void clear();
        std::array<LightNode, TileConfig::CHUNK_SIZE>& getLights();
    };

    ChunkLighting() = default;

    ChunkLightmap& getOrCreateChunkLightmap(int16_t chunkX, int16_t chunkY);
    bool hasChunkLightmap(int16_t chunkX, int16_t chunkY) const;
    void disposeChunkLightmap(int16_t chunkX, int16_t chunkY);

    static int64_t makeKey(int16_t x, int16_t y);

private:
    std::unordered_map<int64_t, ChunkLightmap> m_lightmaps;
};

#endif