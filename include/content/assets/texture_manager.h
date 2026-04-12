//
// Created by j-otavio on 05/04/2026.
//

#ifndef STONEWAKE_TEXTURE_MANAGER_H
#define STONEWAKE_TEXTURE_MANAGER_H
#include <string>
#include <unordered_map>

#include "raylib.h"


class TextureManager {
    std::unordered_map<std::string, Texture2D> m_textures = std::unordered_map<std::string, Texture2D>();
public:
    void load(const std::string& path);

    void unload(const std::string& path);

    void unloadAll();

    Texture2D& get(const std::string& path);

    ~TextureManager();
};



#endif //STONEWAKE_TEXTURE_MANAGER_H
