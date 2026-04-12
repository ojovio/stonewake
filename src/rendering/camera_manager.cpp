//
// Created by j-otavio on 06/04/2026.
//

#include "../../include/rendering/camera_manager.h"

CameraManager::CameraManager() {
    this->camera = Camera2D();
    this->camera.target = { 0, 0 };
    this->camera.offset = { static_cast<float>(GetScreenWidth()) / 2.0f, static_cast<float>(GetScreenHeight()) / 2.0f };
    this->camera.rotation = 0.0f;
    this->camera.zoom = 2.0f;
}

Vector2 CameraManager::getPos() const {
    return this->camera.target;
}

void CameraManager::setPos(const Vector2 newPos) {
    this->camera.target = newPos;
}

float CameraManager::getRotation() const {
    return this->camera.rotation;
}

void CameraManager::setRotation(const float newRotation) {
    this->camera.rotation = newRotation;
}

float CameraManager::getZoom() const {
    return this->camera.zoom;
}

void CameraManager::setZoom(const float newZoom) {
    this->camera.zoom = newZoom;
}

void CameraManager::begin() const {
    BeginMode2D(camera);
}

void CameraManager::end() {
    EndMode2D();
}
