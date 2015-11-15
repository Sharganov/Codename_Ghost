package com.cypress.Levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.cypress.CGHelpers.AssetLoader
import com.cypress.CGHelpers.Controls
import com.cypress.GameObjects.Block
import com.cypress.GameObjects.Enemy
import com.cypress.GameObjects.Player
import com.cypress.Screens.LevelsScreen
import com.cypress.codenameghost.CGGame
import java.util.*

/** Contains definition of first level. */
public class Level1(val game : CGGame, val player : Player) : Screen {

    private val assets    = AssetLoader.getInstance()
    private val batcher   = SpriteBatch()
    private var runTime   = 0f
    private val controls  = Controls(game, player)
    private val spruce    = TextureRegion(assets.levelsFP[1][0], 19, 0, 221, 417)
    private val fence     = TextureRegion(assets.levelsFP[1][0], 30, 446, 207, 344)
    private val roof      = TextureRegion(assets.levelsFP[1][0], 315, 382, 128, 128)
    private val crate     = TextureRegion(assets.levelsFP[1][0], 316, 46, 256, 128)
    private val blockList = ArrayList<Block>()
    private val cam       = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    private var stage = Stage()

    init {
        stage = controls.getStage()
        assets.activeMusic = assets.level1Music

        for (i in 0 .. 2)
            blockList.add(Block(Vector2(857f + i * 128, 655f), 128f, 85f, roof))
        for (i in 0 .. 2)
            blockList.add(Block(Vector2(1274f + i * 128, 358f), 128f, 85f, roof))
        for (i in 0 .. 1)
            blockList.add(Block(Vector2(1861f + i * 128, 373f), 128f, 85f, roof))

        blockList.add(Block(Vector2(626f, 51f), 256f, 128f, crate))
    }

    /** Draws level. */
    public override fun render(delta: Float) {
        runTime += delta

        // drawing background color
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // setting camera
        cam.position.set(player.getX() + 100, player.getY() + 220, 0f)
        cam.zoom = assets.zoom
        batcher.projectionMatrix = cam.combined
        cam.update()

        // drawing background
        batcher.begin()
        if (player.getX() < 3096f) batcher.draw(assets.levelsBG[1][0], -400f, 0f, 4096f, 1024f)
        else batcher.draw(assets.levelsBG[1][1], 2696f, 0f, 4096f, 2048f)
        batcher.end()

        // drawing bullets
        if (player.bulletsList.isNotEmpty() && player.bulletsList[0].distance() > 600)
            player.bulletsList.removeFirst()
        for (b in player.bulletsList)
            b.draw(delta, batcher)

        //drawing player
        player.update()
        player.draw(runTime, batcher)

        for (block in blockList) {
            block.draw(batcher)
            var collision = false

            // detecting collision
            if (player.getBounds().overlaps(block.getBounds())) {
                if (player.getX() + player.getWidth() - 10f < block.getPosition().x) {
                    player.setX(block.getPosition().x - player.getWidth())
                    collision = true
                }
                if (player.getX() > block.getPosition().x + block.getWidth() - 10) {
                    player.setX(block.getPosition().x + block.getWidth())
                    collision = true
                }

                if (!collision) {
                    if (player.getY() > block.getPosition().y) {
                        player.setVelocity(0f)
                        player.onGround = true
                        player.setY(block.getPosition().y + block.getHeight())
                    }
                    else {
                        player.setY(block.getPosition().y - player.getHeight() - 5)
                        player.setVelocity(0f)
                    }
                }
            }
        }

        // drawing first plan objects
        batcher.begin()
        batcher.enableBlending()
        batcher.draw(spruce, 300f, 0f, 221f, 417f)
        batcher.draw(fence, 3085f, 52f, 212f, 344f)
        batcher.end()

        // playing level1 music and sounds
        if (!(assets.activeMusic?.isPlaying ?: false) && assets.musicOn) assets.activeMusic?.play()
        if ((player.shouldGoToLeft || player.shouldGoToRight) && player.onGround && assets.musicOn)
            assets.level1Snow?.play()
        else
            assets.level1Snow?.stop()

        // drawing stage
        stage.act(delta)
        stage.draw()

        // if level completed
        if (player.getX() >= player.maxMapLength) {
            if ((assets.activeMusic?.isPlaying ?: false) && assets.musicOn) assets.activeMusic?.stop()
            if ((assets.level1Snow?.isPlaying ?: false) && assets.musicOn) assets.level1Snow?.stop()
            assets.activeMusic = assets.mainTheme
            game.screen = LevelsScreen(game)
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