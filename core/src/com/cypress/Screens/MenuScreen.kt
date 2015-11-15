package com.cypress.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.cypress.CGHelpers.AssetLoader
import com.cypress.GameObjects.Player
import com.cypress.codenameghost.CGGame
import com.cypress.Levels.Level1

/** Contains definition of pause menu. */
public class MenuScreen(val game : CGGame, val player : Player) : Screen {
    val assets = AssetLoader.getInstance()
    private val batcher = SpriteBatch()
    private val stage   = Stage()

    init {
        // style of big text buttons
        val font            = assets.generateFont("Calibri.ttf", 32, Color.GREEN)
        val textButtonStyle = assets.getTextButtonStyle(314, 128, 41, 128, 191, 127, font)

        // initializing table
        var table = Table()
        table.setFillParent(true)

        // initializing buttons
        var sounds =
                when (assets.musicOn) {
                    true  -> ImageButton(assets.getImageButtonStyle(667, 156, 767, 156, 100, 100, true))
                    false -> ImageButton(assets.getImageButtonStyle(767, 156, 667, 156, 100, 100, true))
                }
        var language =
                when (assets.language) {
                    "english" -> TextButton("Language:\n" + assets.language, textButtonStyle)
                    else      -> TextButton("Язык:\n" + assets.language, textButtonStyle)
                }
        var backToMain =
                when (assets.language) {
                    "english" -> TextButton("Back to \nmain menu", textButtonStyle)
                    else      -> TextButton("В главное \nменю", textButtonStyle)
                }
        var back = ImageButton(assets.getImageButtonStyle(517, 120, 595, 121, 70, 70, false))


        language.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                when (assets.language) {
                    "english" -> {
                        assets.language = "русский"
                        language.setText("Язык:\n" + assets.language)
                        backToMain.setText("В главное \nменю")
                    }
                    else      -> {
                        assets.language = "english"
                        language.setText("Language:\n" + assets.language)
                        backToMain.setText("Back to \nmain menu")
                    }
                }
            }
        })

        sounds.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                if (assets.musicOn) {
                    assets.musicOn = false
                    assets.activeMusic?.stop()
                }
                else {
                    assets.musicOn = true
                    assets.activeMusic?.play()
                }
            }
        })

        backToMain.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                assets.activeMusic?.stop()
                game.screen = MainScreen(game)
                dispose()
            }
        })

        back.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = Level1(game, player)
                dispose()
            }
        })


        table.add(sounds)
        table.row()
        table.add(language)
        table.row()
        table.add(backToMain)
        table.setPosition(-230f, 30f)
        back.setPosition(10f, 10f)

        stage.addActor(table)
        stage.addActor(back)

        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true
    }

    /** Draws pause menu. */
    public override fun render(delta : Float) {
        // drawing background color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // drawing picture
        batcher.begin()
        batcher.disableBlending()
        batcher.draw(assets.settings, 0f, 0f, 800f, 480f)
        batcher.end()

        // playing music
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