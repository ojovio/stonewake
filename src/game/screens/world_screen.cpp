//
// Created by j-otavio on 05/04/2026.
//

#include "../../../include/game/screens/world_screen.h"

#include <filesystem>
#include <fstream>
#include <iostream>

#include "raylib.h"

WorldScreen::WorldScreen(Game& game) : Screen(game, "World", true, false), m_worldGenerator(1337) {
    m_textureManager = TextureManager();
    m_tileRegistry = TileRegistry();
    m_chunkManager = ChunkManager();
    m_chunkRenderer = ChunkRenderer();
    m_cameraManager = CameraManager();
}

void WorldScreen::onResume() {

}

void WorldScreen::onPause() {

}

void WorldScreen::onEnter() {
    m_textureManager.load("assets/textures/tiles/Grass.png");
    m_tileRegistry.registerTile(Tile("stonewake:grass", "Grass", "assets/textures/tiles/Grass.png"));
    m_textureManager.load("assets/textures/tiles/Stone.png");
    m_tileRegistry.registerTile(Tile("stonewake:stone", "Stone", "assets/textures/tiles/Stone.png"));
    m_textureManager.load("assets/textures/tiles/Red Clay.png");
    m_tileRegistry.registerTile(Tile("stonewake:red_clay", "Red Clay", "assets/textures/tiles/Red Clay.png"));
    m_textureManager.load("assets/textures/tiles/Water.png");
    m_tileRegistry.registerTile(Tile("stonewake:water", "Water", "assets/textures/tiles/Water.png"));

    m_worldGenerator.addGenPass("Generating terrain", 0.0f, [](WorldGenerator& gen, const TileRegistry& tileRegistry, Chunk& chunk) {
        for (int x = 0; x < TileConfig::CHUNK_WIDTH; x++) {
            for (int y = 0; y < TileConfig::CHUNK_HEIGHT; y++) {
                Cell& cell = chunk.getCell(TileLayer::GROUND, x, y);
                const float height = gen.getHeightNoise(cell.getGlobalX(), cell.getGlobalY());
                const int terrainHeight = static_cast<int>((height + 1.0f) / 2.0f * 60);

                if (const int globalY = cell.getGlobalY(); globalY > terrainHeight + 15) {
                    chunk.setCell(TileLayer::GROUND, x, y, tileRegistry.getInternalId("stonewake:stone"));
                    chunk.setCell(TileLayer::BACKGROUND, x, y, tileRegistry.getInternalId("stonewake:stone"));
                } else if (globalY > terrainHeight + 10) {
                    if ((x + y) % 2 == 0) {
                        chunk.setCell(TileLayer::GROUND, x, y, tileRegistry.getInternalId("stonewake:stone"));
                        chunk.setCell(TileLayer::BACKGROUND, x, y, tileRegistry.getInternalId("stonewake:stone"));
                    } else {
                        chunk.setCell(TileLayer::GROUND, x, y, tileRegistry.getInternalId("stonewake:grass"));
                        chunk.setCell(TileLayer::BACKGROUND, x, y, tileRegistry.getInternalId("stonewake:grass"));
                    }
                } else if (globalY > terrainHeight) {
                    chunk.setCell(TileLayer::GROUND, x, y, tileRegistry.getInternalId("stonewake:grass"));
                }
            }
        }
    });

    m_worldGenerator.addGenPass("Generating caves", 1.0f, [](WorldGenerator& gen, const TileRegistry& tileRegistry, Chunk& chunk) {
        for (int x = 0; x < TileConfig::CHUNK_WIDTH; x++) {
            for (int y = 0; y < TileConfig::CHUNK_HEIGHT; y++) {
                Cell& cell = chunk.getCell(TileLayer::GROUND, x, y);
                const int globalX = cell.getGlobalX();
                const int globalY = cell.getGlobalY();

                const float height = gen.getHeightNoise(globalX, globalY);

                if (const int terrainHeight = static_cast<int>((height + 1.0f) / 2.0f * TileConfig::CHUNK_HEIGHT); globalY <= terrainHeight + 50) continue;

                 float caveNoise = gen.getHeight().GetNoise(static_cast<float>(globalX) * 0.75f, static_cast<float>(globalY) * 0.75f, static_cast<float>(globalY) * 0.5f);

                 if (caveNoise > 0.2f && caveNoise < 0.8f) {
                     chunk.clearCell(TileLayer::GROUND, x, y);
                 }
            }
        }
    });

    m_worldGenerator.addGenPass("Lakes", 0.2f, [](WorldGenerator& gen, const TileRegistry& tileRegistry, Chunk& chunk) {
        for (int x = 0; x < TileConfig::CHUNK_WIDTH; x++) {
            for (int y = 0; y < TileConfig::CHUNK_HEIGHT; y++) {
                Cell& cell = chunk.getCell(TileLayer::GROUND, x, y);

                if (cell.isEmpty()) {
                    int gx = cell.getGlobalX();
                    int gy = cell.getGlobalY();

                    float moisture = gen.getHumidityNoise(gx, gy);

                    if (moisture > 0.75f && gy > 40) {
                        chunk.setCell(TileLayer::GROUND, x, y, tileRegistry.getInternalId("stonewake:water"));
                    }
                }
            }
        }
    });

    for (int16_t cx = 0; cx < 30; cx++) {
        for (int16_t cy = 0; cy < 5; cy++) {
            m_worldGenerator.generateChunk(m_chunkManager, m_tileRegistry, cx, cy);
        }
    }

    for (int16_t cx = 0; cx < 30; cx++) {
        for (int16_t cy = 0; cy < 5; cy++) {
            m_chunkRenderer.buildLights(m_chunkManager, m_tileRegistry, m_chunkManager.getOrCreateChunk(cx, cy));
        }
    }

    for (int16_t cx = 0; cx < 30; cx++) {
        for (int16_t cy = 0; cy < 5; cy++) {
            m_chunkRenderer.propagateLights(m_chunkManager, m_tileRegistry, m_chunkManager.getOrCreateChunk(cx, cy));
        }
    }
}

void WorldScreen::onInput(const double dt) {
    constexpr float cameraSpeed = 250.0f;

    Vector2 cameraPos = m_cameraManager.getPos();

    if (IsKeyDown(KEY_A)) cameraPos.x -= cameraSpeed * dt;
    if (IsKeyDown(KEY_D)) cameraPos.x += cameraSpeed * dt;
    if (IsKeyDown(KEY_W)) cameraPos.y -= cameraSpeed * dt;
    if (IsKeyDown(KEY_S)) cameraPos.y += cameraSpeed * dt;

    m_cameraManager.setPos(cameraPos);
}
void WorldScreen::onUpdate(double timeStep) {

}

void WorldScreen::onRender(double alpha, double dt) {
    m_cameraManager.begin();

    if (!rendered) {
        for (int cx = 0; cx < 30; cx++) {
            for (int cy = 0; cy < 5; cy++) {
                Chunk& chunk = m_chunkManager.getOrCreateChunk(cx, cy);
                m_chunkRenderer.build(m_chunkManager, m_textureManager, m_tileRegistry, chunk);
            }
        }
        rendered = true;
    }
    m_chunkRenderer.render(m_chunkManager);

    m_cameraManager.end();
}

void WorldScreen::onExit() {
    m_chunkRenderer.unloadAll();
    m_textureManager.unloadAll();
}
