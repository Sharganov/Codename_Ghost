package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader

/** Contains definition of pickable items. */
public class Item(private val position : Vector2, private val type : String) {

    private val bounds  = Rectangle(position.x, position.y, 80f, 55f)
    private val assets  = AssetLoader.getInstance()
    private val guns    = arrayOf(getGun(412, 177), getGun(409,  17), getGun(415, 261),
                                  getGun(418, 358), getGun(422, 545), getGun(424, 452))
    private val ammo    = arrayOf(getAmmo(6,   8, 40, 40), getAmmo(58,  6, 65, 45), getAmmo( 3,  63, 38, 47),
                                  getAmmo(0, 123, 38, 47), getAmmo(62, 65, 57, 53), getAmmo(42, 143, 83, 37))
    private val medikit = TextureRegion(assets.items, 3, 180, 43, 21)
    private val keyCard = TextureRegion(assets.items, 95, 202, 32, 45)

    /** Returns texture of the requested gun. */
    private fun getGun(x : Int, y : Int) : TextureRegion =
            TextureRegion(assets.guns, x, y, 80, 55)

    /** Returns texture of the requested ammo. */
    private fun getAmmo(x : Int, y : Int, width : Int, height : Int) : TextureRegion =
            TextureRegion(assets.items, x, y, width, height)

    /** Returns texture of the requested pill. */
    private fun getPill(x : Int) : TextureRegion =
            TextureRegion(assets.items, x, 209, 25, 35)

    /** Draws item using given [batcher]. */
    public fun draw(batcher : SpriteBatch){
        batcher.begin()
        if (assets.gunsNames.contains(type)) {
            val index = assets.gunsNames.indexOf(type)
            batcher.draw(guns[index - 1], position.x, position.y, 120f, 80f)
        }
        else if (assets.ammoNames.contains(type)) {
            val index = assets.ammoNames.indexOf(type)
            batcher.draw(ammo[index - 1], position.x, position.y, 80f, 55f)
        }
        else when(type) {
            "medikit"   -> batcher.draw(medikit, position.x, position.y, 65f, 48f)
            "greenPill" -> batcher.draw(getPill(0), position.x, position.y, 25f, 35f)
            "redPill"   -> batcher.draw(getPill(31), position.x, position.y, 25f, 35f)
            "bluePill"  -> batcher.draw(getPill(64), position.x, position.y, 25f, 35f)
            "keyCard"   -> batcher.draw(getPill(64), position.x, position.y, 25f, 35f)
        }
        batcher.end()
    }

    /** Performs appropriate action. */
    public fun activity(player: Player){

        fun addAmmo(index : Int, amount : Int) {
            var counter = player.ammoCounter
            if(counter[index].first == 0)
                counter[index] = Pair(counter[index].first + amount, counter[index].second)
            else
                counter[index] = Pair(counter[index].first, counter[index].second + amount)
        }

        if (assets.gunsNames.contains(type)) {
            val index = assets.gunsNames.indexOf(type)
            player.availableGuns[index] = true
            addAmmo(index, assets.maxCapacity[index])
            player.gunType = assets.gunsNames[index]
        }
        else if (assets.ammoNames.contains(type)) {
            val index = assets.ammoNames.indexOf(type)
            addAmmo(index, assets.maxCapacity[index])
        }
        else when(type) {
            "medikit" -> {
                if (player.health <= 100 && player.health + 30 <= 100) player.health += 30
                else player.health = 100
            }
        }
    }

    /** Returns bounds of item. */
    public fun getBounds() = bounds
}