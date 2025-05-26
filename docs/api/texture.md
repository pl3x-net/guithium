---
title: Texture
parent: API
nav_order: 3
has_toc: false
---

## Texture

*This needs updating. Was pulled from the old wiki. Information is likely outdated*

Texture is an image that can be drawn on the screen.

You can use textures that are already loaded in the game (from vanilla, or other mods) or load new ones in directly from the internet.

Textures require 2 parameters. First one is a unique Key that we use to identify it. The second is where the image file is located.

### Vanilla Textures

For example, to use a vanilla texture:

```java
// without namespace
Texture texture = new Texture(Key.of("some:unique_key"), "textures/gui/options_background.png");
// or with the namespace
Texture texture = new Texture(Key.of("some:unique_key"), "minecraft:textures/gui/options_background.png");
```

### Modded Textures

To use a loaded image from another mod just use the mod_id for the namespace:

```java
Texture texture = new Texture(Key.of("some:unique_key"), "mod_id:textures/path/image.png");
```

### External Textures

To use an external image from the internet, just use the url (beginning with `http://` or `https://`)

```java
Texture texture = new Texture(Key.of("some:unique_key"), "https://avatars.githubusercontent.com/u/332527?v=4");
```

# Preloading Textures

If you are using textures from the internet, they will have to be loaded into memory during runtime. Normally textures are loaded when the game does that huge loading bar overlay (starting the game and loading resource packs), and takes quite some time to do.

We offer you a `TextureManager` where you can preload your textures into. This makes using them in your GUIs just as fast and efficient as using native vanilla textures.

```java
public void onEnable() {
    Texture texture = new Texture("my_plugin:hayley", "https://pl3x.net/hayley.png");
    Guithium.api().getTextureManager().add(texture);
}
```
