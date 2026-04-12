cmake_minimum_required(VERSION 3.20)
project(stonewake VERSION 0.1.0)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

option(USE_RAYLIB "Use raylib" ON)
option(USE_BOX2D "Use Box2D" ON)
option(USE_FLECS "Use Flecs ECS" ON)
option(USE_IMGUI "Use Dear ImGui" ON)

include(FetchContent)

if(USE_RAYLIB)
    FetchContent_Declare(
            raylib
            GIT_REPOSITORY https://github.com/raysan5/raylib.git
            GIT_TAG master
    )
    FetchContent_MakeAvailable(raylib)
endif()

if(USE_BOX2D)
    FetchContent_Declare(
            box2d
            GIT_REPOSITORY https://github.com/erincatto/box2d.git
            GIT_TAG main
    )
    FetchContent_MakeAvailable(box2d)
endif()

if(USE_FLECS)
    FetchContent_Declare(
            flecs
            GIT_REPOSITORY https://github.com/SanderMertens/flecs.git
            GIT_TAG master
    )
    FetchContent_MakeAvailable(flecs)
endif()

set(IMGUI_DIR ${CMAKE_CURRENT_SOURCE_DIR}/libs/imgui)
set(RLIMGUI_DIR ${CMAKE_CURRENT_SOURCE_DIR}/libs/imgui)

set(IMGUI_SOURCES
        ${IMGUI_DIR}/imgui.cpp
        ${IMGUI_DIR}/imgui_demo.cpp
        ${IMGUI_DIR}/imgui_draw.cpp
        ${IMGUI_DIR}/imgui_tables.cpp
        ${IMGUI_DIR}/imgui_widgets.cpp
)

set(RLIMGUI_SOURCES
        ${RLIMGUI_DIR}/rlImGui.cpp
)

add_executable(stonewake
        src/main.cpp
        src/rendering/chunk_lighting.cpp
        include/rendering/chunk_lighting.h
        include/rendering/render_config.h
        src/utils/splashtext.cpp
        src/tilemap/cell.cpp
        src/tilemap/chunk.cpp
        src/tilemap/chunk_manager.cpp
        src/content/assets/texture_manager.cpp
        src/content/tile/tile.cpp
        src/content/tile/tile_registry.cpp
        src/utils/debug.cpp
        src/game.cpp
        src/screens/screen.cpp
        src/screens/screen_manager.cpp
        src/game/screens/world_screen.cpp
        src/rendering/chunk_renderer.cpp
        src/rendering/camera_manager.cpp
        src/tilemap/bitmask.cpp
        src/world/procgen/world_generator.cpp
        ${IMGUI_SOURCES}
        ${RLIMGUI_SOURCES}
)

target_link_libraries(stonewake
        PRIVATE
        raylib
        box2d
        flecs
)

target_include_directories(stonewake PRIVATE
        ${IMGUI_DIR}
        ${RLIMGUI_DIR}
)

file(MAKE_DIRECTORY ${CMAKE_CURRENT_BINARY_DIR}/assets)
file(COPY ${CMAKE_CURRENT_SOURCE_DIR}/assets DESTINATION ${CMAKE_CURRENT_BINARY_DIR})

if(USE_RAYLIB)
    target_compile_definitions(raylib PRIVATE GRAPHICS_API_OPENGL_33)
endif()