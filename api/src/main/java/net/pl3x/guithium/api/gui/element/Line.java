package net.pl3x.guithium.api.gui.element;

import java.util.Objects;
import net.pl3x.guithium.api.gui.Vec2;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a line element.
 */
public class Line extends AbstractElement<Line> {
    private Vec2 endPos;
    private Vec2 endAnchor;
    private Vec2 endOffset;
    private Float width;
    private int startColor;
    private int endColor;

    /**
     * Create a new line element.
     *
     * @param key Unique identifier
     */
    public Line(@NotNull String key) {
        this(Key.of(key));
    }

    /**
     * Create a new line element.
     *
     * @param key Unique identifier
     */
    public Line(@NotNull Key key) {
        super(key, Type.LINE);
    }

    /**
     * Create a new line element.
     *
     * @param key Unique identifier
     * @return New line element
     */
    @NotNull
    public static Line of(@NotNull String key) {
        return of(Key.of(key));
    }

    /**
     * Create a new line element.
     *
     * @param key Unique identifier
     * @return New line element
     */
    @NotNull
    public static Line of(@NotNull Key key) {
        return new Line(key);
    }

    /**
     * Get this line's end position from the end anchor position.
     * <p>
     * If null, default end position <code>0,0</code> will be used.
     *
     * @return End position from end anchor
     */
    @Nullable
    public Vec2 getEndPos() {
        return this.endPos;
    }

    /**
     * Set this line's end position from the end anchor position.
     * <p>
     * If null, default end position <code>0,0</code> will be used.
     *
     * @param x X (horizontal) end position
     * @param y Y (vertical) end position
     * @return This line
     */
    @NotNull
    public Line setEndPos(float x, float y) {
        setEndPos(Vec2.of(x, y));
        return this;
    }

    /**
     * Set this line's end position from the end anchor position.
     * <p>
     * If null, default end position <code>0,0</code> will be used.
     *
     * @param pos End position
     * @return This line
     */
    @NotNull
    public Line setEndPos(@Nullable Vec2 pos) {
        this.endPos = pos;
        return this;
    }

    /**
     * Get this line's end anchor position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end anchor <code>0,0</code> will be used.
     *
     * @return End anchor position
     */
    @Nullable
    public Vec2 getEndAnchor() {
        return this.endAnchor;
    }

    /**
     * Set this line's end anchor position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end anchor <code>0,0</code> will be used.
     *
     * @param x X (horizontal) end anchor
     * @param y Y (vertical) end anchor
     * @return This line
     */
    @NotNull
    public Line setEndAnchor(float x, float y) {
        setEndAnchor(Vec2.of(x, y));
        return this;
    }

    /**
     * Set this line's end anchor position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end anchor <code>0,0</code> will be used.
     *
     * @param anchor End anchor position
     * @return This line
     */
    @NotNull
    public Line setEndAnchor(@Nullable Vec2 anchor) {
        this.endAnchor = anchor;
        return this;
    }

    /**
     * Get this line's end offset position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end offset <code>0,0</code> will be used.
     *
     * @return End offset position
     */
    @Nullable
    public Vec2 getEndOffset() {
        return this.endOffset;
    }

    /**
     * Set this line's end offset position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end offset <code>0,0</code> will be used.
     *
     * @param x X (horizontal) end offset
     * @param y Y (vertical) end offset
     * @return This line
     */
    @NotNull
    public Line setEndOffset(float x, float y) {
        setEndOffset(Vec2.of(x, y));
        return this;
    }

    /**
     * Set this line's end offset position on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     * <p>
     * If null, default end offset <code>0,0</code> will be used.
     *
     * @param offset End offset position
     * @return This line
     */
    @NotNull
    public Line setEndOffset(@Nullable Vec2 offset) {
        this.endOffset = offset;
        return this;
    }

    /**
     * Get this line's width in scaled pixels.
     * <p>
     * If null, default width <code>1.0</code> will be used.
     *
     * @return Line width
     */
    @Nullable
    public Float getWidth() {
        return this.width;
    }

    /**
     * Set this line's width in scaled pixels.
     * <p>
     * If null, default width <code>1.0</code> will be used.
     *
     * @param width Line width
     * @return This line
     */
    @NotNull
    public Line setWidth(@Nullable Float width) {
        this.width = width;
        return this;
    }

    /**
     * Get the starting color for this line.
     *
     * @return Starting color
     */
    public int getStartColor() {
        return this.startColor;
    }

    /**
     * Set the starting color for this line.
     *
     * @param color Starting color
     * @return This line
     */
    @NotNull
    public Line setStartColor(int color) {
        this.startColor = color;
        return this;
    }

    /**
     * Get the ending color for this line.
     *
     * @return Ending color
     */
    public int getEndColor() {
        return this.endColor;
    }

    /**
     * Set the ending color for this line.
     *
     * @param color Ending color
     * @return This line
     */
    @NotNull
    public Line setEndColor(int color) {
        this.endColor = color;
        return this;
    }

    /**
     * Set the color for this line.
     * <p>
     * Both the start and end colors will be set to the specified color.
     *
     * @param color Line color
     * @return This line
     */
    @NotNull
    public Line setColor(int color) {
        setStartColor(color);
        setEndColor(color);
        return this;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Line other = (Line) obj;
        return Objects.equals(getEndPos(), other.getEndPos())
                && Objects.equals(getEndAnchor(), other.getEndAnchor())
                && Objects.equals(getEndOffset(), other.getEndOffset())
                && Objects.equals(getWidth(), other.getWidth())
                && Objects.equals(getStartColor(), other.getStartColor())
                && Objects.equals(getEndColor(), other.getEndColor());
    }
}
