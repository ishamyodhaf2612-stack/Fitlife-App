package com.example.ui.screens.calorie

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.FoodEntity
import com.example.data.local.MealLogEntity
import com.example.data.local.UserProfileEntity
import com.example.ui.components.CalorieWeeklyChartCard
import com.example.ui.theme.FitAquaSecondary
import com.example.ui.theme.FitGreenPrimary
import com.example.ui.theme.FitOrangeTertiary
import com.example.viewmodel.DayCalorieStat

@Composable
fun CalorieScreen(
    userProfile: UserProfileEntity,
    mealLogs: List<MealLogEntity>,
    totalConsumed: Int,
    totalBurned: Int,
    weeklyStats: List<DayCalorieStat>,
    foodSearchResults: List<FoodEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddMeal: (FoodEntity, String, Float) -> Unit,
    onAddCustomFood: (String, String, Int, Float, Float, Float, String, String?) -> Unit,
    onDeleteMealLog: (Long) -> Unit,
    onOpenBmrCalculator: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showAddFoodDialogForMealType by remember { mutableStateOf<String?>(null) }

    val targetCalories = userProfile.dailyCalorieTarget
    val netCalories = totalConsumed - totalBurned
    val remainingCalories = targetCalories - netCalories

    // Macro sums
    val totalProtein = mealLogs.sumOf { (it.protein * it.servingCount).toDouble() }.toFloat()
    val totalCarbs = mealLogs.sumOf { (it.carbs * it.servingCount).toDouble() }.toFloat()
    val totalFat = mealLogs.sumOf { (it.fat * it.servingCount).toDouble() }.toFloat()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- SUMMARY HERO CARD ---
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
                        .padding(18.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Ringkasan Kalori Harian",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Goal: ${userProfile.goal}",
                                style = MaterialTheme.typography.labelSmall,
                                color = FitGreenPrimary
                            )
                        }

                        TextButton(
                            onClick = onOpenBmrCalculator,
                            modifier = Modifier.testTag("calorie_calc_bmr_btn")
                        ) {
                            Text(text = "Ubah Target", fontSize = 12.sp, color = FitGreenPrimary)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3-Pillar Stat Box: Masuk, Terbakar, Sisa
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        StatPillar(
                            title = "Kalori Masuk",
                            value = "$totalConsumed",
                            unit = "kkal",
                            valueColor = FitGreenPrimary
                        )

                        StatPillar(
                            title = "Terbakar",
                            value = "-$totalBurned",
                            unit = "kkal",
                            valueColor = FitOrangeTertiary
                        )

                        StatPillar(
                            title = "Sisa Kalori",
                            value = "$remainingCalories",
                            unit = "kkal",
                            valueColor = if (remainingCalories >= 0) MaterialTheme.colorScheme.onSurface else FitOrangeTertiary
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Progress Bar
                    val progressRatio = if (targetCalories > 0) (totalConsumed.toFloat() / targetCalories).coerceIn(0f, 1f) else 0f
                    LinearProgressIndicator(
                        progress = { progressRatio },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = if (totalConsumed > targetCalories) FitOrangeTertiary else FitGreenPrimary,
                        trackColor = MaterialTheme.colorScheme.surface
                    )

                    // Macro Pills
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        MacroBadge(name = "Protein", grams = totalProtein, color = FitAquaSecondary)
                        MacroBadge(name = "Karbohidrat", grams = totalCarbs, color = FitGreenPrimary)
                        MacroBadge(name = "Lemak", grams = totalFat, color = FitOrangeTertiary)
                    }
                }
            }
        }

        // --- 4 MEAL SECTIONS: Sarapan, Makan Siang, Makan Malam, Camilan ---
        val mealSections = listOf(
            Triple("SARAPAN", "Sarapan Pagi", Icons.Default.WbSunny),
            Triple("MAKAN_SIANG", "Makan Siang", Icons.Default.Restaurant),
            Triple("MAKAN_MALAM", "Makan Malam", Icons.Default.Fastfood),
            Triple("CAMILAN", "Snack / Camilan", Icons.Default.LocalFireDepartment)
        )

        items(mealSections) { (typeKey, title, icon) ->
            val sectionMeals = mealLogs.filter { it.mealType.equals(typeKey, ignoreCase = true) }
            val sectionTotalCal = sectionMeals.sumOf { it.calories }

            MealSectionCard(
                title = title,
                icon = icon,
                totalCalories = sectionTotalCal,
                meals = sectionMeals,
                onAddClick = { showAddFoodDialogForMealType = typeKey },
                onDeleteMeal = onDeleteMealLog
            )
        }

        // --- WEEKLY CHART CARD ---
        item {
            CalorieWeeklyChartCard(
                weeklyStats = weeklyStats,
                targetCalories = targetCalories,
                modifier = Modifier.padding(bottom = 20.dp)
            )
        }
    }

    // Dialog Tambah Makanan (Database 100+ Makanan Indonesia & Custom)
    showAddFoodDialogForMealType?.let { mealType ->
        AddFoodModalDialog(
            mealType = mealType,
            foodSearchResults = foodSearchResults,
            searchQuery = searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onAddMeal = { food, serving ->
                onAddMeal(food, mealType, serving)
                showAddFoodDialogForMealType = null
            },
            onAddCustom = { name, cat, cal, prot, carb, fat, unit ->
                onAddCustomFood(name, cat, cal, prot, carb, fat, unit, mealType)
                showAddFoodDialogForMealType = null
            },
            onDismiss = { showAddFoodDialogForMealType = null }
        )
    }
}

@Composable
private fun StatPillar(
    title: String,
    value: String,
    unit: String,
    valueColor: androidx.compose.ui.graphics.Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
            Text(
                text = " $unit",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}

@Composable
private fun MacroBadge(
    name: String,
    grams: Float,
    color: androidx.compose.ui.graphics.Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = "$name: ${String.format("%.1f", grams)}g",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MealSectionCard(
    title: String,
    icon: ImageVector,
    totalCalories: Int,
    meals: List<MealLogEntity>,
    onAddClick: () -> Unit,
    onDeleteMeal: (Long) -> Unit
) {
    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
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
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(FitGreenPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = icon, contentDescription = null, tint = FitGreenPrimary, modifier = Modifier.size(18.dp))
                    }
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "$totalCalories kkal",
                            style = MaterialTheme.typography.bodySmall,
                            color = FitGreenPrimary
                        )
                    }
                }

                IconButton(
                    onClick = onAddClick,
                    modifier = Modifier.testTag("add_food_btn_${title.lowercase().replace(" ", "_")}")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Tambah Makanan", tint = FitGreenPrimary)
                }
            }

            if (meals.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                meals.forEach { meal ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = meal.foodName,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "${meal.calories} kkal • P: ${String.format("%.1f", meal.protein)}g • K: ${String.format("%.1f", meal.carbs)}g • L: ${String.format("%.1f", meal.fat)}g",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        IconButton(
                            onClick = { onDeleteMeal(meal.id) },
                            modifier = Modifier.size(32.dp).testTag("delete_meal_${meal.id}")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Hapus",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AddFoodModalDialog(
    mealType: String,
    foodSearchResults: List<FoodEntity>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddMeal: (FoodEntity, Float) -> Unit,
    onAddCustom: (String, String, Int, Float, Float, Float, String) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0=Katalog Lokal 100+, 1=Custom Manual

    // Custom food form state
    var customName by remember { mutableStateOf("") }
    var customCategory by remember { mutableStateOf("Makanan Pokok") }
    var customCalories by remember { mutableStateOf("") }
    var customProtein by remember { mutableStateOf("") }
    var customCarbs by remember { mutableStateOf("") }
    var customFat by remember { mutableStateOf("") }
    var customServingUnit by remember { mutableStateOf("1 porsi") }

    // Category filter for Catalog
    var selectedCategoryFilter by remember { mutableStateOf<String?>(null) }
    val categories = listOf("Semua", "Makanan Pokok", "Lauk Pauk", "Sayuran & Sup", "Camilan & Gorengan", "Minuman", "Buah")

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .height(560.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Modal Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tambah Makanan ke $mealType",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Tutup")
                    }
                }

                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = MaterialTheme.colorScheme.surface
                ) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                        text = { Text("Database Lokal 100+", fontSize = 12.sp) }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                        text = { Text("+ Custom Makanan", fontSize = 12.sp) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (selectedTab == 0) {
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = onSearchQueryChange,
                        placeholder = { Text("Cari nasi goreng, rendang, sate...", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(onClick = { onSearchQueryChange("") }) {
                                    Icon(Icons.Default.Close, contentDescription = "Hapus", modifier = Modifier.size(16.dp))
                                }
                            }
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("search_food_input")
                    )

                    // Category Filter Chips
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(categories) { cat ->
                            val isSelected = (cat == "Semua" && selectedCategoryFilter == null) || (cat == selectedCategoryFilter)
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategoryFilter = if (cat == "Semua") null else cat },
                                label = { Text(cat, fontSize = 11.sp) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FitGreenPrimary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    // Food List
                    val filtered = foodSearchResults.filter {
                        selectedCategoryFilter == null || it.category == selectedCategoryFilter
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(filtered) { food ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                                    .clickable { onAddMeal(food, 1.0f) }
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = food.name,
                                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "${food.servingUnit} • ${food.category}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                    Text(
                                        text = "P: ${food.protein}g | K: ${food.carbs}g | L: ${food.fat}g",
                                        fontSize = 10.sp,
                                        color = FitGreenPrimary
                                    )
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "${food.calories} kkal",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = FitGreenPrimary
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = "Pilih",
                                        tint = FitGreenPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Custom Food Input Form
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        item {
                            OutlinedTextField(
                                value = customName,
                                onValueChange = { customName = it },
                                label = { Text("Nama Makanan / Minuman") },
                                modifier = Modifier.fillMaxWidth().testTag("custom_food_name")
                            )
                        }

                        item {
                            OutlinedTextField(
                                value = customCalories,
                                onValueChange = { customCalories = it },
                                label = { Text("Jumlah Kalori (kkal)") },
                                modifier = Modifier.fillMaxWidth().testTag("custom_food_calories")
                            )
                        }

                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = customProtein,
                                    onValueChange = { customProtein = it },
                                    label = { Text("Protein (g)") },
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = customCarbs,
                                    onValueChange = { customCarbs = it },
                                    label = { Text("Karbo (g)") },
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = customFat,
                                    onValueChange = { customFat = it },
                                    label = { Text("Lemak (g)") },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }

                        item {
                            OutlinedTextField(
                                value = customServingUnit,
                                onValueChange = { customServingUnit = it },
                                label = { Text("Takaran (misal: 1 porsi / 1 mangkok)") },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item {
                            Button(
                                onClick = {
                                    val calInt = customCalories.toIntOrNull() ?: 0
                                    val protFloat = customProtein.toFloatOrNull() ?: 0f
                                    val carbFloat = customCarbs.toFloatOrNull() ?: 0f
                                    val fatFloat = customFat.toFloatOrNull() ?: 0f
                                    if (customName.isNotBlank() && calInt > 0) {
                                        onAddCustom(
                                            customName.trim(),
                                            customCategory,
                                            calInt,
                                            protFloat,
                                            carbFloat,
                                            fatFloat,
                                            customServingUnit.trim().ifEmpty { "1 porsi" }
                                        )
                                    }
                                },
                                enabled = customName.isNotBlank() && (customCalories.toIntOrNull() ?: 0) > 0,
                                colors = ButtonDefaults.buttonColors(containerColor = FitGreenPrimary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 10.dp)
                                    .testTag("submit_custom_food_btn")
                            ) {
                                Text("Simpan & Catat ke $mealType")
                            }
                        }
                    }
                }
            }
        }
    }
}
