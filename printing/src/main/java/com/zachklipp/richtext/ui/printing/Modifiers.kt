package com.zachklipp.richtext.ui.printing

import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.offset
import kotlin.math.min

/**
 * A [Modifier] that adds different padding depending on the minimum dimension of the max
 * constraints.
 *
 * This modifier is especially useful when passed to the [Printable] composable, in which case the
 * modifier will be applied to every page of the printed document and can be used to create page
 * margins.
 */
public fun Modifier.responsivePadding(vararg minDimensionsToPadding: Pair<Dp, Dp>): Modifier =
  this.then(object : LayoutModifier {
    override fun MeasureScope.measure(
      measurable: Measurable,
      constraints: Constraints
    ): MeasureResult {
      val minDimension = min(constraints.maxWidth, constraints.maxHeight).toDp()
      val breakpoint = minDimensionsToPadding.reversed().last { it.first >= minDimension }
      val padding = breakpoint.second.roundToPx()
      val paddedConstraints = constraints.offset(-padding * 2, -padding * 2)
      val placeable = measurable.measure(paddedConstraints)
      val width = constraints.constrainWidth(placeable.width + padding)
      val height = constraints.constrainHeight(placeable.height + padding)
      return layout(width, height) {
        placeable.placeRelative(padding, padding)
      }
    }
  })
