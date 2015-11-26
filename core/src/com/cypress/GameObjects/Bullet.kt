package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of bullet. */
public class Bullet(private val character : Character) {
    
    private val assets   = AssetLoader.getInstance()
    private val startPos = Vector2(character.getX(), character.getY())
    private val position = Vector2(0f, 0f)
    private val bounds   = Rectangle()
    private val bullets  = Array(7, {TextureRegion()})
    private val index    = assets.gunsNames.indexOf(character.gunType)

    private var direction  = 0f

    public val enemyBulllet = character.isEnemy
    public val damage       = assets.bulletDamage[index]

    init {
        fun addBullet(index : Int, pos : Pair<Int, Int>, size : Pair<Int, Int>) {
            bullets[index] = TextureRegion(assets.bullets, pos.first, pos.second, size.first, size.second)
        }

        var startPosX  = arrayOf(145, 165, 200, 175, 170, 160, 160)
        val startPosY  = arrayOf(77, 75, 80, 80, 75, 55, 85)
        val bulletSize = arrayOf(Pair(45, 25), Pair(50, 42), Pair( 50, 26), Pair(52, 16),
                                 Pair(52, 38), Pair(50, 26), Pair(128, 32))

        startPos.y += startPosY[index]
        bounds.setSize(bulletSize[index].first.toFloat(), bulletSize[index].second.toFloat())

        if (character.shouldGoToRight || character.stayRight) {
            direction = 15f

            val pos = arrayOf(Pair(0, 6),   Pair(0, 37),  Pair(0, 88),
                              Pair(0, 121), Pair(0, 145), Pair(0, 6), Pair(0, 188))
            for (i in 0 .. 6) addBullet(i, pos[i], bulletSize[index])
        }
        else {
            startPosX = arrayOf(-25, -45, -60, -65,-65, -80, -105)
            direction = -15f
            startPos.y -= 5

            val pos = arrayOf(Pair(83, 6),   Pair(78, 37),  Pair(78, 88),
                              Pair(76, 121), Pair(76, 145), Pair(83, 6), Pair(0, 224))
            for (i in 0 .. 6) addBullet(i, pos[i], bulletSize[index])
        }

        startPos.x += startPosX[index]

        position.x = startPos.x
        position.y = startPos.y
        bounds.setPosition(position.x, position.y)
    }

    /** Draws bullet. */
    public fun draw(batcher : SpriteBatch) {
        position.x += direction

        if (character.shouldGoToLeft) position.x -= character.getX()
        else if (character.shouldGoToRight) position.x += character.getX()
        bounds.setPosition(position.x, position.y)

        val texture = bullets[index]
        var size    =
                if (index == 6) Pair(85f, 21f)
                else Pair(texture.regionWidth.toFloat() / 2, texture.regionHeight.toFloat() / 2)

        batcher.begin()
        batcher.draw(texture, position.x, position.y, size.first, size.second)
        batcher.end()
    }

    /** Returns distance from player to bullet. */
    public fun distance() = Math.abs(position.x - startPos.x)

    /** Returns bounds of bullet. */
    public fun getBounds() = bounds
}