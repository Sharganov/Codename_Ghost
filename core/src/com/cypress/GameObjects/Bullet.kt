package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader

public class Bullet(private val player : Player) {
    
    private val assets    = AssetLoader.getInstance()
    private val type      = player.gunType
    private var startPos  = Vector2(player.getX(), player.getY())
    private var position  = Vector2(0f, 0f)
    private var direction = 0f

    private var uziBullet       = TextureRegion()
    private var shotgunBullet   = TextureRegion()
    private var rifleBullet     = TextureRegion()
    private var lasergunBullet  = TextureRegion()
    private var laser2gunBullet = TextureRegion()
    private var rocket          = TextureRegion()

    //private var correction = if (Math.random() mod 2 > 0.5) (Math.random() mod 50).toFloat()
    //                         else -(Math.random() mod 50).toFloat()

    init {
        when (type) {
            "uzi"            -> startPos.y += 77
            "shotgun"        -> startPos.y += 75
            "assaultRiffle"  -> startPos.y += 80
            "lasergun"       -> startPos.y += 80
            "laser2gun"      -> startPos.y += 75
            "rocketLauncher" -> startPos.y += 85
        }

        if (player.shouldGoToRight || player.stayRight) {
            when (type) {
                "uzi"            -> startPos.x += 145
                "shotgun"        -> startPos.x += 165
                "assaultRiffle"  -> startPos.x += 200
                "lasergun"       -> startPos.x += 175
                "laser2gun"      -> startPos.x += 170
                "rocketLauncher" -> startPos.x += 160
            }
            direction = 15f

            uziBullet       = TextureRegion(assets.bullets, 0, 6, 45, 25)
            shotgunBullet   = TextureRegion(assets.bullets, 0, 37, 50, 42)
            rifleBullet     = TextureRegion(assets.bullets, 0, 88, 50, 26)
            lasergunBullet  = TextureRegion(assets.bullets, 0, 121, 52, 16)
            laser2gunBullet = TextureRegion(assets.bullets, 0, 145, 52, 38)
            rocket          = TextureRegion(assets.bullets, 0, 188, 128, 32)
        }
        else {
            when (type) {
                "uzi"            -> startPos.x -= 25
                "shotgun"        -> startPos.x -= 45
                "assaultRiffle"  -> startPos.x -= 60
                "lasergun"       -> startPos.x -= 65
                "laser2gun"      -> startPos.x -= 65
                "rocketLauncher" -> startPos.x -= 105
            }
            startPos.y -= 5
            direction = -15f

            uziBullet       = TextureRegion(assets.bullets, 83, 6, 45, 25)
            shotgunBullet   = TextureRegion(assets.bullets, 78, 37, 50, 42)
            rifleBullet     = TextureRegion(assets.bullets, 78, 88, 50, 26)
            lasergunBullet  = TextureRegion(assets.bullets, 76, 121, 52, 16)
            laser2gunBullet = TextureRegion(assets.bullets, 76, 145, 52, 38)
            rocket          = TextureRegion(assets.bullets, 0, 224, 128, 32)
        }

        position.x = startPos.x
        position.y = startPos.y
    }

    /** Draws bullet. */
    public fun draw(delta: Float, batcher : SpriteBatch) {
        position.x += direction
        if (player.shouldGoToLeft) position.x -= 4
        else if (player.shouldGoToRight) position.x += 4

        // drawing bullet
        batcher.begin()
        when (type) {
            "uzi"            -> batcher.draw(uziBullet, position.x, position.y, 23f, 13f)
            "shotgun"        -> batcher.draw(shotgunBullet, position.x, position.y, 25f, 21f)
            "assaultRiffle"  -> batcher.draw(rifleBullet, position.x, position.y, 25f, 13f)
            "lasergun"       -> batcher.draw(lasergunBullet, position.x, position.y, 26f, 8f)
            "laser2gun"      -> batcher.draw(laser2gunBullet, position.x, position.y, 26f, 16f)
            "rocketLauncher" -> batcher.draw(rocket, position.x, position.y, 85f, 21f)
        }
        batcher.end()
    }

    /** Returns distance from player to bullet. */
    public fun distance(): Float {
        return Math.abs(position.x - startPos.x)
    }
}