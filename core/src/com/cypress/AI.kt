package com.cypress

import com.badlogic.gdx.ai.*
import com.badlogic.gdx.ai.steer.Steerable
import com.badlogic.gdx.math.Vector2
import com.sun.xml.internal.ws.client.sei.ResponseBuilder

public class a(private val body : ResponseBuilder.Body) : Steerable<Vector2>
{
    override fun setMaxLinearAcceleration(maxLinearAcceleration: Float) {
       this.maxLinearAcceleration = maxLinearAcceleration
    }

    override fun setMaxLinearSpeed(maxLinearSpeed: Float) {

    }

    override fun setMaxAngularAcceleration(maxAngularAcceleration: Float) {
        throw UnsupportedOperationException()
    }

    override fun getMaxLinearAcceleration(): Float {
        throw UnsupportedOperationException()
    }

    override fun getMaxAngularSpeed(): Float {
        throw UnsupportedOperationException()
    }

    override fun getMaxLinearSpeed(): Float {
        throw UnsupportedOperationException()
    }

    override fun getMaxAngularAcceleration(): Float {
        throw UnsupportedOperationException()
    }

    override fun setMaxAngularSpeed(maxAngularSpeed: Float) {
        throw UnsupportedOperationException()
    }

    override fun newVector(): Vector2? {
        throw UnsupportedOperationException()
    }

    override fun angleToVector(outVector: Vector2?, angle: Float): Vector2? {
        throw UnsupportedOperationException()
    }

    override fun getAngularVelocity(): Float {
        throw UnsupportedOperationException()
    }

    override fun getOrientation(): Float {
        throw UnsupportedOperationException()
    }

    override fun isTagged(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun vectorToAngle(vector: Vector2?): Float {
        throw UnsupportedOperationException()
    }

    override fun setTagged(tagged: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun getLinearVelocity(): Vector2? {
        throw UnsupportedOperationException()
    }

    override fun getBoundingRadius(): Float {
        throw UnsupportedOperationException()
    }

    override fun getPosition(): Vector2? {
        throw UnsupportedOperationException()
    }

}