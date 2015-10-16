package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader
import com.cypress.codenameghost.CGGame

class Bullet(private val assets : AssetLoader, private val game : CGGame, private var player : Player) {

    private val x = player.x
    private val y = player.getY

    private var batcher   = SpriteBatch()
    private var type      = "uzi"
    private var position  = Vector2(0f, y + 70)
    private var direction = 0f

    private var uziBullet       = TextureRegion()
    private var plasmagunBullet = TextureRegion()

    //private var correction = if (Math.random() mod 2 > 0.5) (Math.random() mod 50).toFloat()
    //                         else -(Math.random() mod 50).toFloat()

    init {
        if (player.shouldGoToRight || player.stayRight) {
            position.x = x + 130
            direction = 15f
            uziBullet = TextureRegion(assets.bullets, 0, 8, 50, 26)
            plasmagunBullet = TextureRegion(assets.bullets, 0, 80, 50, 26)
        }
        else {
            position.x = x + 0
            direction = -15f
            uziBullet = TextureRegion(assets.bullets, 78, 8, 50, 26)
            plasmagunBullet = TextureRegion(assets.bullets, 78, 80, 50, 26)
        }
    }

    /** Updates bullet. */
    public fun update(newType : String, newY : Float) {
        type       = newType
        position.y = -y + newY + 150
    }

    /** Draws bullet. */
    public fun draw(delta : Float) {
        position.x += direction

        // drawing bullet
        batcher.begin()
        when (type) {
            "uzi"           -> batcher.draw(uziBullet, position.x, position.y, 25f, 13f)
            "shotgun"       -> batcher.draw(uziBullet, position.x, position.y, 25f, 13f)
            "assaultRiffle" -> batcher.draw(uziBullet, position.x, position.y, 25f, 13f)
            "plasmagun"     -> batcher.draw(plasmagunBullet, position.x, position.y, 25f, 13f)
        }
        batcher.end()
    }

    /** Returns distance from player to bullet. */
    public fun distance() : Float {
        if (player.shouldGoToRight || player.stayRight)
            return Math.abs(position.x - (x + 130))
        else
            return Math.abs(position.x - x)
    }

    public fun checkCollision() {
    }
}