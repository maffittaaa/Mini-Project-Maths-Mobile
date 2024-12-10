package com.innoveworkshop.gametest.engine

class Physics {
    companion object{ //static variables, can call anywhere without doing physics.
        var gravity = 1.625f //moon gravity
        var deltaTime = 1f/ 60f
        var platforms: MutableList<Rectangle> = mutableListOf();
        var boxes: MutableList<Rectangle> = mutableListOf();
    }
}