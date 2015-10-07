package com.cypress.CGHelpers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.cypress.GameObjects.Player
import com.cypress.codenameghost.CGGame

public class Controls(assets : AssetLoader, player : Player, game : CGGame) {

    private val assets  = assets
    private val stage   = Stage()

    init {
        fun getImageButtonStyle(x1 : Int, y1 : Int, x2 : Int, y2 : Int, height : Int, width : Int)
                : ImageButton.ImageButtonStyle {
            // skin for button
            val skin = Skin()
            skin.add("button-up", TextureRegion(assets.buttons, x1, y1, height, width))
            skin.add("button-down", TextureRegion(assets.buttons, x2, y2, height, width))

            val style  = ImageButton.ImageButtonStyle()
            style.up   = skin.getDrawable("button-up")
            style.down = skin.getDrawable("button-down")
            return style
        }

        // initializing buttons
        val left     = ImageButton(getImageButtonStyle(21, 376, 20, 442, 65, 65))
        val right    = ImageButton(getImageButtonStyle(99, 376, 98, 442, 65, 65))
        val jump     = ImageButton(getImageButtonStyle(174, 376, 173, 442, 65, 65))
        val shoot    = ImageButton(getImageButtonStyle(264, 376, 263, 442, 65, 65))
        val pause    = ImageButton(getImageButtonStyle(350, 370, 349, 437, 67, 67))
        val leftGun  = ImageButton(getImageButtonStyle(458, 376, 457, 443, 40, 55))
        val rightGun = ImageButton(getImageButtonStyle(498, 376, 497, 443, 40, 55))


        val labelStyle = Label.LabelStyle()
        labelStyle.font = assets.generateFont("Calibri.ttf", 20, Color.WHITE)

        val health = Label(player.health.toString(), labelStyle)
        val heart  = Image(TextureRegion(assets.buttons, 591, 389, 45, 41))


        left.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                player.shouldGoLeft = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                player.shouldGoLeft = false
            }
        })

        right.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                player.shouldGoRight = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                player.shouldGoRight = false
            }
        })

        jump.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                player.shouldJump = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            player.shouldJump = false
            }
        })

        shoot.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            }
        })

        pause.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                if (assets.level1Music?.isPlaying ?: false && assets.musicOn) assets.level1Music?.stop()
                game.screen = com.cypress.Screens.SettingsScreen(assets, game)
                dispose()
            }
        })

        leftGun.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            }
        })

        rightGun.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
            }
        })


        left.setPosition(20f, 20f)
        right.setPosition(90f, 20f)
        jump.setPosition(690f, 70f)
        shoot.setPosition(600f, 30f)
        pause.setPosition(735f, 415f)
        leftGun.setPosition(40f, 420f)
        rightGun.setPosition(90f, 420f)
        heart.setPosition(243f, 430f)
        health.setPosition(250f, 440f)

        stage.addActor(left)
        stage.addActor(right)
        stage.addActor(jump)
        stage.addActor(shoot)
        stage.addActor(pause)
        stage.addActor(leftGun)
        stage.addActor(rightGun)
        stage.addActor(heart)
        stage.addActor(health)

        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true
    }

    public fun getStage() : Stage {
        return stage
    }

    public fun dispose() {
        stage.dispose()
    }
}