package com.platformer;

import com.platformer.entities.Platform;

import java.util.List;

public class Level {
    public final float spawnX;
    public final float spawnY;
    public final List<Platform> platforms;

    public Level(float spawnX, float spawnY, List<Platform> platforms) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.platforms = platforms;
    }
}
