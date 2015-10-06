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
import com.cypress.codenameghost.CGGame

public class SettingsScreen(assets : AssetLoader, var game : CGGame) : Screen {

    private val batcher = SpriteBatch()
    private val assets  = assets
    private val stage   = Stage()

    init {
        // style of big text buttons
        val font            = assets.generateFont("Calibri.ttf", 32, Color.GREEN)
        val textButtonStyle = assets.getTextButtonStyle(314, 128, 41, 128, 191, 127, font)

        // style of back button
        var backStyle  = assets.getImageButtonStyle(517, 120, 595, 121, 70, 70)

        // style of back button
        var soundsStyle     = assets.getImageButtonStyle(667, 156, 767, 156, 100, 100)


        // initializing table
        var table = Table()
        table.setFillParent(true)

        // initializing buttons
        var sounds   = ImageButton(soundsStyle)
        var language =
                when (assets.language) {
                    "english" -> TextButton("Language:\n" + assets.language, textButtonStyle)
                    else      -> TextButton("язык:\n" + assets.language, textButtonStyle)
                }
        var about    =
                when (assets.language) {
                    "english" -> TextButton("About", textButtonStyle)
                    else      -> TextButton("ќб игре", textButtonStyle)
                }
        var back     = ImageButton(backStyle)


        language.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                when (assets.language) {
                    "english" -> {
                        assets.language = "русский"
                        language.setText("язык:\n" + assets.language)
                        about.setText("ќб игре")
                    }
                    else      -> {
                        assets.language = "english"
                        language.setText("Language:\n" + assets.language)
                        about.setText("About")
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
                    assets.mainTheme?.stop()
                }
                else {
                    assets.musicOn = true
                    assets.mainTheme?.play()
                }
            }
        })

        about.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = AboutScreen(assets, game)
                dispose()
            }
        })

        back.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = MainScreen(assets, game)
                dispose()
            }
        })


        table.add(sounds)
        table.row()
        table.add(language)
        table.row()
        table.add(about)
        table.setPosition(-230f, 30f)

        back.setPosition(10f, 10f)

        stage.addActor(table)
        stage.addActor(back)

        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true
    }

    public override fun render(delta : Float) {
        // drawing background color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // drawing picture
        batcher.begin()
        batcher.disableBlending()
        batcher.draw(assets.settings, 0f, 0f, 800f, 480f)
        batcher.end()

        // playing main theme
        if (!(assets.mainTheme?.isPlaying ?: false) && assets.musicOn) assets.mainTheme?.play()

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