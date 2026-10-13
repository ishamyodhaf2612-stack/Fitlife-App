package com.example.ui.screens.profile

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VolunteerActivism
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.local.UserProfileEntity
import com.example.ui.theme.FitAquaSecondary
import com.example.ui.theme.FitGreenPrimary
import com.example.ui.theme.FitOrangeTertiary

@Composable
fun ProfileSettingsDialog(
    profile: UserProfileEntity,
    onSaveProfile: (String, Float, Float, Int, String, String, String, Int?, Int?) -> Unit,
    onExportBackup: ((String) -> Unit) -> Unit,
    onImportBackup: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    var name by remember { mutableStateOf(profile.name) }
    var weightText by remember { mutableStateOf(profile.weightKg.toString()) }
    var heightText by remember { mutableStateOf(profile.heightCm.toString()) }
    var ageText by remember { mutableStateOf(profile.age.toString()) }
    var gender by remember { mutableStateOf(profile.gender) }
    var activityLevel by remember { mutableStateOf(profile.activityLevel) }
    var goal by remember { mutableStateOf(profile.goal) }
    var customCalorieTarget by remember { mutableStateOf(profile.dailyCalorieTarget.toString()) }

    var exportedJsonText by remember { mutableStateOf<String?>(null) }
    var importJsonText by remember { mutableStateOf("") }
    var showImportField by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(FitGreenPrimary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = null, tint = FitGreenPrimary)
                        }
                        Column {
                            Text(
                                text = "Profil & Pengaturan",
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Kalkulator BMR/TDEE & Backup Data",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_profile_dialog")) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                    }
                }

                // 100% GRATIS PROMISE CARD
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FitGreenPrimary.copy(alpha = 0.15f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Shield, contentDescription = null, tint = FitGreenPrimary)
                        Column {
                            Text(
                                text = "100% Gratis Selamanya",
                                fontWeight = FontWeight.Bold,
                                color = FitGreenPrimary,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "Tidak ada akun/login wajib, tanpa langganan, tanpa paywall. Seluruh data Anda tersimpan aman lokal di perangkat.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // --- DATA FISIK & BMR KALKULATOR ---
                Text(
                    text = "Data Fisik & Target Kalori",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nama Lengkap / Panggilan") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("profile_name_input")
                )

                // Gender Selector
                Text(text = "Jenis Kelamin:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    listOf("Pria", "Wanita").forEach { g ->
                        FilterChip(
                            selected = gender == g,
                            onClick = { gender = g },
                            label = { Text(g) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FitGreenPrimary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.weight(1f).testTag("gender_chip_$g")
                        )
                    }
                }

                // Body Measurements Row
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedTextField(
                        value = weightText,
                        onValueChange = { weightText = it },
                        label = { Text("Berat (kg)") },
                        modifier = Modifier.weight(1f).testTag("weight_input")
                    )

                    OutlinedTextField(
                        value = heightText,
                        onValueChange = { heightText = it },
                        label = { Text("Tinggi (cm)") },
                        modifier = Modifier.weight(1f).testTag("height_input")
                    )

                    OutlinedTextField(
                        value = ageText,
                        onValueChange = { ageText = it },
                        label = { Text("Usia (th)") },
                        modifier = Modifier.weight(1f).testTag("age_input")
                    )
                }

                // Activity Level
                Text(text = "Tingkat Aktivitas Harian:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                val activityOptions = listOf(
                    "Sedenter (Jarang Olahraga)",
                    "Ringan (1-3 hari/minggu)",
                    "Sedang (3-5 hari/minggu)",
                    "Sangat Aktif (6-7 hari/minggu)"
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    activityOptions.forEach { act ->
                        FilterChip(
                            selected = activityLevel.startsWith(act.substringBefore(" ")),
                            onClick = { activityLevel = act },
                            label = { Text(act, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FitGreenPrimary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Goal
                Text(text = "Tujuan Kebugaran:", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                val goals = listOf(
                    "Defisit Kalori (Turun Berat)",
                    "Maintain (Jaga Berat)",
                    "Surplus Kalori (Naik Massa Otot)"
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    goals.forEach { gl ->
                        FilterChip(
                            selected = goal == gl,
                            onClick = { goal = gl },
                            label = { Text(gl, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FitGreenPrimary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Target Kalori Harian Manual / Hasil Hitung
                OutlinedTextField(
                    value = customCalorieTarget,
                    onValueChange = { customCalorieTarget = it },
                    label = { Text("Target Kalori Harian (kkal)") },
                    supportingText = { Text("Dihitung otomatis atau sesuaikan manual") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("target_calorie_input")
                )

                // Tombol Simpan Profil
                Button(
                    onClick = {
                        val w = weightText.toFloatOrNull() ?: profile.weightKg
                        val h = heightText.toFloatOrNull() ?: profile.heightCm
                        val a = ageText.toIntOrNull() ?: profile.age
                        val cal = customCalorieTarget.toIntOrNull()

                        onSaveProfile(
                            name.trim().ifEmpty { "Sobat FitLife" },
                            w,
                            h,
                            a,
                            gender,
                            activityLevel,
                            goal,
                            cal,
                            profile.dailyWaterTargetGlasses
                        )
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = FitGreenPrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("save_profile_button")
                ) {
                    Text("Simpan Perubahan Profil", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                // --- BACKUP EXPORT & IMPORT (JSON) ---
                Text(
                    text = "Cadangan Data (Backup & Restore JSON)",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            onExportBackup { json ->
                                exportedJsonText = json
                                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip = ClipData.newPlainText("FitLife_Backup", json)
                                clipboard.setPrimaryClip(clip)
                            }
                        },
                        modifier = Modifier.weight(1f).testTag("export_backup_button")
                    ) {
                        Icon(imageVector = Icons.Default.CloudDownload, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Export JSON", fontSize = 12.sp)
                    }

                    OutlinedButton(
                        onClick = { showImportField = !showImportField },
                        modifier = Modifier.weight(1f).testTag("toggle_import_backup_button")
                    ) {
                        Icon(imageVector = Icons.Default.CloudUpload, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Import JSON", fontSize = 12.sp)
                    }
                }

                // Export result display
                exportedJsonText?.let { json ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "✅ Data Berhasil Disalin ke Clipboard!",
                                fontWeight = FontWeight.Bold,
                                color = FitGreenPrimary,
                                fontSize = 12.sp
                            )
                            Text(
                                text = "Ukuran: ${json.length} karakter. Simpan ke catatan aman untuk cadangan.",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                // Import field
                if (showImportField) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = importJsonText,
                            onValueChange = { importJsonText = it },
                            label = { Text("Tempel (Paste) Kode JSON Backup di sini") },
                            maxLines = 6,
                            modifier = Modifier.fillMaxWidth().testTag("import_json_input")
                        )

                        Button(
                            onClick = {
                                if (importJsonText.isNotBlank()) {
                                    onImportBackup(importJsonText.trim())
                                    showImportField = false
                                }
                            },
                            enabled = importJsonText.isNotBlank(),
                            colors = ButtonDefaults.buttonColors(containerColor = FitAquaSecondary),
                            modifier = Modifier.fillMaxWidth().testTag("confirm_import_button")
                        ) {
                            Text("Terapkan & Pulihkan Data", color = MaterialTheme.colorScheme.onSecondary)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
