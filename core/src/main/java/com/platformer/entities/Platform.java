package com.platformer.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * A Platform is a static rectangular obstacle.
 * It is defined by its bottom-left corner (x, y) and its size (width, height).
 * LibGDX uses world coordinates where (0,0) is the bottom-left of the world.
 */
public class Platform {

    public float x, y, width, height;
    public Color color;

    public Platform(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = Color.GREEN;
    }

    public Platform(float x, float y, float width, float height, Color color) {
        this(x, y, width, height);
        this.color = color;
    }

    /**
     * Draws this platform as a filled rectangle.
     * The ShapeRenderer must already be in FILLED mode when this is called.
     */
    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(color);
        shapeRenderer.rect(x, y, width, height);
    }
}
