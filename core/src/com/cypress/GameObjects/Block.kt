package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

/** Contains definition of block. */
public class Block(private val position : Vector2, private val width : Float, private val height : Float,
                   private val texture : TextureRegion ){

    private val bounds = Rectangle(position.x, position.y, width, height)

    /** Draws block. */
    public fun draw(batcher : SpriteBatch){
        batcher.begin()
        batcher.draw(texture, position.x, position.y, width, height)
        batcher.end()
    }

    /** Returns width. */
    public fun getWidth() = width

    /** Returns height. */
    public fun getHeight() = height

    /** Returns bounds of block. */
    public fun getBounds() = bounds

    /** Returns position of block. */
    public fun getPosition() = position
}