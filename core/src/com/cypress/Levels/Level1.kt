package com.cypress.Levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader
import com.cypress.CGHelpers.Controls
import com.cypress.GameObjects.*
import com.cypress.Screens.ScoreScreen
import com.cypress.codenameghost.CGGame
import java.util.*

/** Contains definition of first level. */
public class Level1(private val game : CGGame) : Screen {
    private val assets         = AssetLoader.getInstance()
    private val batch = SpriteBatch()
    private var runTime        = 0f
    private var world = World(Vector2(0f, -9.8f), false)
    val player  = Player(Vector2(20f, 1500f), 120, 177, 6196f, world)

    init {

        createBox(0, 0, 250, 32, true)
    }
    private val controls       = Controls(game, player, this)
    private val spruce         = TextureRegion(assets.levelsFP[1][0], 19, 0, 221, 417)
    private val fence          = TextureRegion(assets.levelsFP[1][0], 29, 437, 236, 356)
    private val blockList      = ArrayList<Block>()
    private val enemyList      = ArrayList<Warrior>()
    private val itemsList      = ArrayList<Item>()
    private val removedBullets = ArrayList<Bullet>()
    private val deadEnemies    = ArrayList<Warrior>()
    private val removedItems   = ArrayList<Item>()
    private val camera         = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
    private var b2dr = Box2DDebugRenderer()

    private var stage     = Stage()
    private var fan       = Animation(0.02f, Array<TextureRegion>())
    private var isPlaying = false
    private var gameStart = false
    private var counter   = 0

    init {
        stage = controls.getStage()
        assets.activeMusic = assets.levelsMusic[1]

        // adding blocks
        val hSnow = TextureRegion(assets.levelsFP[1][0], 28, 834, 911, 73)
        val vSnow = TextureRegion(assets.levelsFP[1][0], 907, 174, 100, 618)
        val crate = TextureRegion(assets.levelsFP[1][0], 316, 46, 256, 128)
        val roof  = TextureRegion(assets.levelsFP[1][0], 314, 348, 386, 73)
        val roof1 = TextureRegion(assets.levelsFP[1][0], 311, 451, 214, 46)
        val roof2 = TextureRegion(assets.levelsFP[1][0], 313, 529, 416, 24)
        val wall  = TextureRegion(assets.levelsFP[1][0], 754, 201, 25, 225)

        blockList.add(Block(Vector2( 0f, 950f), 911f, 73f, hSnow, world))
      //  createBox(500, 987, 912, 74, true)
        blockList.add(Block(Vector2(  86f, 656f), 911f, 73f, hSnow, world))
        //createBox(541, 692, 911, 73, true)
        blockList.add(Block(Vector2( 997f, 950f), 125f, 73f, hSnow, world))
        //createBox(1060, 986, 124, 72, true)
        blockList.add(Block(Vector2( 520f, 390f), 465f, 73f, hSnow, world))
       // createBox(752, 426, 465, 73, true)
        blockList.add(Block(Vector2(1330f, 374f), 880f, 73f, hSnow, world))
        //createBox(-38, 132, 256, 128, true)
        blockList.add(Block(Vector2(-166f,  68f), 256f, 128f, crate, world))

        //createBox(208, 132, 256, 128, true)
        blockList.add(Block(Vector2(  80f,  68f), 256f, 128f, crate, world))
        //createBox(18, 254, 256, 128, true)
        blockList.add(Block(Vector2(-110f, 190f), 256f, 128f, crate, world))
        //createBox(714, 723, 100, 550, true)
        blockList.add(Block(Vector2( 664f, 448f), 100f, 550f, vSnow, world))
        //createBox(1367, 1201, 100, 618, true)
        blockList.add(Block(Vector2( 897f, 448f), 100f, 550f, vSnow, world))
        //createBox(1367, 722, 100, 618, true)
        blockList.add(Block(Vector2(1317f, 892f), 100f, 618f, vSnow, world))
        //createBox(1367, 722, 100, 618, true)
        blockList.add(Block(Vector2(1317f, 413f), 100f, 618f, vSnow, world))
        //createBox(2179, )
        blockList.add(Block(Vector2(2129f, 380f), 100f, 370f, vSnow, world))
        blockList.add(Block(Vector2(3230f, 403f), 386f, 73f, roof, world))
        blockList.add(Block(Vector2(4376f, 929f), 386f, 73f, roof, world))
        blockList.add(Block(Vector2(4763f, 616f), 386f, 73f, roof, world))
        blockList.add(Block(Vector2(4559f, 357f), 214f, 46f, roof1, world))
        blockList.add(Block(Vector2(5860f, 434f), 416f, 24f, roof2, world))
        //blockList.add(Block(Vector2(6005f, 357f), 416f, 24f, roof2))

        blockList.add(Block(Vector2(5860f, 434f), 25f, 225f, wall, world))

        // adding enemies
        enemyList.add(Warrior(Vector2(  43f,  364f), player))
        enemyList.add(Warrior(Vector2(2120f,  105f), player))
        enemyList.add(Warrior(Vector2(3360f,  485f), player))
        enemyList.add(Warrior(Vector2(4394f, 1013f), player))
        enemyList.add(Warrior(Vector2(4612f,  110f), player))

        // adding items
        itemsList.add(Item(Vector2( 525f,  490f), assets.gunNames[1]))
        itemsList.add(Item(Vector2(4547f, 1012f), "medikit"))
        createBox(3500, 40, 7000, 80, true)//floor
        createBox(-30, 500, 32, 1000, true)//left dowm stone
        //createBox(400, 1010, 1050, 30, true)
        //createBox(950, 500, 30, 500, true)
        createBox(-100, 1000, 32, 1000, true)//left up stone



        // animation of fan
        val fanPos   = arrayOf(582, 732, 877)
        val fanArray = Array<TextureRegion>()
        fanArray.addAll(Array(3, {i -> TextureRegion(assets.levelsFP[1][0], fanPos[i], 12, 141, 132)}), 0, 2)
        fan = Animation(0.02f, fanArray, Animation.PlayMode.LOOP)
    }

    /** Updates level information. */
    private fun update() {
        // if level completed
        if (player.getX() >= player.mapLength) {
            assets.snow?.stop()
            assets.fan?.stop()
            player.data[1] = 2 - player.lives
            if (player.data[3] != 0) player.data[5] =
                    (player.data[4].toFloat() / player.data[3].toFloat() * 100).toInt()
            game.availableLevels[2] = true
            game.screen = ScoreScreen(game, player.data)
        }

        // playing level1 music and sounds
        if (assets.musicOn) {
            if (!(assets.activeMusic?.isPlaying ?: false)) {
                assets.activeMusic?.volume = 0.5f
                assets.activeMusic?.play()
            }
            if ((player.shouldGoToLeft || player.shouldGoToRight) && player.onGround && player.getX() < 3096f)
                assets.snow?.play()
            else assets.snow?.stop()

            if (player.getX() > 5345f && player.getX() < 6197f) {
                if (!isPlaying) {
                    assets.fan?.setVolume(1, 0.5f)
                    assets.fan?.loop()
                    isPlaying = true
                }
            }
            else {
                assets.fan?.stop()
                isPlaying = false
            }
        }

        for (bullet in assets.bulletsList) {
            for (enemy in enemyList) {
                if (bullet.getBounds().overlaps(enemy.getBound())) {
                    if(!bullet.enemyBulllet) {
                        enemy.health -= bullet.damage
                        removedBullets.add(bullet)
                        player.data[4]++
                    }
                }
                if(enemy.health <= 0) {
                    deadEnemies.add(enemy)
                    player.data[0] += 50
                    player.data[2]++
                }
            }
        }

        for (bullet in assets.bulletsList) {
            if (bullet.getBounds().overlaps(player.getBound())) {
                if (bullet.enemyBulllet) {
                    player.health -= bullet.damage
                    if (player.health <= 0) {
                        player.health = 100
                        player.lives -= 1
                        if (player.lives < 0) println("Game Over")
                    }
                    removedBullets.add(bullet)
                }
            }
        }

        // delete dead enemy
        for(enemy in deadEnemies) enemyList.remove(enemy)

        // check collision of bullets with blocks
        for(bullet in assets.bulletsList)
            for(block in blockList)
                if(bullet.getBounds().overlaps(block.getBounds())) removedBullets.add(bullet)

        // remove bullets, which hit player or block
        for(bullet in removedBullets) assets.bulletsList.remove(bullet)
    }
    private var warriorStayRight = TextureRegion(assets.warrior, 25, 11, 115, 180)

    /** Draws level. */
    public override fun render(delta: Float) {
        //if (player.onGround) gameStart = true
        gameStart = true
        runTime += delta
        update()
        counter++

        // drawing background color
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        world.step(1 / 60f, 6, 2)
        // setting camera
        camera.position.set(player.getBody().position.x*assets.ppm + 100, player.getBody().position.y*assets.ppm + 160, 0f)
        camera.zoom = assets.zoom
        batch.projectionMatrix = camera.combined
        camera.update()

        // drawing background
        batch.begin()
        if (player.getX() < 3096f) batch.draw(assets.levelsBG[1][0], -400f, 0f, 4096f, 2048f)
        else batch.draw(assets.levelsBG[1][1], 2696f, 0f, 4096f, 2048f)
        batch.end()

        // drawing bullets
        if (!assets.bulletsList.isEmpty() && assets.bulletsList[0].distance() > 600)
            assets.bulletsList.removeFirst()
        for (b in assets.bulletsList) b.draw(runTime, batch)

        // drawing blocks
        for(block in blockList) block.draw(batch)

        player.onGround = true
        // drawing player
        player.update()
//        player.inputUpdate(delta)
        player.draw(runTime, batch)

        // if player has a minigun
        if (player.shouldShoot && counter % 7 == 0) {
            controls.shoot()
            counter = 0
        }

        // check collision with picked up items
        for(item in itemsList) {
            item.draw(batch)
            if (player.getBound().overlaps(item.getBounds())) {
                item.activity(player)
                removedItems.add(item)
            }
        }

        // remove picked up items
        for(item in removedItems) itemsList.remove(item)

        // drawing enemies
        for(enemy in enemyList) {
            enemy.update(runTime)
            enemy.checkCollision(blockList)
            enemy.draw(runTime, batch)
        }

        // drawing first plan objects
        batch.begin()
        batch.enableBlending()
        batch.draw(spruce, 350f, 980f, 221f, 417f)
        batch.draw(fence, 6151f, 77f, 236f, 356f)
        batch.draw(fan.getKeyFrame(runTime), 5885f, 527f, 141f, 132f)
        batch.end()

        // drawing stage
        if (gameStart) {
            controls.update()
            stage.act(runTime)
            stage.draw()
        }
        //b2dr.render(world, camera.combined.scl(assets.ppm))
    }

    public override fun resize(width : Int, height : Int) {}
    public override fun show() {}
    public override fun hide() {}
    public override fun pause() {}

    /** Restores screen after pause. */
    public override fun resume() {
        game.screen = this
        Gdx.input.inputProcessor = stage
    }

    /** Dispose level 1. */
    public override fun dispose() {
        stage.dispose()
        game.dispose()
    }
    fun createBox(x: Int, y: Int, width: Int, height: Int, isStatic: Boolean): Body {
        val def = BodyDef()

        if (isStatic)
            def.type = BodyDef.BodyType.StaticBody
        else
            def.type = BodyDef.BodyType.DynamicBody

        def.position.set(x / assets.ppm, y / assets.ppm)
        def.fixedRotation = true

        val shape = PolygonShape()
        shape.setAsBox(width / 2 / assets.ppm, height / 2 / assets.ppm)

        val fixtureDef = FixtureDef()
        fixtureDef.shape = shape
        fixtureDef.density = 1.0f
        fixtureDef.filter.categoryBits = assets.BIT_WALL // who you are
        fixtureDef.filter.maskBits = assets.BIT_PLAYER
        fixtureDef.filter.groupIndex = 0
        return world.createBody(def).createFixture(fixtureDef).body
    }
}