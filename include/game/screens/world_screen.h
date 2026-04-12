//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_WORLD_SCREEN_H
#define STONEWAKE_WORLD_SCREEN_H
#include "../../content/assets/texture_manager.h"
#include "../../content/tile/tile_registry.h"
#include "../../rendering/camera_manager.h"
#include "../../rendering/chunk_renderer.h"
#include "../../screens/screen.h"
#include "../../tilemap/chunk_manager.h"
#include "../../world/procgen/world_generator.h"


class WorldScreen : public Screen {
    TextureManager m_textureManager;
    TileRegistry m_tileRegistry;
    ChunkManager m_chunkManager;
    ChunkRenderer m_chunkRenderer;
    CameraManager m_cameraManager;
    WorldGenerator m_worldGenerator;
    bool rendered = false;
public:
    explicit WorldScreen(Game& game);

    void onResume() override;
    void onPause() override;

    void onEnter() override;
    void onInput(double dt) override;
    void onUpdate(double timeStep) override;
    void onRender(double alpha, double dt) override;
    void onExit() override;
};



#endif //STONEWAKE_WORLD_SCREEN_H
