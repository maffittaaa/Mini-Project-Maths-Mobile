package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.assets.Player
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector


class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var leftButton: Button? = null
    protected var rightButton: Button? = null
    var holdingLeft = false;
    var holdingRight = false;

    protected var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)

        setupControls()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupControls() {
        upButton = findViewById<View>(R.id.up_button) as Button
        upButton!!.setOnClickListener { game!!.player!!.jump() }

        leftButton = findViewById<View>(R.id.left_button) as Button
        leftButton!!.setOnTouchListener { view, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    holdingLeft = true
                true}
                MotionEvent.ACTION_UP -> {
                    holdingLeft = false
                true}
                else -> false
            }
        }

        rightButton = findViewById<View>(R.id.right_button) as Button
        rightButton!!.setOnTouchListener { view, motionEvent ->
            when (motionEvent?.action) {
                MotionEvent.ACTION_DOWN -> {
                    holdingRight = true
                true}
                MotionEvent.ACTION_UP -> {
                    holdingRight = false
                true}
                else -> false
            }
        }
    }

    inner class Game : GameObject() {
        var player: Player? = null
        var ground: Rectangle? = null
        var platform1: Rectangle? = null

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            //creating player
            player = Player(
                position = Vector((surface!!.width / 2).toFloat(), (surface.height / 2).toFloat()),
                100f, 100f, Color.rgb(255f, 255f, 224f)
            )
            surface.addGameObject(player!!);

            ground = Rectangle(
                position = Vector((surface.width / 2).toFloat(), (surface.height + 50).toFloat()),
                1500f, 100f, Color.rgb(0f, 0f, 0f)
            )
            surface.addGameObject(ground!!);
            Physics.platforms.add(ground!!);

            //creating 1st platform
            platform1 = Rectangle(
                position = Vector((surface.width - 200).toFloat(), (surface.height - 500).toFloat()),
                800f, 50f, Color.rgb(0f, 0f, 0f)
            )
            surface.addGameObject(platform1!!);
            Physics.platforms.add(platform1!!);
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()

            if (holdingLeft) {
                player!!.inputForce = -player!!.speed
            }

            if (holdingRight) {
                player!!.inputForce = player!!.speed
            }

            player!!.onFixedUpdate()
        }
    }
}


