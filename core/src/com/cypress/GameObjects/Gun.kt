package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of player. */
public class Gun(val assets : AssetLoader, val player : Player, private var type : String,
                 private var x : Float, private var y : Float) {

    private val batcher  = SpriteBatch()

    private val assaultRiffleRight = TextureRegion(assets.guns, 11, 6, 187, 80)
    private val assaultRiffleLeft  = TextureRegion(assets.guns, 202, 6, 187, 80)
    private val usiRight           = TextureRegion(assets.guns, 23, 95, 90, 58)
    private val usiLeft            = TextureRegion(assets.guns, 281, 95, 90, 58)
    private val shotgunRight       = TextureRegion(assets.guns, 19, 176, 124, 65)
    private val shotgunLeft        = TextureRegion(assets.guns, 264, 172, 124, 65)
    private val plasmagunRight     = TextureRegion(assets.guns, 9, 250, 178, 86)
    private val plasmagunLeft      = TextureRegion(assets.guns, 223, 250, 178, 86)

    /** Updates player position. */
    public fun update(newType : String, newX : Float, newY : Float) {
        type = newType
        x    = newX
        y    = newY
    }

    /** Draws player. */
    public fun draw(delta : Float) {
        batcher.begin()

        when (type) {
            "usi" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(usiRight, x + 70, y + 35, 90f, 58f)
                else
                    batcher.draw(usiLeft, x - 35, y + 30, 90f, 58f)
            }
            "shotgun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(shotgunRight, x + 50, y + 30, 124f, 65f)
                else
                    batcher.draw(shotgunLeft, x - 45, y + 30, 124f, 65f)
            }
            "assaultRiffle" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(assaultRiffleRight, x + 35, y + 30, 187f, 80f)
                else
                    batcher.draw(assaultRiffleLeft, x - 75, y + 30, 187f, 80f)
            }
            "plasmagun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(plasmagunRight, x + 20, y + 30, 178f, 86f)
                else
                    batcher.draw(plasmagunLeft, x - 55, y + 30, 178f, 86f)
            }
        }

        batcher.end()
    }
}