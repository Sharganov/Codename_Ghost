package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of player. */
public class Gun(val player : Player, private var type : String,
                 private var x : Float, private var y : Float) {

    private val assets = AssetLoader.getInstance()

    private val batcher = SpriteBatch()

    private val assaultRiffleRight = TextureRegion(assets.guns, 11, 6, 187, 80)
    private val assaultRiffleLeft = TextureRegion(assets.guns, 202, 6, 187, 80)
    private val uziRight = TextureRegion(assets.guns, 23, 95, 90, 58)
    private val uziLeft = TextureRegion(assets.guns, 281, 95, 90, 58)
    private val shotgunRight = TextureRegion(assets.guns, 29, 176, 138, 55)
    private val shotgunLeft = TextureRegion(assets.guns, 248, 176, 138, 55)
    private val lasergunRight = TextureRegion(assets.guns, 9, 250, 178, 86)
    private val lasergunLeft = TextureRegion(assets.guns, 223, 250, 178, 86)
    private val laser2gunRight = TextureRegion(assets.guns, 7, 348, 183, 93)
    private val laser2gunLeft = TextureRegion(assets.guns, 221, 348, 183, 93)
    private val rocketLauncherRight = TextureRegion(assets.guns, 7, 456, 188, 73)
    private val rocketLauncherLeft = TextureRegion(assets.guns, 222, 456, 188, 73)

    /** Updates gun. */
    public fun update(newType: String) {
        type = newType
    }

    /** Draws gun. */
    public fun draw(delta: Float) {
        batcher.begin()
        when (type) {
            "uzi" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(uziRight, x + 375, y + 20, 90f / assets.zoom, 58f / assets.zoom)
                else
                    batcher.draw(uziLeft, x + 275, y + 15, 90f / assets.zoom, 58f / assets.zoom)
            }
            "shotgun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(shotgunRight, x + 360, y + 20, 138f / assets.zoom, 55f / assets.zoom)
                else
                    batcher.draw(shotgunLeft, x + 280, y + 15, 138f / assets.zoom, 55f / assets.zoom)
            }
            "assaultRiffle" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(assaultRiffleRight, x + 350, y + 15, 187f / assets.zoom, 80f / assets.zoom)
                else
                    batcher.draw(assaultRiffleLeft, x + 225, y + 15, 187f / assets.zoom, 80f / assets.zoom)
            }
            "lasergun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(lasergunRight, x + 335, y + 10, 178f / assets.zoom, 86f / assets.zoom)
                else
                    batcher.draw(lasergunLeft, x + 260, y + 10, 178f / assets.zoom, 86f / assets.zoom)
            }
            "laser2gun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(laser2gunRight, x + 325, y + 5, 183f / assets.zoom, 93f / assets.zoom)
                else
                    batcher.draw(laser2gunLeft, x + 275, y + 10, 183f / assets.zoom, 93f / assets.zoom)
            }
            "rocketLauncher" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(rocketLauncherRight, x + 315, y + 30, 188f / assets.zoom, 73f / assets.zoom)
                else
                    batcher.draw(rocketLauncherLeft, x + 265, y + 30, 188f / assets.zoom, 73f / assets.zoom)
            }
        }
        batcher.end()
    }
}