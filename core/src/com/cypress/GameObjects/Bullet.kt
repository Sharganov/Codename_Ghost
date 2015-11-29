package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader
import com.cypress.GameObjects.Enemies.*

/** Contains definition of bullet. */
public class Bullet(private val character : Character) {
    private val assets   = AssetLoader.getInstance()
    private val startPos = Vector2(character.getX(), character.getY())
    private val position = Vector2(0f, 0f)
    private val bounds   = Rectangle()
    private val bullets  = Array(7, {TextureRegion()})
    private val index    = assets.gunNames.indexOf(character.gunType)
    private var shot     = Animation(0.01f, Array<TextureRegion>())

    private var direction  = 0f
    private var startValue = 0f

    public val enemyBulllet = character.isEnemy
    public val damage       = assets.bulletDamage[index]

    init {
        fun addBullet(index : Int, x : Int, y : Int, size : Pair<Int, Int>) {
            bullets[index] = TextureRegion(assets.bullets, x, y, size.first, size.second)
        }

        var startPosX  = arrayOf(145, 175, 215, 180, 170, 210, 175)
        val startPosY  = arrayOf( 78,  77,  82,  77,  72,  37,  90)
        val positionY  = arrayOf(  6,  37,  88, 115, 145,   6, 188)
        val bulletSize = arrayOf(Pair(45, 25), Pair(50, 42), Pair( 50, 26), Pair(39, 29),
                                 Pair(62, 38), Pair(50, 26), Pair(128, 32))

        startPos.y += startPosY[index]
        bounds.setSize(bulletSize[index].first.toFloat(), bulletSize[index].second.toFloat())

        if (character.shouldGoToRight || character.stayRight) {
            direction = 15f
            for (i in 0 .. 6) addBullet(i, 0, positionY[i], bulletSize[index])
        }
        else {
            startPosX = arrayOf(-85, -90, -115, -70, -60, -125, -115)
            direction = -15f
            startPos.y -= 5
            positionY[6] = 224
            val positionX = arrayOf(83, 78, 78, 89, 66, 83, 0)
            for (i in 0 .. 6) addBullet(i, positionX[i], positionY[i], bulletSize[index])
        }

        startPos.x += startPosX[index]

        val pos = arrayOf(17, 143, 273, 402, 530, 656, 788, 918, 1044, 1170, 1300, 1430, 1559, 1683, 1811, 1941, 2065)
        val animation = Array<TextureRegion>()
        animation.addAll(Array(17, { i -> TextureRegion(assets.effects, pos[i], 10, 100, 100) }), 0, 16)
        shot = Animation(0.01f, animation, Animation.PlayMode.LOOP)

        if (character is Warrior) {
            if (character.shouldGoToRight || character.stayRight) {
                startPos.x -= 40
                startPos.y -= 20
            }
            else {
                startPos.x -= 10
                startPos.y -= 10
            }
        }

        position.x = startPos.x
        position.y = startPos.y
        bounds.setPosition(position.x, position.y)
    }

    /** Draws bullet. */
    public fun draw(delta : Float, batcher : SpriteBatch) {
        position.x += direction
        if (startValue == 0f) startValue = delta

        if (character.shouldGoToLeft) position.x -= 4
        else if (character.shouldGoToRight) position.x += 4
        bounds.setPosition(position.x, position.y)

        val texture = bullets[index]
        var size    =
                if (index == 6) Pair(85f, 21f)
                else Pair(texture.regionWidth.toFloat() / 2, texture.regionHeight.toFloat() / 2)

        batcher.begin()
        batcher.draw(texture, position.x, position.y, size.first, size.second)
        if (delta - startValue < 0.15f && index != 3 && index != 4)
            batcher.draw(shot.getKeyFrame(delta - startValue), startPos.x, startPos.y - 15, 60f, 40f)
        batcher.end()
    }

    /** Returns distance from player to bullet. */
    public fun distance() = Math.abs(position.x - startPos.x)

    /** Returns bounds of bullet. */
    public fun getBounds() = bounds
}