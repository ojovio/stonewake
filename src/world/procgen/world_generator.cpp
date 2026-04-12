//
// Created by j-otavio on 08/04/2026.
//

#include <utility>

#include "../../../include/world/procgen/world_generator.h"

WorldGenerator::WorldGenerator(uint64_t seed) : m_seed(seed) {
    m_heightNoise.SetNoiseType(FastNoiseLite::NoiseType_OpenSimplex2);
    m_heightNoise.SetFrequency(0.01f);
    m_heightNoise.SetSeed(static_cast<int>(seed));
    m_heightNoise.SetFractalType(FastNoiseLite::FractalType_FBm);
    m_heightNoise.SetFractalOctaves(4);
    m_heightNoise.SetFractalLacunarity(2.0f);
    m_heightNoise.SetFractalGain(0.5f);

    m_tempNoise.SetNoiseType(FastNoiseLite::NoiseType_OpenSimplex2);
    m_tempNoise.SetFrequency(0.005f);
    m_tempNoise.SetSeed(static_cast<int>(seed + 1000));

    m_humidityNoise.SetNoiseType(FastNoiseLite::NoiseType_OpenSimplex2);
    m_humidityNoise.SetFrequency(0.008f);
    m_humidityNoise.SetSeed(static_cast<int>(seed + 2000));
    m_humidityNoise.SetFractalType(FastNoiseLite::FractalType_FBm);
    m_humidityNoise.SetFractalOctaves(3);
    m_humidityNoise.SetFractalLacunarity(2.0f);
    m_humidityNoise.SetFractalGain(0.5f);

    m_biomeNoise.SetNoiseType(FastNoiseLite::NoiseType_Cellular);
    m_biomeNoise.SetFrequency(0.002f);
    m_biomeNoise.SetSeed(static_cast<int>(seed + 3000));
    m_biomeNoise.SetCellularDistanceFunction(FastNoiseLite::CellularDistanceFunction_Euclidean);
    m_biomeNoise.SetCellularReturnType(FastNoiseLite::CellularReturnType_CellValue);
    m_biomeNoise.SetCellularJitter(1.0f);
}
float WorldGenerator::getNoise(FastNoiseLite& noise, const int globalX, const int globalY) {
    return noise.GetNoise(static_cast<float>(globalX), static_cast<float>(globalY));
}

float WorldGenerator::getTempNoise(const int globalX, const int globalY) {
    return getNoise(m_tempNoise, globalX, globalY);
}

float WorldGenerator::getHeightNoise(const int globalX, const int globalY) {
    return getNoise(m_heightNoise, globalX, globalY);
}

float WorldGenerator::getHumidityNoise(const int globalX, const int globalY) {
    return getNoise(m_humidityNoise, globalX, globalY);
}

float WorldGenerator::getBiomeNoise(const int globalX, const int globalY) {
    return getNoise(m_biomeNoise, globalX, globalY);
}

FastNoiseLite WorldGenerator::getHeight() const {
    return m_heightNoise;
}

FastNoiseLite WorldGenerator::getBiome() const {
    return m_biomeNoise;
}

FastNoiseLite WorldGenerator::getHumidity() const {
    return m_humidityNoise;
}

FastNoiseLite WorldGenerator::getTemp() const {
    return m_tempNoise;
}

uint64_t WorldGenerator::getSeed() const {
    return m_seed;
}

void WorldGenerator::addGenPass(const std::string& name, const float priority, GenPass pass) {
    m_genPasses.push_back({name, std::move(pass), priority});

    std::sort(m_genPasses.begin(), m_genPasses.end(),
        [](const GenPassEntry& a, const GenPassEntry& b) {
            return a.priority < b.priority;
        });
}

void WorldGenerator::generateChunk(ChunkManager& chunkManager, TileRegistry& tileRegistry, const int16_t chunkX, const int16_t chunkY) {
    Chunk& chunk = chunkManager.getOrCreateChunk(chunkX, chunkY);

    for (const GenPassEntry& genPass : m_genPasses) {
        genPass.pass(*this, tileRegistry, chunk);
    }
}
