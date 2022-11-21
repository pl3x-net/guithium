package net.pl3x.guithium.api.gui.element;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.Key;
import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.json.JsonObjectWrapper;
import net.pl3x.guithium.api.player.WrappedPlayer;
import net.pl3x.guithium.api.util.QuadConsumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Checkbox extends AbstractElement {
    private String text;
    private Point size;
    private Component tooltip;
    private Boolean defaultSelected;
    private Boolean showLabel;
    private OnClick onClick = (screen, button, player, checked) -> {
    };

    protected Checkbox(@NotNull Key key, @Nullable Point pos, @Nullable Point anchor, @Nullable Point offset, @Nullable String text, @Nullable Point size, @Nullable Component tooltip, @Nullable Boolean defaultSelected, @Nullable Boolean showLabel) {
        super(key, Type.CHECKBOX, pos, anchor, offset);
        setText(text);
        setSize(size);
        setTooltip(tooltip);
        setDefaultSelected(defaultSelected);
        setShowLabel(showLabel);
    }

    @Nullable
    public String getText() {
        return this.text;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    @Nullable
    public Point getSize() {
        return this.size;
    }

    public void setSize(float x, float y) {
        setSize(Point.of(x, y));
    }

    public void setSize(@Nullable Point size) {
        this.size = size;
    }

    @Nullable
    public Component getTooltip() {
        return this.tooltip;
    }

    public void setTooltip(@Nullable Component tooltip) {
        this.tooltip = tooltip;
    }

    @Nullable
    public Boolean isDefaultSelected() {
        return this.defaultSelected;
    }

    public void setDefaultSelected(@Nullable Boolean defaultSelected) {
        this.defaultSelected = defaultSelected;
    }

    @Nullable
    public Boolean isShowLabel() {
        return this.showLabel;
    }

    public void setShowLabel(@Nullable Boolean showLabel) {
        this.showLabel = showLabel;
    }

    @Nullable
    public OnClick onClick() {
        return this.onClick;
    }

    public void onClick(@Nullable OnClick onClick) {
        this.onClick = onClick;
    }

    @Override
    @NotNull
    public JsonElement toJson() {
        JsonObjectWrapper json = new JsonObjectWrapper(super.toJson());
        json.addProperty("text", getText());
        json.addProperty("size", getSize());
        json.addProperty("tooltip", getTooltip());
        json.addProperty("default_selected", isDefaultSelected());
        json.addProperty("show_label", isShowLabel());
        return json.getJsonObject();
    }

    @NotNull
    public static Checkbox fromJson(@NotNull JsonObject json) {
        Preconditions.checkArgument(json.has("key"), "Key cannot be null");
        return new Checkbox(
            Key.of(json.get("key").getAsString()),
            !json.has("pos") ? null : Point.fromJson(json.get("pos").getAsJsonObject()),
            !json.has("anchor") ? null : Point.fromJson(json.get("anchor").getAsJsonObject()),
            !json.has("offset") ? null : Point.fromJson(json.get("offset").getAsJsonObject()),
            !json.has("text") ? null : json.get("text").getAsString(),
            !json.has("size") ? null : Point.fromJson(json.get("size").getAsJsonObject()),
            !json.has("tooltip") ? null : GsonComponentSerializer.gson().deserialize(json.get("tooltip").getAsString()),
            !json.has("default_selected") ? null : json.get("default_selected").getAsBoolean(),
            !json.has("show_label") ? null : json.get("show_label").getAsBoolean()
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
        Checkbox other = (Checkbox) o;
        return Objects.equals(getText(), other.getText())
            && Objects.equals(getSize(), other.getSize())
            && Objects.equals(getTooltip(), other.getTooltip())
            && Objects.equals(isDefaultSelected(), other.isDefaultSelected())
            && Objects.equals(isShowLabel(), other.isShowLabel())
            && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getSize(), getTooltip(), isDefaultSelected(), isShowLabel(), super.hashCode());
    }

    @Override
    @NotNull
    public String toString() {
        return String.format("Button{%s}", getPropertiesAsString());
    }

    @Override
    @NotNull
    protected String getPropertiesAsString() {
        return super.getPropertiesAsString()
            + ",text=" + getText()
            + ",size=" + getSize()
            + ",tooltip=" + getTooltip()
            + ",defaultSelected=" + isDefaultSelected()
            + ",showLabel=" + isShowLabel();
    }

    @NotNull
    public static Builder builder(@NotNull String key) {
        return new Builder(key);
    }

    @NotNull
    public static Builder builder(@NotNull Key key) {
        return new Builder(key);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        private String text;
        private Point size;
        private Component tooltip;
        private Boolean defaultSelected;
        private Boolean showLabel;
        private OnClick onClick = (screen, button, player, checked) -> {
        };

        public Builder(@NotNull String key) {
            this(Key.of(key));
        }

        public Builder(@NotNull Key key) {
            super(key);
        }

        @Nullable
        public String getText() {
            return text;
        }

        @NotNull
        public Builder setText(@Nullable String text) {
            this.text = text;
            return this;
        }

        @Nullable
        public Point getSize() {
            return size;
        }

        @NotNull
        public Builder setSize(float x, float y) {
            return setSize(Point.of(x, y));
        }

        @NotNull
        public Builder setSize(@Nullable Point size) {
            this.size = size;
            return this;
        }

        @Nullable
        public Component getTooltip() {
            return tooltip;
        }

        @NotNull
        public Builder setTooltip(@Nullable Component tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        @Nullable
        public Boolean isDefaultSelected() {
            return this.defaultSelected;
        }

        @NotNull
        public Builder setDefaultSelected(@Nullable Boolean defaultSelected) {
            this.defaultSelected = defaultSelected;
            return this;
        }

        @Nullable
        public Boolean isShowLabel() {
            return this.showLabel;
        }

        @NotNull
        public Builder setShowLabel(@Nullable Boolean showLabel) {
            this.showLabel = showLabel;
            return this;
        }

        @NotNull
        public Builder onClick(@Nullable OnClick onClick) {
            this.onClick = onClick;
            return this;
        }

        @Override
        @NotNull
        public Checkbox build() {
            Checkbox checkbox = new Checkbox(getKey(), getPos(), getAnchor(), getOffset(), getText(), getSize(), getTooltip(), isDefaultSelected(), isShowLabel());
            checkbox.onClick(this.onClick);
            return checkbox;
        }
    }

    @FunctionalInterface
    public interface OnClick extends QuadConsumer<Screen, Checkbox, WrappedPlayer, Boolean> {
    }
}
