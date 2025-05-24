package net.pl3x.guithium.fabric.gui.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.gui.texture.Texture;
import net.pl3x.guithium.api.key.Key;
import net.pl3x.guithium.fabric.util.GifDecoder;
import net.pl3x.guithium.fabric.util.RenderQueue;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class FabricTexture extends Texture {
    private final ResourceLocation identifier;

    private float time;
    private int frame;
    private float[] frames = new float[]{0F};
    private boolean isLoaded;

    public FabricTexture(@NotNull Texture texture) {
        this(texture.getKey(), texture.getUrl());
    }

    public FabricTexture(@NotNull Key key, @NotNull String url) {
        super(key, url);
        if (url.startsWith("http")) {
            // custom texture from internet
            this.identifier = ResourceLocation.parse(key.toString());
            if (url.toLowerCase(Locale.ROOT).endsWith(".gif")) {
                loadGifFromInternet();
            } else {
                loadFromInternet();
            }
        } else {
            // vanilla (or other mod) texture
            this.identifier = ResourceLocation.parse(url);
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

    private void loadGifFromInternet() {
        try {
            byte[] bytes = IOUtils.toByteArray(URI.create(getUrl()));
            GifDecoder.GifImage gif = GifDecoder.read(bytes);
            int frameCount = gif.getFrameCount();
            BufferedImage image = new BufferedImage(
                    gif.getWidth(),
                    gif.getHeight() * frameCount,
                    BufferedImage.TYPE_INT_ARGB
            );
            this.frames = new float[frameCount];
            for (int i = 0; i < frameCount; i++) {
                BufferedImage frame = gif.getFrame(i);
                // delay is number of hundredths (1/100) of a second
                // divide by 5 to align with minecraft ticks (1/20)
                // e.g., 10 delay == 10/100 s == (10/5)/20 s
                this.frames[i] = gif.getDelay(i) / 5F;
                for (int x = 0; x < gif.getWidth(); x++) {
                    for (int y = 0; y < gif.getHeight(); y++) {
                        image.setRGB(x, gif.getHeight() * i + y, frame.getRGB(x, y));
                    }
                }
            }
            loadImage(image);
        } catch (IOException e) {
            System.out.println(getIdentifier() + " " + getUrl());
            throw new RuntimeException(e);
        }
    }

    private void loadFromInternet() {
        try {
            loadImage(ImageIO.read(URI.create(getUrl()).toURL()));
        } catch (IOException e) {
            System.out.println(getIdentifier() + " " + getUrl());
            throw new RuntimeException(e);
        }
    }

    private void loadImage(BufferedImage image) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderQueue.recordRenderCall(() -> loadImage(image));
            return;
        }
        DynamicTexture texture = new DynamicTexture(getKey().toString(), image.getWidth(), image.getHeight(), true);
        NativeImage nativeImage = texture.getPixels();
        if (nativeImage == null) {
            return;
        }
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                nativeImage.setPixelABGR(x, y, rgb2bgr(image.getRGB(x, y)));
            }
        }
        texture.upload();
        Minecraft.getInstance().getTextureManager().register(getIdentifier(), texture);
        this.isLoaded = true;
    }

    public void unload() {
        if (!RenderSystem.isOnRenderThread()) {
            RenderQueue.recordRenderCall(this::unload);
            return;
        }
        try {
            Minecraft.getInstance().getTextureManager().getTexture(getIdentifier()).close();
        } catch (Throwable t) {
            Guithium.logger.error("Could not close texture {}", getIdentifier(), t);
        }
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

    public void render(@NotNull GuiGraphics gfx, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int color) {
        if (this.frames.length > 0) {
            this.time += Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaTicks();
            float delay = this.frames[this.frame];
            if (this.time >= delay) {
                this.time -= delay;
                if (++this.frame >= this.frames.length) {
                    this.frame = 0;
                }
            }
            float frameHeight = (v1 - v0) / this.frames.length;
            v0 = frameHeight * this.frame;
            v1 = v0 + frameHeight;
        }

        Matrix4f model = gfx.pose().last().pose();
        VertexConsumer buf = gfx.bufferSource.getBuffer(RenderType.guiTextured(this.identifier));
        buf.addVertex(model, x0, y0, 0).setUv(u0, v0).setColor(color);
        buf.addVertex(model, x0, y1, 0).setUv(u0, v1).setColor(color);
        buf.addVertex(model, x1, y1, 0).setUv(u1, v1).setColor(color);
        buf.addVertex(model, x1, y0, 0).setUv(u1, v0).setColor(color);
    }
}
