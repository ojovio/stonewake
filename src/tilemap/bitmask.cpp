//
// Created by j-otavio on 07/04/2026.
//

#include "../../include/tilemap/bitmask.h"

#include "../../include/tilemap/chunk.h"

uint8_t Bitmask::get(const int dx, const int dy) {
    if (dx == -1 && dy == -1) return TL;
    if (dx ==  0 && dy == -1) return T;
    if (dx ==  1 && dy == -1) return TR;

    if (dx == -1 && dy ==  0) return L;
    if (dx ==  1 && dy ==  0) return R;

    if (dx == -1 && dy == 1) return BL;
    if (dx ==  0 && dy == 1) return B;
    if (dx ==  1 && dy == 1) return BR;

    return 0;
}
