package com.example.data.model

data class DietPlanRecommendation(
    val id: String,
    val title: String,
    val subtitle: String,
    val targetCalories: String,
    val macroSplit: String,
    val description: String,
    val suitableFor: String,
    val breakfastSample: String,
    val lunchSample: String,
    val dinnerSample: String,
    val snackSample: String
)

data class DayMealPlan(
    val dayName: String,
    val breakfast: String,
    val morningSnack: String,
    val lunch: String,
    val afternoonSnack: String,
    val dinner: String,
    val totalCaloriesEst: Int
)

object DietRecommendationsData {

    val plans: List<DietPlanRecommendation> = listOf(
        DietPlanRecommendation(
            id = "deficit",
            title = "Defisit Kalori Seimbang",
            subtitle = "Target penurunan lemak bertahap tanpa rasa lapar berlebih",
            targetCalories = "1.500 - 1.800 kkal / hari",
            macroSplit = "45% Karbo | 30% Protein | 25% Lemak",
            description = "Pola makan ramah metabolisme dengan memperbanyak serat sayuran, protein sedang-tinggi, dan mengurangi gorengan atau gula sederhana.",
            suitableFor = "Ingin menurunkan berat badan secara sehat dan berkelanjutan",
            breakfastSample = "Oatmeal 40g + 1 buah pisang + 1 butir telur rebus (290 kkal)",
            lunchSample = "Nasi merah 100g + Dada ayam bakar 100g + Sayur bening bayam jagung (340 kkal)",
            dinnerSample = "Ikan nila bakar kecap + Tumis kangkung + 1 potong tempe bacem (390 kkal)",
            snackSample = "1 buah apel fuji potong + segelas air kelapa murni (140 kkal)"
        ),
        DietPlanRecommendation(
            id = "high_protein",
            title = "Tinggi Protein (Muscle Gain & Lean)",
            subtitle = "Optimalkan pembentukan otot dan rasa kenyang maksimal",
            targetCalories = "1.800 - 2.200 kkal / hari",
            macroSplit = "35% Karbo | 40% Protein | 25% Lemak",
            description = "Fokus pada asupan protein 1.6 - 2.0 gram per kilogram berat badan untuk melindungi dan membesarkan massa otot selama aktif berolahraga.",
            suitableFor = "Aktif latihan angkat beban, kalistenik, atau ingin mengencangkan tubuh",
            breakfastSample = "Omelet 2 butir telur + 1 lembar roti gandum + susu kedelai murni (320 kkal)",
            lunchSample = "Nasi putih 100g + Dada ayam panggang 150g + Tahu tempe orek + Lalapan timun (490 kkal)",
            dinnerSample = "Soto ayam suwir dada + 1 potong telur rebus + sate taichan 5 tusuk (450 kkal)",
            snackSample = "Whey protein shake atau 1 mangkok edamame rebus (120 kkal)"
        ),
        DietPlanRecommendation(
            id = "low_carb",
            title = "Rendah Karbohidrat (Low Carb Lifestyle)",
            subtitle = "Stabilkan gula darah dan percepat pembakaran lemak",
            targetCalories = "1.400 - 1.700 kkal / hari",
            macroSplit = "15% Karbo | 35% Protein | 50% Lemak Sehat",
            description = "Membatasi nasi, mie, dan gula olahan. Mengganti sumber energi utama dengan lemak sehat (alpukat, kacang-kacangan, telur) dan protein tinggi.",
            suitableFor = "Mengatasi perut buncit, lonjakan gula darah, dan cepat lapar",
            breakfastSample = "2 butir telur mata sapi dimasak minyak zaitun + 1/2 buah alpukat mentega (310 kkal)",
            lunchSample = "Ayam bakar paha atas + Capcay kuah sayuran banyak brokoli + 1 potong tahu goreng (370 kkal)",
            dinnerSample = "Ikan kembung goreng renyah + Gulai daun singkong porsi sayur + Sambal terasi (390 kkal)",
            snackSample = "Kacang almond / edamame rebus 50g (110 kkal)"
        ),
        DietPlanRecommendation(
            id = "clean_eating",
            title = "Clean Eating & Whole Foods",
            subtitle = "Bebas makanan ultra-proses, fokus bahan alami segar",
            targetCalories = "1.600 - 1.900 kkal / hari",
            macroSplit = "50% Karbo Kompleks | 25% Protein | 25% Lemak Nabati",
            description = "Pola makan alami tanpa penyedap berlebih, tanpa sirup fruktosa, dan tanpa makanan beku cepat saji untuk detoksifikasi alami tubuh.",
            suitableFor = "Meningkatkan energi tubuh, kesehatan pencernaan, dan kulit bersih",
            breakfastSample = "Smoothie bowl pisang, buah naga, chia seeds, dan bayam (260 kkal)",
            lunchSample = "Ubi jalar kukus 1 buah + Pepes ikan kakap / nila + Urap sayuran bumbu kelapa (380 kkal)",
            dinnerSample = "Sop ayam jamur kuah kaldu asli + Pepes tahu jamur + Nasi merah 70g (350 kkal)",
            snackSample = "Potongan buah pepaya segar & melon (110 kkal)"
        )
    )

    val weeklyMealPlan: List<DayMealPlan> = listOf(
        DayMealPlan(
            dayName = "Senin",
            breakfast = "2 Telur rebus + 1 Roti gandum bakar + Teh hijau tawar",
            morningSnack = "1 Buah apel fuji",
            lunch = "Nasi merah 100g + Dada ayam bakar 120g + Sayur asem sunda",
            afternoonSnack = "Segelas susu kedelai murni tanpa gula",
            dinner = "Ikan nila panggang kecap + Tumis kangkung + Sambal terasi",
            totalCaloriesEst = 1580
        ),
        DayMealPlan(
            dayName = "Selasa",
            breakfast = "Oatmeal pisang susu low fat + Taburan kayu manis",
            morningSnack = "Potongan buah pepaya 1 mangkok",
            lunch = "Gado-gado lontong sedikit saus kacang + 1 Telur rebus",
            afternoonSnack = "Segenggam edamame rebus",
            dinner = "Soto ayam lamongan kuah bening (tanpa koya minyak) + Tempe bacem",
            totalCaloriesEst = 1520
        ),
        DayMealPlan(
            dayName = "Rabu",
            breakfast = "Telur dadar bayam 2 butir + 1/2 Alpukat",
            morningSnack = "1 Buah jeruk manis segar",
            lunch = "Nasi putih 100g + Sate ayam 5 tusuk + Capcay kuah sayuran",
            afternoonSnack = "Air kelapa muda murni",
            dinner = "Pepes tahu jamur + Ikan lele bakar + Lalapan mentimun selada",
            totalCaloriesEst = 1600
        ),
        DayMealPlan(
            dayName = "Kamis",
            breakfast = "Bubur ayam tanpa kerupuk + Ekstra suwiran ayam & telur",
            morningSnack = "1 Buah pisang cavendish",
            lunch = "Nasi merah 100g + Ayam pop Minang + Sayur bening bayam",
            afternoonSnack = "Yogurt plain rendah gula",
            dinner = "Sup ayam sayuran makaroni sedikit + 2 potong tempe orek",
            totalCaloriesEst = 1550
        ),
        DayMealPlan(
            dayName = "Jumat",
            breakfast = "Roti gandum isi telur mata sapi & irisan tomat selada",
            morningSnack = "Semangka potong dingin 150g",
            lunch = "Karedok Sunda + 1 Potong empal daging sapi empuk",
            afternoonSnack = "Kopi hitam americano + 1 butir kurma",
            dinner = "Ikan kembung goreng sambal matah + Tumis buncis jagung",
            totalCaloriesEst = 1590
        ),
        DayMealPlan(
            dayName = "Sabtu",
            breakfast = "Pancake pisang telur (2 bahan) madu murni 1 sdt",
            morningSnack = "Buah naga merah potong",
            lunch = "Nasi liwet santan ringan 100g + Ayam panggang + Urap sayur",
            afternoonSnack = "Segelas infused water lemon mentimun",
            dinner = "Bakso kuah bening daging sapi (tanpa mie goreng) + Tahu rebus",
            totalCaloriesEst = 1630
        ),
        DayMealPlan(
            dayName = "Minggu (Cheat Meal Fleksibel)",
            breakfast = "Lontong sayur labu telur kuah encer",
            morningSnack = "Smoothie mangga segar",
            lunch = "Nasi Padang pilihan sehat (Rendang + Daun singkong rebus + Sambal hijau)",
            afternoonSnack = "Es teh tawar dingin segar",
            dinner = "Sop buntut bening sayur + Nasi merah porsi kecil",
            totalCaloriesEst = 1750
        )
    )
}
