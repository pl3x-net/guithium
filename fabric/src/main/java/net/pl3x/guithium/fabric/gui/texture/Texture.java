package net.pl3x.guithium.fabric.gui.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.Key;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class Texture {
    private final ResourceLocation identifier;
    private final String url;

    private boolean isLoaded;

    public Texture(@NotNull Key key, @NotNull String url) {
        this.url = url;
        if (url.startsWith("http")) {
            // custom texture
            this.identifier = new ResourceLocation(key.toString());
            loadFromInternet();
        } else {
            // vanilla texture
            this.identifier = new ResourceLocation(url);
            this.isLoaded = true;
        }
    }

    @NotNull
    public ResourceLocation getIdentifier() {
        return this.identifier;
    }

    public boolean isLoaded() {
        return this.isLoaded;
    }

    private void loadFromInternet() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::loadFromInternet);
            return;
        }
        try {
            BufferedImage image = ImageIO.read(new URL(this.url));
            DynamicTexture texture = new DynamicTexture(image.getWidth(), image.getHeight(), true);
            NativeImage nativeImage = texture.getPixels();
            if (nativeImage == null) {
                return;
            }
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getWidth(); y++) {
                    nativeImage.setPixelRGBA(x, y, rgb2bgr(image.getRGB(x, y)));
                }
            }
            texture.upload();
            Minecraft.getInstance().getTextureManager().register(getIdentifier(), texture);
            this.isLoaded = true;
        } catch (IOException e) {
            System.out.println(getIdentifier() + " " + this.url);
            throw new RuntimeException(e);
        }
    }

    public void unload() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(this::unload);
            return;
        }
        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(getIdentifier());
        if (texture == null) {
            return;
        }

        try {
            texture.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
        texture.releaseId();
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
