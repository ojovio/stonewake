//
// Created by j-otavio on 05/04/2026.
//

#include <utility>

#include "../../include/screens/screen.h"

#include "raylib.h"

Screen::Screen(Game& game, const std::string &name, const bool isOpaque, const bool shouldPause)
    : m_game(game){
    this->m_name = name;
    this->m_isOpaque = isOpaque;
    this->m_shouldPause = shouldPause;
    this->m_paused = false;
}

void Screen::resume() {
    m_paused = false;
    this->onResume();
}

void Screen::pause() {
    m_paused = true;
    this->onPause();
}

void Screen::enter() {
    this->onEnter();
}

void Screen::input() {
    if (WindowShouldClose()) return;
    this->onInput(m_game.getDeltaTime());
}

void Screen::update() {
    if (WindowShouldClose()) return;
    this->onUpdate(m_game.getTimeStep());
}

void Screen::render() {
    if (WindowShouldClose()) return;
    this->onRender(m_game.getAlpha(), m_game.getDeltaTime());
}

void Screen::exit() {
    this->onExit();
}

Game &Screen::getGame() const {
    return m_game;
}

bool Screen::isOpaque() const {
    return m_isOpaque;
}

bool Screen::isPaused() const {
    return m_paused;
}

bool Screen::shouldPause() const {
    return m_shouldPause;
}