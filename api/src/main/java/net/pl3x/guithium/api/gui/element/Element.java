package net.pl3x.guithium.api.gui.element;

import net.pl3x.guithium.api.gui.Point;
import net.pl3x.guithium.api.gui.Screen;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an element to be displayed on a {@link Screen}.
 */
public interface Element {
    /**
     * Get identifying key.
     *
     * @return Identifying key
     */
    @NotNull
    Key getKey();

    /**
     * Get this element's position from the anchor point.
     *
     * @return Position from anchor
     */
    @Nullable
    Point getPos();

    /**
     * Set this element's position from the anchor point.
     *
     * @param x X (horizontal) position
     * @param y Y (vertical) position
     * @return This element
     */
    @NotNull
    Element setPos(float x, float y);

    /**
     * Set this element's position from the anchor point.
     *
     * @param pos Position
     * @return This element
     */
    @NotNull
    Element setPos(@Nullable Point pos);

    /**
     * Get this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @return Anchor point
     */
    @Nullable
    Point getAnchor();

    /**
     * Set this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param x X (horizontal) anchor
     * @param y Y (vertical) anchor
     * @return This element
     */
    @NotNull
    Element setAnchor(float x, float y);

    /**
     * Set this element's anchor point on the screen.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the parent's size.
     *
     * @param anchor Anchor point
     * @return This element
     */
    @NotNull
    Element setAnchor(@Nullable Point anchor);

    /**
     * Get this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @return Position offset
     */
    @Nullable
    Point getOffset();

    /**
     * Set this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param x X (horizontal) position offset
     * @param y Y (vertical) position offset
     * @return This element
     */
    @NotNull
    Element setOffset(float x, float y);

    /**
     * Set this element's position offset.
     * <p>
     * This is represented as a percentage (0.0-1.0) of the element's size.
     *
     * @param offset Position offset
     * @return This element
     */
    @NotNull
    Element setOffset(@Nullable Point offset);
}
