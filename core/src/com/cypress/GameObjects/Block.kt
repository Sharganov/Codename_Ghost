package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

public class Block(private val position : Vector2, private val width : Float, private val height : Float, private val texture : TextureRegion ){

    private val bounds = Rectangle(position.x, position.y, width, height)

    public fun draw(batcher : SpriteBatch){
        batcher.begin()
        batcher.draw(texture, position.x, position.y, width, height)
        batcher.end()
    }

    public fun getWidth() : Float = width

    public fun getHeight() : Float = height

    public fun getBounds() : Rectangle = bounds

    public fun getPosition() : Vector2 = position
}