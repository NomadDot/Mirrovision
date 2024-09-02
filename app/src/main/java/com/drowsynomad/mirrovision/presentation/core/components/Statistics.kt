package com.drowsynomad.mirrovision.presentation.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.drowsynomad.mirrovision.core.createMockCells
import com.drowsynomad.mirrovision.core.lighterColor
import com.drowsynomad.mirrovision.presentation.theme.CategoryMainColor
import com.drowsynomad.mirrovision.presentation.utils.gradientStroke
import com.drowsynomad.mirrovision.presentation.utils.roundBox

/**
 * @author Roman Voloshyn (Created on 25.08.2024)
 */

@Composable
fun HabitProgress(
    modifier: Modifier = Modifier,
    filling: Filling
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(7),
        modifier = modifier.height(80.dp),
        horizontalArrangement = Arrangement.spacedBy(2.3.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(filling.cells, key = { it.dayId }) {
            ProgressCircle(cell = it, color = filling.color, pureAccent = filling.pureAccent)
        }
    }
}

@Composable
fun ProgressCircle(
    modifier: Modifier = Modifier,
    color: CategoryMainColor,
    pureAccent: Int,
    cell: Cell
) {
    val customModifier =
        if(cell.isCurrentDay)
            modifier
                .size(10.5.dp)
                .gradientStroke(width = 1.dp)
                .background(color = cell.calculateShade(color, pureAccent), CircleShape)
        else
            modifier
                .size(10.5.dp)
                .background(color = cell.calculateShade(color, pureAccent), CircleShape)

    Spacer(
        modifier = customModifier
    )
}


enum class CellProgress {
    NO_ACTIVITY, NOT_FINISHED, FINISHED;

    companion object {
        fun calculateProgress(cellAmount: Int, filledCellAmount: Int): CellProgress {
            return when(filledCellAmount.toFloat() / cellAmount.toFloat()) {
                0f -> NO_ACTIVITY
                1f -> FINISHED
                else -> NOT_FINISHED
            }
        }
    }
}

data class Cell(
    val dayId: Long,
    val progress: CellProgress,
    val isCurrentDay: Boolean = false
) {
    companion object {
        private const val SHADE_FRACTION: Double = 0.6
    }

    fun calculateShade(color: CategoryMainColor, pureAccent: Int): Color {
        val lighterColor = lighterColor(pureAccent, SHADE_FRACTION)

        return when (progress) {
            CellProgress.NO_ACTIVITY -> color.pureColor
            CellProgress.NOT_FINISHED -> Color(lighterColor)
            CellProgress.FINISHED -> color.accent.pureColor
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier
            .padding(top = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .roundBox(CategoryMainColor.Blue.pureColor)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .roundBox(Color.White)
            ) {
                HabitProgress(
                    modifier = Modifier.align(Alignment.Center),
                    filling = Filling(
                        color = CategoryMainColor.Blue,
                        cells = createMockCells()
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .roundBox(CategoryMainColor.Pink.pureColor)
        ) {
            Box(modifier = Modifier.roundBox(Color.White)) {
                HabitProgress(
                    modifier = Modifier.fillMaxWidth(),
                    filling = Filling(
                        color = CategoryMainColor.Pink,
                        cells = createMockCells()
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .roundBox(CategoryMainColor.Green.pureColor)
        ) {
            Box(modifier = Modifier.roundBox(Color.White)) {
                HabitProgress(
                    modifier = Modifier.fillMaxWidth(),
                    filling = Filling(
                        color = CategoryMainColor.Green,
                        cells = createMockCells()
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .roundBox(CategoryMainColor.Ocean.pureColor)
        ) {
            Box(modifier = Modifier.roundBox(Color.White)) {
                HabitProgress(
                    modifier = Modifier.fillMaxWidth(),
                    filling = Filling(
                        color = CategoryMainColor.Ocean,
                        cells = createMockCells()
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .roundBox(CategoryMainColor.Purple.pureColor)
        ) {
            Box(modifier = Modifier.roundBox(Color.White)) {
                HabitProgress(
                    modifier = Modifier.fillMaxWidth(),
                    filling = Filling(
                        color = CategoryMainColor.Purple,
                        cells = createMockCells()
                    )
                )
            }
        }
    }
}