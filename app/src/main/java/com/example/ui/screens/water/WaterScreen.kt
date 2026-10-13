package com.example.ui.screens.water

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.UserProfileEntity
import com.example.data.local.WaterLogEntity
import com.example.ui.components.WaterBottleVisual
import com.example.ui.theme.FitAquaSecondary
import com.example.ui.theme.FitGreenPrimary
import com.example.ui.theme.FitOrangeTertiary

@Composable
fun WaterScreen(
    waterLog: WaterLogEntity,
    userProfile: UserProfileEntity,
    recentLogs: List<WaterLogEntity>,
    streakDays: Int,
    onAddWater: (Int) -> Unit,
    onSetTargetGlasses: (Int) -> Unit,
    onUpdateReminder: (Boolean, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var reminderEnabled by remember { mutableStateOf(userProfile.isWaterReminderEnabled) }
    var reminderInterval by remember { mutableIntStateOf(userProfile.waterReminderIntervalHours) }

    val targetGlasses = userProfile.dailyWaterTargetGlasses
    val currentGlasses = waterLog.glasses
    val currentMl = waterLog.amountMl
    val targetMl = if (waterLog.targetMl > 0) waterLog.targetMl else (targetGlasses * 250)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- VISUAL BOTTLE HERO CARD ---
        item {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Pelacak Hidrasi Harian",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Menjaga metabolisme & energi optimal",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Streak Tag
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(FitOrangeTertiary.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Whatshot,
                                contentDescription = null,
                                tint = FitOrangeTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "$streakDays Hari Streak",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = FitOrangeTertiary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Animated Water Bottle Container
                    WaterBottleVisual(
                        currentMl = currentMl,
                        targetMl = targetMl,
                        glasses = currentGlasses,
                        targetGlasses = targetGlasses
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // PRIMARY ACTION BUTTON: +1 GELAS
                    Button(
                        onClick = { onAddWater(250) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = FitAquaSecondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("water_add_glass_button")
                    ) {
                        Icon(imageVector = Icons.Default.WaterDrop, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "+1 Gelas Air (250 ml)",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Quick Log Secondary Buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { onAddWater(500) },
                            modifier = Modifier.weight(1f).testTag("water_add_500ml_btn")
                        ) {
                            Text("+500 ml", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = { onAddWater(100) },
                            modifier = Modifier.weight(1f).testTag("water_add_100ml_btn")
                        ) {
                            Text("+100 ml", fontSize = 12.sp)
                        }

                        OutlinedButton(
                            onClick = { onAddWater(-250) },
                            modifier = Modifier.weight(1f).testTag("water_sub_250ml_btn")
                        ) {
                            Text("-250 ml", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        // --- ATUR TARGET GELAS ---
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Target Minum Harian",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Pilih target sesuai aktivitas dan bobot tubuh Anda:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    val glassOptions = listOf(6, 8, 10, 12)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        glassOptions.forEach { count ->
                            val isSelected = targetGlasses == count
                            FilterChip(
                                selected = isSelected,
                                onClick = { onSetTargetGlasses(count) },
                                label = {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("$count Gelas", fontWeight = FontWeight.Bold)
                                        Text("${count * 250} ml", fontSize = 9.sp)
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FitAquaSecondary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                                ),
                                modifier = Modifier.weight(1f).testTag("target_glass_chip_$count")
                            )
                        }
                    }
                }
            }
        }

        // --- PENGINGAT MINUM OTOMATIS (REMINDER) ---
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier.fillMaxWidth()
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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(FitAquaSecondary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Alarm,
                                    contentDescription = null,
                                    tint = FitAquaSecondary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Pengingat Minum Otomatis",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = if (reminderEnabled) "Aktif setiap $reminderInterval jam" else "Pengingat dinonaktifkan",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (reminderEnabled) FitGreenPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = reminderEnabled,
                            onCheckedChange = {
                                reminderEnabled = it
                                onUpdateReminder(it, reminderInterval)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = FitAquaSecondary,
                                checkedTrackColor = FitAquaSecondary.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier.testTag("water_reminder_switch")
                        )
                    }

                    if (reminderEnabled) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Frekuensi Pengingat:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(1, 2, 3).forEach { hour ->
                                val isSelected = reminderInterval == hour
                                FilterChip(
                                    selected = isSelected,
                                    onClick = {
                                        reminderInterval = hour
                                        onUpdateReminder(reminderEnabled, hour)
                                    },
                                    label = { Text("Tiap $hour Jam", fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = FitAquaSecondary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onSecondary
                                    ),
                                    modifier = Modifier.weight(1f).testTag("reminder_interval_${hour}h")
                                )
                            }
                        }
                    }
                }
            }
        }

        // --- RIWAYAT KONSUMSI BEBERAPA HARI TERAKHIR ---
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Riwayat Hidrasi",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Catatan pemenuhan cairan harian",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    if (recentLogs.isEmpty()) {
                        Text(
                            text = "Belum ada riwayat tercatat.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    } else {
                        recentLogs.take(5).forEach { log ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = log.date,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    val reached = log.amountMl >= log.targetMl && log.targetMl > 0
                                    Text(
                                        text = "${log.amountMl} ml (${log.glasses} gelas)",
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                                        color = if (reached) FitAquaSecondary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    if (reached) {
                                        Text(
                                            text = " ✓",
                                            fontWeight = FontWeight.Bold,
                                            color = FitGreenPrimary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
