package com.cypress.GameObjects

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.PolygonShape
import com.badlogic.gdx.physics.box2d.World
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of player. */
public class Player(public override val position : Vector2, protected override val width : Int,
                    protected override val height : Int, public val mapLength : Float) : Character() {

    public override val isEnemy = false
    public override val bounds  = Rectangle(0f, 0f, width.toFloat(), height.toFloat())
    public override val offsetY = 18f
    public override val offsetX = 10f

    protected override val velocity = Vector2(4f, 12f)
    private var body : Body? = null
    private val assets          = AssetLoader.getInstance()
    private val acceleration    = Vector2(0f, 0.2f)
    private val gun             = Gun(this)
    private val playerStayRight = TextureRegion(assets.player, 47, 802, width, height)
    private val playerStayLeft  = TextureRegion(assets.player, 880, 802, width, height)

    public override var health          = 100
    public override var delta           = 0f
    public override var shouldJump      = false
    public override var shouldGoToLeft  = false
    public override var shouldGoToRight = false
    public override var stayRight       = true
    public override var onGround        = false
    public override var gunType         = assets.gunNames[0]

    public val availableGuns = Array(7, { true }) // TODO: before release change to false
    public val ammoCounter   = Array(7, { Pair(0, 0) })
    public val data          = arrayOf(0, 0, 0, 0, 0, 0)
    public var lives       = 2
    public var shouldShoot = false

    private var playerGoesLeft  = Animation(0.2f, Array<TextureRegion>())
    private var playerGoesRight = Animation(0.2f, Array<TextureRegion>())

    init {
        // setting animation
        val rightPos = arrayOf(219, 47, 391, 219)
        val leftPos  = arrayOf(707, 880, 540, 707)

        val playersRight = Array<TextureRegion>()
        val playersLeft  = Array<TextureRegion>()

        playersRight.addAll(Array(4, {i -> TextureRegion(assets.player, rightPos[i], 802, width, height)}), 0, 3)
        playersLeft.addAll(Array(4, {i -> TextureRegion(assets.player, leftPos[i], 802, width, height)}), 0, 3)

        playerGoesRight = Animation(0.2f, playersRight, Animation.PlayMode.LOOP_PINGPONG)
        playerGoesLeft  = Animation(0.2f, playersLeft, Animation.PlayMode.LOOP_PINGPONG)

        availableGuns[0] = true
        ammoCounter[0] = Pair(assets.maxCapacity[0], 30)
        for (i in 1 .. ammoCounter.size - 1) ammoCounter[i] = Pair(assets.maxCapacity[i], 100)
    }


    /** Updates position of player. */
    public fun update(){

        var horizontalForce = 0

        if (shouldGoToRight)  horizontalForce += 2;
        if (shouldGoToLeft)   horizontalForce -= 2;

        if (shouldJump) {
            getBody().applyForceToCenter(0f, 9500f, false)
            shouldJump = false
            onGround = false
        }
        getBody().setLinearVelocity((horizontalForce*5).toFloat(), getBody().getLinearVelocity().y)

        /*//if player reach right side
        if (position.x > mapLength) position.x = mapLength

        // if player reach left side
        if (position.x < 2f) position.x = 2f*/
    }

fun inputUpdate(delta: Float) {
        var horizontalForce = 0

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1
            shouldGoToLeft = true
        }else shouldGoToLeft = false

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1
            shouldGoToRight = true
        } else shouldGoToRight = false

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            getBody().applyForceToCenter(0f, 300f*assets.ppm, false)
        }

        getBody().setLinearVelocity((horizontalForce).toFloat(), getBody().getLinearVelocity().y)
    }

    /** Draws player. */
    public fun draw(delta: Float, batch: SpriteBatch) {
        // drawing gun
        var current = TextureRegion()
        gun.update()
        gun.draw(batch)
        println("!")


        // player should stay still ...
        if (!shouldGoToLeft && !shouldGoToRight && !shouldJump) {
            when (stayRight) {
                // ... turning to the right side
                true -> current = playerStayRight
                // ... turning to the left side
                false ->
                    current = playerStayLeft
            }
        }

        // player should go to left
        else if (shouldGoToLeft) {
            stayRight = false
            if (shouldJump) onGround = false

            if (!onGround)
                current = playerStayLeft
            else
               current = playerGoesLeft.getKeyFrame(delta)
        }

        // player should go to right
        else if (shouldGoToRight) {
            stayRight = true
            if (shouldJump) onGround = false

            if (!onGround)
            {
                current = playerStayRight
            }
            else
            {
                println("@")
                current = playerGoesRight.getKeyFrame(delta)
            }
        }

        // player should jump
        else if (shouldJump) {
            onGround = false
            current = playerStayRight
        }
        batch.begin()
        batch.draw(current, getBody().position.x*assets.ppm - playerStayRight.regionWidth/2,
                getBody().getPosition().y*assets.ppm - playerStayRight.regionHeight/2, width.toFloat(), height.toFloat())

        batch.end()
    }

    fun initialise(world: World) {
        val pBody: Body
        val def = BodyDef()
        def.type = BodyDef.BodyType.DynamicBody

        def.position.set(position.x / assets.ppm, position.y / assets.ppm)
        def.fixedRotation = true
        pBody = world.createBody(def)

        val shape = PolygonShape()
        shape.setAsBox(width / 2 / assets.ppm, height / 2 / assets.ppm)

        pBody.createFixture(shape, 1.0f)
        shape.dispose()
        body = pBody
    }

    /** Returns position of player on Ox axis. */
    public override fun getX() : Float = body?.position?.x ?: 0f * assets.ppm

    /** Returns position of player on Oy axis. */
    public override fun getY() : Float = body?.position?.y ?: 0f * assets.ppm

    /** Returns bounds of player. */
    public fun getBound() : Rectangle = bounds

    public fun getBody() : Body = body ?: throw Exception("")

}