package net.pl3x.guithium.api.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Simple interface for tagging all action listeners.
 */
public interface ActionListener {
    /**
     * An annotation to mark methods as being action handler methods.
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface ActionHandler {
    }
}
