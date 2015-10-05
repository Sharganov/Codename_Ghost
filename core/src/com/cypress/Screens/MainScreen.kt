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

public class MainScreen(assets : AssetLoader, var game : CGGame) : Screen {

    private val batcher = SpriteBatch()
    private val assets  = assets
    private val stage   = Stage()

    init {
        // skin for buttons
        val skin = Skin()
        skin.add("big_button-up", TextureRegion(assets.buttons, 314, 128, 191, 127))
        skin.add("big_button-down", TextureRegion(assets.buttons, 41, 128, 191, 127))
        skin.add("settings-up", TextureRegion(assets.buttons, 517, 194, 70, 70))
        skin.add("settings-down", TextureRegion(assets.buttons, 596, 194, 70, 70))

        // style of big text buttons
        var textButtonStyle  = TextButton.TextButtonStyle()
        textButtonStyle.font = assets.generateFont("Calibri.ttf", 60, Color.GREEN)
        textButtonStyle.up   = skin.getDrawable("big_button-up")
        textButtonStyle.down = skin.getDrawable("big_button-down")

        // style of settings button
        var settingsStyle  = ImageButton.ImageButtonStyle()
        settingsStyle.up   = skin.getDrawable("settings-up")
        settingsStyle.down = skin.getDrawable("settings-down")

        // style of labels
        var titleStyle = Label.LabelStyle()
        titleStyle.font = assets.generateFont("American_TextC.ttf", 100, Color.valueOf("36ba29"))


        // initializing table
        var table = Table()
        table.setFillParent(true)

        // initializing buttons
        var play =
                when (assets.language) {
                    "english" -> TextButton("Play", textButtonStyle)
                    else      -> TextButton("Играть", textButtonStyle)
                }
        var exit =
                when (assets.language) {
                    "english" -> TextButton("Exit", textButtonStyle)
                    else      -> TextButton("Выход", textButtonStyle)
                }
        var settings = ImageButton(settingsStyle)

        // initializing labels
        var title = Label("Codename: Ghost", titleStyle)

        play.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = LevelsScreen(assets, game)
                dispose()
            }
        })

        exit.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                //val skin = Skin()
                //skin.add("background", assets.dialogBackground)
                //val dialogStyle = Window.WindowStyle()
                //dialogStyle.titleFont = assets.settingsScreenFont
                //dialogStyle.background = skin.getDrawable("background")
                //
                //val dialog = Dialog("Are you really want to exit?", dialogStyle)
                //
                //dialog.setPosition(50f, 20f)
                //stage.addActor(dialog)
                game.dispose()
                Gdx.app.exit()
            }
        })

        settings.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = SettingsScreen(assets, game)
                dispose()
            }
        })

        table.add(play)
        table.row()
        table.add(exit)
        table.setPosition(170f, -40f)

        settings.setPosition(735f, 0f)

        title.setPosition(100f, 350f)

        stage.addActor(table)
        stage.addActor(settings)
        stage.addActor(title)

        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true
    }

    public override fun render(delta : Float) {
        // drawing background color
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

        // playing main theme
        if (!(assets.mainTheme?.isPlaying ?: false) && assets.musicOn) assets.mainTheme?.play()

        // drawing picture
        batcher.begin()
        batcher.disableBlending()
        batcher.draw(assets.main, 0f, 0f, 800f, 480f)
        batcher.end()

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