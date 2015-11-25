package com.cypress.GameObjects

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import java.util.*

/** Abstract class for game characters. */

public abstract class Character {
    abstract public var health: Int
    abstract public var shouldGoToLeft: Boolean
    abstract public var shouldGoToRight: Boolean
    abstract public var stayRight: Boolean
    abstract public var shouldJump: Boolean
    abstract public var onGround: Boolean
    abstract public var gunType: String

    abstract protected val position: Vector2
    abstract protected val velocity: Vector2
    abstract protected val offsetY: Float
    abstract protected val offsetX : Float
    abstract protected val width: Int
    abstract protected val height: Int
    abstract protected val bounds: Rectangle
    abstract protected var delta : Float

    abstract public val isEnemy  : Boolean
    /**Check collision with objects. If find, change position*/
    open public fun checkCollision(blockList: ArrayList<Block>) {
        for (block in blockList) {
            // drawing first plan object
            var collision = false
            //detecting collision

            if (bounds.overlaps(block.getBounds())) {

                if (position.x + width - offsetX < block.getPosition().x && position.y - delta < block.getPosition().y + block.getHeight()) {
                    position.x = block.getPosition().x - width
                    collision = true
                    bounds.setX(position.x)
                }
                if (getX() > block.getPosition().x + block.getWidth() - offsetX && getY() - delta < block.getPosition().y + block.getHeight()) {
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

                    } else if (position.y < block.getPosition().y + 10) {
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
    abstract public fun getY() : Float //= position.y

}

