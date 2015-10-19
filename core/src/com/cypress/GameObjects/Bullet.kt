package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader

class Bullet(private val assets : AssetLoader, private var player : Player) {

    private val x = player.x
    private val y = player.getY

    private var batcher   = SpriteBatch()
    private val type      = player.gunType
    private var startPos  = Vector2(0f, 0f)
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
            "uzi"            -> startPos.y = y + 70
            "shotgun"        -> startPos.y = y + 65
            "assaultRiffle"  -> startPos.y = y + 72
            "lasergun"       -> startPos.y = y + 75
            "laser2gun"      -> startPos.y = y + 70
            "rocketLauncher" -> startPos.y = y + 85
        }
        position.y = startPos.y

        if (player.shouldGoToRight || player.stayRight) {
            when (type) {
                "uzi"            -> startPos.x = x + 130
                "shotgun"        -> startPos.x = x + 165
                "assaultRiffle"  -> startPos.x = x + 205
                "lasergun"       -> startPos.x = x + 180
                "laser2gun"      -> startPos.x = x + 175
                "rocketLauncher" -> startPos.x = x + 40
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
                "uzi"            -> startPos.x = x
                "shotgun"        -> startPos.x = x - 50
                "assaultRiffle"  -> startPos.x = x - 75
                "lasergun"       -> startPos.x = x - 55
                "laser2gun"      -> startPos.x = x - 55
                "rocketLauncher" -> startPos.x = x + 10
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
    public fun update(newY : Float) {
        position.y = -2 * y + newY + startPos.y + 80
    }

    /** Draws bullet. */
    public fun draw(delta : Float) {
        position.x += direction

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
    public fun distance() : Float {
        return Math.abs(position.x - startPos.x)
    }

    public fun checkCollision() {
    }
}