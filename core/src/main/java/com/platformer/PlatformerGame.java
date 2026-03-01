package com.platformer;

import com.badlogic.gdx.Game;

/**
 * PlatformerGame is LibGDX's top-level application class.
 * Extending Game (instead of ApplicationAdapter) gives us the
 * setScreen() / getScreen() pattern for switching between
 * game states (menu, playing, game over) without restructuring code.
 */
public class PlatformerGame extends Game {

    @Override
    public void create() {
        // Switch to the main game screen when the app starts.
        // Game.render() automatically delegates to screen.render() each frame.
        setScreen(new GameScreen(this));
    }
}
