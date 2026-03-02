package com.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenu implements Screen {

    private final PlatformerGame game;
    private SpriteBatch batch;
    private BitmapFont font;

    public MainMenu(PlatformerGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        font  = new BitmapFont(); // LibGDX's built-in Arial font
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.15f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        font.draw(batch, "Loading",300, 300);
        font.draw(batch, "Press ENTER to play", 300, 220);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            game.setScreen(new GameScreen(game, 1)); // level 1
        }
    }

    @Override public void dispose() { batch.dispose(); font.dispose(); }
    @Override public void resize(int w, int h) {}
    @Override public void hide()   {}
    @Override public void pause()  {}
    @Override public void resume() {}
}
