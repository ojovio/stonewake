#include <iostream>
#include "raylib.h"
#include "../include/game.h"
#include "../libs/imgui/rlImGui.h"
#include "../libs/imgui/imgui.h"
#include "../include/tilemap/chunk_manager.h"
#include "../include/utils/debug.h"
#include "../include/utils/splashtext.h"

int main(const int argc, char* argv[]) {
    int width = 800;
    int height = 600;
    int targetFPS = 120;
    bool debugMode = false;
    bool vsync = true;
    bool fullscreen = false;
    if (argc >= 2) {
        try {
            width = std::stoi(argv[1]);
            height = std::stoi(argv[2]);

            for (int i = 0; i < argc; i++) {
                if (strcmp(argv[i], "--max-fps") == 0) {
                    if (i + 1 < argc) {
                        targetFPS = std::stoi(argv[i + 1]);
                        i++;
                    }
                }
                if (std::string(argv[i]) == "--vsync") {
                    if (i + 1 < argc) {
                        vsync = std::string(argv[i + 1]) == "on";
                    }
                }

                if (strcmp(argv[i], "--debug") == 0) debugMode = true;
                if (strcmp(argv[i], "--fullscreen") == 0) fullscreen = true;
            }
        } catch (std::invalid_argument& e) {
            std::cout << "Invalid arguments. Using default values." << std::endl;
        }
    }

    auto game = Game();
    game.run(width, height, targetFPS, vsync, fullscreen, debugMode);

    return 0;
}
