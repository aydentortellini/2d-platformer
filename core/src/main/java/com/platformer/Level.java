package com.platformer;

import com.platformer.entities.Platform;

import java.util.List;

public class Level {
    public final float spawnX;
    public final float spawnY;
    public final float goalX;
    public final float goalY;
    public final List<Platform> platforms;

    public Level(float spawnX, float spawnY, float goalX, float goalY, List<Platform> platforms) {
        this.spawnX = spawnX;
        this.spawnY = spawnY;
        this.goalX = goalX;
        this.goalY = goalY;
        this.platforms = platforms;
    }
}
