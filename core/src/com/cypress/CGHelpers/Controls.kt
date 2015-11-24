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

        // initializing labels
        val labelStyle = Label.LabelStyle()
        labelStyle.font = assets.generateFont("Calibri.ttf", 30, Color.WHITE)

        var health = Label(player.health.toString() + "  x " + player.lives.toString(), labelStyle)

        val index  = assets.gunsNames.indexOf(player.gunType)
        val b      = player.ammoCouner[index]
        var ammo   =
                if (index == 0) Label(b.first.toString()  + " / inf", labelStyle)
                else Label(b.first.toString()  + " / " + b.second.toString(), labelStyle)

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
                var index  = assets.gunsNames.indexOf(player.gunType)
                var b      = player.ammoCouner[index]

                // ammo run out
                if (b.first == 0 && b.second == 0 && index != 0) {
                    player.availableGuns[index] = false
                    nextGun(index)
                }
                else {
                    val bullet = Bullet(player)
                    player.bulletsList.add(bullet)

                    assets.shot[index]?.stop()
                    if (assets.musicOn) assets.shot[1]?.play()

                    player.ammoCouner[index] = Pair(b.first - 1, b.second)

                    // reloading
                    if (b.first == 0) {
                        assets.reload?.play()
                        if (index == 0)
                            player.ammoCouner[0] = Pair(assets.maxCapacity[0], assets.maxCapacity[0])
                        else {
                            val max = assets.maxCapacity[index]
                            if (b.second >= max)
                                player.ammoCouner[index] = Pair(max, b.second - max)
                            else
                                player.ammoCouner[index] = Pair(b.second, 0)
                        }
                    }
                    // ammo run out
                    if (b.first == 0 && b.second == 0 && index != 0) {
                        player.availableGuns[index] = false
                        nextGun(index)
                    }
                }
                update()
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
                val index = assets.gunsNames.indexOf(player.gunType)
                prevGun(index)
                update()
            }
        })

        rightGun.addListener(object : ClickListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                val index = assets.gunsNames.indexOf(player.gunType)
                nextGun(index)
                update()
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
        ammo.setPosition(85f, 350f)

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
        stage.addActor(ammo)

        Gdx.input.inputProcessor = stage
        Gdx.input.isCatchBackKey = true
    }

    /** */
    private fun update() {
        val labelStyle = Label.LabelStyle()
        labelStyle.font = assets.generateFont("Calibri.ttf", 30, Color.WHITE)

        val health = Label(player.health.toString() + "  x " + player.lives.toString(), labelStyle)
        val index  = assets.gunsNames.indexOf(player.gunType)
        val b      = player.ammoCouner[index]
        var ammo   =
                if (index == 0) Label(b.first.toString()  + " / inf", labelStyle)
                else Label(b.first.toString()  + " / " + b.second.toString(), labelStyle)

        health.setPosition(263f, 414f)
        ammo.setPosition(85f, 350f)

        stage.actors[8]  = health
        stage.actors[10] = ammo
    }

    /** Sets gun icon. */
    private fun setIcon() {
        val gunIcon =
            when (player.gunType) {
                assets.gunsNames[1] -> Image(TextureRegion(assets.guns, 412, 177, 80, 55))
                assets.gunsNames[2] -> Image(TextureRegion(assets.guns, 409, 17, 80, 55))
                assets.gunsNames[3] -> Image(TextureRegion(assets.guns, 415, 261, 80, 55))
                assets.gunsNames[4] -> Image(TextureRegion(assets.guns, 418, 358, 80, 55))
                assets.gunsNames[5] -> Image(TextureRegion(assets.guns, 424, 452, 80, 55))
                else                -> Image(TextureRegion(assets.guns, 410, 87, 80, 55))
            }
        gunIcon.setPosition(75f, 390f)
        gunIcon.sizeBy(5f, 9f)
        stage.actors[9] = gunIcon
    }

    /** */
    private fun nextGun(index : Int) {
        var temp = 0
        for (i in 1 .. 5) {
            temp = (index + i) % 6
            if (player.availableGuns[temp]) {
                player.gunType = assets.gunsNames[temp]
                break
            }
        }
        setIcon()
    }

    /** */
    private fun prevGun(index : Int) {
        var temp = 0
        for (i in 1 .. 5) {
            temp = (index - i) % 6
            if (temp < 0) temp += 6
            if (player.availableGuns[temp]) {
                player.gunType = assets.gunsNames[temp]
                break
            }
        }
        setIcon()
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