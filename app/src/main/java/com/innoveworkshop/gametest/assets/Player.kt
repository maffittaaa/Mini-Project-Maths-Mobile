package com.innoveworkshop.gametest.assets

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.core.graphics.toColor
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

open class Player(
    position: Vector?,
    width: Float,
    height: Float,
    color: Int

) : Rectangle(position, width, height, color) {

    var velocity: Vector = Vector(0f, 0f);
    var mass: Float = 1f;
    var jumpForce: Float = 370f;
    var friction: Float = 0.7f;
    var speed = 150f;
    var acceleration: Float = 0f;
    var inputForce: Float = 0f;
    var isJumping = false;

    override fun onFixedUpdate() {
        super.onFixedUpdate();

        acceleration = inputForce / mass; //acceleration when press a button (x-directions)
        velocity.x += acceleration * Physics.deltaTime; //add acceleration to the velocity (multiplying with delta time means that it will be the same in all devices)
        velocity.x *= friction; // adding friction so it's not sliding to the horizon
        acceleration = 0f; //after that, resets again
        inputForce = 0f; //after that, resets again

        if (!isJumping) {
            velocity.y += Physics.gravity * Physics.deltaTime; // add gravity to the jump
            Physics.platforms.forEach { rectangle -> GetCollisionWithRectangle(rectangle) };
        }

        position.x += velocity.x; // adding velocity to the player's position (x-directions)
        position.y += velocity.y; //adding velocity to the player's position (y-directions)
        isJumping = false;
    }

    fun jump() {

        if (isOnTheFloor())//if player is on the ground
        {
            velocity.y = -jumpForce / mass * Physics.deltaTime; // subtract the jumpforce to the velocity on the y-directions to go up (if it was +jumpForce, go down instead of up)
            isJumping = true;
        }
    }

    //get the closest point on a rectangle
    fun GetClosestPointOnRectangle(rectangle: Rectangle): Vector {
        var rectX = position.x.coerceIn(rectangle.position.x - rectangle.width / 2, rectangle.position.x + rectangle.width / 2); //on the x-component, in can be between the min and the max value
        var rectY = position.y.coerceIn(rectangle.position.y - rectangle.height / 2, rectangle.position.y + rectangle.height / 2); //on the y-component, in can be between the min and the max value

        return Vector(rectX, rectY); //returns a vector with the coordinates of the closest point
    }

    fun GetCollisionWithRectangle(rectangle: Rectangle): Boolean {
        val closestPoint = GetClosestPointOnRectangle(rectangle);
        var distance = sqrt((position.x - closestPoint.x).pow(2) + (position.y - closestPoint.y).pow(2)); //distance between the position of the player and the closest point on the platforms

        if (distance < width / 2) { //if the player collides with the platform, basically it stops velocity
            var distanceVector = Vector(position.x - closestPoint.x, position.y - closestPoint.y); //calculating the distance Vector between the platform and the player
            var normalize = sqrt((distanceVector.x).pow(2) + (distanceVector.y).pow(2)); //normalize the vector
            var normal = Vector(distanceVector.x / normalize, distanceVector.y / normalize); //vector after normalize
            var dotProduct = (velocity.x * normal.x) + (velocity.y * normal.y); // calculate the dot product to see the difference between the directions of the velocity of the player and the direction of the collision
            var result = Vector (normal.x * dotProduct, normal.y * dotProduct);
            var overlap = width / 2 - distance

            velocity = Vector(velocity.x - result.x, velocity.y - result.y);
            position = Vector(position.x + normal.x * overlap, position.y + normal.y * overlap)

            return true;
        }
        return false;
    }

    fun isOnTheFloor(): Boolean { // isFloored 2.0
        Physics.platforms.forEach{rectangle -> //for each rectangle that is created
            val closestPoint = GetClosestPointOnRectangle(rectangle);
            if (closestPoint.y <= position.y + height / 2 + 1 && abs(position.x - closestPoint.x) <= 1) { // if the player is on the ground
                if (!isJumping)
                    velocity.y = 0f;
                return true;
            }
        };
        return false; // if player is in the air
    }

//    @SuppressLint("DrawAllocation")
//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//        var painter = Paint(Paint.ANTI_ALIAS_FLAG)
//        painter.color = Color.rgb (255f, 0f, 0f);
//        painter.style = Paint.Style.FILL
//
//        Physics.platforms.forEach { rectangle -> //for each rectangle that is created
//            var closestPoint = GetClosestPointOnRectangle(rectangle);
//            canvas!!.drawRect(closestPoint.x - 10, closestPoint.y - 10, closestPoint.x + 10,closestPoint.y + 10, painter)
//        }
//    }
}