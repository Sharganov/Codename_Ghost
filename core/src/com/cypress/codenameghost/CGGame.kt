package com.cypress.codenameghost

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.cypress.CGHelpers.AssetLoader
import com.cypress.Screens.LogoScreen

/** Contains definition of game. */
public class CGGame : Game() {
    
    public val availableLevels = arrayOf(false, true, false, false, false, false, false, false, false)

    /** Creates game. */
    companion object {
        private var _instance : CGGame = CGGame()
        fun getInstance() : CGGame = _instance
    }

    public override fun create() {
        Gdx.app.log("CGGame", "created")
        val assets = AssetLoader.getInstance()
        assets.load()
        setScreen(LogoScreen(this))
    }

    /** Dispose game. */
    public override fun dispose() {
        super.dispose()
    }
}