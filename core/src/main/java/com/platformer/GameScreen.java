package com.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.platformer.entities.Platform;
import com.platformer.entities.Player;
import com.platformer.physics.AABB;

import java.util.List;

/**
 * GameScreen is the main playable state.
 *
 * It implements LibGDX's Screen interface. The most important method is
 * render(), which LibGDX calls ~60 times per second. Every frame:
 *   1. update() -- move everything, handle input, run physics
 *   2. draw()   -- clear screen, paint everything
 */
public class GameScreen implements Screen {

    private final PlatformerGame game;

    private Player player;
    private List<Platform> platforms;

    // OrthographicCamera = 2D camera, no perspective. Defines the visible area.
    private OrthographicCamera camera;

    // ShapeRenderer draws colored rectangles without needing image files
    private ShapeRenderer shapeRenderer;

    // SpriteBatch draws textures/sprites efficiently
    private SpriteBatch spriteBatch;

    private static final float WORLD_WIDTH  = 800f;
    private static final float WORLD_HEIGHT = 480f;

    private int levelNumber;

    public GameScreen(PlatformerGame game, int levelNumber) {
        this.game = game;
        this.levelNumber = levelNumber;
    }


    @Override
    public void show() {
        // setToOrtho(false, ...) = Y-axis points UP, (0,0) is bottom-left
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);

        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();

        Level level = LevelLoader.load(levelNumber);
        player = new Player(level.spawnX, level.spawnY);
        platforms = level.platforms;
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        draw();
    }

    private void update(float deltaTime) {
        // Clamp deltaTime to prevent huge physics jumps on lag spikes
        deltaTime = Math.min(deltaTime, 1f / 15f);

        player.update(deltaTime);

        // Collision detection: check player against every platform
        for (Platform platform : platforms) {
            if (AABB.overlaps(player.x, player.y, Player.WIDTH, Player.HEIGHT,
                              platform.x, platform.y, platform.width, platform.height)) {
                AABB.resolve(player, platform);
            }
        }

        // Smooth camera follow: move 10% of the gap toward the player each frame
        float targetX = player.x + Player.WIDTH / 2f;
        float targetY = player.y + Player.HEIGHT / 2f;
        camera.position.x += (targetX - camera.position.x) * 0.1f;
        camera.position.y += (targetY - camera.position.y) * 0.1f;
        camera.update(); // must call after changing position
    }

    private void draw() {
        // Draw platforms with ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Platform platform : platforms) {
            platform.render(shapeRenderer);
        }
        shapeRenderer.end();

        // Draw player sprite with SpriteBatch
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        player.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth  = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        spriteBatch.dispose();
        player.dispose();
    }

    @Override public void hide()   {}
    @Override public void pause()  {}
    @Override public void resume() {}
}
