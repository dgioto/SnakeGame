package com.dgioto.snakegame

import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var gameView: GameView
    private lateinit var game: SnakeGame
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        game = SnakeGame(20, 20) // Adjust dimensions as needed
        gameView = GameView(this, game)
        setContentView(gameView)

        // Start the game loop
        startGameLoop()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val x = event.x
            val y = event.y
            val halfWidth = gameView.width / 2
            val halfHeight = gameView.height / 2

            // Определяем направление движения змейки в зависимости от расположения касания экрана
            if (abs(x - halfWidth) > abs(y - halfHeight)) {
                if (x < halfWidth) {
                    game.changeDirection(SnakeGame.Direction.LEFT)
                } else {
                    game.changeDirection(SnakeGame.Direction.RIGHT)
                }
            } else {
                if (y < halfHeight) {
                    game.changeDirection(SnakeGame.Direction.UP)
                } else {
                    game.changeDirection(SnakeGame.Direction.DOWN)
                }
            }
        }
        return super.onTouchEvent(event)
    }

    private fun startGameLoop() {
        runnable = Runnable {
            if (!game.isGameOver()) {
                game.update()
                gameView.invalidate()
                handler.postDelayed(runnable, 500) // Adjust speed as needed
            } else {
                showGameOverDialog()
            }
        }
        handler.postDelayed(runnable, 100)
    }

    private fun showGameOverDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game Over")
        builder.setMessage("You lost. Do you want to play again?")
        builder.setPositiveButton("Yes") { _, _ ->
            resetGame()
        }
        builder.setNegativeButton("No") { _, _ ->
            finish()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun resetGame() {
        game = SnakeGame(20, 20) // Adjust dimensions as needed
        gameView.setGame(game)
        startGameLoop()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}