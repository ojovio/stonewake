#include "../../include/screens/screen_manager.h"

ScreenManager::ScreenManager(Game &game)
    : m_game(game)
{
}

void ScreenManager::push(std::unique_ptr<Screen> screen) {
    if (!m_screens.empty() && m_screens.back()->shouldPause()) {
        m_screens.back()->pause();
    }

    m_screens.push_back(std::move(screen));
    m_screens.back()->enter();
}

std::unique_ptr<Screen> ScreenManager::pop() {
    if (m_screens.empty())
        return nullptr;

    m_screens.back()->exit();

    auto screen = std::move(m_screens.back());
    m_screens.pop_back();

    if (!m_screens.empty() && m_screens.back()->isPaused()) {
        m_screens.back()->resume();
    }

    return screen;
}

Screen* ScreenManager::top() const {
    if (m_screens.empty())
        return nullptr;

    return m_screens.back().get();
}

std::vector<std::unique_ptr<Screen>>& ScreenManager::all() {
    return m_screens;
}

void ScreenManager::input() const {
    for (auto& screen : m_screens) {
        if (screen->isPaused()) continue;

        screen->input();
    }
}

void ScreenManager::update() const {
    for (auto& screen : m_screens) {
        if (screen->isPaused()) continue;
         screen->update();
    }
}

void ScreenManager::render() const {
    for (int i = static_cast<int>(m_screens.size()) - 1; i >= 0; --i) {
        auto& screen = m_screens[i];
        screen->onRender(m_game.getAlpha(), m_game.getDeltaTime());

        if (screen->isOpaque()) {
            break;
        }
    }
}

void ScreenManager::exit() {
    while (!m_screens.empty()) {
        pop();
    }
}
