package com.platformer;

import com.platformer.entities.Platform;

import java.util.Arrays;

public class LevelLoader {

    public static Level load(int levelNumber) {
        switch (levelNumber) {
            case 1: return buildLevel1();
            case 2: return buildLevel2();
            default: throw new IllegalArgumentException("Unknown level: " + levelNumber);
        }
    }

    private static Level buildLevel1() {
        return new Level(100f, 200f, Arrays.asList(
            new Platform(0f,   0f,   2000f, 32f),  // ground
            new Platform(200f, 120f, 200f,  24f),  // low platform
            new Platform(500f, 200f, 180f,  24f),  // mid platform
            new Platform(780f, 290f, 220f,  24f),  // high platform
            new Platform(300f, 350f, 150f,  24f)   // top platform
        ));
    }

    private static Level buildLevel2() {
        return new Level(50f, 200f, Arrays.asList(
            new Platform(0f,   0f,   2000f, 32f),  // ground
            new Platform(150f, 100f, 120f,  24f),
            new Platform(350f, 180f, 120f,  24f),
            new Platform(550f, 260f, 120f,  24f),
            new Platform(750f, 340f, 120f,  24f)
        ));
    }
}
