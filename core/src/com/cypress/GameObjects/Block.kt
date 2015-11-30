package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import  com.cypress.CGHelpers.*


/** Contains definition of block. */
public class Block(private val position : Vector2, private val width : Float, private val height : Float,
                   private val texture : TextureRegion ){

    private val bounds = Rectangle(position.x, position.y, width, height)
    private val assets = AssetLoader.getInstance()

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

    fun initialize(world: World): Body {

        val pBody: Body
        val def = BodyDef()

        def.type = BodyDef.BodyType.StaticBody
        val x = position.x + width / 2
        val y = position.y + height / 2

        def.position.set(x / assets.ppm, y / assets.ppm)
        def.fixedRotation = true
        pBody = world.createBody(def)

        val shape = PolygonShape()
        shape.setAsBox(width / 2 / assets.ppm, height / 2 / assets.ppm)

        pBody.createFixture(shape, 1.0f)
        shape.dispose()
        return pBody
    }

}