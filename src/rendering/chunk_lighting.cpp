#include "../../include/rendering/chunk_lighting.h"
#include <cmath>
#include <queue>
#include <algorithm>

ChunkLighting::ChunkLightmap::ChunkLightmap() {
    clear();
}

std::array<ChunkLighting::LightNode, TileConfig::CHUNK_SIZE>& ChunkLighting::ChunkLightmap::getLights() {
    return m_lights;
}

void ChunkLighting::ChunkLightmap::addLight(const TileLayer layer, const uint8_t localX, const uint8_t localY, const Color& color, const float intensity) {
    const int idx = Chunk::makeKey(layer, localX, localY);
    m_lights[idx] = {
        static_cast<float>(color.r),
        static_cast<float>(color.g),
        static_cast<float>(color.b),
        intensity
    };
}

void ChunkLighting::ChunkLightmap::propagateAll(ChunkLighting& chunkLighting, const Vector2 currentChunk, const TileLayer layer, const int maxDistance) const {
    struct LightNode {
        int x, y;
        float r, g, b;
        float currentDecay;
        int distance;
    };

    std::queue<LightNode> queue;

    for (int y = 0; y < TileConfig::CHUNK_HEIGHT; y++) {
        for (int x = 0; x < TileConfig::CHUNK_WIDTH; x++) {
            const int idx = Chunk::makeKey(layer, x, y);
            const auto& c = m_lights[idx];
            if (c.r > 0 || c.g > 0 || c.b > 0) {
                float lightDecay = 0.7f;
                queue.push({x, y, c.r, c.g, c.b, lightDecay, 0});
            }
        }
    }

    while (!queue.empty()) {
        const auto node = queue.front();
        queue.pop();

        if (node.distance >= maxDistance) continue;

        constexpr int dy[] = {1, 0, -1, 0};
        constexpr int dx[] = {0, 1, 0, -1};

        for (int i = 0; i < 4; i++) {
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];
            int cx = 0, cy = 0;

            if (nx < 0 || nx >= TileConfig::CHUNK_WIDTH || ny < 0 || ny >= TileConfig::CHUNK_HEIGHT) {
                if (nx < 0) {
                    nx = TileConfig::CHUNK_WIDTH - 1;
                    cx = -1;
                }
                else if (nx >= TileConfig::CHUNK_WIDTH) {
                    nx = 0;
                    cx = 1;
                }

                if (ny < 0) {
                    ny = TileConfig::CHUNK_HEIGHT - 1;
                    cy = -1;
                }
                else if (ny >= TileConfig::CHUNK_HEIGHT) {
                    ny = 0;
                    cy = 1;
                }
            }

            const int nIdx = Chunk::makeKey(layer, nx, ny);
            auto& chunk = chunkLighting.getOrCreateChunkLightmap(currentChunk.x + cx, currentChunk.y + cy);
            auto& current = chunk.getLights()[nIdx];

            const float nr = node.r * node.currentDecay;
            const float ng = node.g * node.currentDecay;
            const float nb = node.b * node.currentDecay;

            bool brighter = false;
            if (nr > current.r + 0.5f) { current.r = nr; brighter = true; }
            if (ng > current.g + 0.5f) { current.g = ng; brighter = true; }
            if (nb > current.b + 0.5f) { current.b = nb; brighter = true; }

            const float minLight = 15.0f;
            if (brighter && (nr > minLight || ng > minLight || nb > minLight)) {
                queue.push({nx, ny, nr, ng, nb, node.currentDecay, node.distance + 1});
            }
        }
    }
}

const Color SHADOW_COLOR = {20, 20, 30, 255};

Color ChunkLighting::ChunkLightmap::getLight(const TileLayer layer, const uint8_t localX, const uint8_t localY) const {
    float maxR = 0.0f;
    float maxG = 0.0f;
    float maxB = 0.0f;

    for (int l = 0; l < 3; ++l) {
        const int idx = Chunk::makeKey(static_cast<TileLayer>(l), localX, localY);
        const auto& light = m_lights[idx];
        maxR = std::max(maxR, light.r);
        maxG = std::max(maxG, light.g);
        maxB = std::max(maxB, light.b);
    }

    float factor = (layer == TileLayer::BACKGROUND) ? 0.5f : 1.0f;
    maxR = std::max(maxR * factor, static_cast<float>(SHADOW_COLOR.r));
    maxG = std::max(maxG * factor, static_cast<float>(SHADOW_COLOR.g));
    maxB = std::max(maxB * factor, static_cast<float>(SHADOW_COLOR.b));

    return {
        static_cast<unsigned char>(std::clamp(maxR, 0.0f, 255.0f)),
        static_cast<unsigned char>(std::clamp(maxG, 0.0f, 255.0f)),
        static_cast<unsigned char>(std::clamp(maxB, 0.0f, 255.0f)),
        255
    };
}

Color ChunkLighting::ChunkLightmap::getAmbientLight() {
    return {76, 76, 76, 255};
}

void ChunkLighting::ChunkLightmap::clear() {
    for (auto& light : m_lights) {
        light = {0, 0, 0, 0};
    }
}

int64_t ChunkLighting::makeKey(const int16_t x, const int16_t y) {
    return (static_cast<int64_t>(x) << 32) | static_cast<uint32_t>(y);
}

ChunkLighting::ChunkLightmap& ChunkLighting::getOrCreateChunkLightmap(const int16_t chunkX, const int16_t chunkY) {
    const int64_t key = makeKey(chunkX, chunkY);
    if (const auto it = m_lightmaps.find(key); it != m_lightmaps.end()) {
        return it->second;
    }
    return m_lightmaps[key];
}

bool ChunkLighting::hasChunkLightmap(const int16_t chunkX, const int16_t chunkY) const {
    return m_lightmaps.find(makeKey(chunkX, chunkY)) != m_lightmaps.end();
}

void ChunkLighting::disposeChunkLightmap(const int16_t chunkX, const int16_t chunkY) {
    m_lightmaps.erase(makeKey(chunkX, chunkY));
}