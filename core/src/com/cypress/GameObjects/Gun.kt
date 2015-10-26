package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of player. */
public class Gun(val assets : AssetLoader, val player : Player, private var type : String,
                 private var x : Float, private var y : Float) {

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
                    batcher.draw(uziRight, x + 410, y + 35, 90f / assets.zoom, 58f / assets.zoom)
                else
                    batcher.draw(uziLeft, x + 305, y + 30, 90f / assets.zoom, 58f / assets.zoom)
            }
            "shotgun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(shotgunRight, x + 395, y + 35, 138f / assets.zoom, 55f / assets.zoom)
                else
                    batcher.draw(shotgunLeft, x + 285, y + 30, 138f / assets.zoom, 55f / assets.zoom)
            }
            "assaultRiffle" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(assaultRiffleRight, x + 395, y + 30, 187f / assets.zoom, 80f / assets.zoom)
                else
                    batcher.draw(assaultRiffleLeft, x + 250, y + 30, 187f / assets.zoom, 80f / assets.zoom)
            }
            "lasergun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(lasergunRight, x + 370, y + 25, 178f / assets.zoom, 86f / assets.zoom)
                else
                    batcher.draw(lasergunLeft, x + 280, y + 25, 178f / assets.zoom, 86f / assets.zoom)
            }
            "laser2gun" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(laser2gunRight, x + 360, y + 20, 183f / assets.zoom, 93f / assets.zoom)
                else
                    batcher.draw(laser2gunLeft, x + 280, y + 25, 183f / assets.zoom, 93f / assets.zoom)
            }
            "rocketLauncher" -> {
                if (player.shouldGoToRight || player.stayRight)
                    batcher.draw(rocketLauncherRight, x + 330, y + 50, 188f / assets.zoom, 73f / assets.zoom)
                else
                    batcher.draw(rocketLauncherLeft, x + 300, y + 50, 188f / assets.zoom, 73f / assets.zoom)
            }
        }
        batcher.end()
    }
}