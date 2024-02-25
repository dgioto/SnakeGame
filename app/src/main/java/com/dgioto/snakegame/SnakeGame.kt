package com.dgioto.snakegame

import java.util.LinkedList
import java.util.Random

class SnakeGame(val width: Int, val height: Int) {

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }

    data class Point(var x: Int, var y: Int)

    private var snake: LinkedList<Point> = LinkedList()
    private var food: Point = Point(0, 0)
    private var direction: Direction = Direction.RIGHT
    private var isGameOver: Boolean = false

    init {
        // Initialize the snake at the center of the board
        val startX = width / 2
        val startY = height / 2
        snake.add(Point(startX, startY))

        // Place initial food
        spawnFood()
    }

    fun update() {
        if (!isGameOver) {
            // Move the snake
            val head = snake.first
            val newHead = Point(head.x, head.y)
            when (direction) {
                Direction.UP -> newHead.y--
                Direction.DOWN -> newHead.y++
                Direction.LEFT -> newHead.x--
                Direction.RIGHT -> newHead.x++
            }

            // Check for collision with walls or itself
            if (newHead.x < 0 || newHead.x >= width || newHead.y < 0 || newHead.y >= height
                || snake.contains(newHead)) {
                isGameOver = true
                return
            }

            // Check for food
            if (newHead.x == food.x && newHead.y == food.y) {
                // Grow the snake
                snake.addFirst(newHead)
                spawnFood()
            } else {
                // Move the snake
                snake.addFirst(newHead)
                snake.removeLast()
            }
        }
    }

    fun changeDirection(newDirection: Direction) {
        // Prevent the snake from reversing direction
        if (direction != newDirection.opposite()) {
            direction = newDirection
        }
    }

    fun getSnake(): List<Point> {
        return snake.toList()
    }

    fun getFood(): Point {
        return food
    }

    fun isGameOver(): Boolean {
        return isGameOver
    }

    private fun spawnFood() {
        val random = Random()
        var foodX: Int
        var foodY: Int
        do {
            foodX = random.nextInt(width)
            foodY = random.nextInt(height)
        } while (snake.contains(Point(foodX, foodY)))
        food = Point(foodX, foodY)
    }

    private fun Direction.opposite(): Direction {
        return when (this) {
            Direction.UP -> Direction.DOWN
            Direction.DOWN -> Direction.UP
            Direction.LEFT -> Direction.RIGHT
            Direction.RIGHT -> Direction.LEFT
        }
    }
}