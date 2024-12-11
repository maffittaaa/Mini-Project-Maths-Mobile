package com.innoveworkshop.gametest.assets

import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector


class Box (
    position: Vector?,
    width: Float,
    height: Float,
    color: Int
) : Rectangle(position, width, height, color) {

    var mass: Float = 2f;
    var velocity: Vector = Vector (0f, 0f)
}

