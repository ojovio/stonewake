//
// Created by j-otavio on 05/04/2026.
//

#include "../../../include/content/assets/texture_manager.h"

#include <iostream>

void TextureManager::load(const std::string &path) {
    const Texture2D texture = LoadTexture(path.c_str());
    SetTextureFilter(texture, TEXTURE_FILTER_POINT);
    SetTextureWrap(texture, TEXTURE_WRAP_CLAMP);
    m_textures[path] = texture;
}

void TextureManager::unload(const std::string& path) {
    const Texture2D texture = m_textures.at(path);
    m_textures.erase(path);

    UnloadTexture(texture);
}

void TextureManager::unloadAll() {
    for (auto& [path, texture] : m_textures) {
        UnloadTexture(texture);
    }
    m_textures.clear();
}

Texture2D& TextureManager::get(const std::string& path) {
    if (!m_textures.contains(path)) {
        std::cerr << "Texture not loaded: " << path << std::endl;
        throw std::runtime_error("Texture not loaded");
    }
    return m_textures.at(path);
}

TextureManager::~TextureManager() {
    unloadAll();
}