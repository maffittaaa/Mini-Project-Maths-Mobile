package com.innoveworkshop.gametest.assets

import com.innoveworkshop.gametest.engine.Vector

class Box {
    companion object{
        var mass: Float = 10f;
        var velocity: Vector? = Vector(0f, 0f);
        var position: Vector = Vector(0f, 0f);
    }
}
