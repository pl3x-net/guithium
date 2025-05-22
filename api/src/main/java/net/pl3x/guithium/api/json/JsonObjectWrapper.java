package net.pl3x.guithium.api.json;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.pl3x.guithium.api.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a wrapped {@link JsonObject} with extra '<code>addProperty</code>' methods,
 * because Google made it a final class for some weird reason.
 * <p>
 * Null values cannot be added to this JsonObject, they will simply be ignored when added.
 */
public class JsonObjectWrapper {
    private final JsonObject wrapped;

    /**
     * Creates an empty JsonObjectWrapper.
     */
    public JsonObjectWrapper() {
        this.wrapped = new JsonObject();
    }

    /**
     * Extends an existing JsonObject.
     *
     * @param jsonElement JsonElement to wrap
     */
    public JsonObjectWrapper(@NotNull JsonElement jsonElement) {
        Preconditions.checkArgument(jsonElement instanceof JsonObject, "Can only extend JsonObject");
        this.wrapped = (JsonObject) jsonElement;
    }

    /**
     * Get the underlying JsonObject.
     *
     * @return JsonObject
     */
    @NotNull
    public JsonObject getJsonObject() {
        return this.wrapped;
    }

    // Let's add some stuff...

    /**
     * Convenience method to add a primitive member. The specified value is serialized to a
     * JsonPrimitive of String.
     *
     * @param property name of the member.
     * @param value    the key value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable Component value) {
        if (value == null) {
            return;
        }
        this.wrapped.addProperty(property, GsonComponentSerializer.gson().serialize(value));
    }

    /**
     * Convenience method to add a primitive member. The specified value is converted to a
     * JsonPrimitive of String.
     *
     * @param property name of the member.
     * @param value    the key value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable Key value) {
        if (value == null) {
            return;
        }
        this.wrapped.addProperty(property, value.toString());
    }

    /**
     * Convenience method to add a primitive member. The specified value is converted to a
     * JsonArray of JsonPrimitive Numbers.
     *
     * @param property name of the member.
     * @param value    the enum value associated with the member.
     */
    public void addProperty(@NotNull String property, int[] value) {
        if (value == null) {
            return;
        }
        JsonArray arr = new JsonArray();
        for (int i : value) {
            arr.add(i);
        }
        this.wrapped.add(property, arr);
    }

    /**
     * Convenience method to add a primitive member. The specified value is converted to a
     * JsonPrimitive of String.
     *
     * @param property name of the member.
     * @param value    the enum value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable Enum<?> value) {
        if (value == null) {
            return;
        }
        this.wrapped.addProperty(property, value.toString());
    }

    /**
     * Convenience method to add a primitive member. The specified value is serialized to a
     * JsonPrimitive of String.
     *
     * @param property name of the member.
     * @param value    the json serializable value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable JsonSerializable value) {
        if (value == null) {
            return;
        }
        this.wrapped.add(property, value.toJson());
    }

    /**
     * Convenience method to add a primitive member. The specified value is converted to a
     * JsonArray of JsonPrimitive Strings.
     *
     * @param property name of the member.
     * @param value    the value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable Collection<? extends JsonSerializable> value) {
        if (value == null) {
            return;
        }
        JsonArray arr = new JsonArray();
        value.forEach(serializable -> arr.add(serializable.toJson()));
        this.wrapped.add(property, arr);
    }

    // Now let's finish with the existing stuff from JsonObject

    /**
     * Convenience method to add a primitive member. The specified value is converted to a
     * JsonPrimitive of String.
     *
     * @param property name of the member.
     * @param value    the string value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable String value) {
        if (value == null) {
            return;
        }
        this.wrapped.addProperty(property, value);
    }

    /**
     * Convenience method to add a primitive member. The specified value is converted to a
     * JsonPrimitive of Number.
     *
     * @param property name of the member.
     * @param value    the number value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable Number value) {
        if (value == null) {
            return;
        }
        this.wrapped.addProperty(property, value);
    }

    /**
     * Convenience method to add a boolean member. The specified value is converted to a
     * JsonPrimitive of Boolean.
     *
     * @param property name of the member.
     * @param value    the boolean value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable Boolean value) {
        if (value == null) {
            return;
        }
        this.wrapped.addProperty(property, value);
    }

    /**
     * Convenience method to add a char member. The specified value is converted to a
     * JsonPrimitive of Character.
     *
     * @param property name of the member.
     * @param value    the number value associated with the member.
     */
    public void addProperty(@NotNull String property, @Nullable Character value) {
        if (value == null) {
            return;
        }
        this.wrapped.addProperty(property, value);
    }

    /**
     * Adds a member, which is a name-value pair, to self. The name must be a String, but the value
     * can be an arbitrary JsonElement, thereby allowing you to build a full tree of JsonElements
     * rooted at this node.
     *
     * @param property name of the member.
     * @param value    the member object.
     */
    public void add(@NotNull String property, @Nullable JsonElement value) {
        if (value == null) {
            return;
        }
        this.wrapped.add(property, value);
    }

    /**
     * Removes the {@code property} from this {@link JsonObject}.
     *
     * @param property name of the member that should be removed.
     * @return the {@link JsonElement} object that is being removed.
     * @since 1.3
     */
    @Nullable
    public JsonElement remove(@Nullable String property) {
        return this.wrapped.remove(property);
    }

    /**
     * Returns a set of members of this object. The set is ordered, and the order is in which the
     * elements were added.
     *
     * @return a set of members of this object.
     */
    @NotNull
    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.wrapped.entrySet();
    }

    /**
     * Returns a set of members key values.
     *
     * @return a set of member keys as Strings
     * @since 2.8.1
     */
    @NotNull
    public Set<String> keySet() {
        return this.wrapped.keySet();
    }

    /**
     * Returns the number of key/value pairs in the object.
     *
     * @return the number of key/value pairs in the object.
     */
    public int size() {
        return this.wrapped.size();
    }

    /**
     * Convenience method to check if a member with the specified name is present in this object.
     *
     * @param memberName name of the member that is being checked for presence.
     * @return true if there is a member with the specified name, false otherwise.
     */
    public boolean has(@Nullable String memberName) {
        return this.wrapped.has(memberName);
    }

    /**
     * Returns the member with the specified name.
     *
     * @param memberName name of the member that is being requested.
     * @return the member matching the name. Null if no such member exists.
     */
    @Nullable
    public JsonElement get(@Nullable String memberName) {
        return this.wrapped.get(memberName);
    }

    /**
     * Convenience method to get the specified member as a JsonPrimitive element.
     *
     * @param memberName name of the member being requested.
     * @return the JsonPrimitive corresponding to the specified member.
     */
    @Nullable
    public JsonPrimitive getAsJsonPrimitive(@Nullable String memberName) {
        return this.wrapped.getAsJsonPrimitive(memberName);
    }

    /**
     * Convenience method to get the specified member as a JsonArray.
     *
     * @param memberName name of the member being requested.
     * @return the JsonArray corresponding to the specified member.
     */
    @Nullable
    public JsonArray getAsJsonArray(@Nullable String memberName) {
        return this.wrapped.getAsJsonArray(memberName);
    }

    /**
     * Convenience method to get the specified member as a JsonObject.
     *
     * @param memberName name of the member being requested.
     * @return the JsonObject corresponding to the specified member.
     */
    @Nullable
    public JsonObject getAsJsonObject(@Nullable String memberName) {
        return this.wrapped.getAsJsonObject(memberName);
    }

    /**
     * Creates a deep copy of this element and all its children
     *
     * @return Deep copy
     * @since 2.8.2
     */
    @NotNull
    public JsonObject deepCopy() {
        return this.wrapped.deepCopy();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        JsonObject other;
        if (this.getClass() == obj.getClass()) {
            other = ((JsonObjectWrapper) obj).wrapped;
        } else if (JsonObject.class == obj.getClass()) {
            other = (JsonObject) obj;
        } else {
            return false;
        }
        return this.wrapped.equals(other);
    }

    @Override
    public int hashCode() {
        return this.wrapped.hashCode();
    }
}
