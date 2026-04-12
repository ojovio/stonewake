//
// Created by j-otavio on 06/04/2026.
//

#ifndef STONEWAKE_CAMERA_MANAGER_H
#define STONEWAKE_CAMERA_MANAGER_H
#include "raylib.h"


class CameraManager {
    Camera2D camera{};
public:
    CameraManager();

    Vector2 getPos() const;
    void setPos(Vector2 newPos);

    float getRotation() const;
    void setRotation(float newRotation);

    float getZoom() const;
    void setZoom(float newZoom);

    void begin() const;
    void end();
};



#endif //STONEWAKE_CAMERA_MANAGER_H
