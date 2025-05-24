package net.pl3x.guithium.fabric.gui.texture;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import net.pl3x.guithium.api.Unsafe;
import net.pl3x.guithium.api.gui.element.Image;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.gui.texture.TextureManager;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FabricTextureManager extends TextureManager {
    protected final Map<Key, FabricTexture> textures = new HashMap<>();

    @NotNull
    public FabricTexture getOrCreate(@NotNull Image image) {
        Texture texture = image.getTexture();
        FabricTexture fabricTexture = get(texture.getKey());
        if (fabricTexture == null) {
            return new FabricTexture(texture.getKey(), texture.getUrl());
        }
        return fabricTexture;
    }

    @Override
    public void add(@NotNull Collection<Texture> textures) {
        textures.forEach(this::add);
    }

    @Override
    public void add(@NotNull Texture texture) {
        add(new FabricTexture(texture));
    }

    public void add(@NotNull FabricTexture texture) {
        this.textures.put(texture.getKey(), texture);
    }

    @Override
    @Nullable
    public FabricTexture get(@NotNull String key) {
        return get(Key.of(key));
    }

    @Override
    @Nullable
    public FabricTexture get(@NotNull Key key) {
        return this.textures.get(key);
    }

    @Override
    @Nullable
    public FabricTexture remove(@NotNull String key) {
        return remove(Key.of(key));
    }

    @Override
    @Nullable
    public FabricTexture remove(@NotNull Key key) {
        FabricTexture texture = Unsafe.cast(super.remove(key));
        if (texture != null) {
            texture.unload();
        }
        return texture;
    }

    @Override
    @NotNull
    public Collection<Texture> getTextures() {
        return Collections.unmodifiableCollection(this.textures.values());
    }

    public void clear() {
        new HashSet<>(this.textures.keySet()).forEach(super::remove);
    }

    /*
    @Override
    public void add(@NotNull FabricTexture texture) {
        FabricTexture fabricTexture = new FabricTexture(texture.getKey(), texture.getUrl());
        super.add(Unsafe.<T>cast(fabricTexture));
    }

    @Override
    @Nullable
    public T remove(@NotNull Key key) {
        T texture = super.remove(key);
        if (texture != null) {
            texture.unload();
        }
        return texture;
    }

    public void clear() {
        new HashSet<>(this.textures.keySet()).forEach(this::remove);
    }*/
}
