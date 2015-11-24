package com.cypress.GameObjects

/** Abstract class for game characters. */
public abstract class Character {
    abstract public var health : Int
    abstract public var shouldGoToLeft : Boolean
    abstract public var shouldGoToRight : Boolean
    abstract public var stayRight : Boolean
    abstract public var onGround : Boolean
    abstract public var gunType : String

    abstract public fun getX() : Float
    abstract public fun getY() : Float
}
