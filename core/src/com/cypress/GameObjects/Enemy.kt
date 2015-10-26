package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader
import java.util.*

class Enemy(private val assets : AssetLoader,  val position: Vector2, private val width : Int,
            private val height : Int, private val batcher : SpriteBatch, private val player : Player) {

    public var health = 100
    public var lives = 2
    public var shouldGoToLeft = false
    public var shouldGoToRight = false
    public var stayRight = true
    public var shouldJump = false
    public var onGround = true
    public var gunType = "uzi"
    public var bulletsList = LinkedList<Bullet>()


    private var velocity = Vector2(3f, 10f)
    private val acceleration = Vector2(0f, 0.15f)

    private var playerGoToLeft = Animation(0.2f, Array<TextureRegion>())
    private var playerGoToRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayRight = Animation(0.2f, Array<TextureRegion>())
    private var playerStayLeft = Animation(0.2f, Array<TextureRegion>())
    private var playerJump = Animation(0.2f, Array<TextureRegion>())

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
            position.x += 1
        else position.x -= 1
    }

    /** Draws player. */
    public fun draw(delta: Float) {

        batcher.begin()
        update()
        batcher.draw(playerStayRight.getKeyFrame(delta), position.x, position.y, width.toFloat(), height.toFloat())
        batcher.end()
    }

    /** Returns position of player on Ox axis. */
        return position.x

    /** Returns position of player on Oy axis. */
}
