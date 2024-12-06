package com.innoveworkshop.gametest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.innoveworkshop.gametest.assets.Player
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector


class MainActivity : AppCompatActivity() {
    protected var gameSurface: GameSurface? = null
    protected var upButton: Button? = null
    protected var downButton: Button? = null
    protected var leftButton: Button? = null
    protected var rightButton: Button? = null
    var velocity = 50f;

    protected var game: Game? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameSurface = findViewById<View>(R.id.gameSurface) as GameSurface
        game = Game()
        gameSurface!!.setRootGameObject(game)

        setupControls()
    }

    private fun setupControls() {
        upButton = findViewById<View>(R.id.up_button) as Button
        upButton!!.setOnClickListener { game!!.player!!.jump() }

        leftButton = findViewById<View>(R.id.left_button) as Button
        leftButton!!.setOnClickListener ({ game!!.player!!.position.x -= velocity })

        rightButton = findViewById<View>(R.id.right_button) as Button
        rightButton!!.setOnClickListener({game!!.player!!.position.x += velocity })
    }

    inner class Game : GameObject() {
        var player: Player? = null
        var rectangle: Rectangle? = null

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            player = Player(
                position = Vector((surface!!.width / 2).toFloat(), (surface.height / 2).toFloat()),
                100f, 100f, Color.rgb(255f, 255f, 224f)
            )

            //setting the level of the ground
            player!!.physics.height = surface.height.toFloat() - (player!!.height / 2)

            surface.addGameObject(player!!) //not null

            rectangle = Rectangle(
                position = Vector((surface.width / 2).toFloat(), (surface.height).toFloat()),
                1500f, 100f, Color.rgb(0f, 0f, 0f)
            )

            surface.addGameObject(rectangle!!)

            rectangle = Rectangle(
                position = Vector((surface.width - 200).toFloat(), (surface.height - 500).toFloat()),
                800f, 50f, Color.rgb(0f, 0f, 0f)
            )

            surface.addGameObject(rectangle!!)
        }

        override fun onFixedUpdate() {
            super.onFixedUpdate()

            player!!.onFixedUpdate()

        }
    }
}


