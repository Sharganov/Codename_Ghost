package com.cypress.codenameghost

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.cypress.CGHelpers.AssetLoader
import com.cypress.Screens.LogoScreen
import com.cypress.Levels.Level1

public class CGGame : Game() {
    public override fun create() {
        Gdx.app.log("CGGame", "created")
        val assets = AssetLoader()
        assets.load()
        setScreen(LogoScreen(assets, this))
//        assets.loadLevel1()
  //      setScreen(Level1(assets, this))
    }

    public override fun dispose() {
        super.dispose()
    }
}