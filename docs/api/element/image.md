---
title: Image
parent: Elements
nav_order: 5
has_toc: false
---

## Image

Image is an element to display a Texture on a Screen.

### Position
This is the position on the screen in scaled pixels where 0,0 is the top left of the screen.

Omitting the position will default to `0.0, 0.0`.

### Anchor
This is the anchor point where the Position starts from. This is a percentage (0.0-1.0) of the Screen's size.

Omitting the anchor will default to `0.0, 0.0`.

Examples:<br>
`0.0, 0.0` will anchor the Position to the top left corner of the Screen.<br>
`0.5, 0.5` will anchor the Position to the center of the Screen.<br>
`1.0, 1.0` will anchor the Position to the bottom right of the Screen.

### Offset
This is the offset point where the Position aligns on the Image. This is a percentage (0.0-1.0) of the Image's size.

Omitting the offset will default to `0.0, 0.0`.

Examples:<br>
`0.0, 0.0` will offset the Image so the top left corner of the Image is at the anchored position.<br>
`0.5, 0.5` will offset the Image so the center of the Image is at the anchored position.<br>
`1.0, 1.0` will offset the Image so the bottom right corner of the Image is at the anchored position.

### Size
This is the size of the Image in scaled pixels.

Omitting the size will default to the Screen's full width and height.

### Texture
This is the Texture to draw.

### Vertex Color
This controls how much of a texture's colors show through on the screen. See Mojang's [position texture color fragment shader](https://mcasset.cloud/1.19.2/assets/minecraft/shaders/core/position_tex_color.fsh) for details.

Eg, a modifier of `0xFF00FFFF` will remove all red from the texture.

Omitting the vertex color will default to `0xFFFFFFFF` (opaque white).

### TileModifier
This is used to divide the UV into tiled segments on each axis (repeat).

Eg, a value of 32 is used on vanilla option screens with the dirt texture in order to get the tiled effect. This does <em>not</em> mean it is tiled 32 times.

Omitting the tile modifier will default to `1.0` (no tile/repeat).

Examples:<br>
`1.0` makes the texture fit `1` times inside the allotted size, showing only the single image.<br>
`2.0` makes the texture fit `2` times inside the allotted size, showing 4 images tiled in a 2x2.<br>
`0.5` makes the texture fit `0.5` times inside the allotted size, showing the top left 25% of the image.

# Examples

### Basic Image
An example of a basic image
```java
Image image = Image.builder("my_plugin:github")
    .setPos(0, 20)
    .setSize(50, 50)
    .setTexture(new Texture("my_plugin:github_logo", "https://cdn-icons-png.flaticon.com/512/25/25231.png"))
    .build();
```

### Tiled Image
An example of a tiled image.

**Note**: *This Image is provided to you at `Image.TILED_DIRT` since it is a common Image (used in options screens).*
```java
public static final Image TILED_DIRT = Image.builder("guithium:tiled_dirt")
        .setTexture(new Texture("minecraft:dirt", "textures/gui/options_background.png"))
        .setVertexColor(0xFF404040)
        .setTileModifier(32.0F)
        .build();
```
