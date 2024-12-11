package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.ScrollState
import com.innoveworkshop.gametest.assets.Box
import com.innoveworkshop.gametest.assets.Player
import com.innoveworkshop.gametest.engine.GameObject
import com.innoveworkshop.gametest.engine.GameSurface
import com.innoveworkshop.gametest.engine.Physics
import com.innoveworkshop.gametest.engine.Rectangle
import com.innoveworkshop.gametest.engine.Vector
import java.nio.file.WatchEvent.Modifier


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
        var platform2: Rectangle? = null
        var box1: Box? = null
        var box2: Box? = null

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            //creating player
            player = Player(
                position = Vector((surface!!.width / 2).toFloat(), (surface.height / 2).toFloat()),
                90f, 90f, Color.rgb(255f, 255f, 224f)
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
                position = Vector((surface.width - 200).toFloat(), (surface.height - 755).toFloat()),
                800f, 50f, Color.rgb(0f, 0f, 0f)
            )
            surface.addGameObject(platform1!!);
            Physics.platforms.add(platform1!!);

            //creating 2nd platform
            platform2 = Rectangle(
                position = Vector((surface.width - 1200).toFloat(), (surface.height - 1550).toFloat()),
                800f, 50f, Color.rgb(0f, 0f, 0f)
            )
            surface.addGameObject(platform2!!);
            Physics.platforms.add(platform2!!);

            //creating 1st box
            box1 = Box(
                position = Vector((surface.width / 2 - 200).toFloat(), (surface.height - 55).toFloat()),
                120f,120f, Color.rgb(0, 128, 0)
            )
            surface.addGameObject(box1!!);
            Physics.platforms.add(box1!!);
            Physics.boxes.add(box1!!);

            //creating 2nd box
            box2 = Box(
                position = Vector((surface.width - 200).toFloat(), (surface.height - 840).toFloat()),
                120f,120f, Color.rgb(0, 128, 0)
            )
            surface.addGameObject(box2!!);
            Physics.platforms.add(box2!!);
            Physics.boxes.add(box2!!);

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

//            println("player " + player!!.position.y)
//            println("platform " + platform2!!.position.y)
//
//            if ((player!!.position.y + 69.9729).toFloat() == platform2?.position?.y)
//            {
//                platform2!!.position = Vector((gameSurface?.width?.minus(1200)!!.toFloat()), (gameSurface?.height?.minus(755))!!.toFloat()) * Physics.deltaTime
//                platform1!!.position = Vector((gameSurface?.width?.minus(200)!!.toFloat()), (gameSurface?.height?.minus(1550))!!.toFloat()) * Physics.deltaTime
//            }
        }
    }
}


