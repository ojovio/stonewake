#include "../../include/content/tile/tile_registry.h"
#include <iostream>

void TileRegistry::registerTile(const Tile& tile) {
    if (m_idToId.contains(tile.id)) {
        std::cerr << "Already registered tile: " << tile.id << std::endl;
        return;
    }

    const int16_t newId = m_nextId++;
    m_idToId[tile.id] = newId;
    m_tiles.push_back(tile);
}

int16_t TileRegistry::getInternalId(const std::string& id) const {
    if (const auto it = m_idToId.find(id); it != m_idToId.end()) {
        return it->second;
    }
    return 0;
}

const Tile& TileRegistry::get(int16_t internalId) const {
    if (internalId < 0 || internalId > static_cast<int16_t>(m_tiles.size())) {
        static Tile airTile("stonewake:air", "Air", "");
        return airTile;
    }
    return m_tiles[internalId];
}

const Tile& TileRegistry::get(const std::string& id) const {
    const int16_t internalId = getInternalId(id);
    return get(internalId);
}