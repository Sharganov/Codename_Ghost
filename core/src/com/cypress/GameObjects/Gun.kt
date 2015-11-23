package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of gun. */
public class Gun(private val character : Character, private var type : String) {

    private val assets  = AssetLoader.getInstance()

    private val assaultRiffleRight  = TextureRegion(assets.guns, 11, 6, 187, 80)
    private val assaultRiffleLeft   = TextureRegion(assets.guns, 202, 6, 187, 80)
    private val uziRight            = TextureRegion(assets.guns, 23, 95, 90, 58)
    private val uziLeft             = TextureRegion(assets.guns, 281, 95, 90, 58)
    private val shotgunRight        = TextureRegion(assets.guns, 29, 176, 138, 55)
    private val shotgunLeft         = TextureRegion(assets.guns, 248, 176, 138, 55)
    private val lasergunRight       = TextureRegion(assets.guns, 9, 250, 178, 86)
    private val lasergunLeft        = TextureRegion(assets.guns, 223, 250, 178, 86)
    private val laser2gunRight      = TextureRegion(assets.guns, 7, 348, 183, 93)
    private val laser2gunLeft       = TextureRegion(assets.guns, 221, 348, 183, 93)
    private val rocketLauncherRight = TextureRegion(assets.guns, 7, 456, 188, 73)
    private val rocketLauncherLeft  = TextureRegion(assets.guns, 222, 456, 188, 73)

    /** Updates gun. */
    public fun update(newType: String) {
        type = newType
    }

    /** Draws gun. */
    public fun draw(delta: Float, batcher : SpriteBatch) {
        val x = character.getX()
        val y = character.getY()

        batcher.begin()
        when (type) {
            assets.gunsNames[0] -> {
                if (character.shouldGoToRight || character.stayRight)
                    batcher.draw(uziRight, x + 65, y + 40, 90f, 58f)
                else
                    batcher.draw(uziLeft, x - 45, y + 35, 90f, 58f)
            }
            assets.gunsNames[1] -> {
                if (character.shouldGoToRight || character.stayRight)
                    batcher.draw(shotgunRight, x + 50, y + 40, 138f, 55f)
                else
                    batcher.draw(shotgunLeft, x - 40, y + 35, 138f, 55f)
            }
            assets.gunsNames[2] -> {
                if (character.shouldGoToRight || character.stayRight)
                    batcher.draw(assaultRiffleRight, x + 35, y + 40, 187f, 80f)
                else
                    batcher.draw(assaultRiffleLeft, x - 75, y + 35, 187f, 80f)
            }
            assets.gunsNames[3] -> {
                if (character.shouldGoToRight || character.stayRight)
                    batcher.draw(lasergunRight, x + 23, y + 30, 178f, 86f)
                else
                    batcher.draw(lasergunLeft, x - 50, y + 25, 178f, 86f)
            }
            assets.gunsNames[4] -> {
                if (character.shouldGoToRight || character.stayRight)
                    batcher.draw(laser2gunRight, x + 10, y + 25, 183f, 93f)
                else
                    batcher.draw(laser2gunLeft, x - 50, y + 20, 183f, 93f)
            }
            assets.gunsNames[5] -> {
                if (character.shouldGoToRight || character.stayRight)
                    batcher.draw(rocketLauncherRight, x - 5, y + 50, 188f, 73f)
                else
                    batcher.draw(rocketLauncherLeft, x - 65, y + 45, 188f, 73f)
            }
        }
        batcher.end()
    }
}