package com.innoveworkshop.gametest.assets

import android.util.Log
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector

open class Player(
    position: Vector?,
    width: Float,
    height: Float,
    color: Int

) : Rectangle(position, width, height, color) {

    var time = 0f;
    var physics = Physics();

    override fun onFixedUpdate() {
        super.onFixedUpdate()

        time += physics.deltaTime;
        physics.updateVelocity() //update velocity
        position.y = physics.updatePosition(position.y) //update position of the player

        if (position.y >= physics.height - 65) { //when the player hits the platform, velocity and time turns to 0
            position.y = physics.height - 65
            time = 0f;
            physics.velocity = 0f;
            physics.isJumping = false;
        }
    }

    fun jump() {
        physics.jumping(jumpForce = 300f)
    }
}