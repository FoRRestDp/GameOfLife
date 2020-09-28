package com.github.forrestdp.controller

import com.github.forrestdp.model.Cell
import com.github.forrestdp.model.GameOfLifeGrid
import com.github.forrestdp.model.produceNextGeneration
import com.github.forrestdp.view.HORIZONTAL_CELL_COUNT
import com.github.forrestdp.view.VERTICAL_CELL_COUNT
import javafx.scene.input.MouseEvent
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*

class GameOfLifeController() : Controller() {
    private val grid = GameOfLifeGrid(VERTICAL_CELL_COUNT, HORIZONTAL_CELL_COUNT)
    private val rectangles = Array<Array<Rectangle?>>(VERTICAL_CELL_COUNT) { Array(HORIZONTAL_CELL_COUNT) { null } }
    fun setRectangle(rectangle: Rectangle, coordinateI: Int, coordinateJ: Int) {
        rectangles[coordinateI][coordinateJ] = rectangle
    }
     fun onGridElementClick(event: MouseEvent, i: Int, j: Int) {
        val gridCell = Cell(i, j)
        if (grid.contains(gridCell)) {
            ((event.source as StackPane).children[0] as Rectangle).fill = Color.BLACK
            grid.remove(gridCell)
        } else {
            ((event.source as StackPane).children[0] as Rectangle).fill = Color.YELLOW
            grid.add(Cell(i, j))
        }
    }
    
    fun onNextClick() {
        val (addedCells, removedCells) = grid.produceNextGeneration()
        for (entry in removedCells) {
            val rectangle = rectangles[entry.i][entry.j]
            rectangle?.fill = Color.BLACK
        }
        for (entry in addedCells) {
            val rectangle = rectangles[entry.i][entry.j]
            rectangle?.fill = Color.YELLOW
        }
    }
    
        fun onClearClick() {
        grid.clear()
        for (row in rectangles) {
            for (rectangle in row) {
                rectangle?.fill = Color.BLACK
            }
        }
    }
}