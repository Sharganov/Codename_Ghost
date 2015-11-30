/*package com.cypress.GameObjects
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.cypress.CGHelpers.AssetLoader
    fun createBox(x: Int, y: Int, width: Int, height: Int, isStatic: Boolean): Body {
        val pBody: Body
        val def = BodyDef()

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody
        else
            def.type = BodyDef.BodyType.DynamicBody

        def.position.set(x / PPM, y / PPM)
        def.fixedRotation = true
        pBody = world.createBody(def)

        val shape = PolygonShape()
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM)

        pBody.createFixture(shape, 1.0f)
        shape.dispose()
        return pBody
    }
}

public class Game1r(private val world: World, private var x : Int, private var  y : Int, private val width: Int,
                   private val height: Int ) {


    private val batch = SpriteBatch()
    private var warriorStayRight = TextureRegion(assets.warrior, 25, 11, 115, 180)
    val vSnow = TextureRegion(assets.levelsFP[1][0], 907, 174, 100, 618)

    private var camera = OrthographicCamera()
    private val w = Gdx.graphics.width.toFloat()
    private val h = Gdx.graphics.height.toFloat()

    private var b2dr = Box2DDebugRenderer()
    private var world = World(Vector2(0f, -9.8f), false)
    val player  = Player(Vector2(20f, 1500f), 120, 177, 6196f)

    init {
        player.initialise(world)
        createBox(0, 0, 64, 32, true)
    }

    override fun render(delta: Float) {
        update(Gdx.graphics.deltaTime)

        // Render
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch.begin()
        batch.draw(vSnow, 0f,0f, 100f, 100f)
        batch.draw(warriorStayRight, player.getBody().position.x*PPM,  player.getBody().getPosition().y*PPM, 32f, 32f)
        batch.end()
        player.draw(Gdx.graphics.deltaTime, batch)
        b2dr.render(world, camera.combined.scl(PPM))

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit()
    }

    override fun resize(width: Int, height: Int) {
        camera.setToOrtho(false, (width / 2).toFloat(), (height / 2).toFloat())
    }

    override fun dispose() {
        b2dr.dispose()
    }

    fun update(delta: Float) {
        world.step(1 / 60f, 6, 2)
        player.inputUpdate(delta)
        cameraUpdate(delta)
        batch.projectionMatrix = camera.combined
    }


    fun cameraUpdate(delta: Float) {
        val position = camera.position
        position.x = player.getBody().getPosition().x * PPM
        position.y = player.getBody().getPosition().y * PPM
        camera.position.set(position)

        camera.update()
    }

    fun createBox(x: Int, y: Int, width: Int, height: Int, isStatic: Boolean): Body {
        val pBody: Body
        val def = BodyDef()

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody
        else
            def.type = BodyDef.BodyType.DynamicBody

        def.position.set(x / PPM, y / PPM)
        def.fixedRotation = true
        pBody = world.createBody(def)

        val shape = PolygonShape()
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM)

        pBody.createFixture(shape, 1.0f)
        shape.dispose()
        return pBody
    }
}