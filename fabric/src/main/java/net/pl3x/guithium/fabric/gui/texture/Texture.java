package net.pl3x.guithium.fabric.gui.texture;

import at.dhyan.open_imaging.GifDecoder;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import net.pl3x.guithium.api.Key;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

public class Texture {
    private final ResourceLocation identifier;
    private final String url;

    private float time;
    private int frame;
    private float[] frames = new float[]{0F};
    private boolean isLoaded;

    public Texture(@NotNull Key key, @NotNull String url) {
        this.url = url;
        if (url.startsWith("http")) {
            // custom texture
            this.identifier = new ResourceLocation(key.toString());
            if (url.toLowerCase(Locale.ROOT).endsWith(".gif")) {
                loadGifFromInternet();
            } else {
                loadFromInternet();
            }
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

    private void loadGifFromInternet() {
        try {
            byte[] bytes = IOUtils.toByteArray(new URL(this.url));
            GifDecoder.GifImage gif = GifDecoder.read(bytes);
            int frameCount = gif.getFrameCount();
            BufferedImage image = new BufferedImage(gif.getWidth(), gif.getHeight() * frameCount, BufferedImage.TYPE_INT_ARGB);
            this.frames = new float[frameCount];
            for (int i = 0; i < frameCount; i++) {
                BufferedImage frame = gif.getFrame(i);
                // delay is number of hundredths (1/100) of a second
                // divide by 5 to align with minecraft ticks (1/20)
                // 10/100 = (10/5)/20
                this.frames[i] = gif.getDelay(i) / 5F;
                for (int x = 0; x < gif.getWidth(); x++) {
                    for (int y = 0; y < gif.getHeight(); y++) {
                        image.setRGB(x, gif.getHeight() * i + y, frame.getRGB(x, y));
                    }
                }
            }
            loadImage(image);
        } catch (IOException e) {
            System.out.println(getIdentifier() + " " + this.url);
            throw new RuntimeException(e);
        }
    }

    private void loadFromInternet() {
        try {
            loadImage(ImageIO.read(new URL(this.url)));
        } catch (IOException e) {
            System.out.println(getIdentifier() + " " + this.url);
            throw new RuntimeException(e);
        }
    }

    private void loadImage(BufferedImage image) {
        if (!RenderSystem.isOnRenderThread()) {
            RenderSystem.recordRenderCall(() -> loadImage(image));
            return;
        }
        DynamicTexture texture = new DynamicTexture(image.getWidth(), image.getHeight(), true);
        NativeImage nativeImage = texture.getPixels();
        if (nativeImage == null) {
            return;
        }
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                nativeImage.setPixelRGBA(x, y, rgb2bgr(image.getRGB(x, y)));
            }
        }
        texture.upload();
        Minecraft.getInstance().getTextureManager().register(getIdentifier(), texture);
        this.isLoaded = true;
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

    public void render(PoseStack poseStack, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.setShaderTexture(0, getIdentifier());
        RenderSystem.setShaderColor(1, 1, 1, 1);

        if (this.frames.length > 0) {
            this.time += Minecraft.getInstance().getDeltaFrameTime();
            float delay = this.frames[this.frame];
            if (this.time >= delay) {
                this.time -= delay;
                if (++this.frame >= this.frames.length) {
                    this.frame = 0;
                }
            }
            float h = (v1 - v0) / this.frames.length;
            v0 = h * this.frame;
            v1 = v0 + h;
        }

        Matrix4f model = poseStack.last().pose();
        BufferBuilder buf = Tesselator.getInstance().getBuilder();
        buf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
        buf.vertex(model, x1, y0, 0).uv(u1, v0).color(color).endVertex();
        buf.vertex(model, x0, y0, 0).uv(u0, v0).color(color).endVertex();
        buf.vertex(model, x0, y1, 0).uv(u0, v1).color(color).endVertex();
        buf.vertex(model, x1, y1, 0).uv(u1, v1).color(color).endVertex();
        BufferUploader.drawWithShader(buf.end());
    }
}
