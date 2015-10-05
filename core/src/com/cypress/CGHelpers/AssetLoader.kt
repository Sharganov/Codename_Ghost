package com.cypress.CGHelpers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator

public class AssetLoader {

    public var manager: AssetManager = AssetManager()

    public var logo     : Texture? = null
    public var main     : Texture? = null
    public var buttons  : Texture? = null
    public var settings : Texture? = null
    public var about    : Texture? = null
    public var player   : Texture? = null
    public var levels   : Texture? = null
    public var level1BG : Texture? = null
    public var level1FP : Texture? = null

    public var mainTheme   : Music? = null
    public var level1Music : Music? = null
    public var level1Snow  : Music? = null

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
        manager.load("data/images/levels.png", Texture::class.java)
        manager.load("data/images/Panda.png", Texture::class.java)

        //  loading of sounds
        //manager.load("data/sounds/MainTheme.ogg", Music::class.java)

        manager.finishLoading()

        logo     = manager.get("data/images/logo.png")
        main     = manager.get("data/images/main.png")
        buttons  = manager.get("data/images/buttons.png")
        settings = manager.get("data/images/settings.png")
        about    = manager.get("data/images/about.png")
        levels   = manager.get("data/images/levels.png")
        player   = manager.get("data/images/Panda.png")

        //mainTheme = manager.get("data/sounds/MainTheme.ogg")
    }

    /** Generates font from given parameters. */
    public fun generateFont(fileName : String, size : Int, color : Color) : BitmapFont {
        val generator = FreeTypeFontGenerator(Gdx.files.internal("data/fonts/" + fileName))
        var param     = FreeTypeFontGenerator.FreeTypeFontParameter()

        param.size  = size
        param.color = color

        val RUS_CHARACTERS     = "àáâãäå¸æçèéêëìíîïğñòóôõö÷øùúûüışÿÀÁÂÃÄÅ¨ÆÇÈÉÊËÌÍÎÏĞÑÒÓÔÕÖ×ØÙÚÛÜİŞß"
        val ENG_CHARACTERS     = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val DIGITS_AND_SYMBOLS = "0123456789][_!$%#@|\\/?-+=()*&.:;,{}\"?`'<>"
        param.characters = RUS_CHARACTERS + ENG_CHARACTERS + DIGITS_AND_SYMBOLS

        val font = generator.generateFont(param)
        generator.dispose()

        return font
    }

    /** Loads resources of level 1 to AssetManager. */
    public fun loadLevel1() {
        // loaing of images
        manager.load("data/images/level1/background.png", Texture::class.java)
        manager.load("data/images/level1/firstplan.png", Texture::class.java)

        // loaing of music
        //manager.load("data/sounds/Level1.ogg", Music::class.java)

        manager.finishLoading()

        level1BG = manager.get("data/images/level1/background.png")
        level1FP = manager.get("data/images/level1/firstplan.png")

        //level1Music = manager.get("data/sounds/Level1.ogg")
    }
}