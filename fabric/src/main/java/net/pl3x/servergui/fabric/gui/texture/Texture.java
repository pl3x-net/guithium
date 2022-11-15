package net.pl3x.servergui.fabric.gui.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.servergui.api.Key;
import net.pl3x.servergui.fabric.ServerGUIFabric;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Texture {
    private final ResourceLocation identifier;
    private final String url;

    private boolean isLoaded;

    public Texture(Key key, String url) {
        this.identifier = new ResourceLocation(key.toString());
        this.url = url;

        load();
    }

    public ResourceLocation getIdentifier() {
        return this.identifier;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    private void load() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::load);
            return;
        }

        try {
            BufferedImage image = ImageIO.read(new URL(getUrl()));

            DynamicTexture texture = new DynamicTexture(image.getWidth(), image.getHeight(), true);
            ServerGUIFabric.client.getTextureManager().register(getIdentifier(), texture);

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getWidth(); y++) {
                    //noinspection ConstantConditions
                    texture.getPixels().setPixelRGBA(x, y, rgb2bgr(image.getRGB(x, y)));
                }
            }

            texture.upload();
            this.isLoaded = true;
        } catch (IOException e) {
            System.out.println(getIdentifier() + " " + getUrl());
            throw new RuntimeException(e);
        }
    }

    public void unload() {
        ServerGUIFabric.client.getTextureManager().release(getIdentifier());
    }

    private static int rgb2bgr(int color) {
        // Minecraft flips red and blue for some reason
        // lets flip them back
        int a = color >> 24 & 0xFF;
        int r = color >> 16 & 0xFF;
        int g = color >> 8 & 0xFF;
        int b = color & 0xFF;
        return (a << 24) | (b << 16) | (g << 8) | r;
    }
}
