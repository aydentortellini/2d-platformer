package com.platformer.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.platformer.PlatformerGame;

/**
 * DesktopLauncher contains main() -- the entry point the JVM calls to start the program.
 *
 * It lives in the 'desktop' module (not 'core') because Lwjgl3Application is
 * desktop-only code. Keeping it separate means core stays portable to other platforms.
 *
 * LWJGL3 handles:
 *   - Opening an OS window
 *   - Creating an OpenGL context for rendering
 *   - Running the game loop and calling render() at 60fps
 *   - Forwarding keyboard/mouse events to LibGDX
 */
public class DesktopLauncher {

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setTitle("2D Platformer");
        config.setWindowedMode(800, 480);
        config.setForegroundFPS(60);
        config.setResizable(true);

        new Lwjgl3Application(new PlatformerGame(), config);
    }
}
