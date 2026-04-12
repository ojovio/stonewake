//
// Created by j-otavio on 05/04/2026.
//

#include "../include/game.h"

#include "raylib.h"
#include "rlImGui.h"
#include "../../include/screens/screen_manager.h"
#include "../include/game/screens/world_screen.h"
#include "../include/tilemap/chunk_manager.h"
#include "../include/utils/debug.h"
#include "../include/utils/splashtext.h"

Game::Game()
    : m_screenManager(new ScreenManager(*this)){
    m_timeStep = 1/60.0f;
}

Game::~Game() {
    delete m_screenManager;
}

double Game::getDeltaTime() const {
    return m_deltaTime;
}

double Game::getFrameTime() const {
    return m_frameTime;
}

double Game::getTimeStep() const {
    return m_timeStep;
}

double Game::getAlpha() const {
    return m_alpha;
}

bool Game::inDebugMode() const {
    return m_inDebugMode;
}

void Game::run(int windowWidth, const int windowHeight, int targetFPS, bool vsync, bool fullscreen, bool debug) {
    m_inDebugMode = debug;

    InitWindow(windowWidth, windowHeight, ("Stonewake - " + getRandomSplashText()).c_str());

    if (vsync)
        SetConfigFlags(FLAG_VSYNC_HINT);
    else
        SetTargetFPS(targetFPS);

    if (fullscreen)
        ToggleFullscreen();

    m_screenManager->push(std::make_unique<WorldScreen>(*this));

    rlImGuiSetup(true);

    while (!WindowShouldClose()) {
        const double now = GetTime();
        m_frameTime = now - m_lastFrame;
        m_deltaTime = std::min(m_frameTime, 0.25);
        m_lastFrame = now;
        m_accumulator += m_deltaTime;

        m_screenManager->input();

        int steps = 0;
        while (m_accumulator >= m_timeStep) {
            if (steps > 5) break;

            m_screenManager->update();
            m_accumulator -= m_timeStep;
            steps++;
        }

        m_alpha = m_accumulator / m_timeStep;

        BeginDrawing();
        ClearBackground(SKYBLUE);

        m_screenManager->render();

        if (m_inDebugMode) {
            rlImGuiBegin();
            drawDebugUI(*this);
            rlImGuiEnd();
        }

        EndDrawing();
    }

    m_screenManager->top()->exit();
    rlImGuiShutdown();
    CloseWindow();
}
