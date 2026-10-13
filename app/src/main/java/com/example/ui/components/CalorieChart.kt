package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FitGreenPrimary
import com.example.ui.theme.FitOrangeTertiary
import com.example.viewmodel.DayCalorieStat

@Composable
fun CalorieWeeklyChartCard(
    weeklyStats: List<DayCalorieStat>,
    targetCalories: Int,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Grafik Konsumsi Mingguan",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Perbandingan kalori masuk vs terbakar 7 hari",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Legend
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(FitGreenPrimary)
                        )
                        Text(
                            text = "Masuk",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(FitOrangeTertiary)
                        )
                        Text(
                            text = "Terbakar",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Canvas Bar Chart
            val maxVal = maxOf(
                targetCalories.toFloat(),
                weeklyStats.maxOfOrNull { maxOf(it.consumedCalories, it.burnedCalories).toFloat() } ?: 2000f,
                1500f
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(top = 16.dp)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val w = size.width
                    val h = size.height
                    val barWidth = 14.dp.toPx()
                    val count = weeklyStats.size.coerceAtLeast(1)
                    val spacing = w / count

                    // Draw target guide line
                    val targetY = h - (targetCalories / maxVal) * (h - 25.dp.toPx())
                    drawLine(
                        color = Color.Gray.copy(alpha = 0.25f),
                        start = Offset(0f, targetY),
                        end = Offset(w, targetY),
                        strokeWidth = 2.dp.toPx()
                    )

                    weeklyStats.forEachIndexed { index, stat ->
                        val centerX = spacing * index + spacing / 2

                        val consumedHeight = (stat.consumedCalories / maxVal) * (h - 25.dp.toPx())
                        val burnedHeight = (stat.burnedCalories / maxVal) * (h - 25.dp.toPx())

                        // Consumed bar (Green)
                        val consumedTop = h - 20.dp.toPx() - consumedHeight
                        drawRoundRect(
                            color = FitGreenPrimary,
                            topLeft = Offset(centerX - barWidth - 1.dp.toPx(), consumedTop.coerceAtMost(h - 22.dp.toPx())),
                            size = Size(barWidth, consumedHeight.coerceAtLeast(4.dp.toPx())),
                            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                        )

                        // Burned bar (Orange)
                        val burnedTop = h - 20.dp.toPx() - burnedHeight
                        drawRoundRect(
                            color = FitOrangeTertiary,
                            topLeft = Offset(centerX + 1.dp.toPx(), burnedTop.coerceAtMost(h - 22.dp.toPx())),
                            size = Size(barWidth, burnedHeight.coerceAtLeast(4.dp.toPx())),
                            cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                        )
                    }
                }

                // Day Labels Below
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    weeklyStats.forEach { stat ->
                        Text(
                            text = stat.dayLabel,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
