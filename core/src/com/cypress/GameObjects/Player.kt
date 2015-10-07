package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader

public class Player(assets : AssetLoader, x : Float, y : Float, width : Int, height : Int) {

    public var health = 100
    public var shouldGoLeft    = false
    public var shouldGoRight   = false
    public var shouldJump      = false

    private val batcher  = SpriteBatch()
    private var position = Vector2(x, y)
    private val velocity = Vector2(0f, 0f)
    private val acceleration = Vector2(0f, 480f)

    private val width    = width
    private val height   = height

    private var stayRight       = true
    private var playerGoLeft    = Animation(0.2f, Array<TextureRegion>())
    private var playerGoRight   = Animation(0.2f, Array<TextureRegion>())
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
        playersJump.addAll(playerLeft1, playerLeft2, playerLeft3)

        playerStayRight = Animation(0.2f, playerRight2)
        playerStayLeft  = Animation(0.2f, playerLeft2)

        playerGoRight          = Animation(0.2f, playersRight)
        playerGoRight.playMode = Animation.PlayMode.LOOP_PINGPONG

        playerGoLeft          = Animation(0.2f, playersLeft)
        playerGoLeft.playMode = Animation.PlayMode.LOOP_PINGPONG

        playerJump          = Animation(0.2f, playersJump)
        playerJump.playMode = Animation.PlayMode.LOOP_PINGPONG
    }

    public fun update(delta : Float) {
        velocity.add(acceleration.cpy().scl(delta));
        if(velocity.y > 1) velocity.y = 8f
        position.add(velocity.cpy().scl(delta));

        if (shouldGoRight) position = Vector2(position.x - 5, position.y)
        else if(shouldGoLeft) position = Vector2(position.x + 5, position.y)
        else if(shouldJump) position = Vector2(position.x , position.y - 10)
    }

    public fun draw(delta : Float) {
        batcher.begin()
        update(delta)
        if(shouldJump)
        {
            batcher.draw(playerGoRight.getKeyFrame(delta), 2f, 96f, width.toFloat(), (health * 1.5).toFloat())
            update(delta)
        }
        if (!shouldGoLeft && !shouldGoRight) {
            when (stayRight) {
                true  -> batcher.draw(playerStayRight.getKeyFrame(delta), 2f, 96f, width.toFloat(), (health * 1.5).toFloat())
                false -> batcher.draw(playerStayLeft.getKeyFrame(delta), 2f, 96f, width.toFloat(), (health * 1.5).toFloat())
            }
        }
        if (shouldGoLeft) {
            stayRight = false
            batcher.draw(playerGoLeft.getKeyFrame(delta), 2f, 96f, width.toFloat(), (health * 1.5).toFloat())
            update(delta)
        }
        if (shouldGoRight) {
            stayRight = true
            batcher.draw(playerGoRight.getKeyFrame(delta), 2f, 96f, width.toFloat(), (health * 1.5).toFloat())
            update(delta)
        }

        batcher.end()
    }

    public fun getX() : Float {
        return position.x
    }

    public fun getY() : Float {
        return position.y
    }

    public fun getWidth() : Float {
        return width.toFloat()
    }

    public fun getHeight() : Float {
        return height.toFloat()
    }

}