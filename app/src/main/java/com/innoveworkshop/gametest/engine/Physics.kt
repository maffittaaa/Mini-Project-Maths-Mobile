package com.innoveworkshop.gametest.engine

class Physics {
    val gravity= 90.8f
    var deltaTime = 1f/60f
    var velocity = 0f
    var height = 1000f
    var mass = 10f

    var isJumping = false

    var force: Float = 0f

    fun updateVelocity(){
        velocity += gravity * deltaTime;
    }

    fun updatePosition(initialPosition: Float): Float {
        return initialPosition + (velocity * deltaTime)
    }

    fun jumping(jumpForce: Float) {
        if (!isJumping) {
            velocity = -jumpForce
            isJumping = true
        }
    }
}
