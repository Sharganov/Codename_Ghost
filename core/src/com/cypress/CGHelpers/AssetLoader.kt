package com.cypress.CGHelpers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin

/** Loads assets of project. */
public class AssetLoader {

    public var manager: AssetManager = AssetManager()

    public var logo     : Texture? = null
    public var main     : Texture? = null
    public var buttons  : Texture? = null
    public var settings : Texture? = null
    public var about    : Texture? = null
    public var player   : Texture? = null
    public var guns     : Texture? = null
    public var bullets  : Texture? = null
    public var levels   : Texture? = null
    public var level1BG : Texture? = null
    public var level1FP : Texture? = null

    public var activeMusic       : Music? = null
    public var mainTheme         : Music? = null
    public var shotFromUzi       : Music? = null
    public var shotFromPlasmagun : Music? = null
    public var level1Music       : Music? = null
    public var level1Snow        : Music? = null

    public var musicOn  = true
    public var language = "english"

    /** Loads main resources to AssetManager. */
    public fun load() {
        // loaing of images
        manager.load("data/images/logo.png", Texture::class.java)
        manager.load("data/images/main.png", Texture::class.java)
        manager.load("data/images/buttons.png", Texture::class.java)
        manager.load("data/images/settings.png", Texture::class.java)
        manager.load("data/images/about.png", Texture::class.java)
        manager.load("data/images/Panda.png", Texture::class.java)
        manager.load("data/images/guns.png", Texture::class.java)
        manager.load("data/images/bullets.png", Texture::class.java)
        manager.load("data/images/levels.png", Texture::class.java)

        // loading of sounds
        //manager.load("data/sounds/MainTheme.ogg", Music::class.java)
        manager.load("data/sounds/weapons/uzi.ogg", Music::class.java)
        manager.load("data/sounds/weapons/plasmagun.ogg", Music::class.java)

        manager.finishLoading()

        logo     = manager.get("data/images/logo.png")
        main     = manager.get("data/images/main.png")
        buttons  = manager.get("data/images/buttons.png")
        settings = manager.get("data/images/settings.png")
        about    = manager.get("data/images/about.png")
        player   = manager.get("data/images/Panda.png")
        guns     = manager.get("data/images/guns.png")
        bullets  = manager.get("data/images/bullets.png")
        levels   = manager.get("data/images/levels.png")

        //mainTheme = manager.get("data/sounds/MainTheme.ogg")
        shotFromUzi       = manager.get("data/sounds/weapons/uzi.ogg")
        shotFromPlasmagun = manager.get("data/sounds/weapons/plasmagun.ogg")
    }

    /** Generates font with given parameters. */
    public fun generateFont(fileName : String, size : Int, color : Color) : BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + fileName))
        var param     = FreeTypeFontGenerator.FreeTypeFontParameter()

        param.size  = size
        param.color = color

        val RUS_CHARACTERS     = "‡·‚„‰Â∏ÊÁËÈÍÎÏÌÓÔÒÚÛÙıˆ˜¯˘˙˚¸˝˛ˇ¿¡¬√ƒ≈®∆«»… ÀÃÕŒœ–—“”‘’÷◊ÿŸ⁄€‹›ﬁﬂ"
        val ENG_CHARACTERS     = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val DIGITS_AND_SYMBOLS = "0123456789][_!$%#@|\\/?-+=()*&.:;,{}\"?`'<>"
        param.characters = RUS_CHARACTERS + ENG_CHARACTERS + DIGITS_AND_SYMBOLS

        val font = generator.generateFont(param)
        generator.dispose()

        return font
    }

    /** Returns image button style with given parameters. */
    fun getImageButtonStyle(x1 : Int, y1 : Int, x2 : Int, y2 : Int, height : Int, width : Int)
            : ImageButton.ImageButtonStyle {
        // skin for button
        val skin = Skin()
        skin.add("button-up", TextureRegion(buttons, x1, y1, height, width))
        skin.add("button-down", TextureRegion(buttons, x2, y2, height, width))

        val style  = ImageButton.ImageButtonStyle()
        style.up   = skin.getDrawable("button-up")
        style.down = skin.getDrawable("button-down")
        return style
    }

    /** Returns text button style with given parameters. */
    fun getTextButtonStyle(x1 : Int, y1 : Int, x2 : Int, y2 : Int, height : Int, width : Int, font : BitmapFont)
            : TextButton.TextButtonStyle {
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
        manager.load("data/images/level1/background.png", Texture::class.java)
        manager.load("data/images/level1/firstplan.png", Texture::class.java)

        // loading of music
        //manager.load("data/sounds/Level1.ogg", Music::class.java)
        manager.load("data/sounds/Snow.ogg", Music::class.java)

        manager.finishLoading()

        level1BG = manager.get("data/images/level1/background.png")
        level1FP = manager.get("data/images/level1/firstplan.png")

        //level1Music = manager.get("data/sounds/Level1.ogg")
        level1Snow = manager.get("data/sounds/Snow.ogg")
    }
}