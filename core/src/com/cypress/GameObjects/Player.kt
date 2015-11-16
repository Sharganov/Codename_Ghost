package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader
import java.util.*

/** Contains definition of player. */
public class Player(private val position : Vector2, private val width : Int,
                    private val height : Int, val mapLength: Float)  : Character() {

    public override var health          = 100
    public override var shouldGoToLeft  = false
    public override var shouldGoToRight = false
    public override var stayRight       = true
    public override var shouldJump      = false
    public override var onGround        = true
    public override var gunType         = "uzi"

    public var lives       = 2
    public var bulletsList = LinkedList<Bullet>()
    public var delta       = 0f

    private val bounds       = Rectangle(position.x, position.y, width.toFloat(), height.toFloat())
    private val assets       = AssetLoader.getInstance()
    private var velocity     = Vector2(4f, 12f)
    private val acceleration = Vector2(0f, 0.2f)
    private val gun          = Gun(this, gunType)

    private var playerGoToLeft  = Animation(0.2f, Array<TextureRegion>())
    private var playerGoToRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayLeft  = Animation(0.2f, Array<TextureRegion>())

    init {
        val playerRight1 = TextureRegion(assets.player, 219, 802, width, height)
        val playerRight2 = TextureRegion(assets.player, 47, 802, width, height)
        val playerRight3 = TextureRegion(assets.player, 391, 802, width, height)
        val playerLeft1  = TextureRegion(assets.player, 707, 802, width, height)
        val playerLeft2  = TextureRegion(assets.player, 880, 802, width, height)
        val playerLeft3  = TextureRegion(assets.player, 540, 802, width, height)

        val playersRight = Array<TextureRegion>()
        val playersLeft  = Array<TextureRegion>()

        playersRight.addAll(playerRight1, playerRight2, playerRight3)
        playersLeft.addAll(playerLeft1, playerLeft2, playerLeft3)

        playerStayRight = Animation(0.2f, playerRight2)
        playerStayLeft  = Animation(0.2f, playerLeft2)

        playerGoToRight = Animation(0.2f, playersRight)
        playerGoToRight.playMode = Animation.PlayMode.LOOP_PINGPONG

        playerGoToLeft = Animation(0.2f, playersLeft)
        playerGoToLeft.playMode = Animation.PlayMode.LOOP_PINGPONG
    }

    /** Updates player position. */
    public fun update(){
        val oldY = position.y
        bounds.setPosition(position.x, position.y)

        if (position.y <= 80f) {
            onGround   = true
            position.y = 80f
            velocity.y = 12f
            acceleration.y = 0.2f
        }
        else {
            position.y += velocity.y
            velocity.y -= acceleration.y
        }

        if (shouldGoToRight) position.x += velocity.x

        if (shouldGoToLeft) position.x -= velocity.x

        if (shouldJump) {
            //need to change because of collision
            velocity.y = 12f
            position.y += 60f
            shouldJump = false
            onGround   = false
        }

        //if player reach right side
        if (position.x > mapLength) position.x = mapLength

        // if player reach left side
        if (position.x < 2f) position.x = 2f

        delta = position.y - oldY
    }

    /** Draws player. */
    public fun draw(delta: Float, batcher : SpriteBatch) {
        // drawing gun
        gun.update(gunType)
        gun.draw(delta, batcher)

        batcher.begin()

        // player should stay still ...
        if (!shouldGoToLeft && !shouldGoToRight && !shouldJump) {
            when (stayRight) {
                // ... turning to the right side
                true ->
                    batcher.draw(playerStayRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
                // ... turning to the left side
                false ->
                    batcher.draw(playerStayLeft.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            }
        }

        // player should go to left
        else if (shouldGoToLeft) {
            stayRight = false
            if (shouldJump) onGround = false

            if (!onGround)
                batcher.draw(playerStayLeft.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            else batcher.draw(playerGoToLeft.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
        }

        // player should go to right
        else if (shouldGoToRight) {
            stayRight = true
            if (shouldJump) onGround = false

            if (!onGround)
                batcher.draw(playerStayRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            else batcher.draw(playerGoToRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
        }

        // player should jump
        else if (shouldJump) {
            onGround = false
            batcher.draw(playerStayRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
        }

        update()
        batcher.end()
    }

    /** Returns position of player on Ox axis. */
    public override fun  getX() : Float = position.x

    /** Returns position of player on Oy axis. */
    public override fun getY() : Float = position.y

    public fun setX(value : Float) {
        position.x = value
    }

    public fun setY(value : Float){
        position.y = value
    }

    public fun getBounds() : Rectangle = bounds

    public fun getWidth() : Int = width

    public fun getHeight() : Int = height

    public fun setVelocity(value : Float)
    {
        velocity.y = value
    }
}