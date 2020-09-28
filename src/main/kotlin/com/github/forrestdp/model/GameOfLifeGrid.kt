package com.github.forrestdp.model

import java.util.*

class GameOfLifeGrid(val horizontalCellCount: Int, val verticalCellCount: Int) : TreeSet<Cell>({ first, second ->
    when {
        first.i > second.i -> 1
        first.i < second.i -> -1
        first.j > second.j -> 1
        first.j < second.j -> -1
        else -> 0
    }
})

fun GameOfLifeGrid.produceNextGeneration(): NextGenerationProductionResult {
    val toRemove = mutableListOf<Cell>()
    val toAdd = mutableListOf<Cell>()

    for (cell in this) {
        val neighbours = getNeighbouringCells(cell, isCenterCellIncluded = true)
        for (neighbourCell in neighbours) {
            val neighboursOfNeighbours = getNeighbouringCells(neighbourCell, isCenterCellIncluded = false)

            val numberOfAliveNeighbours = neighboursOfNeighbours.count { this.contains(it) }
            when {
                numberOfAliveNeighbours < 2 || numberOfAliveNeighbours > 3 -> {
                    toRemove.add(neighbourCell)
                }
                numberOfAliveNeighbours == 3 -> {
                    toAdd.add(neighbourCell)
                }
            }
        }
    }
    this.removeAll(toRemove)
    this.addAll(toAdd)
    return NextGenerationProductionResult(toAdd, toRemove)
}

private fun GameOfLifeGrid.getNeighbouringCells(cell: Cell, isCenterCellIncluded: Boolean): List<Cell> {
    val i = cell.i
    val j = cell.j
    val prevI = if (i - 1 >= 0) i - 1 else verticalCellCount - 1
    val prevJ = if (j - 1 >= 0) j - 1 else horizontalCellCount - 1
    val nextI = if (i + 1 < verticalCellCount) i + 1 else 0
    val nextJ = if (j + 1 < horizontalCellCount) j + 1 else 0
    return if (isCenterCellIncluded)
        listOf(prevI, i, nextI)
                .flatMap { indexI -> listOf(prevJ, j, nextJ).map { indexJ -> Cell(indexI, indexJ) } }
    else
        listOf(prevI, i, nextI)
                .flatMap { indexI -> listOf(prevJ, j, nextJ).map { indexJ -> Cell(indexI, indexJ) } }
                .filterNot { it.i == i && it.j == j }
}