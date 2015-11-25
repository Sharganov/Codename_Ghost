package com.cypress.Levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Array
import com.cypress.CGHelpers.AssetLoader
import com.cypress.CGHelpers.Controls
import com.cypress.GameObjects.Block
import com.cypress.GameObjects.Bullet
import com.cypress.GameObjects.Player
import com.cypress.GameObjects.Warrior
import com.cypress.Screens.LevelsScreen
import com.cypress.codenameghost.CGGame
import java.util.*

/** Contains definition of first level. */
public class Level1(val game : CGGame, val player : Player) : Screen {

    private val assets     = AssetLoader.getInstance()
    private val batcher    = SpriteBatch()
    private var runTime    = 0f
    private val controls   = Controls(game, player)
    private val spruce     = TextureRegion(assets.levelsFP[1][0], 19, 0, 221, 417)
    private val fence      = TextureRegion(assets.levelsFP[1][0], 29, 437, 236, 356)
    private val blockList  = ArrayList<Block>()
    private val removeList = ArrayList<Bullet>()
    private val enemyList  = ArrayList<Warrior>()
    private val enemyToRemove = ArrayList<Warrior>()
    private val camera     = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    private var stage     = Stage()
    private var fan       = Animation(0.02f, Array<TextureRegion>())
    private var isPlaying = false
    private var gameStart = false

    init {
        stage = controls.getStage()
        assets.activeMusic = assets.levelsMusic[1]

        val hSnow = TextureRegion(assets.levelsFP[1][0], 28, 834, 911, 73)
        val vSnow = TextureRegion(assets.levelsFP[1][0], 907, 174, 100, 618)
        val crate = TextureRegion(assets.levelsFP[1][0], 316, 46, 256, 128)
        val roof  = TextureRegion(assets.levelsFP[1][0], 314, 348, 386, 73)
        val roof1 = TextureRegion(assets.levelsFP[1][0], 311, 451, 214, 46)
        val roof2 = TextureRegion(assets.levelsFP[1][0], 313, 529, 416, 24)
        val wall  = TextureRegion(assets.levelsFP[1][0], 754, 201, 25, 225)

        blockList.add(Block(Vector2(86f, 950f), 911f, 73f, hSnow))
        blockList.add(Block(Vector2(86f, 656f), 911f, 73f, hSnow))
        blockList.add(Block(Vector2(997f, 950f), 125f, 73f, hSnow))
        blockList.add(Block(Vector2(520f, 390f), 470f, 73f, hSnow))
        blockList.add(Block(Vector2(1330f, 374f), 880f, 73f, hSnow))

        blockList.add(Block(Vector2(-166f, 68f), 256f, 128f, crate))
        blockList.add(Block(Vector2(080f, 68f), 256f, 128f, crate))
        blockList.add(Block(Vector2(-110f, 190f), 256f, 128f, crate))

        blockList.add(Block(Vector2(664f, 448f), 100f, 550f, vSnow))
        blockList.add(Block(Vector2(897f, 448f), 100f, 550f, vSnow))
        blockList.add(Block(Vector2(1317f, 892f), 100f, 618f, vSnow))
        blockList.add(Block(Vector2(1317f, 413f), 100f, 618f, vSnow))
        blockList.add(Block(Vector2(2129f, 380f), 100f, 370f, vSnow))

        blockList.add(Block(Vector2(3230f, 403f), 386f, 73f, roof))
        blockList.add(Block(Vector2(4376f, 929f), 386f, 73f, roof))
        blockList.add(Block(Vector2(4763f, 616f), 386f, 73f, roof))
        blockList.add(Block(Vector2(4559f, 357f), 214f, 46f, roof1))
        blockList.add(Block(Vector2(5860f, 434f), 416f, 24f, roof2))
        //blockList.add(Block(Vector2(6005f, 357f), 416f, 24f, roof2))
        blockList.add(Block(Vector2(5860f, 434f), 25f, 225f, wall))

        enemyList.add( Warrior(Vector2(4863f, 700f), 115, 180, player))
        enemyList.add( Warrior(Vector2(4163f, 700f), 115, 180, player))
        enemyList.add( Warrior(Vector2(3563f, 700f), 115, 180, player))

        // animation of fan
        val fan1 = TextureRegion(assets.levelsFP[1][0], 582, 12, 141, 132)
        val fan2 = TextureRegion(assets.levelsFP[1][0], 732, 12, 141, 132)
        val fan3 = TextureRegion(assets.levelsFP[1][0], 877, 12, 141, 132)

        val fanArray = Array<TextureRegion>()
        fanArray.addAll(fan1, fan2, fan3)

        fan = Animation(0.02f, fanArray)
        fan.playMode = Animation.PlayMode.LOOP_PINGPONG
    }

    /** Updates level information. */
    private fun update() {
        // if level completed
        if (player.getX() >= player.mapLength) {
            if (assets.musicOn) {
                if ((assets.activeMusic?.isPlaying ?: false)) assets.activeMusic?.stop()
                assets.snow?.stop()
                assets.fan?.stop()
            }
            assets.activeMusic = assets.mainTheme
            game.screen = LevelsScreen(game)
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
                    assets.fan?.loop()
                    isPlaying = true
                }
            }
            else {
                assets.fan?.stop()
                isPlaying = false
            }
        }
    }

    /** Draws level. */
    public override fun render(delta: Float) {
        runTime += delta
        update()
        if (player.onGround) gameStart = true

        // drawing background color
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // setting camera
        if (!gameStart) camera.position.set(120f, 1243f, 0f)
        else camera.position.set(player.getX() + 100, player.getY() + 220, 0f)
        camera.zoom = assets.zoom
        batcher.projectionMatrix = camera.combined
        camera.update()

        // drawing background
        batcher.begin()
        if (player.getX() < 3096f) batcher.draw(assets.levelsBG[1][0], -400f, 0f, 4096f, 2048f)
        else batcher.draw(assets.levelsBG[1][1], 2696f, 0f, 4096f, 2048f)
        batcher.end()

        // drawing bullets
        if (assets.bulletsList.isNotEmpty() && assets.bulletsList[0].distance() > 600)
                assets.bulletsList.removeFirst()

        for (bullet in assets.bulletsList) {
            for (enemy in enemyList) {
                if (bullet.getBounds().overlaps(enemy.getBound())) {
                    if(!bullet.enemyBulllet) {
                        enemy.health -= 25
                        removeList.add(bullet)
                    }
                }
                if(enemy.health <= 0) enemyToRemove.add(enemy)
            }
        }

        for (bullet in assets.bulletsList) {
            if (bullet.getBounds().overlaps(player.getBound())) {
                if (bullet.enemyBulllet) {
                    println("@")
                    player.health -= 10
                    removeList.add(bullet)
                }
            }
        }

        //delete dead enemy
        for(enemy in enemyToRemove) enemyList.remove(enemy)

        //drawing blocks
        for(block in blockList) block.draw(batcher)
        //check collision of bullets with blocks
        for(bullet in assets.bulletsList)
            for(block in blockList)
                if(bullet.getBounds().overlaps(block.getBounds()))
                    removeList.add(bullet)
        //remove bulletes, which hit player or block
        for(bullet in removeList) assets.bulletsList.remove(bullet)
        for (b in assets.bulletsList) b.draw(delta, batcher)

        //drawing player
        player.update()
        player.checkCollision(blockList)
        player.draw(runTime, batcher)

        for(enemy in enemyList) {
            enemy.update(runTime)
            enemy.checkCollision(blockList)
            enemy.draw(runTime, batcher)
        }

        // drawing first plan objects
        batcher.begin()
        batcher.enableBlending()
        batcher.draw(spruce, 350f, 980f, 221f, 417f)
        batcher.draw(fence, 6151f, 77f, 236f, 356f)
        batcher.draw(fan.getKeyFrame(runTime), 5885f, 527f, 141f, 132f)
        batcher.end()

        // drawing stage
        if (gameStart) {
            controls.update()
            stage.act(runTime)
            stage.draw()
        }
    }

    public override fun resize(width: Int, height: Int) {}

    public override fun show() {}

    public override fun hide() {}

    public override fun pause() {}

    public override fun resume() {}

    /** Dispose level 1. */
    public override fun dispose() {
        stage.dispose()
        game.dispose()
    }
}