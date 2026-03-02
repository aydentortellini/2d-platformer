package com.platformer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The Player class manages:
 *   1. State: position (x, y), velocity, whether on the ground
 *   2. Input: reading keyboard state every frame
 *   3. Physics: applying gravity, integrating velocity into position
 *
 * Collision detection/resolution lives in GameScreen + AABB,
 * because it needs to know about all platforms (which Player doesn't own).
 */
public class Player {

    // Position (bottom-left corner of the player rectangle)
    public float x, y;

    // Size - static so AABB can reference them without a Player instance
    public static final float WIDTH  = 32f;
    public static final float HEIGHT = 48f;

    // Velocity (world units per second)
    public float velocityX = 0f;
    public float velocityY = 0f;

    // Set to true by AABB.resolve() when standing on a platform
    public boolean isOnGround = false;

    // Physics constants - tweak these to change game feel
    private static final float MOVE_SPEED    = 250f;  // horizontal speed
    private static final float JUMP_VELOCITY = 500f;  // upward burst on jump
    private static final float GRAVITY       = -900f; // downward pull (negative = down in LibGDX)

    private Texture texture;

    public Player(float startX, float startY) {
        this.x = startX;
        this.y = startY;
        texture = new Texture(Gdx.files.internal("assets/player.png"));
    }

    /**
     * Called once per frame. Order matters:
     *   1. Read input
     *   2. Apply gravity
     *   3. Move (integrate velocity into position)
     *   4. Reset isOnGround -- AABB in GameScreen will set it back to true if still grounded
     */
    public void update(float deltaTime) {
        handleInput();
        applyGravity(deltaTime);
        integratePosition(deltaTime);
        isOnGround = false; // reset before collision detection runs
    }

    private void handleInput() {
        // Horizontal: set velocity directly for crisp, responsive movement
        if (Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.A)) {
            velocityX = -MOVE_SPEED;
        } else if (Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.D)) {
            velocityX = MOVE_SPEED;
        } else {
            velocityX = 0; // stop immediately when no key held
        }

        // Jump: isKeyJustPressed fires only on the first frame the key is held,
        // preventing infinite jumping while holding the key in the air
        if ((Gdx.input.isKeyJustPressed(Keys.SPACE) ||
             Gdx.input.isKeyJustPressed(Keys.UP)    ||
             Gdx.input.isKeyJustPressed(Keys.W))
             && isOnGround) {
            velocityY = JUMP_VELOCITY;
        }
    }

    private void applyGravity(float deltaTime) {
        // v = v0 + a*t  (gravity pulls down every frame)
        velocityY += GRAVITY * deltaTime;
    }

    private void integratePosition(float deltaTime) {
        // p = p0 + v*t  (multiply by deltaTime = frame-rate independent movement)
        x += velocityX * deltaTime;
        y += velocityY * deltaTime;
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public void dispose() {
        texture.dispose();
    }
}
