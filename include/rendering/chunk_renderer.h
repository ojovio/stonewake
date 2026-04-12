//
// Created by j-otavio on 06/04/2026.
//

#ifndef STONEWAKE_CHUNK_RENDERER_H
#define STONEWAKE_CHUNK_RENDERER_H
#include <cstdint>
#include <unordered_map>

#include "camera_manager.h"
#include "chunk_lighting.h"
#include "raylib.h"
#include "../content/assets/texture_manager.h"
#include "../content/tile/tile_registry.h"
#include "../tilemap/chunk.h"
#include "../tilemap/chunk_manager.h"


class ChunkRenderer {
    std::unordered_map<int64_t, RenderTexture2D> m_textures = std::unordered_map<int64_t, RenderTexture2D>();
    ChunkLighting m_chunkLighting = ChunkLighting();

    static void draw(ChunkLighting::ChunkLightmap& lightmap, ChunkManager& chunkManager, TextureManager& textureManager,
        const TileRegistry& tileRegistry, Chunk& chunk);
public:
    void buildLights(ChunkManager& chunkManager, const TileRegistry& tileRegistry, Chunk& chunk);
    void propagateLights(ChunkManager& chunkManager, const TileRegistry& tileRegistry, Chunk& chunk);

    void build(ChunkManager& chunkManager, TextureManager& textureManager, const TileRegistry& tileRegistry, Chunk& chunk);

    void unload(const Chunk& chunk);

    void unloadAll();

    void render(ChunkManager& chunkManager);
};



#endif //STONEWAKE_CHUNK_RENDERER_H
