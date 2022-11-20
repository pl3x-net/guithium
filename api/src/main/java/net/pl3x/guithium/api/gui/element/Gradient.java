package net.pl3x.guithium.api.gui.element;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.pl3x.guithium.api.Guithium;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class Gradient extends Rect {
    public static final Gradient DEFAULT = Gradient.builder(Guithium.MOD_ID + ":gradient")
        .setColorTop(0xC0101010)
        .setColorBottom(0xD0101010)
        .build();

    private final int[] colors = new int[]{0, 0, 0, 0};

    protected Gradient(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable Point size, int colorTopLeft, int colorTopRight, int colorBottomLeft, int colorBottomRight) {
        super(key, Type.GRADIENT, pos, anchor, offset, size);
        setColorTopLeft(colorTopLeft);
        setColorTopRight(colorTopRight);
        setColorBottomLeft(colorBottomLeft);
        setColorBottomRight(colorBottomRight);
    }

    public int[] getColors() {
        return this.colors;
    }

    public int getColorTopLeft() {
        return this.colors[0];
    }

    public void setColorTopLeft(int color) {
        this.colors[0] = color;
    }

    public int getColorTopRight() {
        return this.colors[1];
    }

    public void setColorTopRight(int color) {
        this.colors[1] = color;
    }

    public int getColorBottomLeft() {
        return this.colors[2];
    }

    public void setColorBottomLeft(int color) {
        this.colors[2] = color;
    }

    public int getColorBottomRight() {
        return this.colors[3];
    }

    public void setColorBottomRight(int color) {
        this.colors[3] = color;
    }

    public void setColorTop(int color) {
        setColorTopLeft(color);
        setColorTopRight(color);
    }

    public void setColorLeft(int color) {
        setColorTopLeft(color);
        setColorBottomLeft(color);
    }

    public void setColorRight(int color) {
        setColorTopRight(color);
        setColorBottomRight(color);
    }

    public void setColorBottom(int color) {
        setColorBottomLeft(color);
        setColorBottomRight(color);
    }

    public void setColor(int color) {
        setColorTop(color);
        setColorBottom(color);
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("colors", getColors());
        return json.getJsonObject();
    }

    @NotNull
    public static Gradient fromJson(JsonObject json) {
        if (!json.has("colors")) {
            throw new JsonParseException("Missing colors array");
        }
        JsonArray arr = json.get("colors").getAsJsonArray();
        if (arr.size() != 4) {
            throw new JsonParseException("Malformed colors array (size!=4)");
        }
        int[] colors = new int[4];
        for (int i = 0; i < 4; i++) {
            colors[i] = arr.get(i).getAsInt();
        }
        return new Gradient(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("size") ? null : Point.fromJson(json.get("size").getAsJsonObject()),
            colors[0], colors[1], colors[2], colors[3]
        );
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (this.getClass() != o.getClass()) {
            return false;
        }
        Gradient other = (Gradient) o;
        return getColorTopLeft() == other.getColorTopLeft()
            && getColorTopRight() == other.getColorTopRight()
            && getColorBottomLeft() == other.getColorBottomLeft()
            && getColorBottomRight() == other.getColorBottomRight()
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getColorTopLeft(), getColorTopRight(), getColorBottomLeft(), getColorBottomRight(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Gradient{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",colors=" + Arrays.toString(getColors());
    }

    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    public static class Builder extends Rect.Builder<Builder> {
        private final int[] colors = new int[]{0, 0, 0, 0};

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        public int[] getColors() {
            return this.colors;
        }

        public int getColorTopLeft() {
            return this.colors[0];
        }

        @NotNull
        public Builder setColorTopLeft(int color) {
            this.colors[0] = color;
            return this;
        }

        public int getColorTopRight() {
            return this.colors[1];
        }

        @NotNull
        public Builder setColorTopRight(int color) {
            this.colors[1] = color;
            return this;
        }

        public int getColorBottomLeft() {
            return this.colors[2];
        }

        @NotNull
        public Builder setColorBottomLeft(int color) {
            this.colors[2] = color;
            return this;
        }

        public int getColorBottomRight() {
            return this.colors[3];
        }

        @NotNull
        public Builder setColorBottomRight(int color) {
            this.colors[3] = color;
            return this;
        }

        @NotNull
        public Builder setColorTop(int color) {
            setColorTopLeft(color);
            setColorTopRight(color);
            return this;
        }

        @NotNull
        public Builder setColorLeft(int color) {
            setColorTopLeft(color);
            setColorBottomLeft(color);
            return this;
        }

        @NotNull
        public Builder setColorRight(int color) {
            setColorTopRight(color);
            setColorBottomRight(color);
            return this;
        }

        @NotNull
        public Builder setColorBottom(int color) {
            setColorBottomLeft(color);
            setColorBottomRight(color);
            return this;
        }

        @NotNull
        public Builder setColor(int color) {
            setColorTop(color);
            setColorBottom(color);
            return this;
        }

        @Override
        @NotNull
        public Gradient build() {
            return new Gradient(getKey(), getPos(), getAnchor(), getOffset(), getSize(), getColorTopLeft(), getColorTopRight(), getColorBottomLeft(), getColorBottomRight());
        }
    }
}
