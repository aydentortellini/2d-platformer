package com.platformer.physics;

import com.platformer.entities.Platform;
import com.platformer.entities.Player;

/**
 * AABB = Axis-Aligned Bounding Box collision detection and resolution.
 *
 * Two rectangles that are never rotated (sides always parallel to X/Y axes).
 * This makes collision math simple: just compare edges.
 *
 * The core question: "Are two rectangles overlapping, and if so,
 * what is the minimum distance we need to move one to separate them?"
 */
public class AABB {

    /**
     * Checks whether two rectangles overlap.
     * Each rectangle is defined by (x, y) = bottom-left corner, width, height.
     *
     * Two rectangles do NOT overlap if any one of these is true:
     *   A is entirely to the right of B
     *   A is entirely to the left of B
     *   A is entirely above B
     *   A is entirely below B
     */
    public static boolean overlaps(float ax, float ay, float aw, float ah,
                                   float bx, float by, float bw, float bh) {
        return ax < bx + bw &&
               ax + aw > bx &&
               ay < by + bh &&
               ay + ah > by;
    }

    /**
     * Resolves a collision between the player and a platform by pushing
     * the player out along whichever axis has the smallest overlap.
     * This gives the most natural-feeling collision response.
     */
    public static void resolve(Player player, Platform platform) {
        float overlapLeft   = (platform.x + platform.width) - player.x;
        float overlapRight  = (player.x + Player.WIDTH) - platform.x;
        float overlapTop    = (player.y + Player.HEIGHT) - platform.y;
        float overlapBottom = (platform.y + platform.height) - player.y;

        float minOverlap = Math.min(Math.min(overlapLeft, overlapRight),
                                    Math.min(overlapTop, overlapBottom));

        if (minOverlap == overlapBottom) {
            // Player is standing ON TOP of the platform
            player.y = platform.y + platform.height;
            player.velocityY = 0;
            player.isOnGround = true;
        } else if (minOverlap == overlapTop) {
            // Player hit a ceiling
            player.y = platform.y - Player.HEIGHT;
            player.velocityY = 0;
        } else if (minOverlap == overlapLeft) {
            // Player's right side hit the platform's left wall
            player.x = platform.x - Player.WIDTH;
            player.velocityX = 0;
        } else {
            // Player's left side hit the platform's right wall
            player.x = platform.x + platform.width;
            player.velocityX = 0;
        }
    }
}
