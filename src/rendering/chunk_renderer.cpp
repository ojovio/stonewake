#include "../../include/rendering/chunk_renderer.h"

#include <cmath>

#include "../../include/rendering/render_config.h"

void ChunkRenderer::draw(ChunkLighting::ChunkLightmap& lightmap, ChunkManager& chunkManager, TextureManager& textureManager,
                         const TileRegistry& tileRegistry, Chunk& chunk) {
    for (uint8_t layer = 0; layer < TileConfig::CHUNK_DEPTH; layer++) {
        for (int8_t x = 0; x < TileConfig::CHUNK_WIDTH; x++) {
            for (int8_t y = 0; y < TileConfig::CHUNK_HEIGHT; y++) {
                Cell &cell = chunk.getCell(static_cast<TileLayer>(layer), x, y);

                if (cell.isEmpty()) continue;

                const Tile& tile = tileRegistry.get(cell.tile);
                const Texture2D& texture = textureManager.get(tile.texturePath);
                const auto [tx, ty] = tile.getTextureRegion(chunkManager, cell);

                const Rectangle source = {
                    tx * TileConfig::TILE_SIZE, ty * TileConfig::TILE_SIZE,
                    TileConfig::TILE_SIZE,
                    TileConfig::TILE_SIZE
                };

                const Rectangle dest = {
                    std::floor(static_cast<float>(x) * TileConfig::TILE_SIZE),
                    std::floor(static_cast<float>(y) * TileConfig::TILE_SIZE),
                    TileConfig::TILE_SIZE,
                    TileConfig::TILE_SIZE
                };

                const Color lightColor = lightmap.getLight(static_cast<TileLayer>(layer), x, y);
                const auto light = Color(lightColor.r, lightColor.g, lightColor.b, 255.0f);
                DrawTexturePro(texture, source, dest, {0, 0}, 0, light);
            }
        }
    }
}

void ChunkRenderer::buildLights(ChunkManager& chunkManager, const TileRegistry& tileRegistry, Chunk& chunk) {
    const int16_t cx = chunk.x;
    const int16_t cy = chunk.y;
    ChunkLighting::ChunkLightmap& lightmap = m_chunkLighting.getOrCreateChunkLightmap(cx, cy);
    lightmap.clear();

    for (int8_t layer = 0; layer < TileConfig::CHUNK_DEPTH; layer++) {
        for (int8_t x = 0; x < TileConfig::CHUNK_WIDTH; x++) {
            for (int8_t y = 0; y < TileConfig::CHUNK_HEIGHT; y++) {
                Cell &cell = chunk.getCell(static_cast<TileLayer>(layer), x, y);

                const Tile& tile = tileRegistry.get(cell.tile);

                if (!tile.isEmissive) continue;

                lightmap.addLight(static_cast<TileLayer>(layer), x, y, tile.lightColor, tile.lightIntensity);
            }
        }
    }

    for (int8_t x = 0; x < TileConfig::CHUNK_WIDTH; x++) {
        for (int8_t y = 0; y < TileConfig::CHUNK_HEIGHT; y++) {
            Cell &bg = chunk.getCell(TileLayer::BACKGROUND, x, y);
            Cell &g = chunk.getCell(TileLayer::GROUND, x, y);

            if (!bg.isEmpty() || !g.isEmpty()) continue;

            lightmap.addLight(TileLayer::BACKGROUND, x, y, {200, 200, 255}, 10.f);
        }
    }
}

void ChunkRenderer::propagateLights(ChunkManager &chunkManager, const TileRegistry &tileRegistry, Chunk &chunk) {
    const int16_t cx = chunk.x;
    const int16_t cy = chunk.y;
    ChunkLighting::ChunkLightmap& lightmap = m_chunkLighting.getOrCreateChunkLightmap(cx, cy);

    lightmap.propagateAll(m_chunkLighting, Vector2(cx, cy), TileLayer::GROUND, 15);
    lightmap.propagateAll(m_chunkLighting, Vector2(cx, cy), TileLayer::BACKGROUND, 15);
}

void ChunkRenderer::build(ChunkManager& chunkManager, TextureManager& textureManager, const TileRegistry& tileRegistry, Chunk& chunk) {
    const int16_t cx = chunk.x;
    const int16_t cy = chunk.y;
    const auto key = ChunkManager::makeKey(cx, cy);
    ChunkLighting::ChunkLightmap& lightmap = m_chunkLighting.getOrCreateChunkLightmap(cx, cy);

    if (const auto it = m_textures.find(key); it != m_textures.end()) {
        BeginTextureMode(it->second);
        ClearBackground(BLANK);
        draw(lightmap, chunkManager, textureManager, tileRegistry, chunk);
        EndTextureMode();
    } else {
        const RenderTexture2D renderTexture = LoadRenderTexture(
            TileConfig::CHUNK_WIDTH * TileConfig::TILE_SIZE,
            TileConfig::CHUNK_HEIGHT * TileConfig::TILE_SIZE
        );

        SetTextureFilter(renderTexture.texture, TEXTURE_FILTER_POINT);

        BeginTextureMode(renderTexture);
        ClearBackground(BLANK);
        draw(lightmap, chunkManager, textureManager, tileRegistry, chunk);
        EndTextureMode();

        m_textures[key] = renderTexture;
    }
}

void ChunkRenderer::unload(const Chunk& chunk) {
    const int16_t x = chunk.x;
    const int16_t y = chunk.y;
    const auto key = ChunkManager::makeKey(x, y);

    if (const auto it = m_textures.find(key); it != m_textures.end()) {
        UnloadRenderTexture(it->second);
        m_textures.erase(it);
    }
}

void ChunkRenderer::unloadAll() {
    for (auto& [key, rt] : m_textures) {
        UnloadRenderTexture(rt);
    }
    m_textures.clear();
}

void ChunkRenderer::render(ChunkManager& chunkManager) {
    for (auto it = m_textures.begin(); it != m_textures.end(); ++it) {
        const Chunk& chunk = chunkManager.getChunk(it->first);

        constexpr float width = TileConfig::CHUNK_WIDTH * TileConfig::TILE_SIZE;
        constexpr float height = TileConfig::CHUNK_HEIGHT * TileConfig::TILE_SIZE;

        const float worldX = static_cast<float>(chunk.x) * width;
        const float worldY = static_cast<float>(chunk.y) * height;

        constexpr Rectangle source = {
            0, 0,
            width,
            -height
        };

        const Rectangle dest = {
            worldX,
            worldY,
            width,
            height
        };

        DrawTexturePro(it->second.texture, source, dest, {0, 0}, 0, WHITE);
    }
}