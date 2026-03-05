package dev.stonewake.assets;

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

    public void dispose() {
        textures.forEach((name, texture) -> {
            texture.dispose();
        });
    }
}
