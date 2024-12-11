package com.innoveworkshop.gametest

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2.ScrollState
import com.innoveworkshop.gametest.assets.Box
import com.innoveworkshop.gametest.assets.Door
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
        var platform3: Rectangle? = null
        var platform4: Rectangle? = null
        var platform5: Rectangle? = null

        var box1: Box? = null
        var box2: Box? = null
        var box3: Box? = null
        var box4: Box? = null
        var box5: Box? = null

        var door1: Door? = null
        var door2: Door? = null
        var door3: Door? = null

        override fun onStart(surface: GameSurface?) {
            super.onStart(surface)

            //creating player
            player = Player(
                position = Vector((surface!!.width / 2).toFloat(), (surface.height / 2).toFloat()),
                90f, 90f, Color.rgb(255, 255, 224)
            )
            surface.addGameObject(player!!);

            ground = Rectangle(
                position = Vector((surface.width / 2).toFloat(), (surface.height + 50).toFloat()),
                1500f, 100f, Color.rgb(0, 0, 0)
            )
            surface.addGameObject(ground!!);
            Physics.platforms.add(ground!!);

            //creating 1st platform
            platform1 = Rectangle(
                position = Vector((surface.width - 200).toFloat(), (surface.height - 755).toFloat()),
                800f, 50f, Color.rgb(0, 0, 0)
            )
            surface.addGameObject(platform1!!);
            Physics.platforms.add(platform1!!);

            //creating 2nd platform
            platform2 = Rectangle(
                position = Vector((surface.width - 1200).toFloat(), (surface.height - 1550).toFloat()),
                800f, 50f, Color.rgb(0, 0, 0)
            )
            surface.addGameObject(platform2!!);
            Physics.platforms.add(platform2!!);

            //creating 1st box
            box1 = Box(
                position = Vector((surface.width / 2 - 400).toFloat(), (surface.height - 55).toFloat()),
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

            //creating door to 2nd level
            door1 = Door(
                position = Vector((surface.width - 1200).toFloat(), (surface.height - 1650).toFloat()),
                140f,140f, Color.rgb(128f, 0f, 0f)
            )
            surface.addGameObject(door1!!);
            Physics.platforms.add(door1!!);
            Physics.doors.add(door1!!);

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

            println("Player: " + player!!.position.y)
            println("Platform " + platform2!!.position.y)

            if (player!!.GoingToNextLevel(door1))
                Level2();
            else if (player!!.GoingToNextLevel(door2)) {
                Level3();
            }

        }

        fun Level2()
        {
            //removing platforms from list and destroying them
            Physics.platforms.remove(platform1!!);
            Physics.platforms.remove(platform2!!);
            platform1!!.destroy();
            platform2!!.destroy();

            //removing door from list and destroying it
            Physics.platforms.remove(door1!!);
            Physics.doors.remove(door1!!);
            door1!!.destroy();

            //removing boxes from list and destroying it
            Physics.platforms.remove(box1!!);
            Physics.platforms.remove(box2!!);
            Physics.boxes.remove(box1!!);
            Physics.boxes.remove(box2!!);
            box1!!.destroy();
            box2!!.destroy();

            //creating 3rd platform
            platform3 = Rectangle(
                position = Vector((gameSurface!!.width - 1200).toFloat(), (gameSurface!!.height - 750).toFloat()),
                800f, 50f, Color.rgb(0f, 0f, 0f)
            )
            gameSurface?.addGameObject(platform3!!);
            Physics.platforms.add(platform3!!);

            //creating 4th platform
            platform4 = Rectangle(
                position = Vector((gameSurface!!.width - 200).toFloat(), (gameSurface!!.height - 1500).toFloat()),
                800f, 50f, Color.rgb(0f, 0f, 0f)
            )
            gameSurface?.addGameObject(platform4!!);
            Physics.platforms.add(platform4!!);

            //creating 3rd box
            box3 = Box(
                position = Vector((gameSurface!!.width / 2 + 500).toFloat(), (gameSurface!!.height - 55).toFloat()),
                120f,120f, Color.rgb(0, 128, 0)
            )
            gameSurface?.addGameObject(box3!!);
            Physics.platforms.add(box3!!);
            Physics.boxes.add(box3!!);

            //creating 4th box
            box4 = Box(
                position = Vector((gameSurface!!.width / 2 - 450).toFloat(), (gameSurface!!.height - 840).toFloat()),
                120f,120f, Color.rgb(0, 128, 0)
            )
            gameSurface?.addGameObject(box4!!);
            Physics.platforms.add(box4!!);
            Physics.boxes.add(box4!!);

            //creating 2nd door
            door2 = Door(
                position = Vector((gameSurface!!.width - 200).toFloat(), (gameSurface!!.height - 1600).toFloat()),
                140f,140f, Color.rgb(128f, 0f, 0f)
            )
            gameSurface!!.addGameObject(door2!!);
            Physics.platforms.add(door2!!);
            Physics.doors.add(door2!!);

            player!!.position = Vector ((gameSurface!!.width / 2).toFloat(), (gameSurface!!.height / 2).toFloat())
        }

        fun Level3()
        {
            //removing platforms from list and destroying them
            Physics.platforms.remove(platform3!!);
            Physics.platforms.remove(platform4!!);
            platform3!!.destroy();
            platform4!!.destroy();

            //removing door from list and destroying it
            Physics.platforms.remove(door2!!);
            Physics.doors.remove(door2!!);
            door2!!.destroy();

            //removing boxes from list and destroying it
            Physics.platforms.remove(box3!!);
            Physics.platforms.remove(box4!!);
            Physics.boxes.remove(box3!!);
            Physics.boxes.remove(box4!!);
            box3!!.destroy();
            box4!!.destroy();

            //creating 5th platform
            platform5 = Rectangle(
                position = Vector((gameSurface!!.width - 200).toFloat(), (gameSurface!!.height - 755).toFloat()),
                800f, 50f, Color.rgb(0, 0, 0)
            )
            gameSurface?.addGameObject(platform5!!);
            Physics.platforms.add(platform5!!);

            //creating 5th box
            box5 = Box(
                position = Vector((gameSurface!!.width / 2 + 500).toFloat(), (gameSurface!!.height - 55).toFloat()),
                120f,120f, Color.rgb(0, 128, 0)
            )
            gameSurface?.addGameObject(box5!!);
            Physics.platforms.add(box5!!);
            Physics.boxes.add(box5!!);

            //creating 3rd door
            door3 = Door(
                position = Vector((gameSurface!!.width - 1000).toFloat(), (gameSurface!!.height - 1600).toFloat()),
                140f,140f, Color.rgb(128f, 0f, 0f)
            )
            gameSurface!!.addGameObject(door3!!);
            Physics.platforms.add(door3!!);
            Physics.doors.add(door3!!);

            player!!.position = Vector ((gameSurface!!.width / 2).toFloat(), (gameSurface!!.height / 2).toFloat())
        }
    }
}


