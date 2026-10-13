package com.example.ui.components

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.Exercise
import com.example.ui.theme.FitGreenPrimary
import com.example.ui.theme.FitOrangeTertiary
import com.example.viewmodel.ActiveWorkoutState

@Composable
fun WorkoutTimerDialog(
    workoutState: ActiveWorkoutState,
    onPauseResume: () -> Unit,
    onSkipNext: () -> Unit,
    onFinish: () -> Unit,
    onCancel: () -> Unit
) {
    if (!workoutState.isActive || workoutState.program == null) return

    val program = workoutState.program
    val currentExercise: Exercise? = program.exercises.getOrNull(workoutState.currentExerciseIndex)
    val nextExercise: Exercise? = program.exercises.getOrNull(workoutState.currentExerciseIndex + 1)

    Dialog(
        onDismissRequest = { /* Require explicit exit button */ },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = program.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Latihan ${workoutState.currentExerciseIndex + 1} dari ${program.exercises.size}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = onCancel,
                        modifier = Modifier.testTag("workout_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Batalkan Sesi",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Exercise Info or Rest Interval Banner
                if (workoutState.isRestInterval) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = FitOrangeTertiary.copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "WAKTU ISTIRAHAT",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = FitOrangeTertiary,
                                letterSpacing = 1.sp
                            )
                            Text(
                                text = "Tarik napas teratur dan minum sedikit air",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            if (nextExercise != null) {
                                Text(
                                    text = "Selanjutnya: ${nextExercise.name}",
                                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                                    color = FitGreenPrimary,
                                    modifier = Modifier.padding(top = 6.dp)
                                )
                            }
                        }
                    }
                } else if (currentExercise != null) {
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(FitGreenPrimary.copy(alpha = 0.2f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = currentExercise.category,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FitGreenPrimary
                                )
                            }

                            Text(
                                text = currentExercise.name,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            Text(
                                text = "Target: ${currentExercise.targetMuscles}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(top = 4.dp)
                            )

                            if (!currentExercise.isTimerBased) {
                                Text(
                                    text = "Target Repetisi: ${currentExercise.defaultReps}x",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FitGreenPrimary,
                                    modifier = Modifier.padding(top = 6.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Timer Countdown Circle
                Box(
                    modifier = Modifier.size(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val maxSec = if (workoutState.isRestInterval) 20f else (currentExercise?.defaultDurationSec?.toFloat() ?: 30f)
                    val progressRatio = (workoutState.secondsRemaining / maxSec).coerceIn(0f, 1f)

                    CircularProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier.fillMaxSize(),
                        strokeWidth = 12.dp,
                        color = if (workoutState.isRestInterval) FitOrangeTertiary else FitGreenPrimary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${workoutState.secondsRemaining}",
                            fontSize = 54.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (workoutState.isRestInterval) "DETIK ISTIRAHAT" else "DETIK LAGI",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Stats Row: Total Waktu & Estimasi Kalori Terbakar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val minutes = workoutState.totalSecondsElapsed / 60
                        val seconds = workoutState.totalSecondsElapsed % 60
                        val formattedTime = String.format("%02d:%02d", minutes, seconds)
                        Text(
                            text = formattedTime,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Total Durasi",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${workoutState.totalCaloriesBurned} kkal",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = FitOrangeTertiary
                        )
                        Text(
                            text = "Kalori Terbakar",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Player Controls Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Pause / Resume Button
                    Button(
                        onClick = onPauseResume,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (workoutState.isTimerRunning) MaterialTheme.colorScheme.surfaceVariant else FitGreenPrimary,
                            contentColor = if (workoutState.isTimerRunning) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.weight(1f).testTag("workout_pause_resume_button")
                    ) {
                        Icon(
                            imageVector = if (workoutState.isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = if (workoutState.isTimerRunning) "Jeda" else "Lanjut")
                    }

                    // Skip Button
                    OutlinedButton(
                        onClick = onSkipNext,
                        modifier = Modifier.weight(1f).testTag("workout_skip_button")
                    ) {
                        Icon(imageVector = Icons.Default.SkipNext, contentDescription = null)
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Lewati")
                    }

                    // Finish Early Button
                    Button(
                        onClick = onFinish,
                        colors = ButtonDefaults.buttonColors(containerColor = FitGreenPrimary),
                        modifier = Modifier.weight(1.2f).testTag("workout_finish_button")
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                        Spacer(modifier = Modifier.size(6.dp))
                        Text(text = "Selesai")
                    }
                }
            }
        }
    }
}
