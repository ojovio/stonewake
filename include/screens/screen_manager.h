//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_SCREEN_MANAGER_H
#define STONEWAKE_SCREEN_MANAGER_H
#include <memory>
#include <vector>

#include "screen.h"

class Game;
class Screen;

class ScreenManager {
    Game& m_game;
    std::vector<std::unique_ptr<Screen>> m_screens = std::vector<std::unique_ptr<Screen>>();
public:
    ScreenManager(Game &game);

    void push(std::unique_ptr<Screen> screen);
    std::unique_ptr<Screen> pop();
    [[nodiscard]] Screen* top() const;
    std::vector<std::unique_ptr<Screen>>& all();

    void input() const;
    void update() const;
    void render() const;
    void exit();
};



#endif //STONEWAKE_SCREEN_MANAGER_H
