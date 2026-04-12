//
// Created by j-otavio on 08/04/2026.
//

#ifndef STONEWAKE_WORLD_GENERATOR_H
#define STONEWAKE_WORLD_GENERATOR_H
#include <cstdint>
#include <functional>

#include "../../../libs/FastNoiseLite.h"
#include "../../content/tile/tile_registry.h"
#include "../../tilemap/chunk.h"
#include "../../tilemap/chunk_manager.h"


class WorldGenerator {
    const uint64_t m_seed;
    FastNoiseLite m_tempNoise;
    FastNoiseLite m_heightNoise;
    FastNoiseLite m_humidityNoise;
    FastNoiseLite m_biomeNoise;
public:
    explicit WorldGenerator(uint64_t seed);

    float getTempNoise(int globalX, int globalY);
    float getHeightNoise(int globalX, int globalY);
    float getHumidityNoise(int globalX, int globalY);
    float getBiomeNoise(int globalX, int globalY);
    FastNoiseLite getTemp() const;
    FastNoiseLite getHeight() const;
    FastNoiseLite getHumidity() const;
    FastNoiseLite getBiome() const;
    [[nodiscard]] uint64_t getSeed() const;

    using GenPass = std::function<void(WorldGenerator& generator, TileRegistry& tileRegistry, Chunk& chunk)>;

    void addGenPass(const std::string& name, float priority, GenPass pass);

    void generateChunk(ChunkManager& chunkManager, TileRegistry& tileRegistry, int16_t chunkX, int16_t chunkY);
private:
    struct GenPassEntry {
        std::string name;
        GenPass pass;
        float priority;
    };
    std::vector<GenPassEntry> m_genPasses = std::vector<GenPassEntry>();

    static float getNoise(FastNoiseLite& noise, int globalX, int globalY);
};



#endif //STONEWAKE_WORLD_GENERATOR_H
