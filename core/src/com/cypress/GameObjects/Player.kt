package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of player. */
public class Player(val assets : AssetLoader, private var x : Float, private var y : Float,
                    private val width : Int, private val height : Int) {

    public var health          = 100
    public var shouldGoToLeft  = false
    public var shouldGoToRight = false
    public var shouldJump      = false
    private var onGround       = true

    private var position     = Vector2(x, y)
    private var velocity     = Vector2(5f, 15f)
    private val acceleration = Vector2(0f, 0.5f)
    private val maxMapLength = 5000f
    private var stayRight    = true

    private val batcher         = SpriteBatch()
    private var playerGoToLeft  = Animation(0.2f, Array<TextureRegion>())
    private var playerGoToRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayLeft  = Animation(0.2f, Array<TextureRegion>())
    private var playerJump      =  Animation(0.2f, Array<TextureRegion>())

    init {
        val playerRight1 = TextureRegion(assets.player, 218, 802, width, height)
        val playerRight2 = TextureRegion(assets.player, 47, 802, width, height)
        val playerRight3 = TextureRegion(assets.player, 387, 802, width, height)
        val playerLeft1  = TextureRegion(assets.player, 712, 802, width, height)
        val playerLeft2  = TextureRegion(assets.player, 883, 802, width, height)
        val playerLeft3  = TextureRegion(assets.player, 540, 802, width, height)

        val playersRight = Array<TextureRegion>()
        val playersLeft  = Array<TextureRegion>()
        val playersJump  = Array<TextureRegion>()

        playersRight.addAll(playerRight1, playerRight2, playerRight3)
        playersLeft.addAll(playerLeft1, playerLeft2, playerLeft3)

        playerStayRight = Animation(0.2f, playerRight2)
        playerStayLeft  = Animation(0.2f, playerLeft2)

        playerGoToRight          = Animation(0.2f, playersRight)
        playerGoToRight.playMode = Animation.PlayMode.LOOP_PINGPONG

        playerGoToLeft          = Animation(0.2f, playersLeft)
        playerGoToLeft.playMode = Animation.PlayMode.LOOP_PINGPONG

        playerJump          = Animation(0.2f, playersJump)
        playerJump.playMode = Animation.PlayMode.LOOP_PINGPONG
    }

    /** Updates player position. */
    public fun update(delta : Float) {
        if(position.y >= 80f ) {
            onGround = true
            position.y = 80f
            velocity.y = 15f
            acceleration.y = 0.5f

println("ok")
        }
        else {
            println("fuck")
            position.y -= velocity.y
            velocity.y -= acceleration.y
        }

        if (shouldGoToRight) {
            // player goes right
            position.x -= velocity.x

            // if he reaches right end of map, camera stops
            if (position.x < -(maxMapLength - 800f)) {
                position.x = -(maxMapLength - 800f)
                x += velocity.x
            }
        }
        else if (shouldGoToLeft){
            // player goes left
            position = Vector2(position.x + velocity.x, position.y)

            // if he reaches left end of map, camera stops
            if (position.x > 0f) {
                position.x = 0f
                x -= velocity.x
            }
        }
        else if(shouldJump)
        {
            position = Vector2(position.x, position.y - velocity.y)
            onGround = false
            shouldJump = false
        }
    }

    /** Draws player. */
    public fun draw(delta : Float) {
        batcher.begin()

        update(delta)
        // player should stay still ...
        if (!shouldGoToLeft && !shouldGoToRight && !shouldJump) {
            when (stayRight) {

                // ... turning to the right side
                true  -> {
                    if (x > 678f) x = 678f
                    if (x < 2f)   x = 2f
                    batcher.draw(playerStayRight.getKeyFrame(delta), x, y, width.toFloat(), (health * 1.5).toFloat())
                }

                // ... turning to the left side
                false -> {
                    if (x > 678f) x = 678f
                    if (x < 2f) x = 2f
                    batcher.draw(playerStayLeft.getKeyFrame(delta), x, y, width.toFloat(), (health * 1.5).toFloat())
                }
            }
        }

        // player should go to left
        if (shouldGoToLeft) {

            // the camera follows player
            if (position.x < 0f && (x + 50) <= 678f) {
                x += 25f
                position.x += 25f
            }

            if (x > 678f) x = 678f
            if (x < 2f) x = 2f
            stayRight = false

            batcher.draw(playerGoToLeft.getKeyFrame(delta), x, y, width.toFloat(), (health * 1.5).toFloat())
            update(delta)
        }

        // player should go to left
        if (shouldGoToRight) {

            // the camera follows player
            if (position.x < 0f && position.x > -(maxMapLength - 800f) && (x - 50) >= 0f) {
                x -= 25f
                position.x -= 25f
            }

            if (x > 678f) x = 678f
            if (x < 2f) x = 2f
            stayRight = true
            batcher.draw(playerGoToRight.getKeyFrame(delta), x, y, width.toFloat(), (health * 1.5).toFloat())
            update(delta)
        }
        if(shouldJump)
        {
            batcher.draw(playerStayRight.getKeyFrame(delta), x, y, width.toFloat(), (health * 1.5).toFloat())
            update(delta)
        }

        batcher.end()
    }

    /** Returns position of player on Ox axis. */
    public fun getX() : Float {
        return position.x
    }

    /** Returns position of player on Oy axis. */
    public fun getY() : Float {
        return position.y
    }

    /** Returns width of player. */
    public fun getWidth() : Float {
        return width.toFloat()
    }

    /** Returns height of player. */
    public fun getHeight() : Float {
        return height.toFloat()
    }

}