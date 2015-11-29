package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader
import java.util.*

/** Abstract class for game characters. */
public abstract class Character {
    public abstract var health          : Int
    public abstract var shouldGoToLeft  : Boolean
    public abstract var shouldGoToRight : Boolean
    public abstract var stayRight       : Boolean
    public abstract var shouldJump      : Boolean
    public abstract var onGround        : Boolean
    public abstract var gunType         : String
    public abstract var isDead          : Boolean
    public abstract val isEnemy         : Boolean

    protected abstract val position : Vector2
    protected abstract val velocity : Vector2
    protected abstract val offsetY  : Float
    protected abstract val offsetX  : Float
    protected abstract val width    : Int
    protected abstract val height   : Int
    protected abstract val bounds   : Rectangle
    protected abstract var delta    : Float

    public var death  = Animation(0.2f, Array<TextureRegion>())

    private val assets = AssetLoader.getInstance()

    init {
        // animation of death
        val deathPos   = arrayOf(9, 161, 317, 491, 664)
        val deathArray = Array<TextureRegion>()
        deathArray.addAll(Array(5, {i -> TextureRegion(assets.effects, deathPos[i], 106, 155, 155) }), 0, 4)
        death = Animation(0.2f, deathArray, Animation.PlayMode.LOOP)
    }

    /** Checks collision with objects. If finds, changes position. */
    public fun checkCollision(blockList : ArrayList<Block>) {
        for (block in blockList) {
            var collision = false

            //detecting collision
            if (bounds.overlaps(block.getBounds())) {
                if (position.x + width - offsetX < block.getPosition().x
                        && position.y - delta < block.getPosition().y + block.getHeight()) {
                    position.x = block.getPosition().x - width
                    collision = true
                    bounds.setX(position.x)
                }
                if (getX() > block.getPosition().x + block.getWidth() - offsetX
                        && getY() - delta < block.getPosition().y + block.getHeight()) {
                    position.x = block.getPosition().x + block.getWidth()
                    collision = true
                    bounds.setX(position.x)
                }
                if (!collision) {
                    if (position.y + offsetY >= block.getPosition().y + block.getHeight()) {
                        velocity.y = 0f
                        onGround = true
                        position.y = block.getPosition().y + block.getHeight()
                        bounds.setY(position.y)
                    }
                    else if (position.y < block.getPosition().y + 10) {
                        position.y = block.getPosition().y - height
                        velocity.y = 0f
                        bounds.setY(position.y)
                    }
                }
            }
            bounds.setPosition(position.x, position.y)
        }
    }

    /** Returns position of player on Ox axis. */
    abstract  public fun  getX() : Float

    /** Returns position of player on Oy axis. */
    abstract public fun getY() : Float
}

