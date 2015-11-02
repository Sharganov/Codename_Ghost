package com.cypress.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.cypress.CGHelpers.AssetLoader
import com.cypress.codenameghost.CGGame

/** Contains definition of screen with logo. */
public class LogoScreen(var game : CGGame) : Screen {

    private var runTime = 0f
    private val batcher = SpriteBatch()
    private val assets  = AssetLoader.getInstance()

    /** Draws screen with logo. */
    public override fun render(delta : Float) {
        runTime += delta

        // drawing background
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        if (runTime < 3f) {
            // drawing logo
            batcher.begin()
            batcher.disableBlending()
            batcher.draw(assets.logo, 0f, 0f, 800f, 480f)
            batcher.end()
        }
        else {
            if (!(assets.mainTheme?.isPlaying ?: false) && assets.musicOn) assets.mainTheme?.play()
            if (runTime > 4.5f) {
                game.screen = MainScreen(game)
                dispose()
            }
        }
    }

    public override fun resize(width : Int, height : Int) {}

    public override fun show() {}

    public override fun hide() {}

    public override fun pause() {}

    public override fun resume() {}

    /** Clears this screen. */
    public override fun dispose() {
        game.dispose()
    }
}