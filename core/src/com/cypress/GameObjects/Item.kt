package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of pickable items. */
public class Item(private val position : Vector2, private val type : String) {

    /** Returns texture of the requested gun. */
    private fun getGun(x : Int, y : Int) = TextureRegion(assets.guns, x, y, 80, 55)

    /** Returns texture of the requested ammo. */
    private fun getAmmo(x : Int, y : Int, size : Pair<Int, Int>) =
            TextureRegion(assets.items, x, y, size.first, size.second)

    private val bounds   = Rectangle(position.x, position.y, 80f, 55f)
    private val assets   = AssetLoader.getInstance()
    private val guns     = arrayOf(getGun(412, 177), getGun(409,  17), getGun(415, 261),
                                  getGun(418, 358), getGun(422, 545), getGun(424, 452))
    private val ammoSize = arrayOf(Pair(40, 40), Pair(65, 45), Pair(37, 46),
                                   Pair(37, 46), Pair(57, 53), Pair(83, 36))
    private val ammo     = arrayOf(getAmmo( 6,  8, ammoSize[0]), getAmmo(58,   6, ammoSize[1]),
                                   getAmmo( 3, 56, ammoSize[2]), getAmmo( 0, 109, ammoSize[3]),
                                   getAmmo(62, 65, ammoSize[4]), getAmmo(45, 123, ammoSize[5]))
    private val medikit  = TextureRegion(assets.items, 71, 186, 49, 56)
    private val keyCard  = TextureRegion(assets.items, 31, 205, 32, 45)

    /** Returns texture of the requested pill. */
    private fun getPill(x : Int, y : Int) = TextureRegion(assets.items, x, y, 25, 35)

    /** Draws item using given [batcher]. */
    public fun draw(batcher : SpriteBatch){
        batcher.begin()
        if (assets.gunNames.contains(type)) {
            val index = assets.gunNames.indexOf(type)
            batcher.draw(guns[index - 1], position.x, position.y, 120f, 80f)
        }
        else if (assets.ammoNames.contains(type)) {
            val index  = assets.ammoNames.indexOf(type)
            val width  = ammoSize[index - 1].first.toFloat()
            val height = ammoSize[index - 1].second.toFloat()
            batcher.draw(ammo[index - 1], position.x, position.y, width, height)
        }
        else when(type) {
            "medikit"   -> batcher.draw(medikit, position.x, position.y, 49f, 56f)
            "greenPill" -> batcher.draw(getPill(0, 166), position.x, position.y, 25f, 35f)
            "redPill"   -> batcher.draw(getPill(31, 166), position.x, position.y, 25f, 35f)
            "bluePill"  -> batcher.draw(getPill(0, 211), position.x, position.y, 25f, 35f)
            "keyCard"   -> batcher.draw(keyCard, position.x, position.y, 32f, 45f)
        }
        batcher.end()
    }

    /** Performs appropriate action. */
    public fun activity(player : Player){

        fun addAmmo(index : Int, amount : Int) {
            var counter = player.ammoCounter
            if(counter[index].first == 0)
                counter[index] = Pair(counter[index].first + amount, counter[index].second)
            else
                counter[index] = Pair(counter[index].first, counter[index].second + amount)
        }

        if (assets.gunNames.contains(type)) {
            val index = assets.gunNames.indexOf(type)
            player.availableGuns[index] = true
            addAmmo(index, assets.maxCapacity[index])
            player.gunType = assets.gunNames[index]
            assets.happy[(Math.random() * 1000).toInt() % 3]?.play()
        }
        else if (assets.ammoNames.contains(type)) {
            val index = assets.ammoNames.indexOf(type)
            addAmmo(index, assets.maxCapacity[index])
            assets.happy[(Math.random() * 1000).toInt() % 3]?.play()
        }
        else when(type) {
            "medikit" -> {
                if (player.health <= 100 && player.health + 30 <= 100) player.health += 30
                else player.health = 100
                assets.eats[(Math.random() * 1000).toInt() % 4]?.play()
            }
            else -> println("ok")
        }
    }

    /** Returns bounds of item. */
    public fun getBounds() = bounds
}