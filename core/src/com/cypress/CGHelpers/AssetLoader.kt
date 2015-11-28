package com.cypress.CGHelpers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.audio.Sound
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.cypress.GameObjects.Bullet
import java.util.*

/** Loads assets of project. */
public class AssetLoader {
    public val manager      = AssetManager()
    public val levelsBG     = Array(9, { LinkedList<Texture?>() })
    public val levelsFP     = Array(9, { LinkedList<Texture?>() })
    public val gunNames     = arrayOf("uzi", "shotgun", "assaultRiffle", "plasmagun",
                                      "lasergun", "minigun", "rocketLauncher")
    public val ammoNames    = Array(7, {i-> gunNames[i] + "_ammo"})
    public val bulletsList  = LinkedList<Bullet>()
    public val maxCapacity  = arrayOf(30, 8, 45, 25, 50, 100, 1)
    public val bulletDamage = arrayOf(10, 20, 15, 25, 20, 15, 40)
    public val levelsMusic  = Array<Music?>(9, { null })
    public val shot         = Array<Sound?>(7, { null })
    public val happy        = Array<Sound?>(3, { null })
    public val eats         = Array<Sound?>(4, { null })

    public var musicOn  = true
    public var language = "english"
    public var zoom     = 1.25f

    public var logo     : Texture? = null
    public var main     : Texture? = null
    public var buttons  : Texture? = null
    public var settings : Texture? = null
    public var about    : Texture? = null
    public var player   : Texture? = null
    public var guns     : Texture? = null
    public var bullets  : Texture? = null
    public var levels   : Texture? = null
    public var warrior  : Texture? = null
    public var items    : Texture? = null
    public var effects  : Texture? = null

    public var activeMusic : Music? = null
    public var mainTheme   : Music? = null
    public var gameOver    : Music? = null
    public var snow        : Music? = null
    public var fan         : Sound? = null
    public var reload      : Sound? = null

    companion object {
        private var _instance : AssetLoader = AssetLoader()
        fun getInstance() : AssetLoader = _instance
    }

    /** Loads main resources to AssetManager. */
    public fun load() {
        // loading of images
        val textureLoadList = arrayOf("logo", "main", "buttons", "settings", "about", "player",
                                      "guns", "bullets", "levels", "warrior", "items", "effects")
        for (t in textureLoadList) manager.load("data/images/" + t + ".png", Texture::class.java)

        // loading of music and sounds
        manager.load("data/sounds/music/MainTheme.ogg", Music::class.java)
        manager.load("data/sounds/music/GameOver.ogg", Music::class.java)

        val soundLoadList = arrayOf("uzi", "shotgun", "assaultRiffle", "plasmagun", "lasergun",
                                    "minigun", "rocketLauncher", "reload")
        for (s in soundLoadList) manager.load("data/sounds/weapons/" + s + ".ogg", Sound::class.java)

        val voiceLoadList = arrayOf("yeah1", "yeah2", "yeah3", "yum1", "yum2", "yum3", "yum4")
        for (v in voiceLoadList) manager.load("data/sounds/player/" + v + ".ogg", Sound::class.java)

        manager.finishLoading()

        logo     = manager.get("data/images/logo.png")
        main     = manager.get("data/images/main.png")
        buttons  = manager.get("data/images/buttons.png")
        settings = manager.get("data/images/settings.png")
        about    = manager.get("data/images/about.png")
        player   = manager.get("data/images/player.png")
        guns     = manager.get("data/images/guns.png")
        bullets  = manager.get("data/images/bullets.png")
        levels   = manager.get("data/images/levels.png")
        warrior  = manager.get("data/images/warrior.png")
        items    = manager.get("data/images/items.png")
        effects  = manager.get("data/images/effects.png")

        mainTheme = manager.get("data/sounds/music/MainTheme.ogg")
        gameOver  = manager.get("data/sounds/music/GameOver.ogg")
        reload    = manager.get("data/sounds/weapons/reload.ogg")

        for (i in 0 .. 6) shot[i]  = manager.get("data/sounds/weapons/" + gunNames[i] + ".ogg")
        for (i in 0 .. 2) happy[i] = manager.get("data/sounds/player/" + voiceLoadList[i] + ".ogg")
        for (i in 0 .. 3) eats[i]  = manager.get("data/sounds/player/" + voiceLoadList[i + 3] + ".ogg")
    }

    /** Generates font with given parameters. */
    public fun generateFont(fileName : String, size : Int, color : Color) : BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + fileName))
        var parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

        parameter.size  = size
        parameter.color = color

        val RUSSIAN_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
        val ENGLISH_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val DIGITS_AND_SYMBOLS = "0123456789`~!@#$%^&*()-_+=[]{}?/\\,.<>'|:;,\"®©∞"
        parameter.characters   = RUSSIAN_CHARACTERS + ENGLISH_CHARACTERS + DIGITS_AND_SYMBOLS

        val font = generator.generateFont(parameter)
        generator.dispose()
        return font
    }

    /** Returns image button style with given parameters. */
    fun getImageButtonStyle(x1 : Int, y1 : Int, x2 : Int, y2 : Int, height : Int, width : Int,
                            checkedRequired : Boolean) : ImageButton.ImageButtonStyle {
        // skin for button
        val skin = Skin()
        skin.add("button-up", TextureRegion(buttons, x1, y1, height, width))
        skin.add("button-down", TextureRegion(buttons, x2, y2, height, width))

        val style  = ImageButton.ImageButtonStyle()
        style.up   = skin.getDrawable("button-up")
        style.down = skin.getDrawable("button-down")
        if (checkedRequired) style.checked = skin.getDrawable("button-down")
        return style
    }

    /** Returns text button style with given parameters. */
    fun getTextButtonStyle(x1 : Int, y1 : Int, x2 : Int, y2 : Int, height : Int, width : Int,
                           font : BitmapFont) : TextButton.TextButtonStyle {
        // skin for button
        val skin = Skin()
        skin.add("button-up", TextureRegion(buttons, x1, y1, height, width))
        skin.add("button-down", TextureRegion(buttons, x2, y2, height, width))

        val style  = TextButton.TextButtonStyle()
        style.font = font
        style.up   = skin.getDrawable("button-up")
        style.down = skin.getDrawable("button-down")
        return style
    }

    /** Loads resources of level 1 to AssetManager. */
    public fun loadLevel1() {
        // loading of images
        val textureLoadList = arrayOf("background.png", "background2.png", "firstplan.png")
        for (t in textureLoadList) manager.load("data/images/level1/" + t, Texture::class.java)

        // loading of music and sounds
        manager.load("data/sounds/music/Level1.ogg", Music::class.java)
        manager.load("data/sounds/world/snow.ogg", Music::class.java)
        manager.load("data/sounds/world/fan.ogg", Sound::class.java)

        manager.finishLoading()

        for (i in 0 .. 1) levelsBG[1].add(manager.get("data/images/level1/" + textureLoadList[i]))
        levelsFP[1].add(manager.get("data/images/level1/firstplan.png"))

        levelsMusic[1] = manager.get("data/sounds/music/Level1.ogg")
        snow           = manager.get("data/sounds/world/snow.ogg")
        fan            = manager.get("data/sounds/world/fan.ogg")
    }
}