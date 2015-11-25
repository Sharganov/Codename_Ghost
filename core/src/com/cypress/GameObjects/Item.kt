package com.cypress.GameObjects

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.cypress.CGHelpers.AssetLoader

public class Item(private val position : Vector2,private val type : String) {

    private val bounds = Rectangle(position.x, position.y, 80f, 55f)
    private val assets = AssetLoader.getInstance()


    private val shotgun = TextureRegion(assets.guns, 412, 177, 80, 55)
    private val assaultRiffle = TextureRegion(assets.guns, 409, 17, 80, 55)
    private val plasmagun = TextureRegion(assets.guns, 415, 261, 80, 55)
    private val lasergun = TextureRegion(assets.guns, 418, 358, 80, 55)
    private val rocketLauncher = TextureRegion(assets.guns, 424, 452, 80, 55)

    private val fence       = TextureRegion(assets.levelsFP[1][0], 29, 437, 236, 356)

    public fun draw(batcher : SpriteBatch){
        batcher.begin()
        when(type) {
            "medicine"          -> batcher.draw(fence, position.x, position.y, 70f, 70f)
            assets.ammoNames[0] -> batcher.draw(fence, position.x, position.y, 80f, 55f)
            assets.ammoNames[1] -> batcher.draw(fence, position.x, position.y, 80f, 55f)
            assets.ammoNames[2] -> batcher.draw(fence, position.x, position.y, 80f, 55f)
            assets.ammoNames[3] -> batcher.draw(fence, position.x, position.y, 80f, 55f)
            assets.ammoNames[4] -> batcher.draw(fence, position.x, position.y, 80f, 55f)
            assets.ammoNames[5] -> batcher.draw(fence, position.x, position.y, 80f, 55f)
            assets.gunsNames[1] -> batcher.draw(shotgun, position.x, position.y)
            assets.gunsNames[2] -> batcher.draw(assaultRiffle, position.x, position.y)
            assets.gunsNames[3] -> batcher.draw(plasmagun, position.x, position.y)
            assets.gunsNames[4] -> batcher.draw(lasergun, position.x, position.y)
            assets.gunsNames[5] -> batcher.draw(rocketLauncher, position.x, position.y)
        }

        batcher.end()
    }

    public fun activity(player: Player){

        fun addAmmo(index : Int, amount : Int)
        {
            if(player.ammoCounter[index].first == 0)
                player.ammoCounter[index] = Pair(player.ammoCounter[index].first + amount, player.ammoCounter[index].second)
            else
                player.ammoCounter[index] = Pair(player.ammoCounter[index].first, player.ammoCounter[index].second + amount)
        }

        when(type) {
            "medicine"          -> player.health += 50
            assets.ammoNames[0] ->  addAmmo(0, 50)
            assets.ammoNames[1] ->  addAmmo(1, 50)
            assets.ammoNames[2] ->  addAmmo(2, 50)
            assets.ammoNames[3] ->  addAmmo(3, 50)
            assets.ammoNames[4] ->  addAmmo(4, 50)
            assets.ammoNames[5] ->  addAmmo(5, 50)
            assets.gunsNames[0] -> {player.availableGuns[0] = true; addAmmo(0, assets.maxCapacity[0])}
            assets.gunsNames[1] -> {player.availableGuns[1] = true; addAmmo(1, assets.maxCapacity[1])}
            assets.gunsNames[2] -> {player.availableGuns[2] = true; addAmmo(2, assets.maxCapacity[2])}
            assets.gunsNames[3] -> {player.availableGuns[3] = true; addAmmo(3, assets.maxCapacity[3])}
            assets.gunsNames[4] -> {player.availableGuns[4] = true; addAmmo(4, assets.maxCapacity[4])}
            assets.gunsNames[5] -> {player.availableGuns[5] = true; addAmmo(5, assets.maxCapacity[5])}
        }
    }

    public fun getBounds() : Rectangle = bounds
}