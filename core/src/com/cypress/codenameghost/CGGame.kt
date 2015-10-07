package com.cypress.codenameghost

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.cypress.CGHelpers.AssetLoader
import com.cypress.Screens.LogoScreen

/** Contains definition of game. */
public class CGGame : Game() {

    /** Creates game. */
    public override fun create() {
        Gdx.app.log("CGGame", "created")
        val assets = AssetLoader()
        assets.load()
        setScreen(LogoScreen(assets, this))
    }

    /** Dispose game. */
    public override fun dispose() {
        super.dispose()
    }
}