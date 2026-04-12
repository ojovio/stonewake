#ifndef STONEWAKE_TILE_REGISTRY_H
#define STONEWAKE_TILE_REGISTRY_H

#include <string>
#include <unordered_map>
#include <vector>
#include "tile.h"

class TileRegistry {
private:
    struct TileEntry {
        int16_t internalId{};
        Tile tile;
    };

    int16_t m_nextId = 0;
    std::unordered_map<std::string, int16_t> m_idToId;
    std::vector<Tile> m_tiles;

public:
    void registerTile(const Tile& tile);

    int16_t getInternalId(const std::string& id) const;
    const Tile& get(int16_t internalId) const;
    const Tile& get(const std::string& id) const;

    size_t size() const { return m_tiles.size(); }
};

#endif