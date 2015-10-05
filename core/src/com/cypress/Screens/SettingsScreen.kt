package com.cypress.Screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
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
        // skin for buttons
        val skin = Skin()
        skin.add("big_button-up", TextureRegion(assets.buttons, 314, 128, 191, 127))
        skin.add("big_button-down", TextureRegion(assets.buttons, 41, 128, 191, 127))
        skin.add("settings-up", TextureRegion(assets.buttons, 521, 196, 60, 60))
        skin.add("settings-down", TextureRegion(assets.buttons, 600, 196, 60, 60))
        skin.add("back-up", TextureRegion(assets.buttons, 517, 120, 70, 70))
        skin.add("back-down", TextureRegion(assets.buttons, 595, 121, 70, 70))
        skin.add("sounds-up", TextureRegion(assets.buttons, 667, 156, 100, 100))
        skin.add("sounds-checked", TextureRegion(assets.buttons, 767, 156, 100, 100))

        // style of big text buttons
        var textButtonStyle  = TextButton.TextButtonStyle()
        textButtonStyle.font = assets.generateFont("Calibri.ttf", 32, Color.GREEN)
        textButtonStyle.up   = skin.getDrawable("big_button-up")
        textButtonStyle.down = skin.getDrawable("big_button-down")

        // style of back button
        var backStyle  = ImageButton.ImageButtonStyle()
        backStyle.up   = skin.getDrawable("back-up")
        backStyle.down = skin.getDrawable("back-down")

        // style of back button
        var soundsStyle     = ImageButton.ImageButtonStyle()
        soundsStyle.up      = skin.getDrawable("sounds-up")
        soundsStyle.checked = skin.getDrawable("sounds-checked")


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