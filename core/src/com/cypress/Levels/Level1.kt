package com.cypress.Levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.cypress.CGHelpers.AssetLoader
import com.cypress.CGHelpers.Controls
import com.cypress.GameObjects.Player
import com.cypress.codenameghost.CGGame

/** Contains definition of first level. */
public class Level1(val assets : AssetLoader, val game : CGGame, val player : Player) : Screen {

    private val batcher = SpriteBatch()
    private var runTime = 0f
    private val controls = Controls(assets, game, player)
    private var stage = Stage()

    private val spruce = TextureRegion(assets.level1FP, 19, 0, 221, 417)
    private val fence = TextureRegion(assets.level1FP, 30, 446, 207, 344)

    private val cam = OrthographicCamera(Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())

    init {
        stage = controls.getStage()
        assets.activeMusic = assets.level1Music
    }

    /** Draws level. */
    public override fun render(delta: Float) {
        runTime += delta

        // drawing background color
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        player.update()

        cam.position.set(player.getPositionX() + 50, player.getPositionY() + 200, 0f)
        cam.zoom = 1.2f
        batcher.projectionMatrix = cam.combined
        cam.update()

        // drawing background
        batcher.begin()
        batcher.draw(assets.level1BG, -400f, 0f, 4096f, 1024f)
        batcher.end()
        //drawing player
        player.draw(runTime, batcher)

        // drawing bullets
        if (player.bulletsList.isNotEmpty() && player.bulletsList[0].distance() > 650)
            player.bulletsList.removeFirst()
        for (b in player.bulletsList) {
            b.update(player.getPositionY())
            b.draw(delta)
        }

        // drawing first plan objects
        batcher.begin()
        batcher.enableBlending()
        batcher.draw(controls.getIcon(), player.getPositionX() - 330f, player.getPositionY() + 400f, 80f, 55f)
        batcher.draw(spruce, 300f, 0f, 221f, 417f)
        batcher.draw(fence, 3090f, 50f, 212f, 344f)

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
    }

    public override fun resize(width: Int, height: Int) {
    }

    public override fun show() {
    }

    public override fun hide() {
    }

    public override fun pause() {
    }

    public override fun resume() {
    }

    /** Dispose level 1. */
    public override fun dispose() {
        stage.dispose()
        game.dispose()
    }
}