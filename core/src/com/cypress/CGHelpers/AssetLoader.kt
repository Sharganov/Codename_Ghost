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

    public val levelsBG     = Array(9, { LinkedList<Texture?>() })
    public val levelsFP     = Array(9, { LinkedList<Texture?>() })
    public val gunsNames    = arrayOf("uzi", "shotgun", "assaultRiffle", "plasmagun",
                                "lasergun", "minigun", "rocketLauncher")
    public val ammoNames    = Array(7, {i-> gunsNames[i] + "_ammo"})
    public val bulletsList  = LinkedList<Bullet>()
    public val maxCapacity  = arrayOf(30, 8, 45, 25, 20, 100, 1)
    public val bulletDamage = arrayOf(10, 20, 15, 20, 30, 15, 40)

    public var manager  : AssetManager = AssetManager()

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

    public var activeMusic : Music? = null
    public var mainTheme   : Music? = null
    public var snow        : Music? = null
    public var fan         : Sound? = null
    public var reload      : Sound? = null

    public var levelsMusic = Array<Music?>(9, { null })
    public var shot        = Array<Sound?>(7, { null })
    public var musicOn     = true
    public var language    = "english"
    public var zoom        = 1.25f

    companion object {
        private var _instance : AssetLoader = AssetLoader()
        fun getInstance() : AssetLoader = _instance
    }

    /** Loads main resources to AssetManager. */
    public fun load() {
        // loading of images
        val textureLoadList = arrayOf("logo.png", "main.png", "buttons.png", "settings.png", "about.png",
                "player.png", "guns.png", "bullets.png", "levels.png", "warrior.png", "items.png")
        for (t in textureLoadList) manager.load("data/images/" + t, Texture::class.java)

        // loading of music and sounds
        manager.load("data/sounds/music/MainTheme.ogg", Music::class.java)
        val soundLoadList = arrayOf("uzi.ogg", "shotgun.ogg", "lasergun.ogg", "rocket.ogg", "reload.ogg")
        for (s in soundLoadList) manager.load("data/sounds/weapons/" + s, Sound::class.java)

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

        mainTheme = manager.get("data/sounds/music/MainTheme.ogg")
        shot[0]   = manager.get("data/sounds/weapons/uzi.ogg")
        shot[1]   = manager.get("data/sounds/weapons/shotgun.ogg")
        shot[2]   = manager.get("data/sounds/weapons/uzi.ogg")
        shot[3]   = manager.get("data/sounds/weapons/lasergun.ogg")
        shot[4]   = manager.get("data/sounds/weapons/lasergun.ogg")
        shot[5]   = manager.get("data/sounds/weapons/uzi.ogg")
        shot[6]   = manager.get("data/sounds/weapons/rocket.ogg")
        reload    = manager.get("data/sounds/weapons/reload.ogg")
    }

    /** Generates font with given parameters. */
    public fun generateFont(fileName : String, size : Int, color : Color) : BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + fileName))
        var parameter = FreeTypeFontGenerator.FreeTypeFontParameter()

        parameter.size  = size
        parameter.color = color

        val RUSSIAN_CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
        val ENGLISH_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val DIGITS_AND_SYMBOLS = "0123456789`~!@#$%^&*()-_+=[]{}?/\\,.<>'|:;,\"∞"
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

        levelsBG[1].add(manager.get("data/images/level1/background.png"))
        levelsBG[1].add(manager.get("data/images/level1/background2.png"))
        levelsFP[1].add(manager.get("data/images/level1/firstplan.png"))

        levelsMusic[1] = manager.get("data/sounds/music/Level1.ogg")

        snow = manager.get("data/sounds/world/snow.ogg")
        fan  = manager.get("data/sounds/world/fan.ogg")
    }
}