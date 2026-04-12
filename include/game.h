// game.h
#ifndef STONEWAKE_GAME_H
#define STONEWAKE_GAME_H

#include <memory>

#include "screens/screen_manager.h"

class ScreenManager;

class Game {
    bool m_inDebugMode = false;
    ScreenManager* m_screenManager;
    double m_timeStep = 0.0f;
    double m_deltaTime = 0.0f;
    double m_frameTime = 0.0f;
    double m_alpha = 0.0f;
    double m_accumulator = 0.0f;
    double m_lastFrame = 0.0f;
public:
    Game();
    ~Game();

    [[nodiscard]] double getTimeStep() const;
    [[nodiscard]] double getDeltaTime() const;
    [[nodiscard]] double getFrameTime() const;
    [[nodiscard]] double getAlpha() const;
    [[nodiscard]] bool inDebugMode() const;
    void run(int windowWidth, int windowHeight, int targetFPS, bool vsync, bool fullscreen, bool debug);

    ScreenManager& getScreenManager();
};

#endif