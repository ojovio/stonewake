//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_SCREEN_H
#define STONEWAKE_SCREEN_H
#include <string>

#include "../game.h"


class Game;

class Screen {
    Game& m_game;
    std::string m_name;
    bool m_isOpaque;
    bool m_shouldPause;
    bool m_paused{};
public:
    virtual ~Screen() = default;

    Screen(Game& game, const std::string &name, bool isOpaque, bool shouldPause);

    virtual void onResume() = 0;
    virtual void onPause() = 0;

    virtual void onEnter() = 0;
    virtual void onInput(double dt) = 0;
    virtual void onUpdate(double timeStep) = 0;
    virtual void onRender(double alpha, double dt) = 0;
    virtual void onExit() = 0;

    void resume();
    void pause();

    void enter();
    void input();
    void update();
    void render();
    void exit();

    [[nodiscard]] Game& getGame() const;
    std::string getName();
    [[nodiscard]] bool isOpaque() const;
    [[nodiscard]] bool isPaused() const;
    [[nodiscard]] bool shouldPause() const;
};



#endif //STONEWAKE_SCREEN_H
