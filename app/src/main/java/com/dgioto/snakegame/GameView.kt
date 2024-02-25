package com.dgioto.snakegame

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

@SuppressLint("ViewConstructor")
class GameView(context: Context, private var game: SnakeGame) : View(context) {

    private val paint = Paint()

    fun setGame(newGame: SnakeGame) {
        game = newGame
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the game board
        canvas.drawColor(Color.BLACK)

        // Draw the snake
        paint.color = Color.GREEN
        for (point in game.getSnake()) {
            canvas.drawRect(
                point.x * cellSize().toFloat(),
                point.y * cellSize().toFloat(),
                (point.x + 1) * cellSize().toFloat(),
                (point.y + 1) * cellSize().toFloat(),
                paint
            )
        }

        // Draw the food
        paint.color = Color.RED
        val food = game.getFood()
        canvas.drawRect(
            food.x * cellSize().toFloat(),
            food.y * cellSize().toFloat(),
            (food.x + 1) * cellSize().toFloat(),
            (food.y + 1) * cellSize().toFloat(),
            paint
        )
    }

    private fun cellSize(): Int {
        val width = width
        val height = height
        return if (width / game.width < height / game.height) {
            width / game.width
        } else {
            height / game.height
        }
    }
}