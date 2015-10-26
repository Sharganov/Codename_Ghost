package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader

class Bullet(private val assets : AssetLoader, private var player : Player) {

    private var batcher = SpriteBatch()
    private val type = player.gunType
    private var startPos = Vector2(0f, 0f)
    private var position = Vector2(0f, 0f)
    private var direction = 0f

    private var uziBullet = TextureRegion()
    private var shotgunBullet = TextureRegion()
    private var rifleBullet = TextureRegion()
    private var lasergunBullet = TextureRegion()
    private var laser2gunBullet = TextureRegion()
    private var rocket = TextureRegion()

    //private var correction = if (Math.random() mod 2 > 0.5) (Math.random() mod 50).toFloat()
    //                         else -(Math.random() mod 50).toFloat()

    init {
        when (type) {
            "uzi"            -> startPos.y = player.getPositionY() + 47
            "shotgun"        -> startPos.y = player.getPositionY() + 45
            "assaultRiffle"  -> startPos.y = player.getPositionY() + 50
            "lasergun"       -> startPos.y = player.getPositionY() + 50
            "laser2gun"      -> startPos.y = player.getPositionY() + 47
            "rocketLauncher" -> startPos.y = player.getPositionY() + 60
        }
        position.y = startPos.y

        if (player.shouldGoToRight || player.stayRight) {
            when (type) {
                "uzi"            -> startPos.x = player.x + 430
                "shotgun"        -> startPos.x = player.x + 450
                "assaultRiffle"  -> startPos.x = player.x + 485
                "lasergun"       -> startPos.x = player.x + 460
                "laser2gun"      -> startPos.x = player.x + 455
                "rocketLauncher" -> startPos.x = player.x + 445
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
                "uzi"            -> startPos.x = player.x + 320
                "shotgun"        -> startPos.x = player.x + 280
                "assaultRiffle"  -> startPos.x = player.x + 235
                "lasergun"       -> startPos.x = player.x + 270
                "laser2gun"      -> startPos.x = player.x + 270
                "rocketLauncher" -> startPos.x = player.x + 210
            }
            direction = -15f

            uziBullet       = TextureRegion(assets.bullets, 83, 6, 45, 25)
            shotgunBullet   = TextureRegion(assets.bullets, 78, 37, 50, 42)
            rifleBullet     = TextureRegion(assets.bullets, 78, 88, 50, 26)
            lasergunBullet  = TextureRegion(assets.bullets, 76, 121, 52, 16)
            laser2gunBullet = TextureRegion(assets.bullets, 76, 145, 52, 38)
            rocket          = TextureRegion(assets.bullets, 0, 224, 128, 32)
        }

        position.x = startPos.x
    }

    /** Updates bullet. */
    public fun update(newY: Float) {
        position.y = -2 * player.getPositionY() + newY + startPos.y + 80
    }

    /** Draws bullet. */
    public fun draw(delta: Float) {
        position.x += direction

        // drawing bullet
        batcher.begin()
        when (type) {
            "uzi"            -> batcher.draw(uziBullet, position.x, position.y, 23f / assets.zoom, 13f / assets.zoom)
            "shotgun"        -> batcher.draw(shotgunBullet, position.x, position.y, 25f / assets.zoom, 21f / assets.zoom)
            "assaultRiffle"  -> batcher.draw(rifleBullet, position.x, position.y, 25f / assets.zoom, 13f / assets.zoom)
            "lasergun"       -> batcher.draw(lasergunBullet, position.x, position.y, 26f / assets.zoom, 8f / assets.zoom)
            "laser2gun"      -> batcher.draw(laser2gunBullet, position.x, position.y, 26f / assets.zoom, 16f / assets.zoom)
            "rocketLauncher" -> batcher.draw(rocket, position.x, position.y, 85f / assets.zoom, 21f / assets.zoom)
        }
        batcher.end()
    }

    /** Returns distance from player to bullet. */
    public fun distance(): Float {
        return Math.abs(position.x - startPos.x)
    }

    public fun checkCollision() {
    }
}