package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FitAquaSecondary

@Composable
fun WaterBottleVisual(
    currentMl: Int,
    targetMl: Int,
    glasses: Int,
    targetGlasses: Int,
    modifier: Modifier = Modifier
) {
    val progress = if (targetMl > 0) (currentMl.toFloat() / targetMl).coerceIn(0f, 1.25f) else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress.coerceAtMost(1f),
        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
        label = "waterFill"
    )

    Box(
        modifier = modifier
            .width(170.dp)
            .height(230.dp),
        contentAlignment = Alignment.Center
    ) {
        // Bottle Canvas
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Bottle neck & body geometry
            val capWidth = w * 0.32f
            val capHeight = h * 0.08f
            val capLeft = (w - capWidth) / 2

            val bodyTop = capHeight + h * 0.04f
            val bodyHeight = h - bodyTop - 6.dp.toPx()
            val bodyWidth = w * 0.76f
            val bodyLeft = (w - bodyWidth) / 2
            val cornerRad = 24.dp.toPx()

            // Outer bottle outline & subtle glass background
            drawRoundRect(
                color = Color(0xFF1E3A45).copy(alpha = 0.35f),
                topLeft = Offset(bodyLeft, bodyTop),
                size = Size(bodyWidth, bodyHeight),
                cornerRadius = CornerRadius(cornerRad, cornerRad)
            )

            // Bottle Cap
            drawRoundRect(
                color = Color(0xFF375461),
                topLeft = Offset(capLeft, 4.dp.toPx()),
                size = Size(capWidth, capHeight),
                cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
            )

            // Water Liquid Fill
            if (animatedProgress > 0f) {
                val fillHeight = bodyHeight * animatedProgress
                val fillTop = bodyTop + bodyHeight - fillHeight

                // Clip to rounded bottom bottle using path
                val waterPath = Path().apply {
                    addRoundRect(
                        androidx.compose.ui.geometry.RoundRect(
                            left = bodyLeft + 2.dp.toPx(),
                            top = fillTop,
                            right = bodyLeft + bodyWidth - 2.dp.toPx(),
                            bottom = bodyTop + bodyHeight - 2.dp.toPx(),
                            bottomLeftCornerRadius = CornerRadius(cornerRad - 2.dp.toPx(), cornerRad - 2.dp.toPx()),
                            bottomRightCornerRadius = CornerRadius(cornerRad - 2.dp.toPx(), cornerRad - 2.dp.toPx())
                        )
                    )
                }

                drawPath(
                    path = waterPath,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            FitAquaSecondary.copy(alpha = 0.85f),
                            Color(0xFF0091EA)
                        ),
                        startY = fillTop,
                        endY = bodyTop + bodyHeight
                    )
                )

                // Water Surface Wave highlight
                drawLine(
                    color = Color.White.copy(alpha = 0.5f),
                    start = Offset(bodyLeft + 10.dp.toPx(), fillTop),
                    end = Offset(bodyLeft + bodyWidth - 10.dp.toPx(), fillTop),
                    strokeWidth = 3.dp.toPx()
                )
            }

            // Measurement marks on the side of bottle
            val markCount = 4
            for (i in 1 until markCount) {
                val markY = bodyTop + (bodyHeight / markCount) * i
                drawLine(
                    color = Color.White.copy(alpha = 0.2f),
                    start = Offset(bodyLeft + 6.dp.toPx(), markY),
                    end = Offset(bodyLeft + 16.dp.toPx(), markY),
                    strokeWidth = 2.dp.toPx()
                )
            }

            // Outer glass border stroke
            drawRoundRect(
                color = FitAquaSecondary.copy(alpha = 0.45f),
                topLeft = Offset(bodyLeft, bodyTop),
                size = Size(bodyWidth, bodyHeight),
                cornerRadius = CornerRadius(cornerRad, cornerRad),
                style = Stroke(width = 3.dp.toPx())
            )
        }

        // Inner Text Content: Progress % and ml
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {
            val percentage = (progress * 100).toInt()
            Text(
                text = "$percentage%",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (percentage > 50) Color.White else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "$currentMl / $targetMl ml",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (percentage > 50) Color.White.copy(alpha = 0.9f) else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "$glasses dari $targetGlasses Gelas",
                fontSize = 11.sp,
                color = if (percentage > 50) Color.White.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
