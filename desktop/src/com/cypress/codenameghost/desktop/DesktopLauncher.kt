package com.cypress.codenameghost.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
//import com.cypress.Screens.MyGdxGame
import com.cypress.codenameghost.CGGame


fun main (args : Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.title = "Codename: Ghost"
    config.width = 800
    config.height = 480
    LwjglApplication(CGGame(), config)
}