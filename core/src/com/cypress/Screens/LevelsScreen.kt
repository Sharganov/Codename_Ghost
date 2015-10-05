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
import com.cypress.Levels.Level1

public class LevelsScreen(assets : AssetLoader, var game : CGGame) : Screen {

    private val batcher = SpriteBatch()
    private val assets  = assets
    private val stage   = Stage()

    init {
        // skin for buttons
        val skin = Skin()
        skin.add("back-up", TextureRegion(assets.buttons, 517, 120, 70, 70))
        skin.add("back-down", TextureRegion(assets.buttons, 595, 121, 70, 70))
        skin.add("locked-up", TextureRegion(assets.buttons, 804, 5, 75, 100))
        skin.add("locked-down", TextureRegion(assets.buttons, 804, 259, 75, 100))

        val x = arrayOf(6, 102, 201, 299, 396, 494, 592, 691)
        for (i in 1..8) {
            skin.add(i.toString() + "-up", TextureRegion(assets.buttons, x[i - 1], 15, 90, 90))
            skin.add(i.toString() + "-down", TextureRegion(assets.buttons, x[i - 1], 272, 90, 90))
        }


        // style of back button
        var backStyle  = ImageButton.ImageButtonStyle()
        backStyle.up   = skin.getDrawable("back-up")
        backStyle.down = skin.getDrawable("back-down")

        // style of available buttons
        var buttonStyle  = Array(9, { i -> ImageButton.ImageButtonStyle()})
        for (i in 1..8) {
            buttonStyle[i].up   = skin.getDrawable(i.toString() + "-up")
            buttonStyle[i].down = skin.getDrawable(i.toString() + "-down")
        }

        // style of locked buttons
        var lockedStyle  = ImageButton.ImageButtonStyle()
        lockedStyle.up   = skin.getDrawable("locked-up")
        lockedStyle.down = skin.getDrawable("locked-down")


        // initializing buttons
        var back  = ImageButton(backStyle)
        var level = Array(9, { i -> if (i == 1) ImageButton(buttonStyle[i]) else ImageButton(lockedStyle)})


        level[1].addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                assets.mainTheme?.stop()
                assets.loadLevel1()
                game.screen = Level1(assets, game)
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

        for (i in 1..4) level[i].setPosition(55f + (i - 1) * 200, 350f)
        for (i in 5..8) level[i].setPosition(55f + (i - 5) * 200, 150f)
        back.setPosition(10f, 10f)

        for (i in 1..8) stage.addActor(level[i])
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
        batcher.draw(assets.levels, 0f, 0f, 800f, 480f)
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