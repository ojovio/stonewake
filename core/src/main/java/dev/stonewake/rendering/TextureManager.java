package dev.stonewake.rendering;

import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class TextureManager {
    private Map<String, Texture> textures = new HashMap<>();

    public void loadTextures(String[] textures) {
        for (String texture : textures) {
            this.textures.put(texture, new Texture(texture));
        }
    }

    public Texture getTexture(String textureName) {
        return textures.get(textureName);
    }

    public int decodifyTileIndexX(int index, int tileSize) {
        return index % tileSize;
    }

    public int decodifyTileIndexY(int index, int tileSize) {
        return index / tileSize;
    }
}
