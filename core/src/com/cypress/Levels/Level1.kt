package com.cypress.Levels

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
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
    private var stage   = Stage()

    private val spruce = TextureRegion(assets.level1FP, 19, 0, 221, 417)
    private val fence  = TextureRegion(assets.level1FP, 30, 446, 253, 359)

    //private val screenWidth  = Gdx.graphics.width;
    //private val screenHeight = Gdx.graphics.height;
    //private val gameWidth    = 400;
    //private val gameHeight   = screenHeight / (screenWidth / gameWidth);

    init {
        stage = Controls(assets, game, player).getStage()
        assets.activeMusic = assets.level1Music
    }

    /** Draws level. */
    public override fun render(delta : Float) {
        runTime += delta

        // drawing background color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // drawing background
        batcher.begin()
        batcher.disableBlending()
        batcher.draw(assets.level1BG, player.getX(), 0f, 5000f, 730f)
        batcher.end()

        // drawing player
        player.draw(runTime)

        // drawing first plan objects
        batcher.begin()
        batcher.enableBlending()
        batcher.draw(spruce, 300f + player.getX(), player.getY() - 80f, 221f, 417f)
        batcher.draw(fence, 4745f + player.getX(), 120f - player.getY(), 253f, 359f)
        batcher.end()

        // playing level1 music
        if (!(assets.activeMusic?.isPlaying ?: false) && assets.musicOn) assets.activeMusic?.play()

        // drawing stage
        stage.act(delta)
        stage.draw()
    }

    public override fun resize(width : Int, height : Int) {}

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