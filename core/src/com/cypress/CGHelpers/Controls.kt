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
import com.cypress.Screens.MenuScreen

/** Contains definition of controls. */
public class Controls(val assets : AssetLoader, val game : CGGame, val player : Player) {

    private val stage  = Stage()

    init {
        // initializing buttons
        val left     = ImageButton(assets.getImageButtonStyle(21, 376, 20, 442, 65, 65))
        val right    = ImageButton(assets.getImageButtonStyle(99, 376, 98, 442, 65, 65))
        val jump     = ImageButton(assets.getImageButtonStyle(174, 376, 173, 442, 65, 65))
        val shoot    = ImageButton(assets.getImageButtonStyle(264, 376, 263, 442, 65, 65))
        val pause    = ImageButton(assets.getImageButtonStyle(350, 370, 349, 437, 67, 67))
        val leftGun  = ImageButton(assets.getImageButtonStyle(458, 376, 457, 443, 40, 55))
        val rightGun = ImageButton(assets.getImageButtonStyle(498, 376, 497, 443, 40, 55))

        // initializing label
        val labelStyle = Label.LabelStyle()
        labelStyle.font = assets.generateFont("Calibri.ttf", 30, Color.WHITE)

        val health = Label(player.health.toString() + "  x " + player.lives.toString(), labelStyle)

        // initializing image
        val heart   = Image(TextureRegion(assets.buttons, 591, 389, 45, 41))

        left.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                player.shouldGoToLeft = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                player.shouldGoToLeft = false
            }
        })

        right.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                player.shouldGoToRight = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                player.shouldGoToRight = false
            }
        })

        jump.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
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
                if ((assets.activeMusic?.isPlaying ?: false) && assets.musicOn) assets.activeMusic?.stop()
                game.screen = MenuScreen(assets, game, player)
                dispose()
            }
        })

        leftGun.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                when (player.gunType) {
                    "usi"           -> player.gunType = "assaultRiffle"
                    "shotgun"       -> player.gunType = "usi"
                    "assaultRiffle" -> player.gunType = "shotgun"
                }
            }
        })

        rightGun.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                when (player.gunType) {
                    "usi"           -> player.gunType = "shotgun"
                    "shotgun"       -> player.gunType = "assaultRiffle"
                    "assaultRiffle" -> player.gunType = "usi"
                }
            }
        })


        left.setPosition(20f, 0f)
        left.sizeBy(25f, 25f)
        right.setPosition(130f, 0f)
        right.sizeBy(25f, 25f)
        jump.setPosition(530f, 20f)
        jump.sizeBy(20f, 20f)
        shoot.setPosition(660f, 70f)
        shoot.sizeBy(20f, 20f)
        pause.setPosition(715f, 400f)
        pause.sizeBy(10f, 10f)
        leftGun.setPosition(30f, 380f)
        leftGun.sizeBy(20f, 35f)
        rightGun.setPosition(150f, 380f)
        rightGun.sizeBy(20f, 35f)
        heart.setPosition(250f, 395f)
        heart.sizeBy(20f, 20f)
        health.setPosition(263f, 414f)

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

    /** Returns stage. */
    public fun getStage() : Stage {
        return stage
    }

    /** Dispose stage. */
    public fun dispose() {
        stage.dispose()
    }
}