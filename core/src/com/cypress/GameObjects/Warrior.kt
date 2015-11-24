package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader

class Warrior(val position : Vector2, private val width : Int,
            private val height : Int, private val player : Player) : Character() {

    public override var health = 100
    public override var shouldGoToLeft = false
    public override var shouldGoToRight = false
    public override var stayRight = false
    public override var onGround = true
    public override var gunType = "uzi"

    private val assets = AssetLoader.getInstance()

    private var warriorGoToLeft  = Animation(0.1f, Array<TextureRegion>())
    private var warriorGoToRight = Animation(0.1f, Array<TextureRegion>())
    private var warriorStayRight = TextureRegion(assets.warrior, 25, 11, width, height)
    private var warriorStayLeft  = TextureRegion(assets.warrior, 201, 11, width, height)

    init {
        // setting animation
        val rightPos = arrayOf(Pair(28, 235), Pair(350, 237), Pair(674, 236), Pair(30, 445),
               Pair(361, 445), Pair(678, 445), Pair(23, 662), Pair(349, 662), Pair(688, 663))
        val leftPos  = arrayOf(Pair(200, 236), Pair(529, 236), Pair(871, 236), Pair(199, 445),
               Pair(519, 442), Pair(864, 442), Pair(204, 662), Pair(528, 661), Pair(853, 661))

        val warriorsRight = Array<TextureRegion>()
        val warriorsLeft  = Array<TextureRegion>()

        warriorsRight.addAll(
            Array(9, {i -> TextureRegion(assets.warrior, rightPos[i].first, rightPos[i].second, width, height)}), 0, 8
        )
        warriorsLeft.addAll(
            Array(9, {i -> TextureRegion(assets.warrior, leftPos[i].first, leftPos[i].second, width, height)}), 0, 8
        )

        warriorGoToRight = Animation(0.1f, warriorsRight)
        warriorGoToRight.playMode = Animation.PlayMode.LOOP

        warriorGoToLeft = Animation(0.1f, warriorsLeft)
        warriorGoToLeft.playMode = Animation.PlayMode.LOOP
    }

    /** Updates position of warrior. */
    public fun update(delta : Float) {
        //
        if (player.getX() > position.x && stayRight
                && Math.abs(player.getX() - position.x) < 300f) {
            shouldGoToRight = false
            shouldGoToLeft = false
            if (delta.toInt() % 10 == 0) println("bang!")
        }
        if (player.getX() > position.x && stayRight
                && Math.abs(player.getX() - position.x) >= 300f && Math.abs(player.getX() - position.x) < 500f) {
            position.x += 2
            shouldGoToRight = true
            shouldGoToLeft = false
        }
        else if (player.getX() < position.x && !stayRight
                && Math.abs(player.getX() - position.x) < 300f) {
            shouldGoToRight = false
            shouldGoToLeft = false
            if (delta.toInt() % 5 == 0) println("bang!")
        }
        else if (player.getX() < position.x && !stayRight
                && Math.abs(player.getX() - position.x) >= 300f && Math.abs(player.getX() - position.x) < 500f) {
            position.x -= 2
            shouldGoToRight = false
            shouldGoToLeft = true
        }
        else if (player.getX() == position.x) stayRight = !stayRight
        else {
            shouldGoToRight = false
            shouldGoToLeft = false
            if (delta.toInt() % 10 == 0) {
                if (stayRight) stayRight = false
                else stayRight = true
            }
        }
    }

    /** Draws warrior. */
    public fun draw(delta: Float, batcher : SpriteBatch) {
        batcher.begin()

        // warrior should stay still ...
        if (!shouldGoToLeft && !shouldGoToRight) {
            when (stayRight) {
            // ... turning to the right side
                true ->
                    batcher.draw(warriorStayRight, position.x, position.y, width.toFloat(), height.toFloat())
            // ... turning to the left side
                false ->
                    batcher.draw(warriorStayLeft, position.x, position.y, width.toFloat(), height.toFloat())
            }
        }

        // warrior should go to left
        else if (shouldGoToLeft) {
            stayRight = false
            batcher.draw(warriorGoToLeft.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
        }

        // warrior should go to right
        else if (shouldGoToRight) {
            stayRight = true
            batcher.draw(warriorGoToRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
        }

        update(delta)
        batcher.end()
    }

    /** Returns position of warrior on Ox axis. */
    public override fun getX(): Float {
        return position.x
    }

    /** Returns position of warrior on Oy axis. */
    public override fun getY(): Float {
        return position.y
    }
}
