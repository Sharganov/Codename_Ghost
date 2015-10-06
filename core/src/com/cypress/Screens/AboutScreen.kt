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

public class AboutScreen(assets : AssetLoader, var game : CGGame) : Screen {

    private val batcher = SpriteBatch()
    private val assets  = assets
    private val stage   = Stage()

    init {
        // style of labels
        var titleStyle = Label.LabelStyle()
        titleStyle.font = assets.generateFont("American_TextC.ttf", 100, Color.valueOf("36ba29"))

        var textStyle = Label.LabelStyle()
        textStyle.font = assets.generateFont("Academia Roman.ttf", 25, Color.WHITE)

        val text =
                when (assets.language) {
                    "english" -> com.cypress.Locale.en.info()
                    else      -> com.cypress.Locale.ru.info()
                }

        // initializing labels
        var title = Label("Codename: Ghost", titleStyle)
        var info  = Label(text, textStyle)
        val textPanel = ScrollPane(info)

        // initializing button
        var back = ImageButton(assets.getImageButtonStyle(517, 120, 595, 121, 70, 70))


        back.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                game.screen = SettingsScreen(assets, game)
                dispose()
            }
        })


        title.setPosition(100f, 350f)

        textPanel.setPosition(10f, 90f)
        textPanel.sizeBy(800f, 120f)
        textPanel.scrollTo(10f, 120f, 100f, 100f)
        textPanel.setForceScroll(false, true)

        back.setPosition(10f, 10f)

        stage.addActor(title)
        stage.addActor(textPanel)
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
        batcher.draw(assets.about, 0f, 0f, 800f, 480f)
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