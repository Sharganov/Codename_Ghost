package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader
import com.cypress.Screens.LevelsScreen
import com.cypress.codenameghost.CGGame
import java.util.*

/** Contains definition of player. */
public class Player(private val assets : AssetLoader, private val game : CGGame, val x : Float, val y : Float,
                    private val width : Int, private val height : Int, val maxMapLength : Float) {

    public var health = 100
    public var lives = 2
    public var shouldGoToLeft = false
    public var shouldGoToRight = false
    public var stayRight = true
    public var shouldJump = false
    public var onGround = true
    public var gunType = "uzi"
    public var bulletsList = LinkedList<Bullet>()

    private var position = Vector2(x, y)
    private var velocity = Vector2(3f, 10f)
    private val acceleration = Vector2(0f, 0.15f)
    private val gun = Gun(assets, this, gunType, x, y)

    // private val batcher         = SpriteBatch()
    private var playerGoToLeft  = Animation(0.2f, Array<TextureRegion>())
    private var playerGoToRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayLeft  = Animation(0.2f, Array<TextureRegion>())
    private var playerJump      = Animation(0.2f, Array<TextureRegion>())

    init {
        val playerRight1 = TextureRegion(assets.player, 219, 802, width, height)
        val playerRight2 = TextureRegion(assets.player, 47, 802, width, height)
        val playerRight3 = TextureRegion(assets.player, 391, 802, width, height)
        val playerLeft1 = TextureRegion(assets.player, 707, 802, width, height)
        val playerLeft2 = TextureRegion(assets.player, 880, 802, width, height)
        val playerLeft3 = TextureRegion(assets.player, 540, 802, width, height)

        val playersRight = Array<TextureRegion>()
        val playersLeft = Array<TextureRegion>()
        val playersJump = Array<TextureRegion>()

        playersRight.addAll(playerRight1, playerRight2, playerRight3)
        playersLeft.addAll(playerLeft1, playerLeft2, playerLeft3)

        playerStayRight = Animation(0.2f, playerRight2)
        playerStayLeft = Animation(0.2f, playerLeft2)

        playerGoToRight = Animation(0.2f, playersRight)
        playerGoToRight.playMode = Animation.PlayMode.LOOP_PINGPONG

        playerGoToLeft = Animation(0.2f, playersLeft)
        playerGoToLeft.playMode = Animation.PlayMode.LOOP_PINGPONG

        playerJump = Animation(0.2f, playersJump)
        playerJump.playMode = Animation.PlayMode.LOOP_PINGPONG
    }

    /** Updates player position. */
    public fun update() {
        if (position.y <= 80f ) {
            onGround = true
            position.y = 80f
            velocity.y = 10f
            acceleration.y = 0.15f
        }
        else {
            onGround = false
            position.y += velocity.y
            velocity.y -= acceleration.y
        }

        if (shouldGoToRight) {
            // player goes right
            position.x += velocity.x

            // if he should jump
            if (shouldJump) {
                position.y += velocity.y
                shouldJump = false
            }

        } else if (shouldGoToLeft) {
            // player goes left
            position.x -= velocity.x

            // if he should jump
            if (shouldJump) {
                position.y += velocity.y
                shouldJump = false
            }
        }
        else if (shouldJump) {
            position.y = 140f
            acceleration.y = 0.3f
            shouldJump = false
        }

        //if reach right side
        if (position.x > maxMapLength - 1000) position.x = maxMapLength - 1000 // with the offset

        // if reach left side
        if (position.x < 2f) position.x = 2f

        // level completed
        if (position.x == 3096f) {
            if ((assets.activeMusic?.isPlaying ?: false) && assets.musicOn) assets.activeMusic?.stop()
            shouldGoToLeft = false
            shouldGoToRight = false
            assets.activeMusic = assets.mainTheme
            game.screen = LevelsScreen(assets, game)
        }
    }

    /** Draws player. */
    public fun draw(delta: Float, batcher: SpriteBatch) {
        // drawing gun
        gun.update(gunType)
        gun.draw(delta)

        batcher.begin()
        //update()

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
        if (shouldGoToLeft) {
            stayRight = false
            if (shouldJump) onGround = false

            if (!onGround) batcher.draw(playerStayLeft.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            else batcher.draw(playerGoToLeft.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            update()
        }

        // player should go to right
        if (shouldGoToRight) {
            stayRight = true
            if (shouldJump) onGround = false

            if (!onGround) batcher.draw(playerStayRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            else batcher.draw(playerGoToRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            update()
        }

        // player should jump
        if (shouldJump) {
            onGround = false
            batcher.draw(playerStayRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
            update()
        }

        batcher.end()
    }

    /** Returns position of player on Ox axis. */
    public fun getPositionX(): Float {

        return position.x
    }

    /** Returns position of player on Oy axis. */
    public fun getPositionY(): Float {
        return position.y
    }
}