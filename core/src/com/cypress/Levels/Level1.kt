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

public class Level1(assets : AssetLoader, var game : CGGame) : Screen {

    private val batcher = SpriteBatch()
    private val assets  = assets
    private var stage   = Stage()
    private val camera  = OrthographicCamera()
    private val player  = Player(assets, 2f, 96f, 120, 177)

    private var runTime      = 0f
    private val screenWidth  = Gdx.graphics.width;
    private val screenHeight = Gdx.graphics.height;
    private val gameWidth    = 400;
    private val gameHeight   = screenHeight / (screenWidth / gameWidth);

    init {
        camera.setToOrtho(false, 800f, 480f)
        batcher.projectionMatrix = camera.combined

        stage = Controls(assets, player, game).getStage()
    }

    public override fun render(delta : Float) {
        runTime += delta

        // drawing background color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // drawing background
        batcher.begin()
        batcher.disableBlending()
        batcher.draw(assets.level1BG, player.getX(), player.getY() - 96f, 5000f, 480f)
        batcher.end()

        // drawing player
        player.draw(runTime)

        // drawing first plan objects
        batcher.begin()
        batcher.enableBlending()
        batcher.draw(TextureRegion(assets.level1FP, 19, 0, 221, 417), 300f + player.getX(), player.getY() - 96f, 221f, 417f)
        batcher.end()

        // playing level1 music
        if (!(assets.level1Music?.isPlaying ?: false)&& assets.musicOn) assets.level1Music?.play()

        // drawing stage
        stage.act(delta)
        stage.draw()
    }

    public override fun resize(width : Int, height : Int) {}

    public override fun show() {}

    public override fun hide() {}

    public override fun pause() {}

    public override fun resume() {}

    public override fun dispose() {
        stage.dispose()
        game.dispose()
    }
}