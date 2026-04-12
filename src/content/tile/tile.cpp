//
// Created by j-otavio on 05/04/2026.
//

#include "../../../include/content/tile/tile.h"

Tile::Tile(const std::string &id, const std::string &name, const std::string &texturePath) : internalId(-1) {
    this->id = id;
    this->name = name;
    this->texturePath = texturePath;
}
