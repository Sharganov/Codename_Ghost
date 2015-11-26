package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of gun. */
public class Gun(private val character : Character) {
    private val assets = AssetLoader.getInstance()

    /** Returns texture of the requested gun. */
    private fun getGun(x : Int, y : Int, width : Int, height : Int) : TextureRegion =
            TextureRegion(assets.guns, x, y, width, height)

    private val guns = arrayOf(
            Pair(getGun(23,  95,  90, 58), getGun(281,  95,  90, 58)),  // uzi
            Pair(getGun(29, 176, 138, 55), getGun(248, 176, 138, 55)),  // shotgun
            Pair(getGun(11,   6, 187, 80), getGun(202,   6, 187, 80)),  // assault riffle
            Pair(getGun( 9, 250, 178, 86), getGun(223, 250, 178, 86)),  // plasmagun
            Pair(getGun( 7, 348, 183, 93), getGun(221, 348, 183, 93)),  // lasergun
            Pair(getGun(14, 543, 180, 69), getGun(232, 543, 180, 69)),  // minigun
            Pair(getGun( 7, 456, 188, 73), getGun(222, 456, 188, 73))   // rocket launcher
    )
    private val posX = arrayOf(Pair(65, -45), Pair(50, -40), Pair(35, -75), Pair(23, -50),
                               Pair(10, -50), Pair( 5, -65), Pair(-5, -65))
    private val posY = arrayOf(40, 40, 40, 30, 25, 40, 50)
    private val size = arrayOf(Pair( 90f, 58f), Pair(138f, 55f), Pair(187f, 80f), Pair(178f, 86f),
                               Pair(183f, 93f), Pair(180f, 69f), Pair(188f, 73f))

    private var index = assets.gunsNames.indexOf(character.gunType)

    /** Updates gun. */
    public fun update() {
        index = assets.gunsNames.indexOf(character.gunType)
    }

    /** Draws gun. */
    public fun draw(batcher : SpriteBatch) {
        val x = character.getX()
        val y = character.getY()

        var texture     = guns[index].first
        var correctionX = posX[index].first
        var correctionY = posY[index]
        if (character.shouldGoToLeft || !character.stayRight) {
            texture     = guns[index].second
            correctionX = posX[index].second
            correctionY = posY[index] - 5
        }

        batcher.begin()
        batcher.draw(texture, x + correctionX, y + correctionY, size[index].first, size[index].second)
        batcher.end()
    }
}