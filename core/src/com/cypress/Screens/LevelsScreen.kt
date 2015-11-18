package com.cypress.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader
import com.cypress.codenameghost.CGGame
import com.cypress.Levels.*
import com.cypress.GameObjects.Player

/** Contains definition of screen of level selection. */
public class LevelsScreen(val game : CGGame) : Screen {

    private val assets  = AssetLoader.getInstance()
    private val batcher = SpriteBatch()
    private val stage   = Stage()

    init {
        // style of available buttons
        val x = arrayOf(0, 6, 102, 201, 299, 396, 494, 592, 691)
        var buttonStyle  = Array(9, { i -> assets.getImageButtonStyle(x[i], 15, x[i], 272, 90, 90, false)})

        // style of locked buttons
        var lockedStyle  = assets.getImageButtonStyle(804, 5, 804, 259, 75, 100, false)

        // initializing buttons
        var back  = ImageButton(assets.getImageButtonStyle(517, 120, 595, 121, 70, 70, false))
        var level = Array(9, { i -> if (i == 1) ImageButton(buttonStyle[i]) else ImageButton(lockedStyle)})


        level[1].addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                if ((assets.activeMusic?.isPlaying ?: false) && assets.musicOn) assets.activeMusic?.stop()

                assets.loadLevel1()
                val player  = Player(Vector2(20f, 950f), 120, 177, 6196f)
                game.screen = Level1(game, player)
                dispose()
            }
        })

        back.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = MainScreen(game)
                dispose()
            }
        })


        for (i in 1..4) level[i].setPosition(55f + (i - 1) * 200, 350f)
        for (i in 5..8) level[i].setPosition(55f + (i - 5) * 200, 150f)
        back.setPosition(10f, 10f)

        for (i in 1..8) stage.addActor(level[i])
        stage.addActor(back)

        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true
    }

    /** Draws screen of level selection. */
    public override fun render(delta : Float) {
        // drawing background color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // drawing picture
        batcher.begin()
        batcher.disableBlending()
        batcher.draw(assets.levels, 0f, 0f, 800f, 480f)
        batcher.end()

        // playing main theme
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

    /** Clears this screen. */
    public override fun dispose() {
        stage.dispose()
        game.dispose()
    }
}