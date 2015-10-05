package com.cypress.GameObjects

import com.badlogic.gdx.math.Vector2

public open class Scrollable(x : Float, y : Float, width : Int, height : Int, scrollSpeed : Float) {

    // Protected похож private, но позволяет наследоваться в дочерних классах.
    protected var position = Vector2()
    protected var velocity = Vector2()
    protected var width = 0
    protected var height = 0
    protected var isScrolledLeft = false

    init {
        position = Vector2(x, y)
        velocity = Vector2(scrollSpeed, 0f)
        this.width = width
        this.height = height
    }

    public fun update(delta : Float) {
        position.add(velocity.cpy().scl(delta))

        // Если объект Scrollable более не виден:
        if (position.x + width < 0) {
            isScrolledLeft = true
        }
    }

    // Reset: Нужно переопределять в дочернем классе, если необходимо описать
    // другое поведение
    public open fun reset(newX : Float) {
        position.x = newX
        isScrolledLeft = false
    }

    // Методы доступа к переменым класса
    public fun isScrolledLeft() : Boolean {
        return isScrolledLeft
    }

    public fun getTailX() : Float {
        return position.x + width
    }

    public fun getX() : Float {
        return position.x
    }

    public fun getY() : Float {
        return position.y
    }

}