//
// Created by j-otavio on 05/04/2026.
//

#include "../../include/utils/splashtext.h"

#include <random>
#include <vector>

std::vector<std::string> splashTexts = {
    "So did the stone sleep?",
    "Far beyond nowhere",
    "Rewriting for the 6969th time"
};

std::string getRandomSplashText() {
    static std::mt19937 rng(std::random_device{}());
    static std::uniform_int_distribution<size_t> dist(0, splashTexts.size() - 1);

    return splashTexts[dist(rng)];
}
