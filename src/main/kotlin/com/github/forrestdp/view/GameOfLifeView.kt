package com.github.forrestdp.view

import com.github.forrestdp.controller.GameOfLifeController
import javafx.event.EventHandler
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import tornadofx.*

private const val CELL_EDGE_SIZE = 15
const val VERTICAL_CELL_COUNT = 30
const val HORIZONTAL_CELL_COUNT = 30

class GameOfLifeView : View() {
    private val controller: GameOfLifeController by inject()

    override val root = vbox {
        gridpane {
            for (i in 0 until VERTICAL_CELL_COUNT) {
                row {
                    for (j in 0 until HORIZONTAL_CELL_COUNT) {
                        stackpane {
                            val rectangle = Rectangle(
                                    CELL_EDGE_SIZE.toDouble() - 2,
                                    CELL_EDGE_SIZE.toDouble() - 2
                            ).apply {
                                fill = Color.BLACK
                                stroke = Color.GRAY
                            }
                            controller.setRectangle(rectangle, i, j)
                            children.add(rectangle)
                            onMouseClicked = EventHandler { event ->
                                controller.onGridElementClick(event, i, j)
                            }
                        }
                    }
                }
            }
        }
        hbox {
            button("Next") {
                onMouseClicked = EventHandler {
                    controller.onNextClick()
                }
            }
            button("Clear") {
                onMouseClicked = EventHandler {
                    controller.onClearClick()
                }
            }
        }
    }
}
 


