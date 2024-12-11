package com.innoveworkshop.gametest.engine

import com.innoveworkshop.gametest.assets.Box

class Physics {
    companion object{ //static variables, can call anywhere without doing physics.
        var gravity = 1.625f //moon gravity
        var deltaTime = 1f/ 60f
        var platforms: MutableList<Rectangle> = mutableListOf();
        var boxes: MutableList<Box> = mutableListOf();
    }
}