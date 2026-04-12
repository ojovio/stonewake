//
// Created by j-otavio on 05/04/2026.
//

#include "../../include/utils/debug.h"

void drawDebugUI(Game& game) {
    ImGui::Begin("Debug");

    ImGui::TextColored(ImVec4(1.0f, 1.0f, 1.0f, 1.0f), "FPS: %d", GetFPS());
    ImGui::Text("Timestep: %f", game.getTimeStep());
    ImGui::Text("Frame Time: %f", game.getFrameTime());
    ImGui::Text("Delta Time: %f", game.getDeltaTime());
    ImGui::Text("Alpha: %f", game.getAlpha());

    ImGui::End();
}