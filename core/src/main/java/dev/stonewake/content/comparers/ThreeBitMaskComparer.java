package dev.stonewake.tiles.tiling.comparers;

import dev.stonewake.tiles.tiling.BitMaskComparer;

public class ThreeBitMaskComparer extends BitMaskComparer {

    @Override
    public int filter(int mask) {
        return mask;
    }

    public int filter(int mask, int requiredMask, int ignoreMask, int forbiddenMask) {
        int maskToTest = mask & ~ignoreMask;
        int required = requiredMask & ~ignoreMask;
        int forbidden = forbiddenMask & ~ignoreMask;

        int forbiddenFound = maskToTest & forbidden;

        int requiredMissing = required & ~maskToTest;

        return forbiddenFound | requiredMissing;
    }

    public boolean matches(int mask, int requiredMask, int ignoreMask, int forbiddenMask) {
        return filter(mask, requiredMask, ignoreMask, forbiddenMask) == 0;
    }
}
