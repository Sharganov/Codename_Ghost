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
import com.cypress.GameObjects.Bullet

/** Contains definition of controls. */
public class Controls(val game : CGGame, val player : Player) {

    private val assets = AssetLoader.getInstance()
    private val stage = Stage()

    init {
        // initializing buttons
        val left     = ImageButton(assets.getImageButtonStyle(21, 376, 20, 442, 65, 65, false))
        val right    = ImageButton(assets.getImageButtonStyle(99, 376, 98, 442, 65, 65, false))
        val jump     = ImageButton(assets.getImageButtonStyle(174, 376, 173, 442, 65, 65, false))
        val shoot    = ImageButton(assets.getImageButtonStyle(264, 376, 263, 442, 65, 65, false))
        val pause    = ImageButton(assets.getImageButtonStyle(350, 370, 349, 437, 67, 67, false))
        val leftGun  = ImageButton(assets.getImageButtonStyle(458, 376, 457, 443, 40, 55, false))
        val rightGun = ImageButton(assets.getImageButtonStyle(498, 376, 497, 443, 40, 55, false))

        // initializing label
        val labelStyle = Label.LabelStyle()
        labelStyle.font = assets.generateFont("Calibri.ttf", 30, Color.WHITE)

        val health = Label(player.health.toString() + "  x " + player.lives.toString(), labelStyle)

        // initializing images
        val heart = Image(TextureRegion(assets.buttons, 591, 389, 45, 41))
        val icon  = Image(TextureRegion(assets.guns, 410, 87, 80, 55))

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
                if (player.onGround) player.shouldJump = true
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
                val bullet = Bullet(player)
                player.bulletsList.add(bullet)

                when (player.gunType) {
                    "shotgun" -> {
                        if (assets.shotFromShotgun?.isPlaying ?: false) assets.shotFromShotgun?.stop()
                        if (assets.musicOn) assets.shotFromShotgun?.play()
                    }
                    "lasergun" -> {
                        if (assets.shotFromLasergun?.isPlaying ?: false) assets.shotFromLasergun?.stop()
                        if (assets.musicOn) assets.shotFromLasergun?.play()
                    }
                    "laser2gun" -> {
                        if (assets.shotFromLasergun?.isPlaying ?: false) assets.shotFromLasergun?.stop()
                        if (assets.musicOn) assets.shotFromLasergun?.play()
                    }
                    "rocketLauncher" -> {
                        if (assets.rocket?.isPlaying ?: false) assets.rocket?.stop()
                        if (assets.musicOn) assets.rocket?.play()
                    }
                    else -> {
                        if (assets.shotFromUzi?.isPlaying ?: false) assets.shotFromUzi?.stop()
                        if (assets.musicOn) assets.shotFromUzi?.play()
                    }

                }
            }
        })

        pause.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                //if (assets.activeMusic?.isPlaying ?: false) assets.activeMusic?.stop()
                game.screen = MenuScreen(game, player)
                dispose()
            }
        })

        leftGun.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                when (player.gunType) {
                    "uzi"            -> player.gunType = "rocketLauncher"
                    "shotgun"        -> player.gunType = "uzi"
                    "assaultRiffle"  -> player.gunType = "shotgun"
                    "lasergun"       -> player.gunType = "assaultRiffle"
                    "laser2gun"      -> player.gunType = "lasergun"
                    "rocketLauncher" -> player.gunType = "laser2gun"
                }
                setIcon()
            }
        })

        rightGun.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                when (player.gunType) {
                    "uzi"            -> player.gunType = "shotgun"
                    "shotgun"        -> player.gunType = "assaultRiffle"
                    "assaultRiffle"  -> player.gunType = "lasergun"
                    "lasergun"       -> player.gunType = "laser2gun"
                    "laser2gun"      -> player.gunType = "rocketLauncher"
                    "rocketLauncher" -> player.gunType = "uzi"
                }
                setIcon()
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
        icon.setPosition(70f, 385f)
        icon.sizeBy(5f, 9f)
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
        stage.addActor(icon)

        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true
    }

    /** Sets gun icon. */
    private fun setIcon() {
        val gunIcon =
            when (player.gunType) {
                "shotgun"        -> Image(TextureRegion(assets.guns, 412, 177, 80, 55))
                "assaultRiffle"  -> Image(TextureRegion(assets.guns, 409, 17, 80, 55))
                "lasergun"       -> Image(TextureRegion(assets.guns, 415, 261, 80, 55))
                "laser2gun"      -> Image(TextureRegion(assets.guns, 418, 358, 80, 55))
                "rocketLauncher" -> Image(TextureRegion(assets.guns, 424, 452, 80, 55))
                else             -> Image(TextureRegion(assets.guns, 410, 87, 80, 55))
            }
        gunIcon.setPosition(75f, 390f)
        gunIcon.sizeBy(5f, 9f)
        stage.actors[9] = gunIcon
    }

    /** Returns stage. */
    public fun getStage(): Stage {
        return stage
    }

    /** Dispose stage. */
    public fun dispose() {
        stage.dispose()
    }
}